package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;

/**
 * @author guyadong
 *
 */
public class AckMonitor {
	/** 默认检查周期(毫秒) */
	public static long DEFAULT_PERIOD = 20*1000L;
	private final Set<IAckAdapter<?>> ackSubscribers = new HashSet<IAckAdapter<?>>();
	private long periodMills = DEFAULT_PERIOD;
	private final RedisSubscriber subscriber;

	AckMonitor(JedisPoolLazy poolLazy) {
		this.subscriber = RedisFactory.getSubscriber(checkNotNull(poolLazy));
	}

	public boolean add(IAckAdapter<?> adapter) {
		synchronized(this){
			return ackSubscribers.add(checkNotNull(adapter,"adapter is null"));
		}
	}
	/** 清理线程,定期清理超时对象 */
	private Runnable task = new Runnable(){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public void run() {
			long time = System.currentTimeMillis();
			synchronized(AckMonitor.this){
				// 遍历所有对象判断是否有超时的
				for(Iterator<IAckAdapter<?>> itor = ackSubscribers.iterator();itor.hasNext();){
					IAckAdapter adapter = itor.next();
					String channel = adapter.getChannel();
					if(null == subscriber.getChannel(channel)){
						// 未注册频道直接删除
						itor.remove();
						continue;
					}
					if (time >= adapter.getExpire()) {
						// 判断超时则取消订阅
						subscriber.unregister(channel);
						itor.remove();
						try{
							// 通知执行器命令超时
							adapter.onSubscribe(new Ack().setStatus(Ack.Status.TIMEOUT));
						}catch(SmqUnsubscribeException e){								
						}catch(RuntimeException e){
							e.printStackTrace();
						}
					}
				}
			}
		}};

	public AckMonitor startCleaner(ScheduledExecutorService scheduleExecutorService) {
		scheduleExecutorService.scheduleAtFixedRate(task, periodMills, periodMills, TimeUnit.MILLISECONDS);
		return this;
	}

	/**
	 * 设置检查周期(毫秒)
	 * @param periodMills <=0无效
	 * @return
	 */
	public AckMonitor setPeriodMills(long periodMills) {
		if(periodMills > 0){
			this.periodMills = periodMills;
		}
		return this;
	}
	/**
	 * 设置检查周期
	 * @param period <=0无效
	 * @param unit 时间单位
	 * @return
	 * @see #setPeriodMills(long)
	 */
	public AckMonitor setPeriod(long period,TimeUnit unit) {
		if(period > 0){
			return setPeriodMills(TimeUnit.MILLISECONDS.convert(period, checkNotNull(unit,"unit is null")));
		}
		return this;
	}
}
