package gu.simplemq.redis;

import java.lang.reflect.Array;
import java.util.Collection;

import gu.simplemq.Channel;
import gu.simplemq.IProducer;
import gu.simplemq.json.JsonEncoder;
import gu.simplemq.utils.CommonUtils;
import gu.simplemq.utils.TypeUtils;
import redis.clients.jedis.Jedis;

/**
 * {@link IProducer} redis实现
 * @author guyadong
 *
 */
public class RedisProducer implements IRedisComponent, IProducer{
    /** 是否向队列末尾添加 */
	protected boolean offerLast = true;
	private JsonEncoder encoder = JsonEncoder.getEncoder();
	private final JedisPoolLazy poolLazy;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}
	
	RedisProducer(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	
	@Override
	public <T> void produce(Channel<T> channel, T object, boolean offerLast) {
		if(null == object)return;
		Jedis jedis = this.poolLazy.apply();
		try{
			if(offerLast)
				jedis.rpush(channel.name, this.encoder.toJsonString(object));
			else
				jedis.lpush(channel.name, this.encoder.toJsonString(object));
		}finally{
			this.poolLazy.free();
		}		
	}
	
	@Override
	public <T> void produce(Channel<T> channel, T object) {
		produce(channel,object,this.offerLast);	
	}

	@Override
	public <T> void produce(Channel<T> channel, boolean offerLast, @SuppressWarnings("unchecked") T... objects) {
		objects = CommonUtils.cleanNull(objects);
		if(0 == objects.length)return;
		if(null != channel.type){
			// 检查发布的对象类型与频道数据类型是否匹配
			if(channel.type instanceof Class<?> && 
					!((Class<?>)channel.type).isAssignableFrom(objects.getClass().getComponentType())){
				throw new IllegalArgumentException("invalid component type of 'objects'");
			}
		}
		String[] strings = new String[objects.length];
		for(int i=0;i<strings.length;++i)
			strings[i] = this.encoder.toJsonString(objects[i]);
		Jedis jedis = this.poolLazy.apply();
		try{
			if(offerLast)
				jedis.rpush(channel.name, strings);
			else
				jedis.lpush(channel.name, strings);
		}finally{
			this.poolLazy.free();
		}
	}
	
	@Override
	public <T> void produce(Channel<T> channel, @SuppressWarnings("unchecked") T... objects) {
		produce(channel,this.offerLast,objects);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> void produce(Channel<T> channel, boolean offerLast, Collection<T>c) {
		if(null == c ) return;
		produce(channel,offerLast, c.toArray((T[]) Array.newInstance(TypeUtils.getRawClass(channel.type), 0)));
	}
	@Override
	public <T> void produce(Channel<T> channel, Collection<T>c) {
		produce(channel,this.offerLast,c);
	}
	@Override
	public void setOfferLast(boolean offerLast) {
		this.offerLast = offerLast;
	}
}
