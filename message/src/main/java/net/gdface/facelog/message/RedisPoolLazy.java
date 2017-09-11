package net.gdface.facelog.message;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

public class RedisPoolLazy {
	private static final Logger logger = LoggerFactory.getLogger(RedisPoolLazy.class);

	public enum PropName{
		jedisPoolConfig,host,port,password,database,timeout,uri
	}
	
	private static final JedisPoolConfig DEFAULT_CONFIG = new JedisPoolConfig() {
		{
			setMaxTotal(10);
		}
	};
	private final Map<PropName,Object> props = new HashMap<PropName,Object>();
	{
		props.put(PropName.jedisPoolConfig, DEFAULT_CONFIG);
		props.put(PropName.host, Protocol.DEFAULT_HOST);
		props.put(PropName.port, Protocol.DEFAULT_PORT);
		props.put(PropName.password, null);
		props.put(PropName.database, Protocol.DEFAULT_DATABASE);
		props.put(PropName.timeout, Protocol.DEFAULT_TIMEOUT);
	}

	private JedisPool pool;
	
	public RedisPoolLazy(){
	}
	
	public RedisPoolLazy(Map<PropName,Object> props) {
		props.putAll(props);
	}
	public RedisPoolLazy(JedisPoolConfig jedisPoolConfig, URI uri, int timeout) {
		this.props.put(PropName.jedisPoolConfig, jedisPoolConfig);
		this.props.put(PropName.uri, uri);
		this.props.put(PropName.timeout, timeout);
	}
	
	public RedisPoolLazy(URI uri) {
		this.props.put(PropName.uri, uri);
	}
	
	public RedisPoolLazy( JedisPoolConfig jedisPoolConfig, String host, int port, final String password,
			int database, int timeout){
		this.props.put(PropName.jedisPoolConfig, jedisPoolConfig);
		this.props.put(PropName.host, host);
		this.props.put(PropName.port, port);
		this.props.put(PropName.password, password);
		this.props.put(PropName.database, database);
		this.props.put(PropName.timeout, timeout);
	}
	
	public RedisPoolLazy(String host, int port, final String password, int database) {
		this.props.put(PropName.host, host);
		this.props.put(PropName.port, port);
		this.props.put(PropName.password, password);
		this.props.put(PropName.database, database);
	}

	public RedisPoolLazy(String host, int port) {
		this.props.put(PropName.host, host);
		this.props.put(PropName.port, port);
	}
	
	private JedisPool createPool(){
		JedisPoolConfig jedisPoolConfig = (JedisPoolConfig) props.get(PropName.jedisPoolConfig);
		int timeout = props.containsKey(PropName.timeout)?(int)props.get(PropName.timeout):Protocol.DEFAULT_TIMEOUT;
		if(props.containsKey(PropName.uri)){
			return new JedisPool(
					null == jedisPoolConfig?DEFAULT_CONFIG:jedisPoolConfig,
					(URI)props.get(PropName.uri), 
					timeout);
		}else{
			return new JedisPool(
					null == jedisPoolConfig?DEFAULT_CONFIG:jedisPoolConfig,
					props.containsKey(PropName.host)?(String)props.get(PropName.host):Protocol.DEFAULT_HOST, 
					props.containsKey(PropName.port)?(int)props.get(PropName.port):Protocol.DEFAULT_PORT, 
					timeout, 
					(String)props.get(PropName.password), 
					props.containsKey(PropName.database)?(int)props.get(PropName.database):Protocol.DEFAULT_DATABASE);
		}
	}
	
	public Jedis getJedis(){
		if(null == pool ){
			pool = createPool();
			logger.info("连接池初始化");
		}
        return pool.getResource();
    }
    
    public void releaseJedis(Jedis jedis) {
        if (jedis != null){
            jedis.close();
        }
    }
}
