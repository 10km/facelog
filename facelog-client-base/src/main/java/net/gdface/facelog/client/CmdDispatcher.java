package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicates;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import gu.dtalk.MenuItem;
import gu.dtalk.RootMenu;
import gu.dtalk.exception.CmdExecutionException;
import gu.dtalk.exception.UnsupportCmdException;
import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.IPublisher;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.CommonConstant;
import redis.clients.jedis.exceptions.JedisException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;

/**
 * 设备命令分发器,实现{@link IMessageAdapter}接口,将redis操作与业务逻辑隔离<br>
 * 从订阅频道得到设备指令{@link DeviceInstruction},并将交给{@link CommandAdapter}执行<br>
 * 如果是与当前设备无关的命令则跳过<br>
 * 收到的设备命令将按收到命令的在线程池中顺序执行
 * @author guyadong
 *
 */
public class CmdDispatcher implements IMessageAdapter<DeviceInstruction>,CommonConstant{
    public static final Logger logger = LoggerFactory.getLogger(CmdDispatcher.class);

	private final int deviceId;
	private final Supplier<Integer> groupIdSupplier;
	/** 只从{@link JedisPoolLazy}默认实例获取{@link RedisPublisher} */
	private final IPublisher redisPublisher = RedisFactory.getPublisher();
	/**  是否自动注销标志 */
	private final AtomicBoolean autoUnregisterCmdChannel = new AtomicBoolean(false);
	/** 固定为1的线程池，确保所有的命令按收到的顺序执行 */
	private static final ExecutorService executor = MoreExecutors.getExitingExecutorService(
			new ThreadPoolExecutor(1, 1,
	                0L, TimeUnit.MILLISECONDS,
	                new LinkedBlockingQueue<Runnable>(),
	                new ThreadFactoryBuilder().setNameFormat("cmd-dispatcher-%d").build()));
	/** 设备命令通道 */
	private volatile Channel<DeviceInstruction> cmdChannel;
	 /** 设备命令序列号验证器 */
	private Predicate<Long> cmdSnValidator = Predicates.alwaysTrue();
	/** 设备命令响应通道验证器 */
	private Predicate<String> ackChannelValidator = Predicates.alwaysTrue();
	private MenuItem rootMenu = new RootMenu();
	private Supplier<MenuItem> rootSupplier = new Supplier<MenuItem>(){
		@Override
		public MenuItem get() {
			return rootMenu;
		}		
	};
	/**
	 * 构造方法<br>
	 *  设备所属的组可能是可以变化的,所以这里需要用{@code Supplier} 接口来动态获取当前设备的设备组
	 * @param deviceId 当前设备ID,应用项目应确保ID是有效的
	 * @param groupIdSupplier 用于提供{@code deviceId}所属的设备组,
	 * 参见默认实现: {@link IFaceLogClient#getDeviceGroupIdSupplier(int)},为{@code null}不响应设备组命令
	 */
	public CmdDispatcher(int deviceId, 
			Supplier<Integer> groupIdSupplier) {
		this.deviceId= deviceId;
		this.groupIdSupplier = groupIdSupplier;
	}
	/** 判断target列表是否包括当前设备 */
	private boolean selfIncluded(boolean group,List<Integer> target){
		if(group){
			if(null == groupIdSupplier){
				return false;
			}
			Integer groupId = groupIdSupplier.get();
			return null == groupId ? false : target.contains(groupId); 
		}
		else {
			return target.contains(this.deviceId);
		}
	}
	/**
	 * 执行指定的设备命令并向命令响应频道返回命令结果
	 */
	@Override
	public void onSubscribe(final DeviceInstruction t) {
		// 设备命令序列号有效才执行设备命令
		if(null != t.getTarget() && selfIncluded(t.isGroup(),t.getTarget())){
			final long cmdSn = t.getCmdSn();
			if(cmdSnValidator.apply(cmdSn)){
				// 设备命令提交线程池执行
				executor.execute(new Runnable(){
					@Override
					public void run() {
						Ack<Object> ack = new Ack<Object>().setStatus(Ack.Status.OK);
						// 将设备命令交给命令类型对应的方法执行设备命令
						try {
							ack.setValue(getRootMenu().runCmd(t.getCmdpath(),t.getParameters()));
						} catch(UnsupportCmdException e){
		                    // 该命令设备端未实现
		                    ack.setStatus(Ack.Status.UNSUPPORTED);
		                }catch(CmdExecutionException e){
		                    // 填入异常状态,设置错误信息
		                    ack.setStatus(Ack.Status.ERROR).setStatusMessage(e.getMessage());
		                } 
						ack.setCmdSn(cmdSn);
						// 如果指定了响应频道且频道名有效则向指定的频道发送响应消息
						if(!Strings.isNullOrEmpty(t.getAckChannel())){
							String ackChannel = t.getAckChannel();
							if(ackChannelValidator.apply(ackChannel)){
								Channel<Ack<?>> channel = new Channel<Ack<?>>(ackChannel){};
								try{
									redisPublisher.publish(channel, ack);
								}catch(JedisException e){
									logger.error(e.getMessage());
								}
							}else{
								logger.warn("INVALID ack channel: {}",ackChannel);
							}
						}
					}});
			}else{
				logger.warn("INVALID cmd serial number: {}",cmdSn);
			}
		}
	}
	/**
	 * 当前对象注册到指定的频道,重复注册无效
	 * @param channel 设备命令通道名
	 * @return
	 */
	public CmdDispatcher registerChannel(String channel){
		checkArgument(!Strings.isNullOrEmpty(channel),"channel is null or empty");
		// double checked lock
		if(null == cmdChannel){
			synchronized(this){
				if(null == cmdChannel){
					cmdChannel = new Channel<DeviceInstruction>(channel,this){};
					RedisFactory.getSubscriber().register(cmdChannel);
				}
			}
		}
		return this;
	}
	/**
	 * 当前对象注销设备命令频道
	 * @return
	 */
	public CmdDispatcher unregisterChannel(){
		// double check
		if(null != cmdChannel){
			synchronized(this){
				if(null != cmdChannel){
					RedisFactory.getSubscriber().unregister(cmdChannel);
					logger.debug("unregister cmd channel {}",cmdChannel);
					cmdChannel = null;
				}
			}
		}
		return this;
	}
	/**
	 * 设置命令序列号验证器
	 * @param cmdSnValidator 为{@code null}无效
	 * @return
	 */
	public CmdDispatcher setCmdSnValidator(Predicate<Long> cmdSnValidator) {
		if(null != cmdSnValidator){
			this.cmdSnValidator = cmdSnValidator;
		}
		return this;
	}
	/**
	 * 设备命令响应通道验证器
	 * @param ackChannelValidator 为{@code null}无效
	 * @return
	 */
	public CmdDispatcher setAckChannelValidator(Predicate<String> ackChannelValidator) {
		if(null !=ackChannelValidator){
			this.ackChannelValidator = ackChannelValidator;
		}
		return this;
	}
	/**
	 * 设置程序退出时自动执行{@link #unregisterChannel()}
	 * @return
	 */
	public CmdDispatcher autoUnregisterChannel(){
		if(this.autoUnregisterCmdChannel.compareAndSet(false, true)){
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					unregisterChannel();
				}
			});
		}
		return this;
	}
	private MenuItem getRootMenu() {
		if(rootSupplier.get() != null){
			return rootSupplier.get();
		}
		return rootMenu;
	}
	public CmdDispatcher setRootMenu(MenuItem rootMenu) {
		if(null != rootMenu){
			this.rootMenu = rootMenu;
		}
		return this;
	}
	public CmdDispatcher setRootSupplier(Supplier<MenuItem> rootSupplier) {
		if(null != rootSupplier){
			this.rootSupplier = rootSupplier;
		}
		return this;
	}
}