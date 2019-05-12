package net.gdface.facelog.device;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
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
import net.gdface.facelog.hb.BaseServiceHeartbeatListener;

import static gu.dtalk.engine.DeviceUtils.DEVINFO_PROVIDER;
/**
 * 设备心跳包redis实现<br>
 * 以{@link #intervalMills}指定的周期向redis表({@link ChannelConstant#TABLE_HEARTBEAT})写入当前设备序列号及报道时间.<br>
 * 如果指定了心跳实时监控通道({@link #setMonitorChannel(String)}),还会向该通道(频道)发布订阅消息<br>
 * 调用{@link #start()}心跳开始<br>
 * 应用程序结束时心跳包线程自动停止
 * @author guyadong
 * @deprecated replaced by {@link net.gdface.facelog.hb.DeviceHeartbeat}
 *
 */
public class Heartbeat extends BaseServiceHeartbeatListener implements ChannelConstant{
    public static final Logger logger = LoggerFactory.getLogger(Heartbeat.class);

	/**  单实例 */
	private static Heartbeat heartbeat;
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
	/** 定时报道任务 */
	private final Runnable timerTask = new Runnable(){
		@Override
		public void run() {
			try {
				heartBeatPackage.setHostAddress(DEVINFO_PROVIDER.getIpAsString());
				table.set(hardwareAddress,heartBeatPackage, false);
				table.expire(hardwareAddress);
				Channel<DeviceHeartdbeatPackage> monitorChannel = monitorChannelSupplier.get();
				if(null != monitorChannel){
//					logger.info("send hb ->{}",monitorChannel.name);
					publisher.publish(monitorChannel, heartBeatPackage);
				}				
			} catch (Exception e) {
				logger.info(e.getMessage());
			}

		}};
	/**
	 * 构造方法
	 * @param hardwareAddress 当前设备物理地址(MAC)
	 * @param deviceID 当前设备ID
	 * @param poolLazy redis 连接池对象
	 * @throws NullPointerException {@code poolLazy}为{@code null}
	 * @throws IllegalArgumentException {@code hardwareAddress}无效
	 * @deprecated replaced by {@link #Heartbeat(int, JedisPoolLazy)}
	 */
	private Heartbeat(byte[] hardwareAddress,int deviceID, JedisPoolLazy poolLazy) {
		this(deviceID, poolLazy);
	}
	/**
	 * 构造方法
	 * @param deviceID 当前设备ID
	 * @param jedisPoolLazy redis 连接池对象,为{@code null}使用默认实例
	 * @throws NullPointerException {@code poolLazy}为{@code null}
	 * @throws IllegalArgumentException {@code hardwareAddress}无效
	 */
	private Heartbeat(int deviceID, JedisPoolLazy jedisPoolLazy) {
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
	 * 创建{@link Heartbeat}实例<br>
	 * {@link Heartbeat}为单实例,该方法只能调用一次。
	 * @param hardwareAddress 网卡物理地址(MAC)
	 * @param deviceID 设备ID
	 * @param pool
	 * @return
	 * @throws IllegalStateException 实例已经创建
	 * @deprecated use {@link #makeHeartbeat(int, JedisPoolLazy)}
	 */
	public static synchronized final Heartbeat makeHeartbeat(
			byte[] hardwareAddress,
			int deviceID, 
			JedisPoolLazy pool){
		checkState(null == heartbeat,"singleton instance created");
		heartbeat = new Heartbeat(hardwareAddress,deviceID, pool);
		return heartbeat;
	}
	/**
	 * 创建{@link Heartbeat}实例<br>
	 * {@link Heartbeat}为单实例,该方法只能调用一次。
	 * @param deviceID 设备ID
	 * @param pool
	 * @return
	 * @throws IllegalStateException 实例已经创建
	 */
	public static synchronized final Heartbeat makeHeartbeat(
			int deviceID, 
			JedisPoolLazy pool){
		checkState(null == heartbeat,"singleton instance created");
		heartbeat = new Heartbeat(deviceID, pool);
		return heartbeat;
	}
	/**
	 * 返回已经创建的{@link Heartbeat}实例,如果实例还没有创建则抛出异常
	 * @return
	 * @throws IllegalStateException 实例还没有创建
	 */
	public static final Heartbeat getInstance(){
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
	public Heartbeat setInterval(long period,TimeUnit unit){
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
	public Heartbeat setExpire(long duration, TimeUnit unit){
		if(duration > 0){
			this.table.setExpire(duration, checkNotNull(unit));
		}
		return this;
	}
	/**
	 * 设置设备心跳实时监控通道名<br>
	 * 实时监控通道名不是一个常量，要从服务接口获取,
	 * 参见 {@link net.gdface.facelog.client.IFaceLogClient#getRedisParameters(net.gdface.facelog.client.thrift.Token)}
	 * @param channel 
	 * @return 当前 {@link Heartbeat}实例
	 * @throws IllegalArgumentException {@code channel}为{@code null}或空
	 * @deprecated
	 */
	public Heartbeat setMonitorChannel(String channel){
		checkArgument(!Strings.isNullOrEmpty(channel),"channel is null or empty");
		setMonitorChannelSupplier(Suppliers.ofInstance(channel));
		return this;
	}
	
	private class MonitorChannelSupplier  implements Supplier<Channel<DeviceHeartdbeatPackage>>{
		
		Channel<DeviceHeartdbeatPackage> channel;
		Supplier<String> channelSupplier;
		boolean reload = true;

		@Override
		public Channel<DeviceHeartdbeatPackage> get() {
			if(null == channel || reload){
				if(null != channelSupplier && !Strings.isNullOrEmpty(channelSupplier.get())){
					channel = new Channel<DeviceHeartdbeatPackage>(channelSupplier.get()){};
					reload = false;
				}
			}
			return channel;
		}
		
	}
	/**
	 * 设置提供设备心跳实时监控通道名的{@link Supplier}实例<br>
	 * @param channelSupplier
	 * @return 当前 {@link Heartbeat}实例
	 */
	public Heartbeat setMonitorChannelSupplier(Supplier<String> channelSupplier){
		this.monitorChannelSupplier.channelSupplier = checkNotNull(channelSupplier,"channelSupplier is null");
		return this;
	}
	/** 
	 * 验证MAC地址有效性
	 * @throws IllegalArgumentException MAC地址无效
	 * @deprecated
	  */
	public static final byte[] validateMac(byte[]mac){
		checkArgument(null != mac && 6 == mac.length ,"INVAILD MAC address");
		return mac;
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
}
