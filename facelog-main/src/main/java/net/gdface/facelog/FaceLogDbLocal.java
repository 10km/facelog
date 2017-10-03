package net.gdface.facelog;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.javatuples.Pair;
import org.weakref.jmx.com.google.common.primitives.Ints;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.IImageManager;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.image.LazyImage;
import net.gdface.image.NotImage;
import net.gdface.image.UnsupportedFormat;
import net.gdface.utils.Assert;
import net.gdface.utils.FaceUtilits;
import net.gdface.utils.Judge;

public class FaceLogDbLocal implements FaceLogDb,CommonConstant,
		net.gdface.facelog.db.Constant {
	private final  RedisPersonListener redisPersonListener = new RedisPersonListener();
	private final RedisImageListener redisImageListener = new RedisImageListener(redisPersonListener);
	private final RedisFeatureListener redisFeatureListener = new RedisFeatureListener();
	//private final RedisLogConsumer redisLogConsumer  = new RedisLogConsumer();
	public FaceLogDbLocal() {
		init();
	}
	private void init(){
		personManager.registerListener(redisPersonListener);
		imageManager.registerListener(redisImageListener);
		featureManager.registerListener(redisFeatureListener);
	}
	protected static StoreBean _makeStoreBean(ByteBuffer imageBytes,String md5,String encodeing){
		if(Judge.isEmpty(imageBytes))return null;
		if(null == md5)
			md5 = FaceUtilits.getMD5String(imageBytes);
		StoreBean storeBean = new StoreBean();
		storeBean.setData(imageBytes);
		storeBean.setMd5(md5);
		if(!Judge.isEmpty(encodeing))
			storeBean.setEncoding(encodeing);
		return storeBean;
	}
	protected static StoreBean _addStore(StoreBean storeBean){
		return storeManager.save(storeBean);
	}
	protected static StoreBean _getStore(String md5){
		return storeManager.loadByPrimaryKey(md5);
	}
	protected static DeviceBean _getDevice(Integer deviceId){
		return deviceManager.loadByPrimaryKey(deviceId); 
	}
	protected static Pair<ImageBean, StoreBean> _makeImageBean(ByteBuffer imageBytes,String md5) throws NotImage, UnsupportedFormat{
		if(Judge.isEmpty(imageBytes))return null;
		LazyImage image = LazyImage.create(imageBytes);
		if(null == md5)
			md5 = FaceUtilits.getMD5String(imageBytes);
		ImageBean imageBean = new ImageBean();
		imageBean.setMd5(md5);
		imageBean.setWidth(image.getWidth());
		imageBean.setHeight(image.getHeight());
		imageBean.setFormat(image.getSuffix());
		StoreBean storeBean = _makeStoreBean(imageBytes, md5, null);
		return Pair.with(imageBean, storeBean);
	}
	/**
	 * 保存图像数据,如果图像数据已经存在，则抛出异常 
	 * @param imageBytes 图像数据
	 * @param refFlDevicebyDeviceId 图像来源的设备对象，可为null
	 * @param impFlFacebyImgMd5 图像中检测到的人脸信息对象，可为null
	 * @param impFlPersonbyImageMd5 图像对应的 {@link PersonBean}对象,可为null
	 * @return 返回保存的{@link ImageBean}对象
	 * @throws ServiceRuntime
	 * @see {@link IImageManager#save(ImageBean, DeviceBean, StoreBean, StoreBean, FaceBean[], PersonBean[])}
	 */
	protected static ImageBean _addImage(ByteBuffer imageBytes,DeviceBean refFlDevicebyDeviceId
	        , Collection<FaceBean> impFlFacebyImgMd5 , Collection<PersonBean> impFlPersonbyImageMd5){
		if(Judge.isEmpty(imageBytes))return null;
		String md5 = FaceUtilits.getMD5String(imageBytes);
		ImageBean imageBean = _getImage(md5);
		if(null != imageBean){
			throw new DuplicateReord();
		}
		Pair<ImageBean, StoreBean> pair;
		try {
			pair = _makeImageBean(imageBytes,md5);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
		_addStore(pair.getValue1());
		return imageManager.save(pair.getValue0(), refFlDevicebyDeviceId, impFlFacebyImgMd5, impFlPersonbyImageMd5);
	}
	/**
	 * (递归)删除imageMd5指定图像及其缩略图
	 * @param imageMd5
	 * @return
	 */
	protected static int _deleteImage(String imageMd5){
		if(Judge.isEmpty(imageMd5))return 0;
		storeManager.deleteByPrimaryKey(imageMd5);
		ImageBean imageBean = _getImage(imageMd5);
		if(null == imageBean)return 0;
		_deleteImage(imageBean.getThumbMd5());
		return imageManager.deleteByPrimaryKey(imageMd5);
	}
	protected static FeatureBean _makeFeature(ByteBuffer feature){
		Assert.notEmpty(feature, "feature");
		FeatureBean featureBean = new FeatureBean();		
		featureBean.setMd5(FaceUtilits.getMD5String(feature));
		featureBean.setFeature(feature);
		return featureBean;
	}
	protected static FeatureBean _addFeature(ByteBuffer feature,PersonBean refPersonByPersonId, Collection<FaceBean> impFaceByFeatureMd5)throws ServiceRuntime{
		return featureManager.save(_makeFeature(feature), refPersonByPersonId, impFaceByFeatureMd5, null);
	}
	protected static FeatureBean _addFeature(ByteBuffer feature,PersonBean personBean,Map<ByteBuffer, FaceBean> faceInfo,DeviceBean deviceBean)throws ServiceRuntime{
		Assert.notEmpty(faceInfo, "faceInfo");
		for(Entry<ByteBuffer, FaceBean> entry:faceInfo.entrySet()){
			 ByteBuffer imageBytes = entry.getKey();
			 FaceBean faceBean = entry.getValue();
			Assert.notEmpty(imageBytes, "imageBytes");
			Assert.notNull(faceBean, "faceBean");
			_addImage(imageBytes, deviceBean, Arrays.asList(faceBean), Arrays.asList(personBean));
		}
		return _addFeature(feature, personBean, faceInfo.values());
	}
	protected static FeatureBean _addFeature(ByteBuffer feature,int personId,Map<ByteBuffer, FaceBean> faceInfo,DeviceBean deviceBean)throws ServiceRuntime{
		PersonBean personBean = _getPerson(personId);
		Assert.notNull(personBean, "personBean");
		return _addFeature(feature, personBean, faceInfo, deviceBean);
	}
	protected static FeatureBean _getFeature(String md5){
		return featureManager.loadByPrimaryKey(md5);
	}
	protected static List<String> _deleteFeature(String featureMd5,boolean deleteImage){
		List<String> imageKeys = _getImageKeysImportedByFeatureMd5(featureMd5);
		if(deleteImage){
			for(Iterator<String> itor = imageKeys.iterator();itor.hasNext();){
				String md5 = itor.next();
				_deleteImage(md5);
				itor.remove();
			}
		}else{
			featureManager.deleteFaceBeansByFeatureMd5(featureMd5);
		}
		featureManager.deleteByPrimaryKey(featureMd5);
		return imageKeys;
	}
	protected static int _deleteAllFeaturesByPersonId(int personId,boolean deleteImage){
		int count = 0;
		for(FeatureBean featureBean: personManager.getFeatureBeansByPersonIdAsList(personId)){
			_deleteFeature(featureBean.getMd5(),deleteImage);
			++count;
		}
		return count;
	}
	protected static PersonBean _setRefPersonOfFeature(String featureMd5,int personId){
		PersonBean personBean = _getPerson(personId);
		FeatureBean featureBean = _getFeature(featureMd5);
		if(null == personBean || null == featureBean)return null;
		return featureManager.setReferencedByPersonId(featureBean,personBean);
	}
	protected static ImageBean _getImage(String md5){
		return imageManager.loadByPrimaryKey(md5);
	}
	protected static ArrayList<String> _getImageKeysImportedByFeatureMd5(String featureMd5){
		ArrayList<String> imageBeans = new ArrayList<String>();
		for(FaceBean faceBean:featureManager.getFaceBeansByFeatureMd5AsList(featureMd5)){
			imageBeans.add(faceBean.getImageMd5());
		}
		return imageBeans;
	}
	protected static void _replaceFeature(int personId,String featureMd5,boolean deleteImage)throws ServiceRuntime{		
		_deleteAllFeaturesByPersonId(personId, deleteImage);
		_setRefPersonOfFeature(featureMd5,personId);
	}
	protected static PersonBean _getPerson(Integer id){
		return personManager.loadByPrimaryKey(id); 
	}
	protected static List<PersonBean> _getPerson(Collection<Integer> collection){
		return personManager.loadByPrimaryKey(collection); 
	}
	protected static PersonBean _savePerson(PersonBean bean, ImageBean imageBean,Collection<FeatureBean> featureBean)throws ServiceRuntime {
		return personManager.save(bean, imageBean, featureBean, null);
	}
	protected static PersonBean _savePerson(PersonBean bean, ByteBuffer imageData,FeatureBean featureBean, DeviceBean deviceBean)throws ServiceRuntime {
		ImageBean imageBean = _addImage(imageData,deviceBean,null,null);
		return _savePerson(bean, imageBean, Arrays.asList(featureBean));
	}
	protected static int _deletePerson(int personId) {
		PersonBean personBean = _getPerson(personId);
		if(null == personBean)return 0;
		_deleteImage(personBean.getImageMd5());
		return personManager.deleteByPrimaryKey(personId);
	}
	protected static int _deletePerson(Collection<Integer> collection) {
		if(null == collection)return 0;
		int count =0;
		for(Integer personId:collection){
			count += _deletePerson(personId);
		}
		return count;
	}
	public PersonBean getPerson(int id)throws ServiceRuntime {
		try{
			return _getPerson(id);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	public List<PersonBean> getPerson(List<Integer> id)throws ServiceRuntime {
		try{
			return _getPerson(id);
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
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
	public int deletePerson(final List<Integer> personId)throws ServiceRuntime {
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
	public List<FeatureBean> getFeatureBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return personManager.getFeatureBeansByPersonIdAsList(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public List<LogBean> getLogBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return personManager.getLogBeansByPersonIdAsList(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public List<PersonBean> loadAllPerson()throws ServiceRuntime {
		try{
			return personManager.loadAllAsList();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public List<PersonBean> loadPersonByWhere(String where)throws ServiceRuntime {
		try{
			return personManager.loadByWhereAsList(where);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public PersonBean savePerson(PersonBean bean, ImageBean refImageByImageMd5,FeatureBean impFeatureByPersonId)throws ServiceRuntime {
		try{
			return personManager.saveAsTransaction(bean, refImageByImageMd5,new FeatureBean[]{impFeatureByPersonId},null);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer imageData,final FeatureBean featureBean)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, imageData, featureBean, null);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer imageData,final ByteBuffer feature,final List<FaceBean> faceBeans)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean,imageData,_addFeature(feature, bean, faceBeans), null);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer imageData,final ByteBuffer feature,final Map<ByteBuffer, FaceBean> faceInfo,final Integer deviceId)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean,imageData,_addFeature(feature,bean, faceInfo,_getDevice(deviceId)), null);
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
	
	public void saveLog(List<LogBean> beans)throws ServiceRuntime {
		try{
			logManager.saveAsTransaction(beans);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public List<LogBean> loadLogByWhere(String where, List<Integer> fieldList, int startRow, int numRows) throws ServiceRuntime {
		try{
			return logManager.loadByWhereAsList(where, null ==fieldList ?null: Ints.toArray(fieldList), startRow, numRows);
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
	/** @see #_addImage(ByteBuffer, DeviceBean, List, List) */
	public ImageBean addImage(final ByteBuffer imageBytes,final DeviceBean refFlDevicebyDeviceId
	        , final List<FaceBean> impFlFacebyImgMd5 , final List<PersonBean> impFlPersonbyPhotoId) throws ServiceRuntime{
		try{
			return imageManager.runAsTransaction(new Callable<ImageBean>(){
				@Override
				public ImageBean call() throws Exception {
					return _addImage(imageBytes, refFlDevicebyDeviceId, impFlFacebyImgMd5, impFlPersonbyPhotoId);
				}});			
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	public FeatureBean addFeature(ByteBuffer feature,PersonBean refPersonByPersonId,List<FaceBean> impFaceByFeatureMd5)throws ServiceRuntime{
		try{
			return featureManager.saveAsTransaction(_makeFeature(feature), refPersonByPersonId, impFaceByFeatureMd5, null);
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
	 * @see {@link #_addImage(ByteBuffer, DeviceBean, List, List)}
	 */
	public ImageBean saveImage(ByteBuffer imageBytes,Integer deviceId
			, List<FaceBean> impFlFacebyImgMd5 , List<PersonBean> impFlPersonbyPhotoId) throws ServiceRuntime{
		try{
			return _addImage(imageBytes,_getDevice(deviceId),impFlFacebyImgMd5,impFlPersonbyPhotoId);		
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
	public ByteBuffer getImageBytes(String imageMD5)throws ServiceRuntime{
		try{
			StoreBean storeBean = _getStore(imageMD5);
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
			return _getImage(imageMD5);
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
	public ByteBuffer getFeature(String md5)throws ServiceRuntime{
		try{
			FeatureBean featureBean = _getFeature(md5);
			return null ==featureBean?null:featureBean.getFeature();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
}
