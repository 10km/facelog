package gu.simplemq.redis;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import gu.simplemq.Channel;
import gu.simplemq.exceptions.SmqExcepiton;

/**
 * redis对象工厂类用于获取producer/consumer,publisher/subscriber,table对象
 * @author guyadong
 *
 */
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
	public static<V> RedisTable<V> getTable(Channel<V> channel,JedisPoolLazy pool){
		return getTable(channel.type,pool,channel.name);
	}	
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisTable}实例,如果{@link #tables}没有找到，
	 * 就创建一个新实例并加入{@link #tables}
	 * @param type
	 * @param pool
	 * @param tablename
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static<V> RedisTable<V> getTable(Type type,JedisPoolLazy pool, String tablename){
		// Double Checked Locking
		RedisTable table = tables.get(pool);
		if(null ==table ){
			tables.putIfAbsent(pool, new RedisTable(type,pool,tablename));
			table = tables.get(pool);
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
				throw new SmqExcepiton(e);
			}
		}
		void beforeDelete(R r){}
		/** 删除{@link #instances}中所有实例 */
		void clearInstances(){
			synchronized(instances){
				for(R r:instances.values()){
					beforeDelete(r);
				}
				instances.clear();
			}
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
					throw new SmqExcepiton(e);
				}
				instances.putIfAbsent(jedisPoolLazy, r);
				r = instances.get(jedisPoolLazy);
			}
			return r;			
		}
	}
	private static  final RedisInstance<RedisConsumer> consumers = new RedisInstance<RedisConsumer>(RedisConsumer.class){
		@Override
		void beforeDelete(RedisConsumer r) {
			r.subscribe();
	}};
	private static final RedisInstance<RedisSubscriber> subscribers = new RedisInstance<RedisSubscriber>(RedisSubscriber.class){
	@Override
	protected void beforeDelete(RedisSubscriber r) {
		r.subscribe();
	}};
	private static final RedisInstance<RedisProducer> producers = new RedisInstance<RedisProducer>(RedisProducer.class);
	private static final RedisInstance<RedisPublisher> publishers = new RedisInstance<RedisPublisher>(RedisPublisher.class);
	/**
	 * 删除所有{@link RedisConsumer}对象
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#clearInstances()
	 */
	public static  void clearConsumers() {
		consumers.clearInstances();
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisConsumer}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisConsumer getConsumer(JedisPoolLazy jedisPoolLazy) {
		return consumers.getInstance(jedisPoolLazy);
	}
	
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisConsumer}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisConsumer getConsumer() {
		return consumers.getInstance(JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 删除所有{@link RedisSubscriber}对象
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#clearInstances()
	 */
	public static void clearSubscribers() {
		subscribers.clearInstances();
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisSubscriber}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisSubscriber getSubscriber(JedisPoolLazy jedisPoolLazy) {
		return subscribers.getInstance(jedisPoolLazy);
	}
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisSubscriber}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisSubscriber getSubscriber() {
		return subscribers.getInstance(JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisProducer}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisProducer getProducer(JedisPoolLazy jedisPoolLazy) {
		return producers.getInstance(jedisPoolLazy);
	}
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisProducer}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisProducer getProducer() {
		return producers.getInstance(JedisPoolLazy.getDefaultInstance());
	}
	/**
	 * 返回 {@link JedisPoolLazy}对应的{@link RedisPublisher}实例
	 * @param jedisPoolLazy
	 * @return
	 * @see gu.simplemq.redis.RedisFactory.RedisInstance#getInstance(gu.simplemq.redis.JedisPoolLazy)
	 */
	public static RedisPublisher getPublisher(JedisPoolLazy jedisPoolLazy) {
		return publishers.getInstance(jedisPoolLazy);
	}
	/** 
	 * 返回{@link JedisPoolLazy}默认实例对应的{@link RedisPublisher}实例
	 * @see  {@link JedisPoolLazy#getDefaultInstance()}
	 */
	public static RedisPublisher getPublisher() {
		return publishers.getInstance(JedisPoolLazy.getDefaultInstance());
	}
}
