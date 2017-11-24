package net.gdface.facelog.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.weakref.jmx.com.google.common.base.Joiner;

import com.google.common.collect.Lists;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.JedisPoolLazy.PropName;
import gu.simplemq.redis.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * redis管理模块
 * @author guyadong
 *
 */
class RedisManagement implements ServiceConstant{	
	private static String redisURI;
	/** 本地redis服务器启动标志 */
	private static boolean localServerStarted = false;
	private String commendChannel;
	private static Map<PropName, Object> parameters;
	static{
		parameters = GlobalConfig.makeRedisParameters();
		JedisPoolLazy.createDefaultInstance(parameters);
		redisURI = JedisPoolLazy.getDefaultInstance().getCanonicalURI().toString();
		/** 程序结束时关闭 redis 服务器 */
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				shutdownLocalServer();
			}
			
		});
	}
	public RedisManagement() {
		init();
		GlobalConfig.logRedisParameters(JedisPoolLazy.getDefaultInstance().getParameters());
	}
	/** 返回redis服务器地址 */
	public String getRedisURI() {
		return redisURI;
	}
	private void init(){
		JedisPoolLazy poolLazy = JedisPoolLazy.getDefaultInstance();
		boolean waitIfAbsent = CONFIG.getBoolean(REDIS_WAITIFABSENT,false);
		boolean selfBind = NetworkUtil.selfBind(poolLazy.getCanonicalURI().getHost());
		int tryCountLimit = CONFIG.getInt(REDIS_TRYCOUNT);
		long tryInterval = CONFIG.getLong(REDIS_TRYINTERVAL);
		do{
			try{
				// 测试服务器连接和访问
				Jedis jedis = poolLazy.apply();
				try{
					jedis.keys("*");
				}finally{
					poolLazy.free();
				}
				return;
			}catch(JedisConnectionException e){
				if(selfBind){
					// 如果 redis服务器指定为本地启动则尝试启动服务器
					if( CONFIG.containsKey(REDIS_HOME) && !localServerStarted){
						logger.info("try start redis server");
						startLocalServer(parameters);
						localServerStarted = true;	
					}else {
						throw new RuntimeException(String.format("cann't connect redis server(无法连接redis服务器) %s",getRedisURI()),e);
					}
				} else if(waitIfAbsent && tryCountLimit-- > 0){
					logger.info("waiting for redis server...{}",tryCountLimit);
				} else {
					throw new RuntimeException(String.format("cann't connect redis server(无法连接redis服务器) %s",getRedisURI()),e);
				}				
			}catch(JedisDataException e){
				throw new RuntimeException(String.format("access error(服务器访问异常),%s", e.getMessage()),e);
			}
			try {
				Thread.sleep(tryInterval);
			} catch (InterruptedException e) {
				break;
			}
		}while(tryCountLimit >0);
		throw new RuntimeException(String.format("cann't connect redis server(无法连接redis服务器) %s",getRedisURI()));

	}
	private static final String REDIS_SERVER_EXE = "redis-server";
	private static final String suffixOfExe(){
		return System.getProperty("os.name").startsWith("Windows")?".exe":"";
	}
	private static final String shell(){
		return System.getProperty("os.name").startsWith("Windows")?"cmd /c":"sh";
	}
	/** 启动本地 redis 服务器 */
	private static final  void startLocalServer(Map<PropName, Object> parameters){
		String home = CONFIG.getString(REDIS_HOME,"");
		if(!home.isEmpty()){
			String exe = new StringBuffer()
					.append(home)
					.append(File.separator)
					.append(REDIS_SERVER_EXE)
					.append(suffixOfExe()).toString();
			if(new File(exe).canExecute()){
				ArrayList<String> args = Lists.newArrayList(shell(),exe);
				if(parameters.containsKey(PropName.port)){
					args.add("--port " + PropName.port);
				}
				if(parameters.containsKey(PropName.password)){
					args.add("--requirepass " + PropName.password);
				}
				try {
					String cmd = Joiner.on(' ').join(args);
					logger.info("start redis server(启动redis服务器): {}",cmd);
					Runtime.getRuntime().exec(cmd);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}else{
				throw new IllegalArgumentException(String.format("INVALID FILE(无效文件名) %s",exe));
			}
		}else{
			throw new IllegalArgumentException(String.format("NOT DEFINE(参数没有定义) %s",REDIS_HOME));
		}
	}
	/** 中止本地 redis 服务器*/
	private static void  shutdownLocalServer(){
		if(localServerStarted){
			logger.info("shutdown redis server(关闭redis服务器)");
			HashMap<PropName, Object> param = JedisPoolLazy.initParameters(parameters);
			Jedis jedis = new Jedis(JedisUtils.getCanonicalURI(param));
			try{
				jedis.shutdown();
			}finally{
				jedis.close();
			}
		}
	}
}
