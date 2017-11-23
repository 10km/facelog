package net.gdface.facelog.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;
import com.google.common.collect.Maps;

import com.facebook.swift.service.ThriftServerConfig;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;

import gu.simplemq.redis.JedisPoolLazy.PropName;

import static com.google.common.base.Preconditions.checkArgument;

import io.airlift.units.Duration;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 配置参数管理
 * @author guyadong
 *
 */
public class GlobalConfig implements ServiceConstant{
	private static final String ROOT_XML = "root.xml";
	private static final String ATTR_DESCRIPTION ="description"; 
	/** 配置参数对象 */
	private static final CombinedConfiguration CONFIG = init();
	private GlobalConfig() {
	}
	private static CombinedConfiguration init(){
		try{
			// 指定文件编码方式,否则properties文件读取中文会是乱码,要求文件编码是UTF-8
		    FileBasedConfigurationBuilder.setDefaultEncoding(PropertiesConfiguration.class, "UTF-8");
		    // 使用默认表达式引擎
			DefaultExpressionEngine engine = new DefaultExpressionEngine(DefaultExpressionEngineSymbols.DEFAULT_SYMBOLS);
			Configurations configs = new Configurations();
			CombinedConfiguration config = configs.combined(GlobalConfig.class.getClassLoader().getResource(ROOT_XML));
			config.setExpressionEngine(engine);
			return config;
		}catch(Exception e){
			throw new ExceptionInInitializerError(e);
		}
	}
	/**
	 * 返回{@code key}的attribute的表达式
	 * @param key
	 * @param attr attribute name
	 * @return
	 * @throws IllegalArgumentException 输入参数为{@code null}或空
	 */
	public static final String expressionOfAttribute(String key,String attr){
		checkArgument(!Strings.isNullOrEmpty(key) && !Strings.isNullOrEmpty(attr),"input argument must not be null or empty");
		return String.format("%s[@%s]", key,attr);		
	}
	/**
	 * 返回{@code key}的描述({@code 'description'})
	 * @param key
	 * @return
	 * @see #expressionOfAttribute(String, String)
	 */
	public static final String descriptionOf(String key){
		return CONFIG.getString(expressionOfAttribute(key,ATTR_DESCRIPTION),"");
	}
	/** 全局配置参数对象 */
	public static CombinedConfiguration getConfig() {
		return CONFIG;
	}
	/**
	 * 从配置文件中读取参数创建{@link ThriftServerConfig}实例
	 * @return
	 */
	static ThriftServerConfig makeThriftServerConfig(){
		ThriftServerConfig thriftServerConfig = new ThriftServerConfig();
		int intValue ;
		thriftServerConfig.setPort(CONFIG.getInt(SERVER_PORT,DEFAULT_PORT));
		if((intValue  = CONFIG.getInt(SERVER_CONNECTION_LIMIT,0)) >0){
			thriftServerConfig.setConnectionLimit(intValue);
		}
		if((intValue = CONFIG.getInt(SERVER_IDLE_CONNECTION_TIMEMOUT,0))>0){
			Duration timeout = new Duration(intValue,TimeUnit.SECONDS);
			thriftServerConfig.setIdleConnectionTimeout(	timeout);
		}
		if((intValue = CONFIG.getInt(SERVER_WORKER_THREAD_COUNT,0))>0){
			thriftServerConfig.setWorkerThreads(intValue);
		}
		return thriftServerConfig;
	}
	/** log 输出{@code config}中的关键参数 */
	static final void showThriftServerConfig(ThriftServerConfig config){
		logger.info("Service Parameter: port: {}", config.getPort());
		logger.info("Service Parameter: connectionLimit: {}", config.getConnectionLimit());
		logger.info("Service Parameter: idleConnectionTimeout: {}", config.getIdleConnectionTimeout());
		logger.info("Service Parameter: workerThreads: {}", config.getWorkerThreads());
	}
	/** 从配置文件中读取REDIS参数 */
	static Map<PropName,Object> makeRedisParameters(){
		HashMap<PropName, Object> params = new HashMap<PropName,Object>(16);
		boolean bak = CONFIG.isThrowExceptionOnMissing();
		try{
			CONFIG.setThrowExceptionOnMissing(false);
			params.put(PropName.host, CONFIG.getString(REDIS_HOST));
			params.put(PropName.port, CONFIG.getInt(REDIS_PORT));
			params.put(PropName.database, CONFIG.getInt(REDIS_DATABASE));
			params.put(PropName.timeout, CONFIG.getInt(REDIS_TIMEOUT));
			params.put(PropName.password, CONFIG.getString(REDIS_PASSWORD));
			
			if(CONFIG.containsKey(REDIS_POOL_MAXTOTAL)){
				JedisPoolConfig poolConfig = new JedisPoolConfig();
				poolConfig.setMaxTotal(CONFIG.getInt(REDIS_POOL_MAXTOTAL));
				params.put(PropName.jedisPoolConfig, poolConfig);
			}
			// 过滤掉所有为null的参数
			return Maps.filterValues(params, Predicates.notNull());
		}finally{
			CONFIG.setThrowExceptionOnMissing(bak);
		}
	}
	/** log 输出REDIS参数 */
	static final void showRedisParameters(Map<PropName,Object>params){
		if(params.containsKey(PropName.host)){
			logger.info("{}({}):{}", PropName.host,descriptionOf(REDIS_HOST),params.get(PropName.host));
		}
		if(params.containsKey(PropName.port)){
			logger.info("{}({}):{}", PropName.port,descriptionOf(REDIS_PORT),params.get(PropName.port));
		}
		if(params.containsKey(PropName.database)){
			logger.info("{}({}):{}", PropName.database,descriptionOf(REDIS_DATABASE),params.get(PropName.database));
		}
		if(params.containsKey(PropName.timeout)){
			logger.info("{}({}):{}", PropName.timeout,descriptionOf(REDIS_TIMEOUT),params.get(PropName.timeout));
		}
		if(params.containsKey(PropName.password)){
			logger.info("{}({}):{}", PropName.password,descriptionOf(REDIS_PASSWORD),params.get(PropName.password));
		}
		if(params.containsKey(PropName.jedisPoolConfig)){
			logger.info("{}({}):{}", "maxTotal",descriptionOf(REDIS_POOL_MAXTOTAL),
					((JedisPoolConfig)params.get(PropName.jedisPoolConfig)).getMaxTotal());
		}
	}
}
