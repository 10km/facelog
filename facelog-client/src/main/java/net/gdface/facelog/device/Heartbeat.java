package net.gdface.facelog.device;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.weakref.jmx.com.google.common.base.Preconditions;

import com.google.common.base.Strings;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisTable;
import net.gdface.facelog.client.CommonConstant;

/**
 * 设备心跳包redis实现<br>
 * 以{@link #intervalMills}指定的间隔向redis表({@link CommonConstant#TABLE_HEARTBEAT})写入当前设备序列号及报道时间.<br>
 * 心跳包线程为守护线程无需停止
 * @author guyadong
 *
 */
public class Heartbeat extends Thread implements CommonConstant{
	private static Heartbeat heartbeat;
	
	/**
	 * 启动设备心跳线程
	 * @param deviceID
	 * @param pool
	 * @return
	 */
	public static synchronized Heartbeat startHeartbeat(String deviceID,JedisPoolLazy pool){
		if(null ==heartbeat  || !heartbeat.isAlive()){
			heartbeat = new Heartbeat(deviceID,pool);
			heartbeat.start();
		}
		return heartbeat;
	}
	/** 心跳间隔 */
	private long intervalMills = TimeUnit.MILLISECONDS.convert(DEFAULT_HEARTBEAT_INTERVAL,TimeUnit.SECONDS);
	private final RedisTable<Date> table;
	private final String deivceID;
	private Heartbeat(String deviceID,JedisPoolLazy pool) {
		super();
		Preconditions.checkArgument(!Strings.isNullOrEmpty(deviceID),"deviceID is null or empty");
		this.deivceID = deviceID;
		this.table =  RedisFactory.getTable(TABLE_HEARTBEAT, pool);
		this.table.setExpire(DEFAULT_HEARTBEAT_EXPIRE, TimeUnit.SECONDS);
		// 设置为守护线程
		this.setDaemon(true);
		this.setName(TABLE_HEARTBEAT.name);
	}

	@Override
	public void run() {
		while(true){
			try {
				// 写入当前时间
				table.set(deivceID, new Date(), false);
				table.expire(deivceID);
				Thread.sleep(intervalMills);
			} catch (Exception e) {}
		}
	}

	public Heartbeat setInterval(long time,TimeUnit timeUnit){
		if(time > 0 )
			this.intervalMills = TimeUnit.MILLISECONDS.convert(time, timeUnit);
		return this;
	}
	
	/**
	 * 设置设备心跳表中数据过期时间
	 * @param time
	 * @return
	 */
	public Heartbeat setExpire(long time){
		if(time > 0)
			this.table.setExpire(time, TimeUnit.SECONDS);
		return this;
	}
}
