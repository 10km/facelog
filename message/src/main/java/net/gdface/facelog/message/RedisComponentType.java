package net.gdface.facelog.message;

import redis.clients.jedis.Jedis;

enum RedisComponentType{
	Table,Queue, Channel;
	enum RedisKeyType{string,list,set,zset,none,hash}
	public String check(JedisPoolLazy poolLazy,String name){
		if(null == name || 0 == name.length())
			throw new IllegalArgumentException("name must not be null or empty"); 
		Jedis jedis = poolLazy.apply();
		if(!jedis.exists(name))return name;
		try{
			switch(this){
			case Table:break;
			case Queue:{
				if( RedisKeyType.valueOf(jedis.type(name)) == RedisKeyType.list)
					return name;
			}
			case Channel:
			default:
			}
			throw new IllegalStateException(String.format("the '%s' can't be used for %s",name,this.name()));
		}finally{
			poolLazy.free();
		}			
	}
}