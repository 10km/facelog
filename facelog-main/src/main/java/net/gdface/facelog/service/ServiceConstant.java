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
    /** 默认人员令牌失效时间(分钟) */
    public static final int DEFAULT_PERSON_TOKEN_EXPIRE = 60;

	///////////// PROPERTY KEY DEFINITION ///////////
	
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
	
	/** 全局配置参数对象 */
	public static final CombinedConfiguration CONFIG = GlobalConfig.getConfig();
}
