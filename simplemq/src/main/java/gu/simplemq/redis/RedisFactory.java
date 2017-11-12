package gu.simplemq.redis;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import gu.simplemq.Channel;
import gu.simplemq.exceptions.SmqException;

/**
 * redis对象工厂类用于获取producer/consumer,publisher/subscriber,table对象
 * @author guyadong
 *
 */
public class RedisFactory {

	private RedisFactory() {}
	@SuppressWarnings("rawtypes")
	private static final ConcurrentMap<JedisPoolLazy,RedisTable> TABLES = new ConcurrentHashMap<JedisPoolLazy,RedisTable>();

	public static<V> RedisTable<V> getTable(Class<V> clazz){
		return getTable((Type)clazz,JedisPoolLazy.getDefaultInstance(),null);
	}
	public static<V> RedisTable<V> getTable(Class<V> clazz,JedisPoolLazy pool, String tablename){
		return getTable((Type)clazz,pool,tablename);
	}
	public static<V> RedisTable<V> getTable(Channel<V> channel,JedisPoolLazy pool){
		return getTable(channel.type,pool,channel.name);
	}	
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisTable}实例,如果{@link #TABLES}没有找到，
	 * 就创建一个新实例并加入{@link #TABLES}
	 * @param type
	 * @param pool
	 * @param tablename
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<V> RedisTable<V> getTable(Type type,JedisPoolLazy pool, String tablename){
		// Double Checked Locking
		RedisTable table = TABLES.get(pool);
		if(null ==table ){
			TABLES.putIfAbsent(pool, new RedisTable(type,pool,tablename));
			table = TABLES.get(pool);
		}
		if( !table.getType().equals(type)){
			throw new IllegalStateException("mismatch type " + type + " vs " + table.getType());
		}
		return table;
	}
	
	/**
	 * 线程安全的redis组件实例管理类
	 * @author guyadong
	 *
	 * @param <R> redis组件类型
	 */
	private static class  RedisInstance<R>{
		/** 保存每个 {@link JedisPoolLazy}对应的redis组件实例 */
		private final ConcurrentMap<JedisPoolLazy,R>  instances = new ConcurrentHashMap<JedisPoolLazy,R>();
		/** R 的构造函数 */
		private final Constructor<R> constructor;
		public RedisInstance(Class<R> clazz) {
			try {
				constructor = clazz.getDeclaredConstructor(JedisPoolLazy.class);
			} catch (Exception e) {
				throw new SmqException(e);
			}
		}
		void beforeDelete(R r){}
		/** 删除{@link #instances}中所有实例 */
		synchronized void  clearInstances(){
				for(R r:instances.values()){
					beforeDelete(r);
				}
				instances.clear();
		}
		/**
		 * 返回 {@link #instances}中 jedisPoolLazy 对应的R实例, 如果没有找到就创建一个新实例加入。
		 * @param jedisPoolLazy
		 * @return
		 */
		R getInstance(JedisPoolLazy jedisPoolLazy){
			// Double Checked Locking
			R r = instances.get(jedisPoolLazy);
			if (null == r) {
				try {
					r = constructor.newInstance(jedisPoolLazy);
				} catch (Exception e) {
					throw new SmqException(e);
				}
				instances.putIfAbsent(jedisPoolLazy, r);
				r = instances.get(jedisPoolLazy);
			}
			return r;			
		}
	}
	private static  final RedisInstance<RedisConsumer> CONSUMERS = new RedisInstance<RedisConsumer>(RedisConsumer.class){
		@Override
		void beforeDelete(RedisConsumer r) {
			r.subscribe();
	}};
	private static final RedisInstance<RedisSubscriber> SUBSCRIBERS = new RedisInstance<RedisSubscriber>(RedisSubscriber.class){
	@Override
	protected void beforeDelete(RedisSubscriber r) {
		r.subscribe();
	}};
	private static final RedisInstance<RedisProducer> PRODUCERS = new RedisInstance<RedisProducer>(RedisProducer.class);
	private static final RedisInstance<RedisPublisher> PUBLISHERS = new RedisInstance<RedisPublisher>(RedisPublisher.class);
	/**
	 * 删除所有{@link RedisConsumer}对象
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#clearInstances()
	 */
	public static  void clearConsumers() {
		CONSUMERS.clearInstances();
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisConsumer}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisConsumer getConsumer(JedisPoolLazy jedisPoolLazy) {
		return CONSUMERS.getInstance(jedisPoolLazy);
	}
	
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisConsumer}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisConsumer getConsumer() {
		return CONSUMERS.getInstance(JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 删除所有{@link RedisSubscriber}对象
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#clearInstances()
	 */
	public static void clearSubscribers() {
		SUBSCRIBERS.clearInstances();
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisSubscriber}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisSubscriber getSubscriber(JedisPoolLazy jedisPoolLazy) {
		return SUBSCRIBERS.getInstance(jedisPoolLazy);
	}
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisSubscriber}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisSubscriber getSubscriber() {
		return SUBSCRIBERS.getInstance(JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisProducer}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisProducer getProducer(JedisPoolLazy jedisPoolLazy) {
		return PRODUCERS.getInstance(jedisPoolLazy);
	}
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisProducer}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisProducer getProducer() {
		return PRODUCERS.getInstance(JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisPublisher}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisPublisher getPublisher(JedisPoolLazy jedisPoolLazy) {
		return PUBLISHERS.getInstance(jedisPoolLazy);
	}
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisPublisher}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisPublisher getPublisher() {
		return PUBLISHERS.getInstance(JedisPoolLazy.getDefaultInstance());
	}
}
