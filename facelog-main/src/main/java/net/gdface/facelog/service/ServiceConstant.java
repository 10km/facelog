package net.gdface.facelog.service;

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
    
	public static final String ROOT_NAME = "root";
	///////////// PROPERTY KEY DEFINITION ///////////
	
	public static final String ROOT_PASSWORD = "root.password";
	public static final String TOKEN_DEVICE_VALIDATE = "token.device.validate";
	public static final String TOKEN_PERSON_VALIDATE = "token.person.validate";
	public static final String TOKEN_PERSON_EXPIRE = "token.person.expire";
	public static final String TOKEN_PERSON_EXPIRE_DESC = "token.person.expire[@description]";
	public static final String NET_PORT = "net.port";
	public static final String HEARTBEAT_INTERVAL = "heartbeat.interval";
	public static final String HEARTBEAT_EXPIRE = "heartbeat.expire";

}
