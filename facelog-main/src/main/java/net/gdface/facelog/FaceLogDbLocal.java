package net.gdface.facelog;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.mysql.TableInstance;

public class FaceLogDbLocal implements FaceLogDb,CommonConstant,
		net.gdface.facelog.db.Constant {
	private static TableManager<DeviceBean> deviceManager = TableInstance.getInstance(DeviceBean.class);
	private static TableManager<FaceBean> faceManager = TableInstance.getInstance(FaceBean.class);
	private static TableManager<FeatureBean> featureManager = TableInstance.getInstance(FeatureBean.class);
	private static TableManager<ImageBean> imagemanager = TableInstance.getInstance(ImageBean.class);
	private static TableManager<LogBean> logManager = TableInstance.getInstance(LogBean.class);
	private static TableManager<PersonBean> personManager = TableInstance.getInstance(PersonBean.class);
	private static TableManager<StoreBean> storeManager = TableInstance.getInstance(StoreBean.class);
	public FaceLogDbLocal() {
	}

}
