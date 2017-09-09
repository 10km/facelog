package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.Transaction;

public class RedisTable<V> extends KVTable<V> {
	private static final Logger logger = LoggerFactory.getLogger(RedisTable.class);
	private JedisPool pool;
	private Jedis getJedis(){
        return pool.getResource();
    }
    
    private void releaseJedis(Jedis jedis) {
        if (jedis != null){
            jedis.close();
        }
    }
    
	private static final JedisPoolConfig DEFAULT_CONFIG = new JedisPoolConfig() {
		{
			setMaxTotal(10);
		}
	};

	public RedisTable(Type type) {
		this(type, DEFAULT_CONFIG, Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT, null, Protocol.DEFAULT_DATABASE, Protocol.DEFAULT_TIMEOUT);
	}

	public RedisTable(Type type,String host, int port, final String password, int database) {
		this(type, DEFAULT_CONFIG, host, port, password, database, Protocol.DEFAULT_TIMEOUT);
	}

	public RedisTable(Type type, JedisPoolConfig jedisPoolConfig, URI uri, int timeout) {
		super(type);
		pool = new JedisPool(jedisPoolConfig, uri, timeout);
		logger.info("连接池初始化");
	}

	public RedisTable(Type type, JedisPoolConfig jedisPoolConfig, String host, int port, final String password,
			int database, int timeout) {
		super(type);
		pool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
		logger.info("连接池初始化");
	}

	@Override
	protected V _get(String key) {
		Jedis jedis = getJedis();
		try {
			if(isJavaBean){
				return this.encoder.fromJson(jedis.hgetAll(key), this.getType());
			}else			
				return this.encoder.fromJson(jedis.get(key), this.getType());
			
		} finally {
			releaseJedis(jedis);
		}
	}
	
	protected void redisSet(String key, V value, boolean nx) {
		Jedis jedis = getJedis();
		try {
			if(nx)
				jedis.setnx(key, this.encoder.toJsonString(value));
			else
				jedis.set(key, this.encoder.toJsonString(value));
		} finally {
			releaseJedis(jedis);
		}
	}
	
	@Override
	protected void _set(String key, V value, boolean nx) {
		if (isJavaBean){
			setFields(nx, key, value);			
		}else {
			redisSet(key, value, nx);
		}
	}
	
	@Override
	protected void _setFields(String key, Map<String,Object>fieldsValues, boolean nx) {
		if(null == fieldsValues || fieldsValues.isEmpty())return;
		Jedis jedis = getJedis();
		try {
			HashMap<String, String> hash = new HashMap<String,String>();
			ArrayList<String> nullFields = new ArrayList<String>();
			for(Entry<String, Object> entry:fieldsValues.entrySet()){
				Object value = entry.getValue();
				String field = entry.getKey();
				if(null != value)
					hash.put(field, (value instanceof String) ?(String)value:this.encoder.toJsonString(value));
				else
					nullFields.add(field);
			}
			Transaction ctx = jedis.multi();
			if(!hash.isEmpty()){
				if(nx){
					for(Entry<String, String> entry:hash.entrySet()){
						ctx.hsetnx(key, entry.getKey(), entry.getValue());
					}
				}					
				else
					ctx.hmset(key, hash);
			}
				
			if(!nullFields.isEmpty() && !nx)
				ctx.hdel(key, nullFields.toArray(new String[0]));
			List<Object> response = ctx.exec();
			if(response.isEmpty())
				throw new TableException("Transaction error");
		} finally {
			releaseJedis(jedis);
		}
	}	
	
	@Override
	protected void _setField(String key, String field,Object value,boolean nx) {
		Jedis jedis = getJedis();
		try {
			if(null != value){
				if(nx)
					jedis.hsetnx(key, field, this.encoder.toJsonString(value));
				else
					jedis.hset(key, field, this.encoder.toJsonString(value));
			}else if(!nx)
				jedis.hdel(key, field);
		} finally {
			releaseJedis(jedis);
		}
	}

	@Override
	protected int _remove(String... key) {
		Jedis jedis = getJedis();
		try {
			return jedis.del(key).intValue(); 
		} finally {
			releaseJedis(jedis);
		}
	}

	@Override
	protected Set<String> _keys(String pattern) {
		Jedis jedis = getJedis();
		try {
			return jedis.keys(pattern);
		} finally {
			releaseJedis(jedis);
		}
	}

	protected void _setString(Map<String, V> m, boolean nx) {
		Jedis jedis = getJedis();
		try {
			ArrayList<String> keysValues = new ArrayList<String>();
			ArrayList<String> keysNull = new ArrayList<String>();
			for(Entry<String, ? extends V> entry:m.entrySet())	{
				V value = entry.getValue();
				if(null != value){
					keysValues.add(entry.getKey());
					keysValues.add(this.encoder.toJsonString(value));
				}else
					keysNull.add(entry.getKey());
			}
			Transaction ctx = jedis.multi();
			if(!keysValues.isEmpty()){
				if(nx)
					ctx.msetnx(keysValues.toArray(new String[0]));
				else
					ctx.mset(keysValues.toArray(new String[0]));
			}				
			if(!keysNull.isEmpty() && !nx)
				jedis.del(keysNull.toArray(new String[0]));
			List<Object> response = ctx.exec();
			if(response.isEmpty())
				throw new TableException("Transaction error");
		} finally {
			releaseJedis(jedis);
		}
	}
	
	protected void _setHash(Map<String,? extends V> m, boolean nx) {
		Jedis jedis = getJedis();
		try {
			Map<String, Map<String,String>> keysValues = new HashMap<String, Map<String,String>>();
			ArrayList<String> keysNull = new ArrayList<String>();
			for(Entry<String, ? extends V> entry:m.entrySet())	{
				V value = entry.getValue();
				if(null != value){
					keysValues.put(entry.getKey(), this.encoder.toJsonMap(value));
				}else
					keysNull.add(entry.getKey());
			}
			Transaction ctx = jedis.multi();
			if(!keysValues.isEmpty()){
				if(nx){
					for(Entry<String, Map<String, String>> entry:keysValues.entrySet()){
						for(Entry<String, String> props:entry.getValue().entrySet()){
							ctx.hsetnx(entry.getKey(), props.getValue(), props.getValue());
						}
					}
				}
				else{
					for(Entry<String, Map<String, String>> entry:keysValues.entrySet()){
						ctx.hmset(entry.getKey(), entry.getValue());
					}
				}					
			}				
			if(!keysNull.isEmpty() && !nx)
				jedis.del(keysNull.toArray(new String[0]));
			List<Object> response = ctx.exec();
			if(response.isEmpty())
				throw new TableException("Transaction error");
		} finally {
			releaseJedis(jedis);
		}
	}
		
	@Override
	protected void _set(Map<String, V> m, boolean nx) {
		if(isJavaBean)
			_setHash(m,nx);
		else
			_setString(m,nx);
	}
}
