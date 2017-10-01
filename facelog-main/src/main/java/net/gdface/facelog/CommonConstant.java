package net.gdface.facelog;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gu.simplemq.Channel;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.db.IFaceManager;
import net.gdface.facelog.db.IFeatureManager;
import net.gdface.facelog.db.IImageManager;
import net.gdface.facelog.db.ILogManager;
import net.gdface.facelog.db.IPersonManager;
import net.gdface.facelog.db.IStoreManager;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.db.mysql.TableInstance;

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
	public static final Channel<String> PUBSUB_FEATURE_INSERT = new Channel<String>("FeatureInsert"){};
	public static final Channel<String> PUBSUB_FEATURE_UPDATE = new Channel<String>("FeatureUpdate"){};
	public static final Channel<String> PUBSUB_FEATURE_DELETE = new Channel<String>("FeatureDelete"){};
	public static final Channel<LogBean> QUEUE_LOG = new Channel<LogBean>("logQueue"){};
	public static final IDeviceManager deviceManager = (IDeviceManager) TableInstance.getInstance(DeviceBean.class);
	public static final IFaceManager faceManager = (IFaceManager) TableInstance.getInstance(FaceBean.class);
	public static final IImageManager imageManager = (IImageManager) TableInstance.getInstance(ImageBean.class);
	public static final ILogManager logManager = (ILogManager) TableInstance.getInstance(LogBean.class);
	public static final IPersonManager personManager = (IPersonManager) TableInstance.getInstance(PersonBean.class);
	public static final IStoreManager storeManager = (IStoreManager) TableInstance.getInstance(StoreBean.class);
	public static final IFeatureManager featureManager = (IFeatureManager) TableInstance.getInstance(FeatureBean.class);
}
