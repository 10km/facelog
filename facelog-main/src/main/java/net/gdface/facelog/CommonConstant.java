package net.gdface.facelog;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.Channel;
import net.gdface.facelog.db.IDeviceGroupManager;
import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.db.IFaceManager;
import net.gdface.facelog.db.IFeatureManager;
import net.gdface.facelog.db.IImageManager;
import net.gdface.facelog.db.ILogLightManager;
import net.gdface.facelog.db.ILogManager;
import net.gdface.facelog.db.IPermitManager;
import net.gdface.facelog.db.IPersonGroupManager;
import net.gdface.facelog.db.IPersonManager;
import net.gdface.facelog.db.IStoreManager;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PermitBean;

public interface CommonConstant {
	public static final Logger logger = LoggerFactory.getLogger(CommonConstant.class);

	/** 设备心跳包表 */
	public static final Channel<Date> TABLE_HEARTBEAT = new Channel<Date>("DeviceHeartbeat"){} ;
	/** 心跳包间隔(秒) */
	public static final int DEFAULT_HEARTBEAT_INTERVAL = 8;
	/** 心跳包失效时间(秒) */
	public static final int DEFAULT_HEARTBEAT_EXPIRE = 60;
	/** 默认(设备/人员)组id */
	public static final int DEFAULT_GROUP_ID = 0;
	/** 默认(设备/人员)组名 */
	public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";

	public static final Channel<Integer> PUBSUB_PERSON_INSERT = new Channel<Integer>("PersonInsert"){};
	public static final Channel<Integer> PUBSUB_PERSON_UPDATE = new Channel<Integer>("PersonUpdate"){};
	public static final Channel<Integer> PUBSUB_PERSON_DELETE = new Channel<Integer>("PersonDelete"){};
	
	public static final Channel<String> PUBSUB_FEATURE_INSERT = new Channel<String>("FeatureInsert"){};
	public static final Channel<String> PUBSUB_FEATURE_UPDATE = new Channel<String>("FeatureUpdate"){};
	public static final Channel<String> PUBSUB_FEATURE_DELETE = new Channel<String>("FeatureDelete"){};
	
	public static final Channel<PermitBean> PUBSUB_PERMIT_INSERT = new Channel<PermitBean>("PermitInsert"){};
	public static final Channel<PermitBean> PUBSUB_PERMIT_UPDATE = new Channel<PermitBean>("PermitUpdate"){};
	public static final Channel<PermitBean> PUBSUB_PERMIT_DELETE = new Channel<PermitBean>("PermitDelete"){};

	public static final Channel<LogBean> QUEUE_LOG = new Channel<LogBean>("logQueue"){};
	
	public static final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final IDeviceManager deviceManager = TableManagerInitializer.instance.deviceManager;
	public static final IDeviceGroupManager deviceGroupManager = TableManagerInitializer.instance.deviceGroupManager;
	public static final IFaceManager faceManager = TableManagerInitializer.instance.faceManager;
	public static final IImageManager imageManager = TableManagerInitializer.instance.imageManager;
	public static final ILogManager logManager = TableManagerInitializer.instance.logManager;
	public static final ILogLightManager logLightManager = TableManagerInitializer.instance.logLightManager;
	public static final IPersonManager personManager = TableManagerInitializer.instance.personManager;
	public static final IPersonGroupManager personGroupManager = TableManagerInitializer.instance.personGroupManager;
	public static final IStoreManager storeManager = TableManagerInitializer.instance.storeManager;
	public static final IFeatureManager featureManager = TableManagerInitializer.instance.featureManager;
	public static final IPermitManager permitManager = TableManagerInitializer.instance.permitManager;

}
