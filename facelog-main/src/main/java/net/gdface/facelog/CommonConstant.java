package net.gdface.facelog;

import java.util.Date;

import gu.simplemq.Channel;

public interface CommonConstant {
	/** 设备心跳包表 */
	public static final Channel<Date> TABLE_HEARTBEAT = new Channel<Date>("deviceHeartbeat",Date.class) ;
	/** 心跳包间隔(秒) */
	public static final int DEFAULT_HEARTBEAT_INTERVAL = 8;
	/** 心跳包失效时间(秒) */
	public static final int DEFAULT_HEARTBEAT_EXPIRE = 60;
}
