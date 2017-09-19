package net.gdface.facelog.simplemq.redis;

import java.lang.reflect.Type;
import java.util.concurrent.BlockingQueue;

import net.gdface.facelog.simplemq.Channel;
import net.gdface.facelog.simplemq.ConsumerSingle;

/**
 * 
 * 基于 {@link RedisQueue} 实现单消费者模型{@link ConsumerSingle}
 * @author guyadong
 *
 * @param <T>
 */
public class RedisConsumerSingle<T> extends ConsumerSingle<T> implements IRedisComponent{
	
	@Override
	public JedisPoolLazy getPoolLazy() {
		return ((RedisQueue<T>)queue).getPoolLazy();
	}
	
	@Override
	public String getQueueName() {
		return ((RedisQueue<T>)queue).getQueueName();
	}

	@Override
	public ConsumerSingle<T> setQueue(BlockingQueue<T> queue) {
		throw new UnsupportedOperationException();
	}

	public RedisConsumerSingle(Type type,JedisPoolLazy poolLazy, String queueName) {
		super(new RedisQueue<T>(type,poolLazy).setQueueName(queueName));
	}
	
	public RedisConsumerSingle(Type type, JedisPoolLazy poolLazy) {
		this(type,poolLazy,null);
	}
	
	public RedisConsumerSingle(Type type) {
		this(type,JedisPoolLazy.getDefaultInstance());
	}

	public RedisConsumerSingle(Channel<T> channel,JedisPoolLazy poolLazy){
		this(channel.type,poolLazy,channel.name);
	}
}
