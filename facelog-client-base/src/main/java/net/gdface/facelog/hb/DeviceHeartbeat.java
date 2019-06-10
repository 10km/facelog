package net.gdface.facelog.hb;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import static com.google.common.base.Preconditions.*;

import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import gu.simplemq.redis.RedisTable;
import net.gdface.facelog.DeviceHeartdbeatPackage;
import net.gdface.facelog.ServiceHeartbeatPackage;
import net.gdface.facelog.client.ChannelConstant;

import static gu.dtalk.engine.DeviceUtils.DEVINFO_PROVIDER;
/**
 * 设备心跳包redis实现<br>
 * 以{@link #intervalMills}指定的周期向redis表({@link ChannelConstant#TABLE_HEARTBEAT})写入当前设备序列号及报道时间.<br>
 * 如果指定了心跳实时监控通道({@link #setMonitorChannel(String)}),还会向该通道(频道)发布订阅消息<br>
 * 调用{@link #start()}心跳开始<br>
 * 应用程序结束时心跳包线程自动停止
 * @author guyadong
 *
 */
public class DeviceHeartbeat extends BaseServiceHeartbeatListener implements ChannelConstant{
    public static final Logger logger = LoggerFactory.getLogger(DeviceHeartbeat.class);

	/**  单实例 */
	private static DeviceHeartbeat heartbeat;
	/** 心跳周期(毫秒) */
	private long intervalMills = TimeUnit.MILLISECONDS.convert(DEFAULT_HEARTBEAT_PERIOD,TimeUnit.SECONDS);
	/** 心跳报告表 */
	private final RedisTable<DeviceHeartdbeatPackage> table;
	/** 
	 * 提供设备心跳实时监控通道名,如果指定了通道名,
	 * 每次心跳都不仅会向{@link TABLE_HEARTBEAT} 写入心跳报告,还会向该频道发布订阅消息
	 */
	private final MonitorChannelSupplier monitorChannelSupplier = new MonitorChannelSupplier();
	/** MAC 地址 */
	private final String hardwareAddress = DEVINFO_PROVIDER.getMacAsString();
	private final DeviceHeartdbeatPackage heartBeatPackage;
	private final RedisPublisher publisher;
	/** 执行定时任务的线程池对象 */
	private final ScheduledThreadPoolExecutor scheduledExecutor;
	/** {@link #scheduledExecutor}的自动退出封装 */
	private final ScheduledExecutorService timerExecutor;
	private ScheduledFuture<?> future;
	/** 附加任务表,执行定时任务发送心跳包时执行附加任务表中的任务对象 */
	private final LinkedHashMap<String,Runnable> additionalTasks = Maps.newLinkedHashMap();
	/** 定时任务 */
	private final Runnable timerTask = new Runnable(){
		@Override
		public void run() {
			try {
				heartBeatPackage.setHostAddress(DEVINFO_PROVIDER.getIpAsString());
				table.set(hardwareAddress,heartBeatPackage, false);
				table.expire(hardwareAddress);
				Channel<DeviceHeartdbeatPackage> monitorChannel = monitorChannelSupplier.get();
				if(null != monitorChannel){
					publisher.publish(monitorChannel, heartBeatPackage);
				}
				synchronized (additionalTasks) {
					for(Iterator<Entry<String, Runnable>> itor = additionalTasks.entrySet().iterator(); itor.hasNext(); ){
						Entry<String, Runnable> entry = itor.next();
						try {
							entry.getValue().run();
						} catch (Exception e) {
							itor.remove();
							logger.info("additionalTask [{}] removed:caused by {}",entry.getKey(),e.getMessage());
						}
					}					
				}

			} catch (Exception e) {
				logger.info(e.getMessage());
			}

		}};
	/**
	 * 构造方法
	 * @param deviceID 当前设备ID
	 * @param jedisPoolLazy redis 连接池对象,为{@code null}使用默认实例
	 * @throws NullPointerException {@code poolLazy}为{@code null}
	 * @throws IllegalArgumentException {@code hardwareAddress}无效
	 */
	private DeviceHeartbeat(int deviceID, JedisPoolLazy jedisPoolLazy) {
		jedisPoolLazy = MoreObjects.firstNonNull(jedisPoolLazy,JedisPoolLazy.getDefaultInstance());
		this.heartBeatPackage = new DeviceHeartdbeatPackage().setDeviceId(deviceID);
		this.publisher = RedisFactory.getPublisher(jedisPoolLazy);
		this.scheduledExecutor =new ScheduledThreadPoolExecutor(1,
				new ThreadFactoryBuilder().setNameFormat("heartbeat-pool-%d").build());	
		this.timerExecutor = MoreExecutors.getExitingScheduledExecutorService(	scheduledExecutor);
		this.table =  RedisFactory.getTable(TABLE_HEARTBEAT, jedisPoolLazy);
		this.table.setExpire(DEFAULT_HEARTBEAT_EXPIRE, TimeUnit.SECONDS);
	}
	/**
	 * 创建{@link DeviceHeartbeat}实例<br>
	 * {@link DeviceHeartbeat}为单实例,该方法只能调用一次。
	 * @param deviceID 设备ID
	 * @param pool
	 * @return
	 * @throws IllegalStateException 实例已经创建
	 */
	public static synchronized final DeviceHeartbeat makeHeartbeat(
			int deviceID, 
			JedisPoolLazy pool){
		checkState(null == heartbeat,"singleton instance created");
		heartbeat = new DeviceHeartbeat(deviceID, pool);
		return heartbeat;
	}
	/**
	 * 返回已经创建的{@link DeviceHeartbeat}实例,如果实例还没有创建则抛出异常
	 * @return
	 * @throws IllegalStateException 实例还没有创建
	 */
	public static final DeviceHeartbeat getInstance(){
		checkState(null !=heartbeat,"singleton instance be  not yet created,call makeHeartbeat method firstly");
		return heartbeat;
	}

