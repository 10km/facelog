package net.gdface.facelog.device;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisTable;
import net.gdface.facelog.client.CommonConstant;
import net.gdface.facelog.client.NetworkUtil;

/**
 * 设备心跳包redis实现<br>
 * 以{@link #intervalMills}指定的间隔向redis表({@link CommonConstant#TABLE_HEARTBEAT})写入当前设备序列号及报道时间.<br>
 * 心跳包线程为守护线程无需停止
 * @author guyadong
 *
 */
public class Heartbeat implements CommonConstant{
	private static Heartbeat heartbeat;
	/** 心跳间隔 */
	private long intervalMills = TimeUnit.MILLISECONDS.convert(DEFAULT_HEARTBEAT_INTERVAL,TimeUnit.SECONDS);
	private final RedisTable<Integer> table;
	private final int deivceID;
	/** MAC 地址 */
	private final String hardwareAddress;
	private final HeartbeatThread heartBeatThread;
	private Heartbeat(byte[] hardwareAddress,int deviceID, JedisPoolLazy pool) {
		super();
		this.deivceID = deviceID;
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
					// 写入当前时间
					table.set(hardwareAddress,deivceID, false);
					table.expire(hardwareAddress);
					Thread.sleep(intervalMills);
				} catch(InterruptedException e){
					// do nothing
				} catch(RuntimeException e){
					logger.warn(e.getMessage());
				}
			}
		}
	};
	public Heartbeat setInterval(long time,TimeUnit timeUnit){
		if(time > 0 ){
			this.intervalMills = TimeUnit.MILLISECONDS.convert(time, timeUnit);
		}
		return this;
	}
	
	/**
	 * 设置设备心跳表中数据过期时间
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
	 * 验证MAC地址有效性
	 * @throws IllegalArgumentException MAC地址无效
	  */
	public static final byte[] validateMac(byte[]mac){
		Preconditions.checkArgument(null != mac && 6 == mac.length ,"MAC address must ");
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
}
