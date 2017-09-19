package net.gdface.facelog.message;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import redis.clients.jedis.Jedis;

public class NameChecker {
	private static final ConcurrentMap<JedisPoolLazy,NameChecker> checkers = new ConcurrentHashMap<JedisPoolLazy,NameChecker>();

	public static NameChecker getNameChecker(JedisPoolLazy poolLazy){
		// Double Checked Locking
		NameChecker checker = checkers.get(poolLazy);
		if(null ==checker ){
			checkers.putIfAbsent(poolLazy, new NameChecker(poolLazy));
			checker = checkers.get(poolLazy);
		}
		return checker;
	}
	
	private final JedisPoolLazy poolLazy;
	enum RedisKeyType{string,list,set,zset,none,hash}
	enum ComponentType{
		Table,Queue, Channel;
		public boolean check(JedisPoolLazy poolLazy,String name){
			Jedis jedis = poolLazy.apply();
			if(!jedis.exists(name))return true;
			RedisKeyType keyType = RedisKeyType.valueOf(jedis.type(name));
			try{
				switch(this){
				case Table:{
					return false;
				}
				case Queue:{
					return keyType == RedisKeyType.list;
				}
				case Channel:
					return false;
				default:
				}
				return false;
			}finally{
				poolLazy.free();
			}			
		}
	}
	private final ConcurrentMap<String,ComponentType> nameTypes = new ConcurrentHashMap<String,ComponentType>(); 
	protected NameChecker(JedisPoolLazy poolLazy) {
		super();
		this.poolLazy = poolLazy;
	}
	public void check(String name,ComponentType type){
		if(Judge.isEmpty(name)|| null == type)
			throw new IllegalArgumentException("the arguments must not be null or empty");
		ComponentType old = nameTypes.putIfAbsent(name, type);
		if(old != type){
			throw new IllegalStateException(String.format("the '%s' can't be used for %s",name,type.toString()));
		}
	}
	/**
	 * @return poolLazy
	 */
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}
}
