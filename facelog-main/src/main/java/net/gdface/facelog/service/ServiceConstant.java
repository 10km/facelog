package net.gdface.facelog.service;

import org.apache.commons.configuration2.CombinedConfiguration;

import gu.simplemq.Channel;

/**
 * 服务端常量定义
 * @author guyadong
 *
 */
public interface ServiceConstant extends CommonConstant{
	/** 设备访问令牌表,{@code 设备ID -> token}  */
    public static final Channel<Token> TABLE_DEVICE_TOKEN = new Channel<Token>("DeviceToken"){} ;
    /** 人员访问令牌表 {@code 人员ID -> token} */
    public static final Channel<Token> TABLE_PERSON_TOKEN = new Channel<Token>("PersonToken"){} ;
    /////////////  REDIS KEYS ////////////////////////////
	/** redis 全局变量 : 设备命令序列号, 调用{@link RedisManagement#applyCmdSn()}时每次加1,保证序列号在全网络唯一 */
	String KEY_CMD_SN = "CMD_SN";
	/** redis 全局变量 : 设备响应通道序列号, 调用{@link RedisManagement#applyCmdSn()}时每次加1,保证序列号在全网络唯一 */
	String KEY_ACK_SN = "ACK_CHANNEL";
	/** redis 全局常量 : 设备命令通道 */
	String KEY_CMD_CHANNEL = "CMD_CHANNEL";
	/** redis 全局常量 : 人员验证实时监控通道 */
	String KEY_LOG_MONITOR_CHANNEL = "LOG_MONITOR_CHANNEL";
	/** redis 全局常量 : 设备心跳实时监控通道 */
	String KEY_HB_MONITOR_CHANNEL = "HB_MONITOR_CHANNEL";
	///////////// PROPERTY KEY DEFINITION ///////////
	
    /** root 用户密码 */
	public static final String ROOT_PASSWORD = "root.password";
	/** 是否执行设备令牌验证 */
	public static final String TOKEN_DEVICE_VALIDATE = "token.device.validate";
	/** 是否执行人员令牌验证 */
	public static final String TOKEN_PERSON_VALIDATE = "token.person.validate";
	/** 人员令牌失效时间(分钟) */
	public static final String TOKEN_PERSON_EXPIRE = "token.person.expire";
	/** 服务端口号 */
	public static final String SERVER_PORT = "server.port";
	/** 最大连接数 */
	public static final String SERVER_CONNECTION_LIMIT = "server.connectiontLimit";
	/** 空闲连接超时(秒) */
	public static final String SERVER_IDLE_CONNECTION_TIMEMOUT = "server.idleConnectionTimeout";
	/** 工作线程数 */
	public static final String SERVER_WORKER_THREAD_COUNT ="server.workerThreadCount";
	/**  心跳包间隔(秒)  */
	public static final String HEARTBEAT_INTERVAL = "heartbeat.interval";
	/** 心跳包失效时间(秒) */
	public static final String HEARTBEAT_EXPIRE = "heartbeat.expire";
	/** REDIS 安装位置 */
	public static final String REDIS_HOME = "redis.home";
	/** REDIS 没启动时是否等待 */
	public static final String REDIS_WAITIFABSENT = "redis.waitIfAbsent";
	/** REDIS  等待重试次数 */
	public static final String REDIS_TRYCOUNT = "redis.tryCount";
	/** REDIS  等待重试间隔(毫秒) */
	public static final String REDIS_TRYINTERVAL = "redis.tryInterval";
	/** REDIS 主机名 */
	public static final String REDIS_HOST = "redis.host";
	/** REDIS 端口 */
	public static final String REDIS_PORT = "redis.port";	
	/** REDIS 数据库 */
	public static final String REDIS_DATABASE = "redis.database";		
	/** REDIS 密码 */
	public static final String REDIS_PASSWORD = "redis.password";		
	/** REDIS 访问地址(host,port,database,password) */
	public static final String REDIS_URI = "redis.uri";
	/** REDIS 超时(秒) */
	public static final String REDIS_TIMEOUT = "redis.timeout";
	/** jedis pool 最大线程数 */
	public static final String REDIS_POOL_MAXTOTAL = "redis.pool.maxTotal";
	
	/** 全局配置参数对象 */
	public static final CombinedConfiguration CONFIG = GlobalConfig.getConfig();


}
