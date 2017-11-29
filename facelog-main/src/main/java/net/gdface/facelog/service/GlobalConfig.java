package net.gdface.facelog.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.nio.file.Paths;
import java.util.EnumMap;
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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import com.facebook.swift.service.ThriftServerConfig;
import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;

import gu.simplemq.redis.JedisPoolLazy.PropName;

import static com.google.common.base.Preconditions.checkArgument;

import io.airlift.units.Duration;
import net.gdface.facelog.db.Constant.JdbcProperty;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 配置参数管理
 * @author guyadong
 *
 */
public class GlobalConfig implements ServiceConstant{
	public static final String HOME_FOLDER = ".facelog";
	public static final String USER_PROPERTIES= "config.properties";
	private static final String ROOT_XML = "root.xml";
	private static final String ATTR_DESCRIPTION ="description"; 
	/** 用户自定义文件位置 ${user.home}/{@value #HOME_FOLDER}/{@value #USER_PROPERTIES} */
	private static final File USER_CONFIG_FILE = Paths.get(System.getProperty("user.home"),HOME_FOLDER,USER_PROPERTIES).toFile();
	/** 全局配置参数对象 */
	private static final CombinedConfiguration CONFIG =readConfig();
	/** 用户定义配置对象 */
	private static final PropertiesConfiguration USER_CONFIG = createUserConfig();
	private GlobalConfig() {
	}
	private static CombinedConfiguration readConfig(){
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
	private static PropertiesConfiguration createUserConfig(){
		PropertiesConfiguration userConfig ;
		if(CONFIG.getNumberOfConfigurations()>1){
			// 有自定义配置文件
			userConfig = (PropertiesConfiguration) CONFIG.getConfiguration(0);	
		}else{
			// 创建一个新的自定义配置文件
			userConfig = new PropertiesConfiguration();
		}
		return userConfig;
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
			thriftServerConfig.setIdleConnectionTimeout(timeout);
		}
		if((intValue = CONFIG.getInt(SERVER_WORKER_THREAD_COUNT,0))>0){
			thriftServerConfig.setWorkerThreads(intValue);
		}
		return thriftServerConfig;
	}
	/** log 输出{@code config}中的关键参数 */
	static final void logThriftServerConfig(ThriftServerConfig config){
		logger.info("RPC Service Parameters(服务运行参数):");
		logger.info("port({}): {}", descriptionOf(SERVER_PORT),config.getPort());
		logger.info("connectionLimit({}): {}", descriptionOf(SERVER_CONNECTION_LIMIT),config.getConnectionLimit());
		logger.info("idleConnectionTimeout({}): {}", descriptionOf(SERVER_IDLE_CONNECTION_TIMEMOUT),config.getIdleConnectionTimeout());
		logger.info("workerThreads({}): {}", descriptionOf(SERVER_WORKER_THREAD_COUNT),config.getWorkerThreads());
	}
	/** 从配置文件中读取REDIS参数 */
	static Map<PropName,Object> makeRedisParameters(){
		HashMap<PropName, Object> params = new HashMap<PropName,Object>(16);
		if(CONFIG.containsKey(REDIS_URI)){
			params.put(PropName.uri, URI.create(CONFIG.getString(REDIS_URI)));
		}
		params.put(PropName.host, CONFIG.getString(REDIS_HOST,null));
		params.put(PropName.port, CONFIG.getInteger(REDIS_PORT,null));
		params.put(PropName.database, CONFIG.getInteger(REDIS_DATABASE,null));
		params.put(PropName.timeout, CONFIG.getInteger(REDIS_TIMEOUT,null));
		params.put(PropName.password, CONFIG.getString(REDIS_PASSWORD,null));

		if(CONFIG.containsKey(REDIS_POOL_MAXTOTAL)){
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxTotal(CONFIG.getInt(REDIS_POOL_MAXTOTAL));
			params.put(PropName.jedisPoolConfig, poolConfig);
		}
		// 过滤掉所有为null的参数
		return Maps.filterValues(params, Predicates.notNull());

	}
	/** log 输出REDIS参数 */
	static final void logRedisParameters(Map<PropName,Object>params){
		logger.info("redis 服务器参数:");
		if(params.containsKey(PropName.uri)){
			logger.info("{}({}):{}", PropName.uri,descriptionOf(REDIS_URI),params.get(PropName.uri));
		}else{
			if(params.containsKey(PropName.host)){
				logger.info("{}({}):{}", PropName.host,descriptionOf(REDIS_HOST),params.get(PropName.host));
			}
			if(params.containsKey(PropName.port)){
				logger.info("{}({}):{}", PropName.port,descriptionOf(REDIS_PORT),params.get(PropName.port));
			}
			if(params.containsKey(PropName.database)){
				logger.info("{}({}):{}", PropName.database,descriptionOf(REDIS_DATABASE),params.get(PropName.database));
			}
			if(params.containsKey(PropName.password)){
				logger.info("{}({}):{}", PropName.password,descriptionOf(REDIS_PASSWORD),params.get(PropName.password));
			}
		}
		if(params.containsKey(PropName.timeout)){
			logger.info("{}({}):{}", PropName.timeout,descriptionOf(REDIS_TIMEOUT),params.get(PropName.timeout));
		}

		if(params.containsKey(PropName.jedisPoolConfig)){
			logger.info("{}({}):{}", "jedisPoolConfig.maxTotal",descriptionOf(REDIS_POOL_MAXTOTAL),
					((JedisPoolConfig)params.get(PropName.jedisPoolConfig)).getMaxTotal());
		}
		if(CONFIG.containsKey(REDIS_HOME)){
			logger.info("{}:{}",descriptionOf(REDIS_HOME),CONFIG.getString(REDIS_HOME));
		}
	}
	/** log 输出令牌管理参数 */
	static void logTokenParameters(){
		logger.info("令牌管理参数:");
		logger.info("{}:{}",
				descriptionOf(TOKEN_DEVICE_VALIDATE),
				CONFIG.getBoolean(TOKEN_DEVICE_VALIDATE));
		logger.info("{}:{}",
				descriptionOf(TOKEN_PERSON_VALIDATE),
				CONFIG.getBoolean(TOKEN_PERSON_VALIDATE));
		logger.info("{}:{}",
				descriptionOf(TOKEN_PERSON_EXPIRE),
				CONFIG.getInt(TOKEN_PERSON_EXPIRE));		
	}
	
