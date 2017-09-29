package net.gdface.facelog;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.Channel;
import net.gdface.facelog.db.LogBean;

public interface CommonConstant {
	public static final Logger logger = LoggerFactory.getLogger(CommonConstant.class);

	/** 设备心跳包表 */
	public static final Channel<Date> TABLE_HEARTBEAT = new Channel<Date>("DeviceHeartbeat"){} ;
	/** 心跳包间隔(秒) */
	public static final int DEFAULT_HEARTBEAT_INTERVAL = 8;
	/** 心跳包失效时间(秒) */
	public static final int DEFAULT_HEARTBEAT_EXPIRE = 60;
	public static final Channel<Integer> PUBSUB_PERSON_INSERT = new Channel<Integer>("PersonInsert"){};
	public static final Channel<Integer> PUBSUB_PERSON_UPDATE = new Channel<Integer>("PersonUpdate"){};
	public static final Channel<Integer> PUBSUB_PERSON_DELETE = new Channel<Integer>("PersonDelete"){};
	public static final Channel<LogBean> QUEUE_LOG = new Channel<LogBean>("logQueue"){};

}
