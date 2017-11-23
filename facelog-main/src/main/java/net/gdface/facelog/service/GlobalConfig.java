package net.gdface.facelog.service;

import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.tree.DefaultExpressionEngine;
import org.apache.commons.configuration2.tree.DefaultExpressionEngineSymbols;

import com.facebook.swift.service.ThriftServerConfig;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;

import io.airlift.units.Duration;

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
	 * 返回描述({@code 'description'})attribute表达式
	 * @param key
	 * @return
	 * @see #expressionOfAttribute(String, String)
	 */
	public static final String descriptionOf(String key){
		return expressionOfAttribute(key,ATTR_DESCRIPTION);
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
}
