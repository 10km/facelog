package net.gdface.facelog.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import net.gdface.facelog.db.Constant.JdbcProperty;
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
import net.gdface.facelog.db.ITableCache.UpdateStrategy;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.mysql.DeviceCacheManager;
import net.gdface.facelog.db.mysql.DeviceGroupCacheManager;
import net.gdface.facelog.db.mysql.FaceCacheManager;
import net.gdface.facelog.db.mysql.FeatureCacheManager;
import net.gdface.facelog.db.mysql.ImageCacheManager;
import net.gdface.facelog.db.mysql.ManagerUtil;
import net.gdface.facelog.db.mysql.PermitCacheManager;
import net.gdface.facelog.db.mysql.PersonCacheManager;
import net.gdface.facelog.db.mysql.PersonGroupCacheManager;
import net.gdface.facelog.db.mysql.StoreCacheManager;
import net.gdface.facelog.db.mysql.TableInstance;

/**
 * 全局初始化数据库访问对象(TableManager)
 * @author guyadong
 *
 */
public class TableManagerInitializer {
	
	public final IDeviceManager deviceManager;
	public final IDeviceGroupManager deviceGroupManager;
	public final IFaceManager faceManager ;
	public final IImageManager imageManager ;
	public final ILogManager logManager ;
	public final ILogLightManager logLightManager ;
	public final IPersonManager personManager ;
	public final IPersonGroupManager personGroupManager;
	public final IStoreManager storeManager ;
	public final IFeatureManager featureManager ;
	public final IPermitManager permitManager;
	static{
		// 向底层数据库操作类注入当前项目使用的数据库连接配置
		Map<JdbcProperty, String> databaseConfig = GlobalConfig.makeDatabaseConfig();
		//GlobalConfig.logDatabaseProperties(databaseConfig);
		ManagerUtil.injectProperties(GlobalConfig.toStringKey(databaseConfig));
	}
	public static final TableManagerInitializer INSTANCE = new TableManagerInitializer();
	private TableManagerInitializer() {
		// 配置cache参数
		// log,log_light 表因为不会被修改，也不会被设备频繁读取，不需要使用cache对象
		deviceManager = DeviceCacheManager.makeInstance(UpdateStrategy.always,10000,60,TimeUnit.MINUTES);
		deviceGroupManager = DeviceGroupCacheManager.makeInstance(UpdateStrategy.always,10000,60,TimeUnit.MINUTES);
		faceManager = FaceCacheManager.makeInstance(UpdateStrategy.always,10000,10,TimeUnit.MINUTES);
		imageManager = ImageCacheManager.makeInstance(UpdateStrategy.always,1000,10,TimeUnit.MINUTES);		 
		logManager = (ILogManager) TableInstance.getInstance(LogBean.class);
		logLightManager = (ILogLightManager) TableInstance.getInstance(LogLightBean.class);
		personManager = PersonCacheManager.makeInstance(UpdateStrategy.always,10000,10,TimeUnit.MINUTES);
		personGroupManager = PersonGroupCacheManager.makeInstance(UpdateStrategy.always,10000,60,TimeUnit.MINUTES);
		storeManager = StoreCacheManager.makeInstance(UpdateStrategy.always,1000,10,TimeUnit.MINUTES);
		featureManager = FeatureCacheManager.makeInstance(UpdateStrategy.always,10000,10,TimeUnit.MINUTES);
		permitManager = PermitCacheManager.makeInstance(UpdateStrategy.always,10000,10,TimeUnit.MINUTES);
		
/*		deviceManager = (IDeviceManager) TableInstance.getInstance(DeviceBean.class);
		faceManager = (IFaceManager) TableInstance.getInstance(FaceBean.class);
		imageManager = (IImageManager) TableInstance.getInstance(ImageBean.class);
		logManager = (ILogManager) TableInstance.getInstance(LogBean.class);
		logLightManager = (ILogLightManager) TableInstance.getInstance(LogLightBean.class);
		personManager = (IPersonManager) TableInstance.getInstance(PersonBean.class);
		storeManager = (IStoreManager) TableInstance.getInstance(StoreBean.class);
		featureManager = (IFeatureManager) TableInstance.getInstance(FeatureBean.class);*/
	}	
	

}
