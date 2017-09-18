package net.gdface.facelog.message;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

/**
 * {@link AbstractConsumer}消费者模型实现,支持多个list阻塞式读取(blpop, brpop)<br>
 * 执行 {@link #subscribe(String...)}方法时会自动开启消费线程
 * @author guyadong
 *
 * @param <T>
 */
public class RedisConsumer extends AbstractConsumer implements IRedisComponent,ISubscriber {
	/**
	 * 保存每个 {@link JedisPoolLazy}对应的实例
	 */
	private static final Map<JedisPoolLazy,RedisConsumer>  consumers = new Hashtable<JedisPoolLazy,RedisConsumer>();
	
	/**
	 * 删除所有{@link RedisSubscriber}对象
	 */
	public static void clearSubscribers(){
		synchronized(RedisConsumer.class){
			for(RedisConsumer subscribe:consumers.values()){
				subscribe.unsubscribe();
			}
			consumers.clear();
		}
	}
	
	/**
	 * 返回 {@link JedisPoolLazy}对应的实例,如果{@link #consumers}没有找到，就创建一个新实例并加入{@link #consumers}
	 * @param jedisPoolLazy
	 * @return 
	 */
	public static RedisConsumer getSubscriber(JedisPoolLazy jedisPoolLazy) {
		synchronized(RedisConsumer.class){
			RedisConsumer pool = consumers.get(jedisPoolLazy);
			if (null == pool) {
				pool = new RedisConsumer(jedisPoolLazy);
				pool.setDaemon(true);
				consumers.put(jedisPoolLazy, pool);
			}
			return pool;
		}
	}
	
	private final JedisPoolLazy poolLazy;
	private final ChannelDispatcher register=new ChannelDispatcher();
	/** 以秒为单位的超时参数 */
	private int timeout; 
	@Override
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}

	protected RedisConsumer(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
		this.setTimeoutMills(DEFAULT_CONSUMER_CHECK_INTERVAL);
	}
	private final Runnable customRunnable = new Runnable(){
		@Override
		public void run() {
			try {
				List<String> list;
				Jedis jedis = poolLazy.apply();
				try{
					String[] keys =register.getSubscribes();
					// 订阅频道为0时关闭线程
					if(0 == keys.length)close();
					if(isFifo){
						list = jedis.blpop(timeout, keys);
					}else{
						list = jedis.brpop(timeout, keys);
					}
				}finally{
					poolLazy.free();
				}
				if(!list.isEmpty()){
					String channel = list.get(0);
					String message = list.get(1);
					register.onMessage(channel, message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	protected Runnable getCustomRunnable() {
		return customRunnable;
	}

	@SuppressWarnings("rawtypes")
	public Set<Channel> register(Channel... channels) {
		Set<Channel> chSet = register.register(channels);
		this.open();
		return chSet;
	}
	
	@Override
	public Set<String> unregister(String... channels) {
		return register.unregister(channels);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Set<String> unregister(Channel... channels) {
		return register.unregister(channels);
	}

	@Override
	public void subscribe(String... channels) {
		this.register.subscribe(channels);
		this.open();
	}

	@Override
	public void unsubscribe(String... channels) {
		this.register.unsubscribe(channels);
	}

	@SuppressWarnings("rawtypes")
	public Channel getChannelSub(String channel) {
		return register.getChannelSub(channel);
	}

	@Override
	public String[] getSubscribes() {
		return register.getSubscribes();
	}

	@Override
	public AbstractConsumer setTimeoutMills(int timeoutMills) {
		super.setTimeoutMills(timeoutMills);
		this.timeout = (int) TimeUnit.SECONDS.convert(this.timeoutMills, TimeUnit.MILLISECONDS);
		return this;
	}
}
