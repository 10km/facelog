package gu.simplemq.redis;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import gu.simplemq.exceptions.SmqTypeException;
import redis.clients.jedis.Jedis;

enum RedisComponentType{
	/** table 类型*/Table,
	/** queue 类型*/Queue, 
	/** channel 类型*/Channel;
	private enum RedisKeyType{
		/** string 类型*/string,
		/** list 类型*/list,
		/** set 类型*/set,
		/** zset 类型*/zset,
		/** none 类型*/none,
		/** hash 类型*/hash
	}
	/**
	 * 检查对于指定的{@link JedisPoolLazy},{@code name}名字是否可用创建对象
	 * @param poolLazy
	 * @param name
	 * @return {@code name}
	 * @throws SmqTypeException {@code name}不可用
	 */
	public String check(JedisPoolLazy poolLazy,String name) throws SmqTypeException{
		Preconditions.checkArgument(!Strings.isNullOrEmpty(name),"name must not be null or empty");
		Jedis jedis = poolLazy.apply();
		if(!jedis.exists(name)){
			return name;
		}
		try{
			switch(this){
			case Table:break;
			case Queue:{
				if( RedisKeyType.valueOf(jedis.type(name)) == RedisKeyType.list){
					return name;
				}
			}
			case Channel:
			default:
			}
			throw new SmqTypeException(String.format("the '%s' can't be used for %s",name,this.name()));
		}finally{
			poolLazy.free();
		}
	}
}