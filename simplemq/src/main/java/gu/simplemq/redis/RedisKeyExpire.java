package gu.simplemq.redis;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import gu.simplemq.KeyExpire;
import redis.clients.jedis.Jedis;

class RedisKeyExpire extends KeyExpire {
	private final JedisPoolLazy poolLazy;
	public RedisKeyExpire(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	
	public static void expire(Jedis jedis,String key,long timeMills,boolean timestamp){
		if(timestamp){
			jedis.pexpireAt(key, timeMills);
		}
		else{
			jedis.pexpire(key, timeMills);
		}
	}
	public static void expire(Jedis jedis,String key,long time,TimeUnit timeUnit){
		expire(jedis,key,TimeUnit.MILLISECONDS.convert(time, timeUnit),true);
	}
	public static void expire(Jedis jedis,String key,Date date){
		expire(jedis,key,date.getTime(),true);
	}
	
	protected final void expire(Jedis jedis,String key){
		expire(jedis,wrapKey(key),this.timeMills,this.timestamp);
	}
	@Override
	protected final void doExpire(String key, long timeMills, boolean timestamp) {
		Jedis jedis = poolLazy.apply();
		try{
			expire(jedis,wrapKey(key),timeMills,timestamp);
		}finally{
			poolLazy.free();
		}
	}
	protected String wrapKey(String key){return key;}
}
