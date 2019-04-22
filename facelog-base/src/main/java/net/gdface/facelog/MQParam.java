package net.gdface.facelog;

/**
 * 消息系统(redis)基本参数名
 * @author guyadong
 *
 */
public enum MQParam {
	/** redis服务器地址 */REDIS_URI,
	/** webredis服务器地址 */WEBREDIS_URL,
	/** 设备命令通道名 */CMD_CHANNEL,
	/** 人员验证实时监控通道名 */LOG_MONITOR_CHANNEL,
	/** 设备心跳实时监控通道名 */HB_MONITOR_CHANNEL,
	/** 设备心跳包间隔时间(秒) */HB_INTERVAL,
	/** 设备心跳包失效时间(秒) */HB_EXPIRE
}