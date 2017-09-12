package net.gdface.facelog.message;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * 延迟初始化的 JedisPool封装类（线程安全）
 * @author guyadong
 *
 */
public class RedisPoolLazy {
	private static final Logger logger = LoggerFactory.getLogger(RedisPoolLazy.class);
	private static final SortedSet<RedisPoolLazy> poolSet = Collections.synchronizedSortedSet(new TreeSet<RedisPoolLazy>());
	static {
		// 程序退出时自动销毁连接池对象
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				System.out.println("discard jedis pool");
				for(Iterator<RedisPoolLazy> itor = poolSet.iterator();itor.hasNext();){
					RedisPoolLazy p = itor.next();
					itor.remove();
					if(null != p.pool)
						p.pool.destroy();					
				}
			}});
	}
	public static enum PropName{
		jedisPoolConfig,host,port,password,database,timeout,uri
	}
	
	private static final JedisPoolConfig DEFAULT_CONFIG = new JedisPoolConfig() {
		{
			setMaxTotal(Runtime.getRuntime().availableProcessors());
		}
	};
	
	public static final Map<PropName, Object> DEFAULT_PARAMETERS = Collections.unmodifiableMap(new HashMap<PropName, Object>() {
		private static final long serialVersionUID = 1L;
		{
			DEFAULT_PARAMETERS.put(PropName.jedisPoolConfig, DEFAULT_CONFIG);
			DEFAULT_PARAMETERS.put(PropName.host, Protocol.DEFAULT_HOST);
			DEFAULT_PARAMETERS.put(PropName.port, Protocol.DEFAULT_PORT);
			DEFAULT_PARAMETERS.put(PropName.password, null);
			DEFAULT_PARAMETERS.put(PropName.database, Protocol.DEFAULT_DATABASE);
			DEFAULT_PARAMETERS.put(PropName.timeout, Protocol.DEFAULT_TIMEOUT);
		}
	});

	private static RedisPoolLazy defaultInstance;
	
	public static RedisPoolLazy getDefaultInstance() {
		return null == defaultInstance
				? createDefaultInstance(null) 
				: defaultInstance;
	}

	public static synchronized final RedisPoolLazy createDefaultInstance(Map<PropName,Object> props){
		if(null == defaultInstance){
			defaultInstance = createInstance(props);
		}else
			logger.warn("default instance was initialized already before this invocation");
		return defaultInstance;
	}

	private static <K,V>boolean equals(Map<K,V> m1,Map<K,V> m2){
		if(m1==m2)return true;
		if(null ==m1 || null ==m2)return false;
		if(m1.size() != m2.size())return false;
		for(Entry<K, V> entry:m1.entrySet()){
			K key = entry.getKey();
			if(!m2.containsKey(key))return false;
			V v1 = entry.getValue();
			V v2 = m2.get(key);
			if(v1 ==v2 ) continue;
			if(null ==v1 || null ==v2)return false;
			if(!v1.equals(v2))
				return false;
		}
		return true;
	}
	
	static RedisPoolLazy createInstance(Map<PropName,Object> props) {
		return new RedisPoolLazy(props);
	}
	
	public static synchronized RedisPoolLazy getInstance(Map<PropName,Object> props) {
		HashMap<PropName,Object> params = new HashMap<PropName,Object>(DEFAULT_PARAMETERS);
		if(null != props){
			params.putAll(props);
		}

		for(RedisPoolLazy pool : poolSet){
			if(equals(params,pool.getParameters())){
				return pool;
			}
		}
		return createInstance(params);
	}
	
	private static RedisPoolLazy getInstance( JedisPoolConfig jedisPoolConfig, String host, int port, final String password,
			int database, int timeout, URI uri){
		HashMap<PropName,Object> param = new HashMap<PropName,Object>(DEFAULT_PARAMETERS);
		if(null != jedisPoolConfig)
			param.put(PropName.jedisPoolConfig, jedisPoolConfig);
		if(null != host && !host.isEmpty())
			param.put(PropName.host, host);
		if(0 < port)
			param.put(PropName.port, port);
		param.put(PropName.password, password);
		if(0 <= database)
			param.put(PropName.database, database);
		if( 0 < timeout )
			param.put(PropName.timeout, timeout);
		param.put(PropName.uri, uri);
		return getInstance(param);
	}
	
	public static RedisPoolLazy getInstance(JedisPoolConfig jedisPoolConfig, URI uri, int timeout) {
		if(null == uri)
			throw new NullPointerException(" the 'uri' must not be null");
		return getInstance(jedisPoolConfig,null,0,null,-1,timeout,uri);
	}
	
	public static RedisPoolLazy getInstance(URI uri) {
		return getInstance(DEFAULT_CONFIG,uri,Protocol.DEFAULT_TIMEOUT);
	}

	public static RedisPoolLazy getInstance( JedisPoolConfig jedisPoolConfig, String host, int port, final String password,
			int database, int timeout){
		return getInstance(DEFAULT_CONFIG,host,port,password,database,Protocol.DEFAULT_TIMEOUT, null);
	}
	
	public static RedisPoolLazy getInstance(String host, int port, final String password, int database) {
		return getInstance(DEFAULT_CONFIG,host,port,password,database,Protocol.DEFAULT_TIMEOUT);
	}

	public static RedisPoolLazy getInstance(String host, int port) {
		return getInstance(host,port,null,Protocol.DEFAULT_DATABASE);
	}
	
	public static  RedisPoolLazy getInstance(String host) {
		 return getInstance(host,Protocol.DEFAULT_PORT);
	}

	private final Map<PropName,Object> parameters = new HashMap<PropName,Object>(DEFAULT_PARAMETERS);
	
	public Map<PropName, Object> getParameters() {
		return new HashMap<PropName,Object>(parameters);
	}

	private JedisPool pool;

	public RedisPoolLazy(){
		poolSet.add(this);
	}
	
	protected RedisPoolLazy (Map<PropName,Object> props) {
		this();
		if(null == props || props.isEmpty())
			logger.warn("the 'props' is null or empty,default property will be used");
		else
			this.parameters.putAll(props);
	}
	
	private JedisPool createPool(){
		JedisPool pool;
		JedisPoolConfig jedisPoolConfig = (JedisPoolConfig) parameters.get(PropName.jedisPoolConfig);
		if(null == jedisPoolConfig)
			jedisPoolConfig = DEFAULT_CONFIG;
		int timeout = parameters.containsKey(PropName.timeout)
				? (int)parameters.get(PropName.timeout)
				: Protocol.DEFAULT_TIMEOUT;
		if(parameters.containsKey(PropName.uri)){
			URI uri = (URI)parameters.get(PropName.uri);
			logger.info("initialization parameter {} timeout:{}",uri,timeout);
			pool = new JedisPool(
					jedisPoolConfig,
					(URI)parameters.get(PropName.uri), 
					timeout);
		}else{
			String host = parameters.containsKey(PropName.host)?(String)parameters.get(PropName.host):Protocol.DEFAULT_HOST;
			int port = parameters.containsKey(PropName.port)?(int)parameters.get(PropName.port):Protocol.DEFAULT_PORT;
			int database = parameters.containsKey(PropName.database)?(int)parameters.get(PropName.database):Protocol.DEFAULT_DATABASE;
			logger.info("initialization parameter{}:{} {} timeout:{}",host,port,database,timeout);
			pool = new JedisPool(
					jedisPoolConfig,
					host, 
					port, 
					timeout, 
					(String)parameters.get(PropName.password), 
					database);			
		}		
		logger.info("jedis pool initialized(连接池初始化) OK");
		return pool;
	}
	
	private Jedis getJedis(){
		// double-checked locking
		if(null == pool){
			synchronized (this){
				if(null == pool){
					pool = createPool();
				}
			}
		}
        return pool.getResource();
    }
    
    private void releaseJedis(Jedis jedis) {
        if (jedis != null){
            jedis.close();
        }
    }

	private final ThreadLocal<AtomicInteger> tlNestCount  = new ThreadLocal<AtomicInteger>(){
		@Override
		protected AtomicInteger initialValue() {
			return new AtomicInteger(0);
		}
	};
	
	private final ThreadLocal<Jedis> tlJedis = new ThreadLocal<Jedis>(); 
	
	public Jedis apply(){
		Jedis jedis = this.tlJedis.get();
		if(null == jedis){
			jedis = this.getJedis();
			this.tlJedis.set(jedis);
		}
		tlNestCount.get().incrementAndGet();
		return jedis;
	}
	
	public void free(){
		Jedis jedis =this.tlJedis.get();
		if(null == jedis)
			throw new IllegalStateException("apply/free mismatch");
		if(0 == tlNestCount.get().decrementAndGet()){
			releaseJedis(jedis);
			this.tlJedis.set(null);
		}
	}
}
