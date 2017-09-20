package gu.simplemq.redis;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import gu.simplemq.IProducer;
import gu.simplemq.IPublisher;

public class RedisFactory {

	private RedisFactory() {}
	@SuppressWarnings("rawtypes")
	private static final ConcurrentMap<JedisPoolLazy,RedisTable> tables = new ConcurrentHashMap<JedisPoolLazy,RedisTable>();

	public static<V> RedisTable<V> getTable(Class<V> clazz){
		return getTable((Type)clazz,JedisPoolLazy.getDefaultInstance(),null);
	}
	public static<V> RedisTable<V> getTable(Class<V> clazz,JedisPoolLazy pool, String tablename){
		return getTable((Type)clazz,pool,tablename);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<V> RedisTable<V> getTable(Type type,JedisPoolLazy pool, String tablename){
		// Double Checked Locking
		RedisTable table = tables.get(pool);
		if(null ==table ){
			tables.putIfAbsent(pool, new RedisTable(type,pool,tablename));
			table = tables.get(pool);
		}
		if(type != table.getType()){
			throw new IllegalStateException("mismatch type " + type + " vs " + table.getType());
		}
		return table;
	}
	
	/**
	 * 保存每个 {@link JedisPoolLazy}对应的{@link RedisSubscriber}实例
	 */
	private static final ConcurrentMap<JedisPoolLazy,RedisSubscriber>  subscribers = new ConcurrentHashMap<JedisPoolLazy,RedisSubscriber>();
	
	/**
	 * 删除所有{@link RedisSubscriber}对象
	 */
	public static void clearSubscribers(){
		synchronized(RedisSubscriber.class){
			for(RedisSubscriber subscribe:subscribers.values()){
				subscribe.unsubscribe();
			}
			subscribers.clear();
		}
	}
	
	/**
	 * 返回 {@link JedisPoolLazy}对应的实例,如果{@link #subscribers}没有找到，就创建一个新实例并加入{@link #subscribers}
	 * @param jedisPoolLazy
	 * @return 
	 */
	public static RedisSubscriber getSubscriber(JedisPoolLazy jedisPoolLazy) {
		// Double Checked Locking
		RedisSubscriber subscriber = subscribers.get(jedisPoolLazy);
		if(null == subscriber){
			subscribers.putIfAbsent(jedisPoolLazy, new RedisSubscriber(jedisPoolLazy).setDaemon(true));
			subscriber = subscribers.get(jedisPoolLazy);
		}
		return subscriber;
	}
	
	/**
	 * 保存每个 {@link JedisPoolLazy}对应的{@link RedisConsumer}实例
	 */
	private static final ConcurrentMap<JedisPoolLazy,RedisConsumer>  consumers = new ConcurrentHashMap<JedisPoolLazy,RedisConsumer>();
	
	/**
	 * 删除所有{@link RedisSubscriber}对象
	 */
	public static void clearConsumers(){
		synchronized(RedisConsumer.class){
			for(RedisConsumer subscribe:consumers.values()){
				subscribe.unsubscribe();
			}
			consumers.clear();
		}
	}
	
	/**
	 * 返回 {@link JedisPoolLazy}对应的实例,如果{@link #consumers}没有找到，
	 * 就创建一个新实例并加入{@link #consumers}
	 * @param jedisPoolLazy
	 * @return 
	 */
	public static RedisConsumer getConsumer(JedisPoolLazy jedisPoolLazy) {
		// Double Checked Locking
		RedisConsumer consumer = consumers.get(jedisPoolLazy);
		if (null == consumer) {
			consumers.putIfAbsent(jedisPoolLazy,
					(RedisConsumer) new RedisConsumer(jedisPoolLazy).setDaemon(true));
			consumer = consumers.get(jedisPoolLazy);
		}
		return consumer;
	}
	
	/**
	 * 保存每个 {@link JedisPoolLazy}对应的{@link RedisPublisher}实例
	 */
	private static final ConcurrentMap<JedisPoolLazy,RedisPublisher>  publishers = new ConcurrentHashMap<JedisPoolLazy,RedisPublisher>();
	
	/**
	 * 返回 {@link JedisPoolLazy}对应的实例,如果{@link #publishers}没有找到，
	 * 就创建一个新实例并加入{@link #publishers}
	 * @param jedisPoolLazy
	 * @return 
	 */
	public static IPublisher getPublisher(JedisPoolLazy jedisPoolLazy) {
		// Double Checked Locking
		IPublisher publisher = publishers.get(jedisPoolLazy);
		if (null == publisher) {
			publishers.putIfAbsent(jedisPoolLazy, new RedisPublisher(jedisPoolLazy));
			publisher = publishers.get(jedisPoolLazy);
		}
		return publisher;
	}

	/**
	 * 保存每个 {@link JedisPoolLazy}对应的{@link RedisProducer}实例
	 */
	private static final ConcurrentMap<JedisPoolLazy,RedisProducer>  producers = new ConcurrentHashMap<JedisPoolLazy,RedisProducer>();
	
	/**
	 * 返回 {@link JedisPoolLazy}对应的实例,如果{@link #producers}没有找到，
	 * 就创建一个新实例并加入{@link #producers}
	 * @param jedisPoolLazy
	 * @return 
	 */
	public static IProducer getProducer(JedisPoolLazy jedisPoolLazy) {
		// Double Checked Locking
		IProducer producer = producers.get(jedisPoolLazy);
		if (null == producer) {
			producers.putIfAbsent(jedisPoolLazy, new RedisProducer(jedisPoolLazy));
			producer = producers.get(jedisPoolLazy);
		}
		return producer;
	}
}
