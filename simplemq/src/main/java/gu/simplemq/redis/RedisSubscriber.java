package gu.simplemq.redis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

import gu.simplemq.AbstractSubcriber;
import gu.simplemq.IMessageDispatcher;
import gu.simplemq.exceptions.SmqTypeException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * {@link AbstractSubcriber}的redis 实现<br>
 * 每个 {@link JedisPoolLazy} 实例保持一个 RedisSubscriber 对象<br>
 * 对象可以复用(反复打开关闭) <br>
 * 应用程序结束时要调用 {@link #close()} 取消所有订阅频道才能结束消息线程<br>
 * 当设置为{@link #daemon}为true时,无需{@link #close()}关闭
 * @author guyadong
 *
 */
public class RedisSubscriber extends AbstractSubcriber implements IRedisComponent {

	private final JedisPoolLazy poolLazy;
	private final RedisSubHandle jedisPubSub; 
	/** 执行消息线程的线程池对象 */
	private ExecutorService executorService;
	/** 为true时消息线程为守护线程，仅在{@link executorService}为null 时有效 */
	private boolean daemon=false;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return this.poolLazy;
	}
	
	RedisSubscriber(JedisPoolLazy poolLazy) {
		super();
		this.jedisPubSub=new RedisSubHandle(this); 
		this.poolLazy = poolLazy;
	}

	@Override
	protected void doSubscribe(String... channels) {
		try{
			jedisPubSub.subscribe(channels);
		}catch(JedisConnectionException e){
			open(channels);
		}
	}

	@Override
	protected void doUnsubscribe(String... channels) {
		if(jedisPubSub.isSubscribed()) {
			jedisPubSub.unsubscribe(channels);
		}
	}
	
	/**
	 * 创建消息线程,如果指定了{@link #executorService} ，则消息线程在线程池中执行<br>
	 * 否则创建新线程
	 * @param channels 频道名列表
	 */
	private void open(final String... channels){
		Runnable run = new Runnable(){
			@Override
			public void run() {
				Jedis jedis = poolLazy.apply();
				try{
					jedis.subscribe(jedisPubSub,channels);
				} catch (Exception e) {
	                logger.error("Subscribing failed.", e);
	            }finally{
					poolLazy.free();
				}
			}};
			
		if( !this.daemon && null != executorService){
			try{
				executorService.submit(run);
				return;
			}catch(RejectedExecutionException e){
				executorService = null;
				logger.warn("RejectedExecutionException: {}",e.getMessage());
			}
		}
		Thread thread=new Thread(run);
		thread.setDaemon(this.daemon);
		thread.start();
	}

	/**
	 * 设置用于执行消息线程的线程池
	 * @param executorService
	 * @return
	 */
	public RedisSubscriber setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}

	/**
	 * @see #daemon
	 */
	public RedisSubscriber setDaemon(boolean daemon) {
		this.daemon = daemon;
		return this;
	}

	public RedisSubscriber setDispatcher(IMessageDispatcher dispatcher) {
		jedisPubSub.setDispatcher(dispatcher);
		return this;
	}

	@Override
	protected String check(String name) throws SmqTypeException {
		return RedisComponentType.Channel.check(poolLazy, name);
	}
	
}
