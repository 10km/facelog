package net.gdface.facelog.message;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

import redis.clients.jedis.Jedis;

public class RedisSubscriber extends Subcriber implements IRedisComponent {
	private static final Map<JedisPoolLazy,RedisSubscriber>  subscribers = new Hashtable<JedisPoolLazy,RedisSubscriber>();
	
	public static void clearSubscribers(){
		synchronized(RedisSubscriber.class){
			for(RedisSubscriber subscribe:subscribers.values()){
				subscribe.unsubscribe();
			}
			subscribers.clear();
		}
	}
	
	public static RedisSubscriber getSubscriber(JedisPoolLazy jedisPoolLazy) {
		synchronized(RedisSubscriber.class){
			RedisSubscriber pool = subscribers.get(jedisPoolLazy);
			if (null == pool) {
				pool = new RedisSubscriber(jedisPoolLazy).setDaemon(true);
				subscribers.put(jedisPoolLazy, pool);
			}
			return pool;
		}
	}

	private final JedisPoolLazy poolLazy;
	private final SubscriberHandle jedisPubSub; 
	private ExecutorService executorService;
	private boolean daemon=false;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return this.poolLazy;
	}
	
	protected RedisSubscriber(JedisPoolLazy poolLazy) {
		super();
		jedisPubSub=new SubscriberHandle(this); 
		this.poolLazy = poolLazy;
		subscribers.put(poolLazy, this);
	}

	@Override
	protected void _subscribe(String... channels) {
		if(!jedisPubSub.isSubscribed())
			open();
		jedisPubSub.subscribe(channels);		
	}

	@Override
	protected void _unsubscribe(String... channels) {		
		if(jedisPubSub.isSubscribed()) {
			if(null == channels || 0 == channels.length)
				jedisPubSub.unsubscribe();
			else
				jedisPubSub.unsubscribe(channels);
		}
	}
	
	private synchronized void open(){
		if(jedisPubSub.isSubscribed()) return;
		Runnable run = new Runnable(){
			@Override
			public void run() {
				Jedis jedis = poolLazy.apply();
				try{
					jedis.subscribe(jedisPubSub);
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

	public RedisSubscriber setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}

	public RedisSubscriber setDaemon(boolean daemon) {
		this.daemon = daemon;
		return this;
	}

	public RedisSubscriber setOnMessageHandle(IOnMessage onMessageHandle) {
		jedisPubSub.setOnMessageHandle(onMessageHandle);
		return this;
	}	
}
