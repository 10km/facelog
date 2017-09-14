package net.gdface.facelog.message;

import java.lang.reflect.Type;

/**
 * 基于 {@link RedisQueue} 的消息费模型实现
 * @author guyadong
 *
 * @param <T>
 */
public class RedisConsumer<T> extends Consumer<T> implements IRedisComponent {
	
	@Override
	public JedisPoolLazy getPoolLazy() {
		return ((RedisQueue<T>)queue).getPoolLazy();
	}
	
	@Override
	public String getQueueName() {
		return ((RedisQueue<T>)queue).getQueueName();
	}

	public RedisConsumer(Type type,JedisPoolLazy poolLazy, String queueName) {
		super(new RedisQueue<T>(type,poolLazy).setQueueName(queueName));
	}
	
	public RedisConsumer(Type type, JedisPoolLazy poolLazy) {
		this(type,poolLazy,null);
	}
	
	public RedisConsumer(Type type) {
		this(type,JedisPoolLazy.getDefaultInstance());
	}
}
