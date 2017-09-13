package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;
import net.gdface.facelog.message.JedisPoolLazy.PropName;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisProducer<T> extends Producer<T> implements IRedisComponent {
	@Override
	public JedisPoolLazy getPoolLazy() {
		return ((RedisQueue<T>)queue).getPoolLazy();
	}
	
	public RedisProducer(String queueName,Type type, JedisPoolLazy poolLazy) {
		super();
		this.setQueue(new RedisQueue<T>(type,poolLazy).setQueueName(queueName));
	}
	
	@Override
	public String getQueueName() {
		return ((RedisQueue<T>)queue).getQueueName();
	}
	
	public RedisProducer(String queueName,Type type, Map<PropName, Object> props) {
		this(queueName,type, JedisPoolLazy.getInstance(props, true));
	}

	public RedisProducer(String queueName,Type type,String host, int port, final String password, int database) {
		this(queueName, type, JedisPoolLazy.DEFAULT_CONFIG, host, port, password, database, Protocol.DEFAULT_TIMEOUT);
	}

	public RedisProducer(String queueName,Type type, JedisPoolConfig jedisPoolConfig, URI uri, int timeout) {
		this(queueName,type, JedisPoolLazy.getInstance(jedisPoolConfig, uri, timeout));
	}

	public RedisProducer(String queueName, Type type, JedisPoolConfig jedisPoolConfig, String host, int port,
			final String password, int database, int timeout) {
		this(queueName,type, JedisPoolLazy.getInstance(jedisPoolConfig, host, port, password, database, timeout));
	}

}
