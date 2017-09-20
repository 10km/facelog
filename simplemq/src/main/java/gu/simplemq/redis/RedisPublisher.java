package gu.simplemq.redis;

import java.util.Collection;

import gu.simplemq.Channel;
import gu.simplemq.IPublisher;
import gu.simplemq.json.JsonEncoder;
import gu.simplemq.utils.CommonUtils;
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

	RedisPublisher(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	
	@Override
	public <T>void publish(Channel<T> channel, T obj) {
		if(null == obj)return;
		if(null != channel.type){
			// 检查发布的对象类型与频道数据类型是否匹配
			if(channel.type instanceof Class<?> && !((Class<?>)channel.type).isInstance(obj)){
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

	@Override
	public <T> void publish(Channel<T> channel, Collection<T> objects) {
		objects= CommonUtils.cleanNullAsList(objects);
		for(T obj:objects){
			publish(channel,obj);
		}
	}

	@Override
	public <T> void publish(Channel<T> channel, @SuppressWarnings("unchecked") T... objects) {
		objects = CommonUtils.cleanNull(objects);
		for(T obj:objects){
			publish(channel,obj);
		}		
	}

}
