package net.gdface.facelog;

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

/**
 * 系统常量定义
 * @author guyadong
 *
 */
public interface ServiceConstant extends CommonConstant {
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
