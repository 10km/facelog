package net.gdface.facelog;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.javatuples.Pair;

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
import net.gdface.image.LazyImage;
import net.gdface.image.NotImage;
import net.gdface.image.UnsupportedFormat;
import net.gdface.utils.Assert;
import net.gdface.utils.FaceUtilits;

public class FaceLogDbLocal implements FaceLogDb,CommonConstant,
		net.gdface.facelog.db.Constant {
	private static final IDeviceManager deviceManager = (IDeviceManager) TableInstance.getInstance(DeviceBean.class);
	private static final IFaceManager faceManager = (IFaceManager) TableInstance.getInstance(FaceBean.class);
	private static final IImageManager imageManager = (IImageManager) TableInstance.getInstance(ImageBean.class);
	private static final ILogManager logManager = (ILogManager) TableInstance.getInstance(LogBean.class);
	private static final IPersonManager personManager = (IPersonManager) TableInstance.getInstance(PersonBean.class);
	private static final IStoreManager storeManager = (IStoreManager) TableInstance.getInstance(StoreBean.class);
	private static final IFeatureManager featureManager = (IFeatureManager) TableInstance.getInstance(FeatureBean.class);
	private final  RedisPersonListener redisPersonListener = new RedisPersonListener();
	//private final RedisLogConsumer redisLogConsumer  = new RedisLogConsumer();
	public FaceLogDbLocal() {
		init();
	}
	private void init(){
		personManager.registerListener(redisPersonListener);
	}
	public PersonBean getPerson(int id)throws ServiceRuntime {
		try{
			return personManager.loadByPrimaryKey(id);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	protected static int _deletePerson(int personId) {
		PersonBean personBean = personManager.loadByPrimaryKey(personId);
		if(null != personBean){
			imageManager.deleteByPrimaryKey(personBean.getImageMd5());
			return personManager.deleteByPrimaryKey(personId);
		}
		return 0;
	}
	public int deletePerson(final int personId)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePerson(personId);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch(Exception e){
			throw new ServiceRuntime(e);
		}
	}
	public boolean existsPerson(int id)throws ServiceRuntime {
		try{
			return personManager.existsPrimaryKey(id);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public boolean isDisable(int id)throws ServiceRuntime{
		try{
			PersonBean personBean = getPerson(id);
			if(null == personBean)
				return true;
			Date expiryDate = personBean.getExpiryDate();
			return null== expiryDate?false:expiryDate.before(new Date());
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	public void disablePerson(int id)throws ServiceRuntime{
		setPersonExpiryDate(id,new Date());
	}
	public void setPersonExpiryDate(int id,Date expiryDate)throws ServiceRuntime{
		try{
			PersonBean personBean = getPerson(id);
			if(null == personBean)
				return ;
			personBean.setExpiryDate(expiryDate);
			personManager.save(personBean);
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	public PersonBean savePerson(PersonBean bean)throws ServiceRuntime {
		try{
			return personManager.save(bean);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}	
	public void savePerson(List<PersonBean> beans)throws ServiceRuntime  {
		try{
			personManager.saveAsTransaction(beans);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public PersonBean getPersonByPapersNum(String papersNum)throws ServiceRuntime  {
		try{
			Assert.notEmpty(papersNum, "papersNum");
			PersonBean bean = new PersonBean();
			bean.setPapersNum(papersNum);
			return personManager.loadUniqueUsingTemplate(bean);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public FeatureBean[] getFeatureBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return personManager.getFlFeatureBeansByPersonId(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public LogBean[] getLogBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return personManager.getFlLogBeansByPersonId(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean[] loadAllPerson()throws ServiceRuntime {
		try{
			return personManager.loadAll();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean[] loadPersonByWhere(String where)throws ServiceRuntime {
		try{
			return personManager.loadByWhere(where);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public PersonBean savePerson(PersonBean bean, ImageBean refFlImagebyPhotoId,FaceBean[] impFlFacebyPersonId)throws ServiceRuntime {
		try{
			return personManager.saveAsTransaction(bean, refFlImagebyPhotoId,impFlFacebyPersonId,null);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	protected static PersonBean _savePerson(PersonBean bean, byte[] imageData,FeatureBean featureBean)throws ServiceRuntime {
		ImageBean imageBean = _saveImage(imageData,(DeviceBean)null,null,null);
		return personManager.save(bean, imageBean, new FeatureBean[]{featureBean}, null);
	}
	public PersonBean savePerson(final PersonBean bean, final byte[] imageData,final FeatureBean featureBean)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, imageData, featureBean);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean savePerson(final PersonBean bean, final byte[] imageData,final byte[] feature,final FaceBean[] faceBeans)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean,imageData,_saveFeature(feature, faceBeans));
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean savePerson(final PersonBean bean, final byte[] imageData,final byte[] feature,final FaceBean[] faceBeans,final List<byte[]>images,final DeviceBean deviceId)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					for(byte[] imageBytes:images){
						_saveImage(imageBytes, deviceId, null, null);
					}
					return _savePerson(bean,imageData,_saveFeature(feature, faceBeans));
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public LogBean saveLog(LogBean bean)throws ServiceRuntime {
		try{
			return logManager.save(bean);		
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public void saveLog(Collection<LogBean> beans)throws ServiceRuntime {
		try{
			logManager.saveAsTransaction(beans);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public void saveLog(LogBean[] beans) throws ServiceRuntime {
		try{
			logManager.saveAsTransaction(beans);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public LogBean[] loadLogByWhere(String where, int[] fieldList, int startRow, int numRows) throws ServiceRuntime {
		try{
			return logManager.loadByWhere(where, fieldList, startRow, numRows);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public int countLogWhere(String where) throws ServiceRuntime {
		try{
			return logManager.countWhere(where);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	protected static Pair<ImageBean, StoreBean> makeImageBean(byte[] imageBytes,String md5) throws NotImage, UnsupportedFormat{
		if(null == imageBytes || 0 == imageBytes.length)return null;
		LazyImage image = LazyImage.create(imageBytes);
		if(null == md5)
			md5 = FaceUtilits.getMD5String(imageBytes);
		ImageBean imageBean = new ImageBean();
		imageBean.setMd5(md5);
		imageBean.setWidth(image.getWidth());
		imageBean.setHeight(image.getHeight());
		imageBean.setFormat(image.getSuffix());
		StoreBean storeBean = new StoreBean();
		storeBean.setData(imageBytes);
		storeBean.setMd5(md5);
		return Pair.with(imageBean, storeBean);
	}
	/**
	 * 保存图像数据,如果图像数据已经存在，则直接返回对应的{@link ImageBean} 
	 * @param imageBytes 图像数据
	 * @param refFlDevicebyDeviceId 图像来源的设备对象，可为null
	 * @param impFlFacebyImgMd5 图像中检测到的人脸信息对象，可为null
	 * @param impFlPersonbyImageMd5 图像对应的 {@link PersonBean}对象,可为null
	 * @return 返回保存的{@link ImageBean}对象
	 * @throws ServiceRuntime
	 * @see {@link IImageManager#save(ImageBean, DeviceBean, StoreBean, StoreBean, FaceBean[], PersonBean[])}
	 */
	protected static ImageBean _saveImage(byte[] imageBytes,DeviceBean refFlDevicebyDeviceId
	        , FaceBean[] impFlFacebyImgMd5 , PersonBean[] impFlPersonbyImageMd5) throws ServiceRuntime{
		if(null == imageBytes || 0 == imageBytes.length)return null;
		String md5 = FaceUtilits.getMD5String(imageBytes);
		ImageBean imageBean = imageManager.loadByPrimaryKey(md5);
		if(null != imageBean){
			return imageBean;
		}
		Pair<ImageBean, StoreBean> pair;
		try {
			pair = makeImageBean(imageBytes,md5);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
		return imageManager.save(pair.getValue0(), refFlDevicebyDeviceId, pair.getValue1(), (StoreBean)null, impFlFacebyImgMd5, impFlPersonbyImageMd5);
	}
	protected static ImageBean _saveImage(byte[] imageBytes,Integer refFlDevicebyDeviceId
	        , FaceBean[] impFlFacebyImgMd5 , PersonBean[] impFlPersonbyImageMd5) throws ServiceRuntime{
		DeviceBean deviceBean = null == refFlDevicebyDeviceId? null : deviceManager.loadByPrimaryKey(refFlDevicebyDeviceId);
		return _saveImage(imageBytes,deviceBean,impFlFacebyImgMd5,impFlPersonbyImageMd5);
	}
	/** @see #_saveImage(byte[], DeviceBean, FaceBean[], PersonBean[]) */
	public ImageBean saveImage(final byte[] imageBytes,final DeviceBean refFlDevicebyDeviceId
	        , final FaceBean[] impFlFacebyImgMd5 , final PersonBean[] impFlPersonbyPhotoId) throws ServiceRuntime{
		try{
			return imageManager.runAsTransaction(new Callable<ImageBean>(){
				@Override
				public ImageBean call() throws Exception {
					return _saveImage(imageBytes, refFlDevicebyDeviceId, impFlFacebyImgMd5, impFlPersonbyPhotoId);
				}});			
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	private static FeatureBean makeFeature(byte[] feature){
		Assert.notEmpty(feature, "feature");
		FeatureBean featureBean = new FeatureBean();		
		featureBean.setMd5(FaceUtilits.getMD5String(feature));
		featureBean.setFeature(feature);
		return featureBean;
	}
	protected static FeatureBean _saveFeature(byte[] feature,FaceBean[] faceBeans)throws ServiceRuntime{
		try{
			return featureManager.save(makeFeature(feature), null, faceBeans, null, null);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	protected static FeatureBean _saveFeature(byte[] feature,Map<FaceBean,byte[]>faceInfo,Integer deviceId)throws ServiceRuntime{
		try{
			Assert.notEmpty(faceInfo, "faceInfo");
			for(Entry<FaceBean, byte[]> entry:faceInfo.entrySet()){
				 byte[] imageBytes = entry.getValue();
				 FaceBean faceBean = entry.getKey();
				Assert.notEmpty(imageBytes, "imageBytes");
				Assert.notNull(faceBean, "faceBean");
				_saveImage(imageBytes, deviceId, new FaceBean[]{faceBean}, null);
			}
			return featureManager.save(makeFeature(feature), null, faceInfo.keySet(), null, null);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public FeatureBean saveFeature(byte[] feature,FaceBean[] faceBeans)throws ServiceRuntime{
		try{
			return featureManager.saveAsTransaction(makeFeature(feature), null, faceBeans, null, null);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * @param imageBytes
	 * @param deviceId 设备id
	 * @param impFlFacebyImgMd5
	 * @param impFlPersonbyPhotoId
	 * @return
	 * @throws ServiceRuntime
	 * @see {@link #_saveImage(byte[], DeviceBean, FaceBean[], PersonBean[])}
	 */
	public ImageBean saveImage(byte[] imageBytes,Integer deviceId
			, FaceBean[] impFlFacebyImgMd5 , PersonBean[] impFlPersonbyPhotoId) throws ServiceRuntime{
		try{
			return _saveImage(imageBytes,deviceId,impFlFacebyImgMd5,impFlPersonbyPhotoId);		
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 根据图像的MD5校验码返回图像数据
	 * @param imageMD5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntime
	 * @see {@link #getBinary(String)}
	 */
	public byte[] getImageBytes(String imageMD5)throws ServiceRuntime{
		try{
			StoreBean storeBean = storeManager.loadByPrimaryKey(imageMD5);
			return null ==storeBean?null:storeBean.getData();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 根据图像的MD5校验码返回图像记录
	 * @param imageMD5
	 * @return {@link ImageBean} ,如果没有对应记录则返回null
	 * @throws ServiceRuntime
	 */
	public ImageBean getImageBean(String imageMD5)throws ServiceRuntime{
		try{
			return imageManager.loadByPrimaryKey(imageMD5);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 根据MD5校验码返回人脸特征数据
	 * @param md5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntime
	 */
	public byte[] getFeature(String md5)throws ServiceRuntime{
		try{
			FeatureBean featureBean = featureManager.loadByPrimaryKey(md5);
			return null ==featureBean?null:featureBean.getFeature();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
}
