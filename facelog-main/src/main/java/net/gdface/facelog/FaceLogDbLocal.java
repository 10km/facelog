package net.gdface.facelog;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.IDeviceManager;
import net.gdface.facelog.db.IFaceManager;
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
	public int deletePerson(int id)throws ServiceRuntime {
		try{		
			return personManager.deleteByPrimaryKey(id);
		}catch (Exception e) {
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
	public PersonBean savePerson(PersonBean bean, ImageBean refFlImagebyPhotoId,FaceBean[] impFlFacebyPersonId)throws ServiceRuntime {
		try{
			return personManager.saveAsTransaction(bean, refFlImagebyPhotoId,impFlFacebyPersonId,null);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean savePerson(PersonBean bean, byte[] imageData,FaceBean faceBean)throws ServiceRuntime {
		try{
			if(null == imageData || null == faceBean){
				return savePerson(bean);
			}
			ImageBean imageBean = saveImage(imageData,null,null,null);
			return personManager.saveAsTransaction(bean, imageBean,new FaceBean[]{faceBean},null);
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
	
	/**
	 * 保存图像数据,如果图像数据已经存在，则直接返回对应的{@link ImageBean} 
	 * @param imageBytes 图像数据
	 * @param refFlDevicebyDeviceId 图像来源的设备对象，可为null
	 * @param impFlFacebyImgMd5 图像中检测到的人脸信息对象，可为null
	 * @param impFlPersonbyPhotoId 图像对应的 {@link PersonBean}对象
	 * @return 返回保存的{@link ImageBean}对象
	 * @throws ServiceRuntime
	 * @see {@link IImageManager#save(ImageBean, DeviceBean, StoreBean, StoreBean, FaceBean[], PersonBean[])}
	 */
	public ImageBean saveImage(byte[] imageBytes,DeviceBean refFlDevicebyDeviceId
	        , FaceBean[] impFlFacebyImgMd5 , PersonBean[] impFlPersonbyPhotoId) throws ServiceRuntime{
		if(null == imageBytes || 0 == imageBytes.length)return null;
		try {
			LazyImage image = LazyImage.create(imageBytes);
			String md5 = FaceUtilits.getMD5String(imageBytes);
			ImageBean imageBean = imageManager.loadByPrimaryKey(md5);
			if(null != imageBean){
				return imageBean;
			}
			imageBean = new ImageBean();
			imageBean.setMd5(md5);
			imageBean.setWidth(image.getWidth());
			imageBean.setHeight(image.getHeight());
			imageBean.setFormat(image.getSuffix());
			StoreBean storeBean = new StoreBean();
			storeBean.setData(imageBytes);
			storeBean.setMd5(md5);
			return imageManager.save(imageBean, refFlDevicebyDeviceId, storeBean, (StoreBean)null, impFlFacebyImgMd5, impFlPersonbyPhotoId);
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
	 * @see {@link #saveImage(byte[], DeviceBean, FaceBean[], PersonBean[])}
	 */
	public ImageBean saveImage(byte[] imageBytes,int deviceId
			, FaceBean[] impFlFacebyImgMd5 , PersonBean[] impFlPersonbyPhotoId) throws ServiceRuntime{
		try{
			DeviceBean deviceBean = deviceManager.loadByPrimaryKey(deviceId);
			if(null == deviceBean){
				throw new IllegalArgumentException(String.format("invalid device id %d",deviceId));
			}
			return saveImage(imageBytes,deviceBean,impFlFacebyImgMd5,impFlPersonbyPhotoId);		
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 根据图像的MD5校验码返回图像数据
	 * @param imageMD5
	 * @return 
	 * @throws ServiceRuntime
	 * @see {@link #getBinary(String)}
	 */
	public byte[] getImage(String imageMD5)throws ServiceRuntime{
		return getBinary(imageMD5);
	}
	/**
	 * 根据MD5校验码返回人脸特征数据
	 * @param md5
	 * @return
	 * @throws ServiceRuntime
	 * @see {@link #getBinary(String)}
	 */
	public byte[] getFeature(String md5)throws ServiceRuntime{
		return getBinary(md5);
	}
	/**
	 * 根据MD5校验码返回二进制数据
	 * @param md5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntime
	 */
	public byte[] getBinary(String md5)throws ServiceRuntime{
		try{
			StoreBean storeBean = storeManager.loadByPrimaryKey(md5);
			return null ==storeBean?null:storeBean.getData();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
}
