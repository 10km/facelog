package net.gdface.facelog.device;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.weakref.jmx.com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;
import gu.simplemq.Channel;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisPublisher;
import gu.simplemq.redis.RedisTable;
import net.gdface.facelog.client.CommonConstant;
import net.gdface.facelog.client.NetworkUtil;

/**
 * 设备心跳包redis实现<br>
 * 以{@link #intervalMills}指定的间隔向redis表({@link CommonConstant#TABLE_HEARTBEAT})写入当前设备序列号及报道时间.<br>
 * 如果指定了心跳实时监控通道({@link #setMonitorChannel(String)}),还会向该通道(频道)发布订阅消息, 以便于应用实时显示人员验证信息。
 * 心跳包线程为守护线程无需停止
 * @author guyadong
 *
 */
public class Heartbeat implements CommonConstant{
	private static Heartbeat heartbeat;
	/** 心跳间隔(毫秒) */
	private long intervalMills = TimeUnit.MILLISECONDS.convert(DEFAULT_HEARTBEAT_INTERVAL,TimeUnit.SECONDS);
	private final RedisTable<HeadbeatPackage> table;
	/** MAC 地址 */
	private final String hardwareAddress;
	private final HeartbeatThread heartBeatThread;
	private final HeadbeatPackage heartBeatPackage;
	private final RedisPublisher publisher;
	/** 设备心跳实时监控通道名,如果指定了通道名,
	 * 每次心跳都不仅会向{@link TABLE_HEARTBEAT} 写入心跳报告,还会向该频道发布订阅消息
	 */
	private Channel<HeadbeatPackage> monitorChannel;
	private Heartbeat(byte[] hardwareAddress,int deviceID, JedisPoolLazy pool) {
		checkArgument(null != pool);
		this.heartBeatPackage = new HeadbeatPackage().setDeviceId(deviceID);
		this.publisher = RedisFactory.getPublisher(pool);
		this.hardwareAddress = NetworkUtil.formatMac(validateMac(hardwareAddress), null);
		this.table =  RedisFactory.getTable(TABLE_HEARTBEAT, pool);
		this.table.setExpire(DEFAULT_HEARTBEAT_EXPIRE, TimeUnit.SECONDS);
		this.heartBeatThread = new HeartbeatThread();
		// 设置为守护线程
		this.heartBeatThread.setDaemon(true);
		this.heartBeatThread.setName(TABLE_HEARTBEAT.name);
	}
	/** 心跳包线程 */
	private final class HeartbeatThread extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					heartBeatPackage.setHostAddress(getHostAddress());
					table.set(hardwareAddress,heartBeatPackage, false);
					table.expire(hardwareAddress);
					if(null != monitorChannel){
						publisher.publish(monitorChannel, heartBeatPackage);
					}
					Thread.sleep(intervalMills);
				} catch(InterruptedException e){
					// do nothing
				} catch(RuntimeException e){
					logger.warn(e.getMessage());
				}
			}
		}
	};
	/**
	 * 设置设备心跳包发送间隔
	 * @param time
	 * @param timeUnit
	 * @return
	 */
	public Heartbeat setInterval(long time,TimeUnit timeUnit){
		if(time > 0 ){
			this.intervalMills = TimeUnit.MILLISECONDS.convert(time, timeUnit);
		}
		return this;
	}
	
	/**
	 * 设置设备心跳表中数据过期时间(秒)
	 * @param time
	 * @return
	 */
	public Heartbeat setExpire(long time){
		if(time > 0){
			this.table.setExpire(time, TimeUnit.SECONDS);
		}
		return this;
	}
	/**
	 * 设置设备心跳实时监控通道名<br>
	 * 实时监控通道名不是一个常量，要从服务接口获取,
	 * 参见 {@link net.gdface.facelog.client.IFaceLogClient#getRedisParameters(net.gdface.facelog.client.thrift.Token)}
	 * @param channel
	 * @return
	 */
	public Heartbeat setMonitorChannel(String channel){
		checkArgument(!Strings.isNullOrEmpty(channel),"channel is null or empty");
		this.monitorChannel = new Channel<HeadbeatPackage>(channel){};
		return this;
	}
	/** 
	 * 验证MAC地址有效性
	 * @throws IllegalArgumentException MAC地址无效
	  */
	public static final byte[] validateMac(byte[]mac){
		checkArgument(null != mac && 6 == mac.length ,"MAC address must ");
		return mac;
	}

	/**
	 * 启动设备心跳线程
	 * @param hardwareAddress 网卡物理地址(MAC)
	 * @param deviceID 设备ID
	 * @param pool
	 * @return
	 */
	public static synchronized Heartbeat startHeartbeat(
			byte[] hardwareAddress,
			int deviceID, 
			JedisPoolLazy pool){
		if(null ==heartbeat  || !heartbeat.heartBeatThread.isAlive()){
			heartbeat = new Heartbeat(hardwareAddress,deviceID, pool);
			heartbeat.heartBeatThread.start();
		}
		return heartbeat;
	}
	/** 返回本机ip地址,如果获取IP地址不正确,请重写此方法 */
	protected String getHostAddress(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}
}
