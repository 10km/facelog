package net.gdface.facelog.message;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import net.gdface.facelog.message.JedisPoolLazy.PropName;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Protocol;

public class RedisSubscriber extends Subcriber implements IRedisComponent {
	private final JedisPoolLazy poolLazy;
	private final JedisPubSub jedisPubSub; 
	private ExecutorService executorService;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return this.poolLazy;
	}
	
	public RedisSubscriber(JedisPoolLazy poolLazy) {
		super();
		jedisPubSub=new SubscriberHandle(this); 
		this.poolLazy = poolLazy;
		open();
	}
	
	public RedisSubscriber(Map<PropName, Object> props) {
		this(JedisPoolLazy.getInstance(props, true));
	}

	public RedisSubscriber(String host,int port,final String password, int database) {
		this(JedisPoolLazy.DEFAULT_CONFIG, host, port, password, database, Protocol.DEFAULT_TIMEOUT);
	}

	public RedisSubscriber(JedisPoolConfig jedisPoolConfig,URI uri, int timeout) {
		this(JedisPoolLazy.getInstance(jedisPoolConfig, uri, timeout));
	}

	public RedisSubscriber(JedisPoolConfig jedisPoolConfig, String host, int port, final String password, int database,
			int timeout) {
		this(JedisPoolLazy.getInstance(jedisPoolConfig, host, port, password, database, timeout));
	}

	@Override
	protected void subscribe(String... channels) {
		jedisPubSub.subscribe(channels);
		open();
	}

	@Override
	protected void unsubscribe(String... channels) {		
		if(jedisPubSub.isSubscribed()) {
			if(null == channels || 0 == channels.length)
				jedisPubSub.unsubscribe();
			else
				jedisPubSub.unsubscribe(channels);
		}
	}
	
	private synchronized void open(){
		if(jedisPubSub.isSubscribed())return;		
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
		if(null != executorService)
			executorService.submit(run);
		else
			new Thread(run).start();
	}

	public RedisSubscriber setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}	
}
