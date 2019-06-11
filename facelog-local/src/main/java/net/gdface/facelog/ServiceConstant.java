package net.gdface.facelog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.configuration2.CombinedConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.Channel;
import net.gdface.facelog.CommonConstant;

/**
 * 服务端常量定义
 * @author guyadong
 *
 */
public interface ServiceConstant extends CommonConstant{
    public static final Logger logger = LoggerFactory.getLogger(ServiceConstant.class);
    
	/** 设备访问令牌表,{@code 设备ID -> token}  */
    public static final Channel<Token> TABLE_DEVICE_TOKEN = new Channel<Token>("DeviceToken"){} ;
    /** 人员访问令牌表 {@code 人员ID -> token} */
    public static final Channel<Token> TABLE_PERSON_TOKEN = new Channel<Token>("PersonToken"){} ;
    /** 设备命令序列号 {@code cmd sn -> 人员ID} */
    public static final Channel<Integer> TABLE_CMD_SN = new Channel<Integer>("CmdSN"){} ;
    /** 设备响应通道 {@code channel -> 人员ID} */
    public static final Channel<Integer> TABLE_ACK_CHANNEL = new Channel<Integer>("AckChannel"){} ;

    /////////////  REDIS KEYS ////////////////////////////
	/** redis 全局变量 : 设备命令序列号, 调用{@link TokenMangement#applyCmdSn(Token)}时每次加1,保证序列号在全网络唯一 */
	String KEY_CMD_SN = "CMD_SN";
	/** redis 全局变量 : 设备响应通道序列号, 调用{@link TokenMangement#applyAckChannel(Token, long)}时每次加1,保证序列号在全网络唯一 */
	String KEY_ACK_SN = "ACK_CHANNEL";
	/** redis 全局常量 : 设备命令通道 */
	String KEY_CMD_CHANNEL = "CMD_CHANNEL";
	/** redis 全局常量 : 人员验证实时监控通道 */
	String KEY_LOG_MONITOR_CHANNEL = "LOG_MONITOR_CHANNEL";
	/** redis 全局常量 : 设备心跳实时监控通道 */
	String KEY_HB_MONITOR_CHANNEL = "HB_MONITOR_CHANNEL";
    ///////////// PROPERTY KEY DEFINITION (不对 client 公开的 key 定义)///////////
	
    /** salt for password */
    public static final String TOKEN_SALT = "token.salt";

	/////////////////////////////////////////////////
	
	/** database 配置属性前缀 */
	public static final String PREFIX_DATABASE = "database.";
	
	/** 全局配置参数对象 */
	public static final CombinedConfiguration CONFIG = GlobalConfig.getConfig();
	
	/** 全局线程池(自动退出封装) */
	public static final ExecutorService GLOBAL_EXCEUTOR = ExecutorProvider.getGlobalExceutor();

	/** 定时任务线程池对象(自动退出封装) */
	public static final ScheduledExecutorService TIMER_EXECUTOR = ExecutorProvider.getTimerExecutor();
}
