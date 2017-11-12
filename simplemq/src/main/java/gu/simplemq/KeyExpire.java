package gu.simplemq;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 过期时间管理类 
 * @author guyadong
 *
 */
public class KeyExpire {
	/** 默认过期时间 */
	protected long timeMills=0;
	/** 过期时间是否为时间戳 */
	protected boolean timestamp=false;
	public KeyExpire() {
	}
	public final boolean hasExpire(){
		return 0 != timeMills; 
	}
	
	/**
	 * 设置过期时间
	 * @param time
	 * @param timeUnit
	 */
	public final void setExpire(long time,TimeUnit timeUnit){
		setExpire(false,TimeUnit.MILLISECONDS.convert(time, timeUnit));
	}
	/**
	 * 设置过期时间戳
	 * @param date
	 */
	public final void setExpire(Date date){
		setExpire(true,date.getTime());
	}
	/**
	 * 设置过期时间
	 * @param timestamp 是否为时间戳
	 * @param timeMills 过期时间
	 */
	public final void setExpire(boolean timestamp,long timeMills){
		this.timeMills = timeMills;
		this.timestamp = timestamp;
	}
	public final void expire(String key,long timeMills,boolean timestamp){
		if(timeMills>0){
			doExpire(key,timeMills,timestamp);
		}
	}
	public final void expire(String key,long time,TimeUnit timeUnit){
		expire(key,TimeUnit.MILLISECONDS.convert(time, timeUnit),false);
	}
	public final void expire(String key,Date date){
		expire(key,date.getTime(),true);
	}
	public final void expire(String key){
		expire(key,timeMills,timestamp);
	}
	/** 子类实现 */
	protected void doExpire(String key,long timeMills,boolean timestamp){}
}
