package net.gdface.facelog.message;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;

/**
 * {@link AbstractConsumer}消费者模型实现,支持多个list阻塞式读取(blpop, brpop)
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
	private final ChannelRegister register=new ChannelRegister();
	private final Set<String> channelSub=Collections.synchronizedSet(new LinkedHashSet<String>());
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

	@Override
	protected boolean needOpen() {
		return register.channelSubs.size()>0;
	}

	@Override
	protected Runnable getRunnable() {
		return new Runnable(){
			@Override
			public void run() {
				while (!isClosed()) {
					try {
						List<String> list;
						Jedis jedis = poolLazy.apply();
						try{
							String[] keys = channelSub.toArray(new String[channelSub.size()]);
							if(0 == keys.length)break;
							if(isFifo){
								list = jedis.blpop(timeout, keys);
							}else{
								list = jedis.brpop(timeout, keys);
							}
						}finally{
							poolLazy.free();
						}
						if(!list.isEmpty()){
							register.onMessage(list.get(0), list.get(1));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	@SuppressWarnings("rawtypes")
	public Set<ChannelSub> register(ChannelSub... channels) {
		Set<ChannelSub> chSet = register.register(channels);
		subscribe(ChannelRegister.getChannelNames(chSet).toArray(new String[chSet.size()]));
		this.open();
		return chSet;
	}
	
	@Override
	public Set<String> unregister(String... channels) {
		Set<String> chSet = register.unregister(channels);
		unsubscribe(chSet.toArray(new String[chSet.size()]));
		return chSet;
	}
	
	@Override
	public Set<String> unregister(Channel... channels) {
		return register.unregister(channels);
	}

	@Override
	public void subscribe(String... channels) {
		this.channelSub.addAll(this.register.registedOnlyAsSet(channels));		
	}

	@Override
	public void unsubscribe(String... channels) {
		this.channelSub.removeAll(CommonUtils.cleanEmptyAsList(channels));		
	}

	@SuppressWarnings("rawtypes")
	public ChannelSub getChannelSub(String channel) {
		return register.getChannelSub(channel);
	}

	@Override
	public AbstractConsumer setTimeoutMills(int timeoutMills) {
		super.setTimeoutMills(timeoutMills);
		this.timeout = (int) TimeUnit.SECONDS.convert(this.timeoutMills, TimeUnit.MILLISECONDS);
		return this;
	}
}
