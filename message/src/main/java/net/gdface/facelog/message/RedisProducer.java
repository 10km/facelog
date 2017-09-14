package net.gdface.facelog.message;

import java.lang.reflect.Type;

public class RedisProducer<T> extends Producer<T> implements IRedisComponent {
	@Override
	public JedisPoolLazy getPoolLazy() {
		return ((RedisQueue<T>)queue).getPoolLazy();
	}
	
	@Override
	public String getQueueName() {
		return ((RedisQueue<T>)queue).getQueueName();
	}

	public RedisProducer(Type type,JedisPoolLazy poolLazy, String queueName) {
		super();
		this.setQueue(new RedisQueue<T>(type,poolLazy).setQueueName(queueName));
	}
	
	public RedisProducer(Type type, JedisPoolLazy poolLazy) {
		this(type,poolLazy,null);
	}
	
	public RedisProducer(Type type) {
		this(type,JedisPoolLazy.getDefaultInstance());
	}
}
