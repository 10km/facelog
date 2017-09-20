package gu.simplemq.redis;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Set;
import com.alibaba.fastjson.util.FieldInfo;

import gu.simplemq.AbstractTable;
import gu.simplemq.exceptions.SmqTableException;
import gu.simplemq.utils.Assert;
import gu.simplemq.utils.CommonUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 基于redis的 string,hash类型实现 {@link AbstractTable}
 * @author guyadong
 *
 * @param <V> 元素数据类型
 */
public class RedisTable<V> extends AbstractTable<V> implements IRedisComponent {
	private final JedisPoolLazy pool;
	private static final String prefixEnd= ".";
	private final TablenameChecker checker ;
	/** 表名 */
	private final String prefix ;
	@Override
	public JedisPoolLazy getPoolLazy() {
		return pool;
	}

	RedisTable(Class<V> clazz) {
		this(clazz,JedisPoolLazy.getDefaultInstance(), null);
	}
	
	RedisTable(Class<V> clazz,JedisPoolLazy pool, String tablename){
		this((Type)clazz,pool,tablename);
	}
	
	/**
	 * @param type 表中元素类型
	 * @param pool 数据库连接池对象
	 * @param tablename 表名,[a-zA-Z0-9_]以外的字符都被替换为_,参见 {@link #format(String)}
	 */
	RedisTable(Type type,JedisPoolLazy pool, String tablename){
		super(type);
		Assert.notNull(pool, "pool");
		this.pool = pool;
		this.checker = TablenameChecker.getNameChecker(pool);
		try{
			tablename = format(tablename);
		}catch(Exception e){
			if(type instanceof Class)
				tablename = format(((Class<?>)type).getSimpleName());
			else
				tablename = format(type.toString());
		}
		this.prefix = tablename;
	}

	@Override
	protected V _get(String key) {
		key = wrapKey(key);
		Jedis jedis = pool.apply();
		try {
			if(isJavaBean){
				return this.encoder.fromJson(jedis.hgetAll(key), this.getType());
			}else			
				return this.encoder.fromJson(jedis.get(key), this.getType());
			
		} finally {
			pool.free();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, V> get(String... keys) {
		if(isJavaBean)
			return super.get(keys);
		else{
			String[] wkeys = CommonUtils.cleanEmpty(keys);
			for(int i =0 ;i<keys.length;++i)wkeys[i] = wrapKey(wkeys[i]);
			Jedis jedis = pool.apply();
			try {				
				List<String> values = jedis.mget(wkeys);
				Map<String, Object> m = new HashMap<String,Object>();
				for(int i=0;i<wkeys.length;++i)
					m.put(wkeys[i], this.encoder.fromJson(values.get(i), this.getType()));
				return (Map<String, V>) m;
			} finally {
				pool.free();
			}
		}
	}

	@Override
	protected void _set(String key, V value, boolean nx) {
		key = wrapKey(key);
		Jedis jedis = pool.apply();
		try {
			if(nx)
				jedis.setnx(key, this.encoder.toJsonString(value));
			else
				jedis.set(key, this.encoder.toJsonString(value));
		} finally {
			pool.free();
		}
	}
	
	@Override
	protected void _setFields(String key, Map<String, String> fieldsValues, boolean nx) {
		key = wrapKey(key);
		if(null == fieldsValues || fieldsValues.isEmpty())return;
		Jedis jedis = pool.apply();
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
				throw new SmqTableException("Transaction error");
		} finally {
			pool.free();
		}
	}	
	
	@Override
	protected void _setField(String key, String field,Object value,boolean nx) {
		key = wrapKey(key);
		Jedis jedis = pool.apply();
		try {
			if(null != value){
				if(nx)
					jedis.hsetnx(key, field, this.encoder.toJsonString(value));
				else
					jedis.hset(key, field, this.encoder.toJsonString(value));
			}else if(!nx)
				jedis.hdel(key, field);
		} finally {
			pool.free();
		}
	}

	@Override
	protected int _remove(String... keys) {
		Jedis jedis = pool.apply();
		try {
			String[] wkeys = new String[keys.length]; 
			for(int i =0 ;i<keys.length;++i)wkeys[i] = wrapKey(keys[i]);
			return jedis.del(wkeys).intValue(); 
		} finally {
			pool.free();
		}
	}

	@Override
	protected Set<String> _keys(String pattern) {
		Jedis jedis = pool.apply();
		try {
			Set<String> keys = jedis.keys( wrapKey(pattern));
			for(String key:keys.toArray(new String[keys.size()])){
				keys.remove(key);
				keys.add(unwrapKey(key));
			}
			return keys;
		} finally {
			pool.free();
		}
	}

	protected void _setString(Map<String, V> m, boolean nx) {
		Jedis jedis = pool.apply();
		try {
			ArrayList<String> keysValues = new ArrayList<String>();
			ArrayList<String> keysNull = new ArrayList<String>();
			for(Entry<String, ? extends V> entry:m.entrySet())	{
				V value = entry.getValue();
				if(null != value){
					keysValues.add(wrapKey(entry.getKey()));
					keysValues.add(this.encoder.toJsonString(value));
				}else
					keysNull.add(wrapKey(entry.getKey()));
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
				throw new SmqTableException("Transaction error");
		} finally {
			pool.free();
		}
	}
	
	protected void _setHash(Map<String,? extends V> m, boolean nx) {
		Jedis jedis = pool.apply();
		try {
			Map<String, Map<String,String>> keysValues = new HashMap<String, Map<String,String>>();
			ArrayList<String> keysNull = new ArrayList<String>();
			for(Entry<String, ? extends V> entry:m.entrySet())	{
				V value = entry.getValue();
				if(null != value){
					keysValues.put(wrapKey(entry.getKey()), this.encoder.toJsonMap(value));
				}else
					keysNull.add(wrapKey(entry.getKey()));
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
				throw new SmqTableException("Transaction error");
		} finally {
			pool.free();
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
		key = wrapKey(key);
		Jedis jedis = pool.apply();
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
			pool.free();
		}
	}
	
	@Override
	public boolean containsKey(String key) {
		Assert.notEmpty(key,"key");
		key = wrapKey(key);
		Jedis jedis = pool.apply();
		try {
			return jedis.exists(key);
		} finally {
			pool.free();
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

	public String getTableName() {
		return prefix;
	}

	/**
	 * 格式化表名并检查检查名字是否可用
	 * @param prefix
	 * @return
	 * @see {@link RedisComponentType#check(JedisPoolLazy, String)}
	 */
	private String format(String prefix) {
		Assert.notEmpty(prefix, "prefix");
		return checker.check(prefix.replaceAll("[\\s\\W]+", "_"), RedisComponentType.Table, this.getType());
	}

	private String wrapKey(String key) {
		return new StringBuilder(this.prefix).append(prefixEnd).append(key).toString();
	}

	private String unwrapKey(String key) {
		return key.substring(this.prefix.length()+prefixEnd.length());
	}
}