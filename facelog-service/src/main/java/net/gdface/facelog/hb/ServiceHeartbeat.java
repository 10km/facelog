package net.gdface.facelog.hb;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.MoreObjects;
import com.google.common.io.ByteStreams;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

import static com.google.common.base.Preconditions.*;
import static net.gdface.utils.NetworkUtil.*;

import gu.simplemq.json.BaseJsonEncoder;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import net.gdface.facelog.ChannelConstant;
import net.gdface.facelog.CommonConstant;
import net.gdface.facelog.ServiceHeartbeatPackage;
/**
 * 服务心跳包redis实现<br>
 * 以{@link #intervalMills}指定的周期向{@link net.gdface.facelog.CommonConstant#FACELOG_HB_CHANNEL}频道发布订阅消息.<br>
 * 调用{@link #start()}心跳开始<br>
 * 应用程序结束时心跳包线程自动停止
 * @author guyadong
 *
 */
public class ServiceHeartbeat implements ChannelConstant{
    private static final Logger logger = LoggerFactory.getLogger(ServiceHeartbeat.class);
    
    /**  单实例 */
	private static ServiceHeartbeat heartbeat;
	/** 心跳周期(毫秒) */
	private long intervalMills = TimeUnit.MILLISECONDS.convert(DEFAULT_HEARTBEAT_PERIOD,TimeUnit.SECONDS);

	private final ServiceHeartbeatPackage heartBeatPackage;
	private final RedisPublisher publisher;
	/** 执行定时任务的线程池对象 */
	private final ScheduledThreadPoolExecutor scheduledExecutor;
	/** {@link #scheduledExecutor}的自动退出封装 */
	private final ScheduledExecutorService timerExecutor;
	private volatile ScheduledFuture<?> future;

	/** 定时报道任务 */
	private final Runnable timerTask = new Runnable(){
		@Override
		public void run() {
			try {
				publisher.publish(SERVICE_HEARTBEAT_CHANNEL, heartBeatPackage);	
			} catch (Exception e) {
				logger.error(e.getMessage());
			}			
		}};
	/** 定时广播任务 */
	private final Runnable timerTask2 = new Runnable(){
		@Override
		public void run() {
			try {
				sendMultiCast(CommonConstant.MULTICAST_ADDRESS, BaseJsonEncoder.getEncoder().toJsonString(heartBeatPackage).getBytes());
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}			
		}};

	private ScheduledFuture<?> futureMc;

	/**
	 * 构造方法
	 * @param serviceID 当前服务ID(确保每次服务启动都不一样)
	 * @param port (FRAMED)服务端口
	 * @param xhrPort (XHR)服务端口
	 * @param poolLazy redis 连接池对象
	 * @throws NullPointerException {@code poolLazy}为{@code null}
	 * @throws IllegalArgumentException {@code hardwareAddress}无效
	 */
	private ServiceHeartbeat(int serviceID, Integer port, Integer xhrPort, JedisPoolLazy poolLazy) {
		this.heartBeatPackage = new ServiceHeartbeatPackage(
				serviceID,
				port,
				xhrPort, 
				hostname());
		this.publisher = RedisFactory.getPublisher(checkNotNull(poolLazy,"pool is null"));
		this.scheduledExecutor =new ScheduledThreadPoolExecutor(1,
				new ThreadFactoryBuilder().setNameFormat("heartbeat-pool-%d").build());	
		this.timerExecutor = MoreExecutors.getExitingScheduledExecutorService(	scheduledExecutor);
	}
	private static String  hostname(){
        try {
        	//获取本机计算机名称
			return InetAddress.getLocalHost().getHostName();
		} catch (IOException e) {
			try {
				byte[] out = ByteStreams.toByteArray(Runtime.getRuntime().exec("hostname").getInputStream());
				return new String(out);
			} catch (IOException e1) {
				e = e1;
			}
			throw new RuntimeException(e);
		}
	}
	/**
	 * 构造方法
	 * @param serviceID 当前服务ID(确保每次服务启动都不一样)
	 * @param port (FRAMED)服务端口
	 * @param xhrPort (XHR)服务端口
	 * @throws NullPointerException {@code poolLazy}为{@code null}
	 * @throws IllegalArgumentException {@code hardwareAddress}无效
	 */
	private ServiceHeartbeat(int serviceID, Integer port, Integer xhrPort) {
		this(serviceID, port, xhrPort, JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 创建{@link ServiceHeartbeat}实例<br>
	 * {@link ServiceHeartbeat}为单实例,该方法只能调用一次。
	 * @param serviceID 设备ID
	 * @param port (FRAMED)服务端口
	 * @param xhrPort (XHR)服务端口
	 * @param pool
	 * @return
	 * @throws IllegalStateException 实例已经创建
	 */
	public static synchronized final ServiceHeartbeat makeHeartbeat(
			int serviceID, 
			Integer port,
			Integer xhrPort,
			JedisPoolLazy pool){
		checkState(null == heartbeat,"singleton instance created");
		heartbeat = new ServiceHeartbeat(serviceID, port,xhrPort,MoreObjects.firstNonNull(pool, JedisPoolLazy.getDefaultInstance()));
		return heartbeat;
	}
	/**
	 * 创建{@link ServiceHeartbeat}实例<br>
	 * {@link ServiceHeartbeat}为单实例,该方法只能调用一次。
	 * @param serviceID 设备ID
	 * @param port (FRAMED)服务端口
	 * @param xhrPort (XHR)服务端口
	 * @return
	 * @throws IllegalStateException 实例已经创建
	 */
	public static synchronized final ServiceHeartbeat makeHeartbeat(
			int serviceID, 
			Integer port,
			Integer xhrPort){
		checkState(null == heartbeat,"singleton instance created");
		heartbeat = new ServiceHeartbeat(serviceID, port,xhrPort);
		return heartbeat;
	}
	/**
	 * 返回已经创建的{@link ServiceHeartbeat}实例,如果实例还没有创建则抛出异常
	 * @return
	 * @throws IllegalStateException 实例还没有创建
	 */
	public static final ServiceHeartbeat getInstance(){
		checkState(null !=heartbeat,"singleton instance be  not yet created,call makeHeartbeat method firstly");
		return heartbeat;
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
		future = this.timerExecutor.scheduleAtFixedRate(timerTask, 0, intervalMills, TimeUnit.MILLISECONDS);
		if(null == futureMc){
			futureMc = this.timerExecutor.scheduleAtFixedRate(timerTask2, 0, 2, TimeUnit.SECONDS);
		}
	}
	/**
	 * 设置设备心跳包发送周期<br>
	 * 设置后须调用{@link #start()}才能生效
	 * @param period 心跳周期(大于0有效)
	 * @param unit
	 * @return
	 */
	public ServiceHeartbeat setInterval(long period,TimeUnit unit){
		if(period > 0 ){
			this.intervalMills = TimeUnit.MILLISECONDS.convert(period, checkNotNull(unit));
		}
		return this;
	}
}
