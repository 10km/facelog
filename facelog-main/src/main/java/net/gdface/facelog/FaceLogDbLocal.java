package net.gdface.facelog;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.mysql.DeviceManager;
import net.gdface.facelog.db.mysql.FaceManager;
import net.gdface.facelog.db.mysql.FeatureManager;
import net.gdface.facelog.db.mysql.ImageManager;
import net.gdface.facelog.db.mysql.LogManager;
import net.gdface.facelog.db.mysql.PersonManager;
import net.gdface.facelog.db.mysql.StoreManager;

public class FaceLogDbLocal implements FaceLogDb,CommonConstant,
		net.gdface.facelog.db.Constant {
	private static TableManager<DeviceBean> deviceManager = DeviceManager.getInstance();
	private static TableManager<FaceBean> faceManager = FaceManager.getInstance();
	private static TableManager<FeatureBean> featureManager = FeatureManager.getInstance();
	private static TableManager<ImageBean> imagemanager = ImageManager.getInstance();
	private static TableManager<LogBean> logManager = LogManager.getInstance();
	private static TableManager<PersonBean> personManager = PersonManager.getInstance();
	private static TableManager<StoreBean> storeManager = StoreManager.getInstance();
	public FaceLogDbLocal() {
	}

}
