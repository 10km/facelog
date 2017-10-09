package net.gdface.facelog;

import java.util.concurrent.TimeUnit;

import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.db.IFaceManager;
import net.gdface.facelog.db.IFeatureManager;
import net.gdface.facelog.db.IImageManager;
import net.gdface.facelog.db.ILogLightManager;
import net.gdface.facelog.db.ILogManager;
import net.gdface.facelog.db.IPersonManager;
import net.gdface.facelog.db.IStoreManager;
import net.gdface.facelog.db.ITableCache.UpdateStrategy;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.mysql.DeviceCacheManager;
import net.gdface.facelog.db.mysql.FaceCacheManager;
import net.gdface.facelog.db.mysql.FeatureCacheManager;
import net.gdface.facelog.db.mysql.ImageCacheManager;
import net.gdface.facelog.db.mysql.LogCacheManager;
import net.gdface.facelog.db.mysql.PersonCacheManager;
import net.gdface.facelog.db.mysql.StoreCacheManager;
import net.gdface.facelog.db.mysql.TableInstance;

/**
 * 全局初始化数据库访问对象(TableManager)
 * @author guyadong
 *
 */
public class TableManagerInitializer {
	
	public final IDeviceManager deviceManager;
	public final IFaceManager faceManager ;
	public final IImageManager imageManager ;
	public final ILogManager logManager ;
	public final ILogLightManager logLightManager ;
	public final IPersonManager personManager ;
	public final IStoreManager storeManager ;
	public final IFeatureManager featureManager ;
	
	public static final TableManagerInitializer instance = new TableManagerInitializer();
	private TableManagerInitializer() {
		deviceManager = DeviceCacheManager.makeInstance(UpdateStrategy.always,100,60,TimeUnit.MINUTES);
		faceManager = FaceCacheManager.makeInstance(UpdateStrategy.always,1000,10,TimeUnit.MINUTES);
		imageManager = ImageCacheManager.makeInstance(UpdateStrategy.always,100,10,TimeUnit.MINUTES);
		logManager = LogCacheManager.makeInstance(UpdateStrategy.always,1000,60,TimeUnit.MINUTES);
		logLightManager = (ILogLightManager) TableInstance.getInstance(LogLightBean.class);
		personManager = PersonCacheManager.makeInstance(UpdateStrategy.always,10000,10,TimeUnit.MINUTES);
		storeManager = StoreCacheManager.makeInstance(UpdateStrategy.always,100,10,TimeUnit.MINUTES);
		featureManager = FeatureCacheManager.makeInstance(UpdateStrategy.always,1000,10,TimeUnit.MINUTES);
 
		
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
