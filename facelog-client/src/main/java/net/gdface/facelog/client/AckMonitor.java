package net.gdface.facelog.client;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Predicate;
import gu.simplemq.Channel;
import gu.simplemq.exceptions.SmqUnsubscribeException;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.RedisFactory;
import gu.simplemq.redis.RedisSubscriber;

/**
 * 设备命令响应对象监控类<br>
 * 处理命令响应的{@link IAckAdapter}对象只是一个被动接收订阅消息的处理器,
 * 设备命令发送后,发送端并不一定能保证收到所有命令接收端的响应{@link Ack},
 * 如果没有外部协助，{@link IAckAdapter}对象无法主动知道自己已经等待超时。
 * 此类的作用就是定时检查在{@link RedisSubscriber}注册的每个{@link IAckAdapter}对象是否已经超时，
 * 如果超时，就将这个{@link IAckAdapter}对象从{@link RedisSubscriber}注销，
 * 并向这个对象发送带有超时错误的{@code Ack}。参见 {@link #timeoutCleaner}的处理逻辑。
 * {@link AckMonitor}不提供公开的构造函数，对于每一个{@link JedisPoolLazy}只会有一个{@link AckMonitor}实例。
 * 通过调用 {@link AckMonitor#getInstance(JedisPoolLazy)}获取对应的实例。
 * 应用项目获取 {@link AckMonitor}实例后需要调用 {@link #startCleaner(ScheduledExecutorService)}方法启动任务线程后，
 * {@link AckMonitor}实例才开始工作。
 * 
 * @author guyadong
 *
 */
public class AckMonitor {
	
	private static final ConcurrentHashMap<JedisPoolLazy, AckMonitor> MONITOR_INSTANCE = 
			new ConcurrentHashMap<JedisPoolLazy, AckMonitor>();
    /**
     * 获取{@code poolLazy}对应的{@code AckMonitor}实例
     * @param poolLazy 不可为{@code null}
     * @return
     */
    public AckMonitor getInstance(JedisPoolLazy poolLazy){
    	if(!MONITOR_INSTANCE.containsKey(checkNotNull(poolLazy))){
    		MONITOR_INSTANCE.putIfAbsent(poolLazy, new AckMonitor(poolLazy));
    	}
    	return MONITOR_INSTANCE.get(poolLazy);
    }
	/** 默认检查周期(毫秒) */
	public static long DEFAULT_PERIOD = 20*1000L;
	private long periodMills = DEFAULT_PERIOD;
	private final RedisSubscriber subscriber;

	protected AckMonitor(JedisPoolLazy poolLazy) {
		this.subscriber = RedisFactory.getSubscriber(checkNotNull(poolLazy));
	}
	/**
	 * {@link Predicate}实例,用于过滤所有超时等待的{@link Ack}对象<br>
	 * 检查正在订阅频道的{@link Ack}对象是否等待超时<br>
	 * 如果超时则注销频道,同时向{@link Ack}对象发送超时错误{@link Ack.Status#TIMEOUT}
	 */
	private final Predicate<Channel<?>> timeoutCleaner = new Predicate<Channel<?>>(){
		@Override
		public boolean apply(Channel<?> input) {
			if(input.getAdapter() instanceof IAckAdapter){
				@SuppressWarnings("unchecked")
				IAckAdapter<Object> adapter = (IAckAdapter<Object>)input.getAdapter();
				if (System.currentTimeMillis() >= adapter.getExpire()) {
					try{
						// 通知执行器命令超时
						adapter.onSubscribe(new Ack<Object>().setStatus(Ack.Status.TIMEOUT));
					}catch(SmqUnsubscribeException e){								
					}catch(RuntimeException e){
						e.printStackTrace();
					}
					// 注销当前频道
					return true;
				}
			}
			return false;
		}};
	/**
	 * 定时任务对象执行,对注册的{@link Ack}进行超时检查并清理
	 * @see #timeoutCleaner
	 * @see RedisSubscriber#unregister(Predicate)
	 */
	private final Runnable timerTask = new Runnable(){
		@Override
		public void run() {
			subscriber.unregister(timeoutCleaner);
		}};
	/**
	 * 启动定期清除超时响应任务,参见{@link #timerTask}
	 * @param scheduleExecutorService 执行定时任务的线程池对象
	 * @return
	 * @see ScheduledExecutorService#scheduleAtFixedRate(Runnable, long, long, TimeUnit)
	 * @return
	 */
	public ScheduledFuture<?> startCleaner(ScheduledExecutorService scheduleExecutorService) {
		return scheduleExecutorService.scheduleAtFixedRate(
				timerTask, 
				periodMills,
				periodMills,
				TimeUnit.MILLISECONDS);
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

	/**
	 * 返回定时任务对象,如果不希望用{@link #startCleaner(ScheduledExecutorService)}方法开启定时任务,
	 * 应用项目可以获取此方法自己实现定时任务
	 * @return
	 */
	public Runnable getTimerTask() {
		return timerTask;
	}
}
