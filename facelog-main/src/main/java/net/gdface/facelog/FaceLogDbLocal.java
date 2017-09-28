package net.gdface.facelog;

import java.util.Collection;
import java.util.List;

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
	public PersonBean getPerson(int id) {
		return personManager.loadByPrimaryKey(id);
	}
	public int deletePerson(int id) {
		return personManager.deleteByPrimaryKey(id);
	}
	public boolean existsPerson(int id) {
		return personManager.existsPrimaryKey(id);
	}
	public PersonBean savePerson(PersonBean bean) {
		return personManager.save(bean);
	}	
	public void savePerson(List<PersonBean> beans) {
		personManager.saveAsTransaction(beans);
	}
	
	public PersonBean getPersonByPapersNum(String papersNum) {
		PersonBean bean = new PersonBean();
		bean.setPapersNum(papersNum);
		return personManager.loadUniqueUsingTemplate(bean);
	}
	public PersonBean savePerson(PersonBean bean, ImageBean refImage,FaceBean[] includeFaces) {
		return personManager.saveAsTransaction(bean, refImage,includeFaces);
	}

}
