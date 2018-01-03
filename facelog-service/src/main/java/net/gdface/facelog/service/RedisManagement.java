package net.gdface.facelog.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.facebook.swift.codec.ThriftStruct;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.JedisPoolLazy.PropName;
import gu.simplemq.redis.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

/**
 * 
 * redis管理模块
 * @author guyadong
 *
 */
class RedisManagement implements ServiceConstant{
	/**
	 * 消息系统(redis)基本参数名
	 * @author guyadong
	 *
	 */
	@ThriftStruct
	public enum MQParam {
		/** redis服务器地址 */REDIS_URI,
		/** 设备命令通道名 */CMD_CHANNEL,
		/** 人员验证实时监控通道名 */LOG_MONITOR_CHANNEL,
		/** 设备心跳实时监控通道名 */HB_MONITOR_CHANNEL,
		/** 设备心跳包间隔时间(秒) */HB_INTERVAL,
		/** 设备心跳包失效时间(秒) */HB_EXPIRE
	}
	private static final String CMD_PREFIX = "cmd_";
	private static final String LOG_MONITOR_PREFIX = "log_monitor_";
	private static final String HEARTBEAT_MONITOR_PREFIX = "hb_monitor_";

	private static String redisURI;
	/** 本地redis服务器启动标志 */
	private static boolean localServerStarted = false;
	/** redis数据库配置参数 */
	private static Map<PropName, Object> parameters;
	/** 消息系统(redis)基本参数  */
	private final ImmutableMap<MQParam,String> redisParam ;
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
	protected RedisManagement() {
		init();
		redisParam = ImmutableMap.<MQParam,String>builder()
			.put(MQParam.REDIS_URI, redisURI)
			.put(MQParam.CMD_CHANNEL, createCmdChannel())
			.put(MQParam.LOG_MONITOR_CHANNEL, createLogMonitorChannel())
			.put(MQParam.HB_MONITOR_CHANNEL, createHeartbeatMonitorChannel())
			.put(MQParam.HB_INTERVAL, CONFIG.getInteger(HEARTBEAT_INTERVAL, DEFAULT_HEARTBEAT_PERIOD).toString())
			.put(MQParam.HB_EXPIRE, CONFIG.getInteger(HEARTBEAT_EXPIRE, DEFAULT_HEARTBEAT_EXPIRE).toString())
			.build();
		GlobalConfig.logRedisParameters(JedisPoolLazy.getDefaultInstance().getParameters());
	}
	/** 创建随机命令通道 */
	private String createCmdChannel(){
		return createRandomConstOnRedis(KEY_CMD_CHANNEL,CMD_PREFIX);
	}
	/** 创建随机人员验证实时监控通道名 */
	private String createLogMonitorChannel(){
		return createRandomConstOnRedis(KEY_LOG_MONITOR_CHANNEL,LOG_MONITOR_PREFIX);
	}
	/** 创建随机设备心跳实时监控通道名 */
	private String createHeartbeatMonitorChannel(){
		return createRandomConstOnRedis(KEY_HB_MONITOR_CHANNEL,HEARTBEAT_MONITOR_PREFIX);
	}
	/**
	 * 用当前时间生成一个随机的字符串值存到Redis服务器上({@code key})
	 * @param key 常量名 redis上key
	 * @param prefix 随机字符串前缀
	 * @return
	 */
	private String createRandomConstOnRedis(String key,String prefix){
		// 初始化redis 全局常量 KEY_LOG_MONITOR_CHANNEL
		String timestamp = String.format("%06x", System.nanoTime());
		String monitorChannel = prefix + timestamp.substring(timestamp.length()-6, timestamp.length());
		return JedisUtils.setnx(key,monitorChannel);		
	}
	/** redis 连接初始化,并测试连接,如果连接异常,则尝试启动本地redis服务器或等待redis server启动 */
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
						startLocalServer(parameters);
						localServerStarted = true;	
					}else {
						throw new RuntimeException(
								String.format("cann't connect redis server(无法连接redis服务器,请检查redis服务器是否启动以及密码是否正确) %s",redisURI),e);
					}
				} else if(waitIfAbsent && tryCountLimit-- > 0){
					logger.info("waiting for redis server...{}",tryCountLimit);
				} else {
					throw new RuntimeException(String.format("cann't connect redis server(无法连接redis服务器) %s",redisURI),e);
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
		throw new RuntimeException(String.format("cann't connect redis server(无法连接redis服务器) %s",redisURI));

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
			// redis-server 可执行程序路径
			String exe = new StringBuffer()
					.append(home)
					.append(File.separator)
					.append(REDIS_SERVER_EXE)
					.append(suffixOfExe()).toString();
			if(new File(exe).canExecute()){
				ArrayList<String> args = Lists.newArrayList(shell(),exe);
				// 命令行指定端口
				if(parameters.containsKey(PropName.port)){
					args.add("--port " + parameters.get(PropName.port));
				}
				// 命令行指定password
				if(parameters.containsKey(PropName.password)){
					args.add("--requirepass " + parameters.get(PropName.password));
				}
				try {
					logger.info("start redis server(启动redis服务器)");
					String cmd = Joiner.on(' ').join(args);
					logger.debug("cmd(启动命令): {}",cmd);
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
				// shutdown前清除全局变量避免持久化
				jedis.del(KEY_CMD_SN);
				jedis.del(KEY_ACK_SN);
				jedis.del(KEY_CMD_CHANNEL);
				jedis.del(KEY_LOG_MONITOR_CHANNEL);
				jedis.del(KEY_HB_MONITOR_CHANNEL);
				jedis.shutdown();
			}finally{
				jedis.close();
			}
		}
	}
	/** 返回redis访问参数 */
	protected Map<MQParam,String> getRedisParameters(){
		return this.redisParam;
	}
}
