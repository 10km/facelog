package net.gdface.facelog.message;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

import net.gdface.facelog.message.JedisPoolLazy.PropName;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisPublish implements IPublish,IRedisComponent{
	private JsonEncoder encoder = JsonEncoder.getEncoder();
	private final JedisPoolLazy poolLazy;

	@Override
	public JedisPoolLazy getPoolLazy() {
		return this.poolLazy;
	}
	
	public RedisPublish(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	
	public RedisPublish(Map<PropName, Object> props) {
		this(JedisPoolLazy.getInstance(props, true));
	}

	public RedisPublish(String host,int port,final String password, int database) {
		this(JedisPoolLazy.DEFAULT_CONFIG, host, port, password, database, Protocol.DEFAULT_TIMEOUT);
	}

	public RedisPublish(JedisPoolConfig jedisPoolConfig,URI uri, int timeout) {
		this(JedisPoolLazy.getInstance(jedisPoolConfig, uri, timeout));
	}

	public RedisPublish(JedisPoolConfig jedisPoolConfig, String host, int port, final String password, int database,
			int timeout) {
		this(JedisPoolLazy.getInstance(jedisPoolConfig, host, port, password, database, timeout));
	}	
	
	@Override
	public void publish(Channel channel, Object obj,Type type) {
		if(null == obj)return;
		if(null != channel.type){
			if(channel.type instanceof Class<?> && !((Class<?>)channel.type).isInstance(obj)){
				throw new IllegalArgumentException("invalid type of 'obj'");
			}else if(channel.type instanceof ParameterizedType ){
				if(null == type)
					throw new IllegalArgumentException("type must not be null'");
				if(! (type !=channel.type))
					throw new IllegalArgumentException("invalid type of 'obj'");
			}
		}
		Jedis jedis = this.poolLazy.apply();
		try{
			jedis.publish(channel.name, this.encoder.toJsonString(obj));
		}finally{
			this.poolLazy.free();
		}
	}
}
