package net.gdface.facelog.message;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
					if(null != p.pool)
						p.pool.destroy();
					itor.remove();
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
	private final Map<PropName,Object> props = new HashMap<PropName,Object>();
	public Map<PropName, Object> getParameters() {
		return new HashMap<PropName,Object>(props);
	}

	{
		props.put(PropName.jedisPoolConfig, DEFAULT_CONFIG);
		props.put(PropName.host, Protocol.DEFAULT_HOST);
		props.put(PropName.port, Protocol.DEFAULT_PORT);
		props.put(PropName.password, null);
		props.put(PropName.database, Protocol.DEFAULT_DATABASE);
		props.put(PropName.timeout, Protocol.DEFAULT_TIMEOUT);
	}

	private JedisPool pool;
	
	private static RedisPoolLazy defaultInstance;
	
	public static RedisPoolLazy getDefaultInstance() {
		return null == defaultInstance
				? createDefaultInstance(null) 
				: defaultInstance;
	}

	public static synchronized final RedisPoolLazy createDefaultInstance(Map<PropName,Object> props){
		if(null == defaultInstance){
			defaultInstance = new RedisPoolLazy(props);
		}else
			logger.warn("default instance was initialized already before this invocation");
		return defaultInstance;
	}
	
	public RedisPoolLazy(){
		poolSet.add(this);
	}
	
	public RedisPoolLazy(Map<PropName,Object> props) {
		this();
		if(null == props || props.isEmpty())
			logger.warn("the 'props' is null or empty,default property will be used");
		else
			this.props.putAll(props);
	}
	public RedisPoolLazy(JedisPoolConfig jedisPoolConfig, URI uri, int timeout) {
		this();
		if(null != jedisPoolConfig)
			this.props.put(PropName.jedisPoolConfig, jedisPoolConfig);
		this.props.put(PropName.uri, uri);
		if( 0 < timeout )
			this.props.put(PropName.timeout, timeout);
	}
	
	public RedisPoolLazy(URI uri) {
		this(DEFAULT_CONFIG,uri,Protocol.DEFAULT_TIMEOUT);
	}
	
	public RedisPoolLazy( JedisPoolConfig jedisPoolConfig, String host, int port, final String password,
			int database, int timeout){
		this();
		if(null != jedisPoolConfig)
			this.props.put(PropName.jedisPoolConfig, jedisPoolConfig);
		if(null != host && !host.isEmpty())
			this.props.put(PropName.host, host);
		if(0 < port)
			this.props.put(PropName.port, port);
		this.props.put(PropName.password, password);
		if(0 <= database)
			this.props.put(PropName.database, database);
		if( 0 < timeout )
			this.props.put(PropName.timeout, timeout);
	}
	
	public RedisPoolLazy(String host, int port, final String password, int database) {
		this(DEFAULT_CONFIG,host,port,password,database,Protocol.DEFAULT_TIMEOUT);
	}

	public RedisPoolLazy(String host, int port) {
		this(host,port,null,Protocol.DEFAULT_DATABASE);
	}
	
	public RedisPoolLazy(String host) {
		this(host,Protocol.DEFAULT_PORT);
	}
	
	private JedisPool createPool(){
		JedisPool pool;
		JedisPoolConfig jedisPoolConfig = (JedisPoolConfig) props.get(PropName.jedisPoolConfig);
		if(null == jedisPoolConfig)
			jedisPoolConfig = DEFAULT_CONFIG;
		int timeout = props.containsKey(PropName.timeout)
				? (int)props.get(PropName.timeout)
				: Protocol.DEFAULT_TIMEOUT;
		if(props.containsKey(PropName.uri)){
			URI uri = (URI)props.get(PropName.uri);
			logger.info("initialization parameter {} timeout:{}",uri,timeout);
			pool = new JedisPool(
					jedisPoolConfig,
					(URI)props.get(PropName.uri), 
					timeout);
		}else{
			String host = props.containsKey(PropName.host)?(String)props.get(PropName.host):Protocol.DEFAULT_HOST;
			int port = props.containsKey(PropName.port)?(int)props.get(PropName.port):Protocol.DEFAULT_PORT;
			int database = props.containsKey(PropName.database)?(int)props.get(PropName.database):Protocol.DEFAULT_DATABASE;
			logger.info("initialization parameter{}:{} {} timeout:{}",host,port,database,timeout);
			pool = new JedisPool(
					jedisPoolConfig,
					host, 
					port, 
					timeout, 
					(String)props.get(PropName.password), 
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
