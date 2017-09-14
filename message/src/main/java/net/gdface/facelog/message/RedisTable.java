package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import com.alibaba.fastjson.util.FieldInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class RedisTable<V> extends KVTable<V> implements IRedisComponent {
	private final JedisPoolLazy poolLazy;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return poolLazy;
	}

	private Jedis getJedis(){
        return poolLazy.apply();
    }
    
    private void releaseJedis(Jedis jedis) {
    	poolLazy.free();
    }
    
	public RedisTable(Type type) {
		this(type,JedisPoolLazy.getDefaultInstance());
	}
	
	public RedisTable(Type type,JedisPoolLazy pool){
		super(type);
		poolLazy = pool;
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
	protected void _setFields(String key, Map<String, String> fieldsValues, boolean nx) {
		if(null == fieldsValues || fieldsValues.isEmpty())return;
		Jedis jedis = getJedis();
		try {
			HashMap<String, String> hash = new HashMap<String,String>();
			ArrayList<String> nullFields = new ArrayList<String>();
			for(Entry<String, String> entry:fieldsValues.entrySet()){
				String value = entry.getValue();
				String field = entry.getKey();
				if(null == value)
					nullFields.add(field);
				else
					hash.put(field, value);
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

	@Override
	protected Map<String, Object> _getFields(String key, Map<String, Type> types) {
		Jedis jedis = getJedis();
		try {
			Map<String, String> fieldHash;
			if(null == types || types.isEmpty()){
				// types 为 空或 null时,返回所有 field
				fieldHash = jedis.hgetAll(key);
			}else{
				String[] fields = types.keySet().toArray(new String[0]);
				List<String> values = jedis.hmget(key, fields);
				fieldHash = new LinkedHashMap<String,String>();
				for(int i = 0; i < fields.length ; ++i){
					fieldHash.put(fields[i], values.get(i));
				}				
			}
			return this.encoder.fromJson(fieldHash, types);
		} finally {
			releaseJedis(jedis);
		}
	}
	
	@Override
	protected List<String> _getFieldNames(){
		List<FieldInfo> fieldList = com.alibaba.fastjson.util.TypeUtils.computeGetters(
				com.alibaba.fastjson.util.TypeUtils.getRawClass(this.getType()), null);
		ArrayList<String> fields = new ArrayList<String>(fieldList.size());
		for(FieldInfo field:fieldList){
			fields.add(field.name);
		}
		return fields;

	}
}