	/**
	 * 设置设备心跳包发送周期<br>
	 * 设置后须调用{@link #start()}才能生效
	 * @param period 心跳周期(大于0有效)
	 * @param unit
	 * @return
	 */
	public DeviceHeartbeat setInterval(long period,TimeUnit unit){
		if(period > 0 ){
			this.intervalMills = TimeUnit.MILLISECONDS.convert(period, checkNotNull(unit));
		}
		return this;
	}
	
	/**
	 * 设置设备心跳表中数据过期时间
	 * @param duration
	 * @param unit 时间单位
	 * @return
	 */
	public DeviceHeartbeat setExpire(long duration, TimeUnit unit){
		if(duration > 0){
			this.table.setExpire(duration, checkNotNull(unit));
		}
		return this;
	}
	/**
	 * 设置设备当前工作状态
	 * @param status
	 * @return
	 * @see net.gdface.facelog.DeviceHeartdbeatPackage#setStatus(int)
	 */
	public DeviceHeartbeat setStatus(int status) {
		heartBeatPackage.setStatus(status);
		return this;
	}
	/**
	 * @return 返回设备当前工作状态
	 * @see net.gdface.facelog.DeviceHeartdbeatPackage#getStatus()
	 */
	public int getStatus() {
		return heartBeatPackage.getStatus();
	}
	private class MonitorChannelSupplier  implements Supplier<Channel<DeviceHeartdbeatPackage>>{
		
		Channel<DeviceHeartdbeatPackage> channel;
		Supplier<String> channelSupplier;
		boolean reload = true;
		String hbChannel = null;
		@Override
		public Channel<DeviceHeartdbeatPackage> get() {
			if(null == channel || reload){
				if(null != channelSupplier){
					String n = channelSupplier.get();
					if(!Strings.isNullOrEmpty(n)){
						// 通道名变化了则输出日志
						if(!Objects.equal(hbChannel, n)){							
							logger.info("Device Heartbeat channel changed:{}->{}",hbChannel,n);
							hbChannel = n;
							channel = new Channel<DeviceHeartdbeatPackage>(hbChannel){};
							reload = false;
						}
					}
				}
			}
			return channel;
		}
		
	}
	/**
	 * 设置提供设备心跳实时监控通道名的{@link Supplier}实例<br>
	 * @param channelSupplier
	 * @return 当前 {@link DeviceHeartbeat}实例
	 */
	public DeviceHeartbeat setMonitorChannelSupplier(Supplier<String> channelSupplier){
		this.monitorChannelSupplier.channelSupplier = checkNotNull(channelSupplier,"channelSupplier is null");
		return this;
	}
	/**
	 * 用指定的心跳周期参数({@link #intervalMills})启动心跳包报告定时任务<p>
	 * 如果定时任务已经启动则先取消当前的定时任务再启动一个新的定时任务,确保只有一个定时任务在执行
	 */
	public synchronized void start(){
		if(null != future){
			this.scheduledExecutor.remove((Runnable) future);
		}
		/** 返回 RunnableScheduledFuture<?>实例  */
		future = this.timerExecutor.scheduleAtFixedRate(timerTask, intervalMills, intervalMills, TimeUnit.MILLISECONDS);
	}
	@Override
	protected boolean doServiceOnline(ServiceHeartbeatPackage heartbeatPackage) {
		monitorChannelSupplier.reload = true;
		return true;
	}
	
	/**
	 * 添加附加任务<br>
	 * 附加任务在执行时抛出异常则自动被移除不再执行
	 * @param name 任务名,不可为{@code null},如果同名任务已经存在则覆盖
	 * @param task 任务对象,不可为{@code null}
	 * @return 返回{@code name}之前关联的任务对象，如果{@code name}为新加入任务名则返回{@code null}
	 * @see {@link LinkedHashMap#put(Object, Object)}
	 */
	public Runnable addAdditionalTask(String name,Runnable task) {
		checkArgument(null != name,"name is null");
		checkArgument(null != task,"task is null");
		synchronized (additionalTasks) {
			return additionalTasks.put(name,task);
		}
	}

	/**
	 * 删除附加任务
	 * @param task
	 */
	public void removeAdditionalTask(Runnable task) {
		synchronized (additionalTasks) {
			if(null != task){
				for(Iterator<Entry<String, Runnable>> itor = additionalTasks.entrySet().iterator();itor.hasNext();){
					Entry<String, Runnable> entry = itor.next();
					if(task == entry.getValue()){
						itor.remove();
					}
				}
			}
		}
	}
	/**
	 * 删除{@code name}指定附加任务
	 * @param name 任务名
	 */
	public void removeAdditionalTask(String name) {
		if(null != name){
			synchronized (additionalTasks) {
				additionalTasks.remove(name);	
			}
		}
	}
	/**
	 * 清除所有附加任务
	 */
	public void clear() {
		synchronized (additionalTasks) {
			additionalTasks.clear();
		}
	}
}
