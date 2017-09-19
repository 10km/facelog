package gu.simplemq.redis;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import gu.simplemq.Channel;
import gu.simplemq.IPublisher;
import gu.simplemq.json.JsonEncoder;
import redis.clients.jedis.Jedis;

/**
 * 
 * {@link IPublisher} redis实现
 * @author guyadong
 *
 */
public class RedisPublisher implements IPublisher,IRedisComponent{
	private JsonEncoder encoder = JsonEncoder.getEncoder();
	private final JedisPoolLazy poolLazy;

	@Override
	public JedisPoolLazy getPoolLazy() {
		return this.poolLazy;
	}

	public RedisPublisher() {
		this(JedisPoolLazy.getDefaultInstance());
	}
	
	public RedisPublisher(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	
	@Override
	public void publish(@SuppressWarnings("rawtypes") Channel channel, Object obj, Type type) {
		if(null == obj)return;
		if(null != channel.type){
			// 检查发布的对象类型与频道数据类型是否匹配
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