	/**
	 * 从配置文件中读取数据库配置参数
	 * @return
	 */
	static EnumMap<JdbcProperty,String> makeDatabaseConfig(){
		EnumMap<JdbcProperty, String> params = new EnumMap<JdbcProperty,String>(JdbcProperty.class);
		for(JdbcProperty prop: JdbcProperty.values()){
			String value = CONFIG.getString(prop.withPrefix(PREFIX_DATABASE), null);
			if(null !=value){
				params.put(prop, value);
			}
		}
		return params;		
	}
	static Map<String,String> toStringKey(Map<JdbcProperty,String> params){
		ImmutableMap<String, Entry<JdbcProperty, String>> m1 = Maps.uniqueIndex(params.entrySet(), new Function<Entry<JdbcProperty,String>,String>(){
			@Override
			public String apply(Entry<JdbcProperty, String> input) {
				return input.getKey().key;
			}});
		return Maps.transformValues(m1, new Function<Entry<JdbcProperty, String>,String>(){
			@Override
			public String apply(Entry<JdbcProperty, String> input) {
				return input.getValue();
			}});
	}
	/** log 输出数据库配置参数 */
	static void logDatabaseProperties(Map<JdbcProperty,String> params){
		for(Entry<JdbcProperty, String> entry:params.entrySet()){
			String value = entry.getValue();
			if(null != value){
				logger.info("{}({}):{}",entry.getKey().key,descriptionOf(entry.getKey().withPrefix(PREFIX_DATABASE)),value);
			}
		}
	}
	/**
	 * 修改指定的配置参数
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key,Object value){
		CONFIG.setProperty(key, value);
		USER_CONFIG.setProperty(key, value);
	}
	/**
	 * 保存修改的配置到自定义配置文件中
	 */
	public static void persistence() {
		try {
			USER_CONFIG.setIOFactory(new IOFactoryNoescape());
			OutputStreamWriter wirter = new OutputStreamWriter(
					new FileOutputStream(USER_CONFIG_FILE));
			USER_CONFIG.write(wirter);
			wirter.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
