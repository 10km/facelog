package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.*;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.google.common.base.Predicates;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;

import gu.simplemq.Channel;
import gu.simplemq.IMessageAdapter;
import gu.simplemq.IPublisher;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.RedisPublisher;
import redis.clients.jedis.exceptions.JedisException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;

/**
 * 设备命令分发器,实现{@link IMessageAdapter}接口,将redis操作与业务逻辑隔离<br>
 * 从订阅频道得到设备指令{@link DeviceInstruction},并将交给{@link CommandAdapter}执行<br>
 * 如果是与当前设备无关的命令则跳过
 * @author guyadong
 *
 */
public class CmdDispatcher implements IMessageAdapter<DeviceInstruction>,CommonConstant{
	private final int deviceId;
	private final Supplier<Integer> groupIdSupplier;
	/** 只从{@link JedisPoolLazy}默认实例获取{@link RedisPublisher} */
	private final IPublisher redisPublisher = RedisFactory.getPublisher();
	/** 命令执行器 */
	private CommandAdapter cmdAdapter;
	/** 设备命令通道 */
	private volatile Channel<DeviceInstruction> cmdChannel;
	 /** 设备命令序列号验证器 */
	private Predicate<Long> cmdSnValidator = Predicates.alwaysTrue();
	/** 设备命令响应通道验证器 */
	private Predicate<String> ackChannelValidator = Predicates.alwaysTrue();
	private ExecutorService executor ;
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
	public void onSubscribe(DeviceInstruction t) throws SmqUnsubscribeException {
		// 设备命令序列号有效才执行设备命令
		if(null != cmdAdapter 
				&& null != t.getTarget() 
				&& selfIncluded(t.isGroup(),t.getTarget())){
			long cmdSn = t.getCmdSn();
			if(cmdSnValidator.apply(cmdSn)){
				// 将设备命令交给命令类型对应的方法执行设备命令
				final Ack<?> ack = t.getCmd().run(cmdAdapter, t.getParameters()).setCmdSn(cmdSn);
				// 如果指定了响应频道且频道名有效则向指定的频道发送响应消息
				if(!Strings.isNullOrEmpty(t.getAckChannel())){
					final String ackChannel = t.getAckChannel();
					if(ackChannelValidator.apply(ackChannel)){
						checkNotNull(executor,"please call setExecutor to initialize executor ").execute(new Runnable(){
							@Override
							public void run() {
								Channel<Ack<?>> channel = new Channel<Ack<?>>(ackChannel){};
								try{
									redisPublisher.publish(channel, ack);
								}catch(JedisException e){
									logger.error(e.getMessage());
								}
							}});
					}else{
						logger.warn("INVALID ack channel: {}",ackChannel);
					}
				}
			}else{
				logger.warn("INVALID cmd serial number: {}",cmdSn);
			}
		}
	}
	/**
	 * 设置应用程序执行设备命令的对象<br>
	 * 创建{@link CmdDispatcher}对象时如果不调用本方法,设备不会响应任何设备命令
	 * @param cmdAdapter
	 * @return
	 */
	public CmdDispatcher setCmdAdapter(CommandAdapter cmdAdapter) {
		this.cmdAdapter = cmdAdapter;
		return this;
	}
	
	public CommandAdapter getCmdAdapter() {
		return cmdAdapter;
	}
	/**
	 * 返回{@link CommandAdapterContainer}实例
	 * @return
	 * @throws IllegalStateException {@link #cmdAdapter}实例类型不是{@link CommandAdapterContainer}
	 */
	public CommandAdapterContainer getCommandAdapterContainer() {
		checkState(cmdAdapter instanceof CommandAdapterContainer,"the cmdAdapter is not Container instance");
		return (CommandAdapterContainer) cmdAdapter;
	}
	
	/**
	 * 当前对象注册到指定的频道,重复注册无效
	 * @param channel 设备命令通道名
	 * @return
	 */
	public CmdDispatcher registerChannel(String channel){
		// double check
		if(null == cmdChannel){
			synchronized(this){
				if(null == cmdChannel){
					cmdChannel = new Channel<DeviceInstruction>(checkNotNull(channel),	this){};
					RedisFactory.getSubscriber().register(cmdChannel);
				}
			}
		}
		return this;
	}
	/**
	 * 当前对象注销频道
	 * @return
	 */
	public CmdDispatcher unregisterChannel(){
		// double check
		if(null != cmdChannel){
			synchronized(this){
				if(null != cmdChannel){
					RedisFactory.getSubscriber().unregister(cmdChannel);
					cmdChannel = null;
				}
			}
		}
		return this;
	}
	/**
	 * {@link CommandAdapterContainer#register(Cmd, CommandAdapter)}代理方法<br>
	 * @param cmd
	 * @param adapter
	 * @return
	 * @see #getCommandAdapterContainer()
	 */
	public CmdDispatcher registerAdapter(Cmd cmd,CommandAdapter adapter){
		getCommandAdapterContainer().register(cmd, adapter);		
		return this;
	}
	/**
	 * {@link CommandAdapterContainer#unregister(Cmd)}代理方法<br>
	 * @param cmd
	 * @return
	 * @see #getCommandAdapterContainer()
	 */
	public CmdDispatcher unregisterAdapter(Cmd cmd){
		getCommandAdapterContainer().unregister(cmd);
		return this;
	}
	public CmdDispatcher setCmdSnValidator(Predicate<Long> cmdSnValidator) {
		if(null != cmdSnValidator){
			this.cmdSnValidator = cmdSnValidator;
		}
		return this;
	}
	public CmdDispatcher setAckChannelValidator(Predicate<String> ackChannelValidator) {
		if(null !=ackChannelValidator){
			this.ackChannelValidator = ackChannelValidator;
		}
		return this;
	}
	public CmdDispatcher setExecutor(ExecutorService executor) {
		this.executor = checkNotNull(executor);
		return this;
	}
}