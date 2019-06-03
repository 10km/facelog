package net.gdface.facelog;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import gu.simplemq.redis.JedisPoolLazy;
import gu.simplemq.redis.JedisPoolLazy.PropName;
import gu.simplemq.redis.JedisUtils;
import net.gdface.utils.NetworkUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisDataException;

import static com.google.common.base.Preconditions.*;
/**
 * 
 * redis管理模块
 * @author guyadong
 *
 */
class RedisManagement implements ServiceConstant{
	private static final String CMD_PREFIX = "cmd_";
	private static final String TASK_PREFIX = "task_";
	private static final String LOG_MONITOR_PREFIX = "log_monitor_";
	private static final String HEARTBEAT_MONITOR_PREFIX = "hb_monitor_";

	private static String redisURI;
	/** 本地redis服务器启动标志 */
	private static boolean localServerStarted = false;
	/** redis数据库配置参数 */
	private static Map<PropName, Object> parameters;
	private static Process webredisProcess;
	private static String webredisURL = null;
	/** webredis服务参数 */
	private final static Map<String, Object> webredisParameters;
	/** 消息系统(redis)基本参数  */
	private final ImmutableMap<MQParam,String> redisParam ;
	/** 所有任务队列key */
	private static final Set<String> taskKeys = Sets.newConcurrentHashSet();
	static{
		parameters = GlobalConfig.makeRedisParameters();
		webredisParameters = GlobalConfig.makeWebredisParameters();
		JedisPoolLazy.createDefaultInstance(parameters);
		redisURI = JedisPoolLazy.getDefaultInstance().getCanonicalURI().toString();
		/** 程序结束时关闭 redis 服务器 */
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				shutdownLocalWebredisServer();
				shutdownLocalServer();
			}
		});
	}
	protected RedisManagement() {
		init();
		initWebredis();
		Builder<MQParam, String> builder = ImmutableMap.<MQParam,String>builder()
			.put(MQParam.REDIS_URI, redisURI)
			.put(MQParam.CMD_CHANNEL, createCmdChannel())
			.put(MQParam.LOG_MONITOR_CHANNEL, createLogMonitorChannel())
			.put(MQParam.HB_MONITOR_CHANNEL, createHeartbeatMonitorChannel())
			.put(MQParam.HB_INTERVAL, CONFIG.getInteger(HEARTBEAT_INTERVAL, DEFAULT_HEARTBEAT_PERIOD).toString())
			.put(MQParam.HB_EXPIRE, CONFIG.getInteger(HEARTBEAT_EXPIRE, DEFAULT_HEARTBEAT_EXPIRE).toString());
		if(!Strings.isNullOrEmpty(webredisURL)){
			builder.put(MQParam.WEBREDIS_URL, webredisURL);
		}
		redisParam = builder.build();
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
	private static String taskKeyOf(String taskname){
		return TASK_PREFIX + taskname + "_";
	}
	/**
	 * 用当前时间生成一个随机的字符串值存到Redis服务器上({@code key})
	 * @param key 常量名 redis上key
	 * @param prefix 随机字符串前缀
	 * @return
	 */
	private String createRandomConstOnRedis(String key,String prefix){
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
		return System.getProperty("os.name").startsWith("Windows")?"cmd /c":"sh -c";
	}
	/** 启动本地 redis 服务器 */
	private static final  void startLocalServer(Map<PropName, Object> parameters){
		String home = CONFIG.getString(REDIS_HOME,"");
		checkArgument(!home.isEmpty(),"NOT DEFINE(参数没有定义) %s",REDIS_HOME);
		// redis-server 可执行程序路径
		String exe = new StringBuffer()
				.append(home)
				.append(File.separator)
				.append(REDIS_SERVER_EXE)
				.append(suffixOfExe()).toString();
		checkArgument(new File(exe).canExecute(),"NOT EXECUTABLE FILE(非可执行文件名) %s",exe);

		ArrayList<String> args = Lists.newArrayList(exe);
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
			String cmd;
			if(System.getProperty("os.name").startsWith("Windows")){
				cmd = shell() + " " + Joiner.on(' ').join(args);	
			}else{
				cmd = shell() + " \"" + Joiner.on(' ').join(args) + "\"";
			}
			
			logger.debug("cmd(启动命令): {}",cmd);
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			throw new RuntimeException(e);
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
				for(String key:taskKeys){
					jedis.del(key);
				}
				taskKeys.clear();
				jedis.shutdown();
			}finally{
				jedis.close();
			}
		}
	}
	/** 
	 * 根据提供的参数启动本地 webredis 服务(node.js) 
	 * @param parameters 
	 * @return node.js进程
	 */
	private static Process startLocalWebredis(Map<String, Object> parameters){
		String nodejsExe = (String) parameters.get(NODEJS_EXE);
		String webredisFile = (String) parameters.get(WEBREDIS_FILE);

		ArrayList<String> args = Lists.newArrayList();
		args.add(nodejsExe);
		args.add(webredisFile);
		// 命令行指定端口
		if(parameters.containsKey(WEBREDIS_PORT)){
			args.add("--port=" + parameters.get(WEBREDIS_PORT));
		}
		/** 优先使用  WEBREDIS_RURL  */
		if(parameters.containsKey(WEBREDIS_RURI)){
			// 命令行指定redis 连接url
			args.add("--rurl=" + parameters.get(WEBREDIS_RURI));
		}else{
			// 命令行指定redis 主机
			if(parameters.containsKey(WEBREDIS_RHOST)){
				args.add("--rhost=" + parameters.get(WEBREDIS_RHOST));
			}
			// 命令行指定redis 端口
			if(parameters.containsKey(WEBREDIS_RPORT)){
				args.add("--rport=" + parameters.get(WEBREDIS_RPORT));
			}
			// 命令行指定redis 连接密码
			if(parameters.containsKey(WEBREDIS_RAUTH)){
				args.add("--rauth=" + parameters.get(WEBREDIS_RAUTH));
			}
			// 命令行指定redis 数据库
			if(parameters.containsKey(WEBREDIS_RDB)){
				args.add("--rdb=" + parameters.get(WEBREDIS_RDB));
			}
		}
		try {
			logger.info("start webredis server(启动webredis服务器)");
			String cmd = Joiner.on(' ').join(args);
			logger.debug("cmd(启动命令): {}",cmd);
			return new ProcessBuilder(args)
					.redirectError(Redirect.INHERIT)
					.redirectOutput(Redirect.INHERIT)
					.start();

		} catch (IOException e) {
			throw new RuntimeException(e);
		} 
	}
	
	/** 中止本地 webredis 服务 */
	private static void  shutdownLocalWebredisServer(){
		if(webredisProcess != null){
			try {
				webredisProcess.exitValue();				
			} catch (IllegalThreadStateException  e) {
				logger.info("shutdown webredis server(关闭webredis服务)");
				webredisProcess.destroy();
			}
			webredisProcess = null;
		}
	}
	private void initWebredis(){
		String host = (String) webredisParameters.get(WEBREDIS_HOST);
		int port = (int) webredisParameters.get(WEBREDIS_PORT);
		String webredisURL = String.format("http://%s:%d",host,port);
		Predicate<String> responseValidator = new Predicate<String>() {

			@Override
			public boolean apply(String input) {
				return null == input ? false : input.startsWith("webredis");
			}
		};
		if(NetworkUtil.selfBind(host)){
			if(webredisParameters.containsKey(NODEJS_EXE) 
					&& webredisParameters.containsKey(WEBREDIS_FILE)){

				if(!NetworkUtil.testHttpConnectChecked(webredisURL, responseValidator)){
					webredisProcess = startLocalWebredis(webredisParameters);
					for(int c=0;c<3;++c){
						try {
							Thread.sleep(2000);
							try {
								webredisProcess.exitValue();
								break;
							} catch (IllegalThreadStateException e) {
								if(NetworkUtil.testHttpConnectChecked(webredisURL, responseValidator)){
									RedisManagement.webredisURL = webredisURL;
									return;
								}
							}

						} catch (InterruptedException e) {
							break;
						}
					}
					throw new IllegalStateException("FAIL TO START WEBREDIS SERVER(启动webredis失败) ");
				}
			}
		}else	 if(!NetworkUtil.testHttpConnectChecked(webredisURL, responseValidator)){
			// 检查指定的webredis外部连接是否可访问
			logger.warn("INVALID WEBREDIS SERVER(webredis服务不可访问) http://{}:{}",host,port);			
		}
		RedisManagement.webredisURL = webredisURL;
	}
	/** 返回redis访问参数 */
	protected Map<MQParam,String> getRedisParameters(){
		return this.redisParam;
	}
	/**
	 * 注册一个任务名<br>
	 * 方法将会根据任务名在redis上生成一个对应的队列<br>
	 * 对同一个任务名多次调用本方法，不会产生不同的队列名字
	 * @param task 任务名
	 * @return 返回保存队列名的key
	 */
	protected String taskRegister(String task) {
		checkArgument(Strings.isNullOrEmpty(task),"task is empty or null");
		String key = taskKeyOf(task);
		createRandomConstOnRedis(key,key);
		taskKeys.add(key);
		return key;
	}
	/**
	 * 根据任务名返回redis队列名
	 * @param task 任务名
	 * @return 返回redis队列名,队列不存在则返回{@code null}
	 */
	protected String taskQueueOf(String task) {	
		checkArgument(Strings.isNullOrEmpty(task),"task is empty or null");
		return JedisUtils.get(taskKeyOf(task));
	}
}
