package net.gdface.facelog.message;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
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

public class RedisTable<KO extends IIncludeKey> extends KOTable<KO> {
	private static final Logger logger = LoggerFactory.getLogger(RedisTable.class);
	private JedisPool pool;
	private JsonEncoder<KO> encoder;
    private static InheritableThreadLocal<Jedis> threadJedis = new InheritableThreadLocal<Jedis>();
    private Jedis getJedis(){
        Jedis jedis = threadJedis.get();
        if (jedis != null) {
            return jedis;
        }
        return pool.getResource();
    }
    
    private void releaseJedis(Jedis jedis) {
        Jedis tj = threadJedis.get();
        if (tj != null){
            return;
        }
        if (jedis != null){
            jedis.close();
        }
    }
    
	private static final JedisPoolConfig DEFAULT_CONFIG = new JedisPoolConfig() {
		{
			setMaxTotal(10);
		}
	};

	public RedisTable() {
		this(null, DEFAULT_CONFIG, Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT, null, Protocol.DEFAULT_DATABASE,
				Protocol.DEFAULT_TIMEOUT);
	}

	public RedisTable(Type type, String host, int port, final String password, int database) {
		this(null, DEFAULT_CONFIG, host, port, password, database, Protocol.DEFAULT_TIMEOUT);
	}

	public RedisTable(Type type, JedisPoolConfig jedisPoolConfig, URI uri, int timeout) {
		super(type);
		pool = new JedisPool(jedisPoolConfig, uri, timeout);
		logger.info("连接池初始化成功");
	}

	public RedisTable(Type type, JedisPoolConfig jedisPoolConfig, String host, int port, final String password,
			int database, int timeout) {
		super(type);
		pool = new JedisPool(jedisPoolConfig, host, port, timeout, password, database);

	}

	@SuppressWarnings("unchecked")
	@Override
	public KO get(String key) {
		Jedis jedis = getJedis();
		try {
			return this.getType() instanceof Class 
					? this.encoder.fromJson(jedis.get(key), (Class<KO>) this.getType())
					: this.encoder.fromJson(jedis.get(key), this.getType());
		} finally {
			releaseJedis(jedis);
		}
	}

	@Override
	public boolean set(String key, KO value) {
		Jedis jedis = getJedis();
		try {
			return "OK".equals(jedis.set(key, this.encoder.toJson(value)));
		} finally {
			releaseJedis(jedis);
		}
	}

	@Override
	public int remove(String key) {
		Jedis jedis = getJedis();
		try {
			return jedis.del(key).intValue();			
		} finally {
			releaseJedis(jedis);
		}
	}

	@Override
	public Set<String> keys(String pattern) {
		if(null == pattern || pattern.isEmpty())
			pattern="*";
		Jedis jedis = getJedis();
		try {
			return jedis.keys(pattern);
		} finally {
			releaseJedis(jedis);
		}
	}

	public JsonEncoder<KO> getEncoder() {
		return encoder;
	}

	public void setEncoder(JsonEncoder<KO> encoder) {
		this.encoder = encoder;
	}

	@Override
	public <T> void modify(String key, String field, T value, Type type) {
		Jedis jedis = getJedis();
		try {
			jedis.watch(key);
			Transaction ctx = jedis.multi();
			ctx.get(key);
			//return jedis.keys(pattern);
		} finally {
			releaseJedis(jedis);
		}
	}

	@Override
	public void set(Map<String, ? extends KO> m) {
		if(null == m || m.isEmpty()) return ;
		Jedis jedis = getJedis();
		try {
			ArrayList<String> keysValues = new ArrayList<String>();
			ArrayList<String> keysNull = new ArrayList<String>();
			for(Entry<String, ? extends KO> entry:m.entrySet())	{
				KO value = entry.getValue();
				if(null != value){
					keysValues.add(String.format("\"%s\" \"%s\"", entry.getKey(),this.encoder.toJson(value)));
				}else
					keysNull.add(entry.getKey());
			}
			if(!keysValues.isEmpty())
				jedis.msetnx(keysValues.toArray(new String[0]));
			if(!keysNull.isEmpty())
				jedis.del(keysNull.toArray(new String[0]));
		} finally {
			releaseJedis(jedis);
		}
	}
	
	@Override
	public void set(Collection<KO> c){
		if(null == c || c.isEmpty()) return ;
		Jedis jedis = getJedis();
		try {
			ArrayList<String> keysValues = new ArrayList<String>();
			for(KO value:c)	{
				if(null != value){
					keysValues.add(String.format("\"%s\" \"%s\"", value.returnKey(),this.encoder.toJson(value)));
				}
			}
			if(!keysValues.isEmpty())
				jedis.msetnx(keysValues.toArray(new String[0]));
		} finally {
			releaseJedis(jedis);
		}
	}
}
