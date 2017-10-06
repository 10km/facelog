package net.gdface.facelog;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
	protected static DeviceBean _saveDevice(DeviceBean deviceBean){
		return deviceManager.save(deviceBean);
	}
	protected static DeviceBean _getDevice(Integer deviceId){
		return deviceManager.loadByPrimaryKey(deviceId); 
	}
	protected static int _deleteDevice(Integer deviceId){
		return deviceManager.deleteByPrimaryKey(deviceId);
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
	protected static int _deleteImage(ImageBean imageBean){
		return null == imageBean ? 0 : _deleteImage(imageBean.getMd5());
	}
	protected static FeatureBean _makeFeature(ByteBuffer feature){
		Assert.notEmpty(feature, "feature");
		FeatureBean featureBean = new FeatureBean();		
		featureBean.setMd5(FaceUtilits.getMD5String(feature));
		featureBean.setFeature(feature);
		return featureBean;
	}
	protected static FeatureBean _addFeature(ByteBuffer feature,PersonBean refPersonByPersonId, Collection<FaceBean> impFaceByFeatureMd5){
		return featureManager.save(featureManager.checkDuplicate(_makeFeature(feature)), refPersonByPersonId, impFaceByFeatureMd5, null);
	}
	protected static FeatureBean _addFeature(ByteBuffer feature,PersonBean personBean,Map<ByteBuffer, FaceBean> faceInfo,DeviceBean deviceBean){
		if(null != faceInfo){
			for(Entry<ByteBuffer, FaceBean> entry:faceInfo.entrySet()){
				ByteBuffer imageBytes = entry.getKey();
				FaceBean faceBean = entry.getValue();
				_addImage(imageBytes, deviceBean, Arrays.asList(faceBean), Arrays.asList(personBean));
			}
		}
		return _addFeature(feature, personBean, null == faceInfo?null:faceInfo.values());
	}
	protected static FeatureBean _getFeature(String md5){
		return featureManager.loadByPrimaryKey(md5);
	}
	/**
	 * 删除featureMd5指定的特征记录及关联的face记录
	 * @param featureMd5
	 * @param deleteImage 是否删除关联的 image记录
	 * @return
	 */
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
	/**
	 * 删除 personId 关联的所有特征(feature)记录
	 * @param personId
	 * @param deleteImage 是否删除关联的 image记录
	 * @return
	 * @see #_deleteFeature(String, boolean)
	 */
	protected static int _deleteAllFeaturesByPersonId(Integer personId,boolean deleteImage){
		int count = 0;
		for(FeatureBean featureBean: personManager.getFeatureBeansByPersonIdAsList(personId)){
			_deleteFeature(featureBean.getMd5(),deleteImage);
			++count;
		}
		return count;
	}
	protected static PersonBean _setRefPersonOfFeature(String featureMd5,Integer personId){
		PersonBean personBean = _getPerson(personId);
		FeatureBean featureBean = _getFeature(featureMd5);
		if(null == personBean || null == featureBean)return null;
		return featureManager.setReferencedByPersonId(featureBean,personBean);
	}
	protected static ImageBean _getImage(String md5){
		return imageManager.loadByPrimaryKey(md5);
	}
	protected static List<String> _getImageKeysImportedByFeatureMd5(String featureMd5){
		ArrayList<String> imageBeans = new ArrayList<String>();
		for(FaceBean faceBean:featureManager.getFaceBeansByFeatureMd5AsList(featureMd5)){
			imageBeans.add(faceBean.getImageMd5());
		}
		return imageBeans;
	}
	protected static PersonBean _replaceFeature(Integer personId,String featureMd5,boolean deleteImage){		
		_deleteAllFeaturesByPersonId(personId, deleteImage);
		return _setRefPersonOfFeature(featureMd5,personId);
	}
	protected static PersonBean _getPerson(Integer personId){
		return personManager.loadByPrimaryKey(personId); 
	}
	protected static List<PersonBean> _getPerson(Collection<Integer> collection){
		return personManager.loadByPrimaryKey(collection); 
	}
	protected static PersonBean _getPersonByPapersNum(String papersNum){
		return personManager.loadByIndexPapersNum(papersNum);
	}
	protected static List<PersonBean> _getPersonByPapersNum(List<String> collection){
		if(null == collection)return null;
		ArrayList<PersonBean> beans = new ArrayList<PersonBean>(collection.size());
		for(String num: collection){
			beans.add(_getPersonByPapersNum(num));
		}
		return beans;
	}
	protected static int _savePerson(Map<ByteBuffer,PersonBean> persons) {
		if(null == persons )return 0;
		int count = 0;
		PersonBean personBean ;
		for(Entry<ByteBuffer, PersonBean> entry:persons.entrySet()){
			personBean = _savePerson(entry.getValue(),_addImage(entry.getKey(),null,null,null),null);
			if(null != personBean)++count;
		}
		return count;
	}
	protected static PersonBean _savePerson(PersonBean bean, ImageBean idPhotoBean,
			Collection<FeatureBean> featureBean) {
		_deleteImage(personManager.getReferencedByImageMd5(bean));// delete old photo if exists
		return personManager.save(bean, idPhotoBean, featureBean, null);
	}

	protected static PersonBean _savePerson(PersonBean bean, ByteBuffer idPhoto, FeatureBean featureBean,
			DeviceBean deviceBean) throws ServiceRuntime {
		ImageBean imageBean = _addImage(idPhoto, deviceBean, null, null);
		return _savePerson(bean, imageBean, Arrays.asList(featureBean));
	}

	protected static PersonBean _savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			Map<ByteBuffer, FaceBean> faceInfo, DeviceBean deviceBean) {
		return _savePerson(bean, idPhoto, _addFeature(feature, bean, faceInfo, deviceBean), null);
	}

	/**
	 * 
	 * @param bean 人员信息对象
	 * @param idPhoto 标准照图像
	 * @param feature 人脸特征数据
	 * @param featureImage 提取特征源图像,为null 时,默认使用idPhoto
	 * @param featureFaceBean 人脸位置对象,为null 时,不保存人脸数据
	 * @param deviceBean featureImage来源设备对象
	 * @return
	 */
	protected static PersonBean _savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			ByteBuffer featureImage, FaceBean featureFaceBean, DeviceBean deviceBean) {
		Map<ByteBuffer, FaceBean> faceInfo = null;
		if (null != featureFaceBean) {
			if (!Judge.isEmpty(featureImage))
				featureImage = idPhoto;
			if (!Judge.isEmpty(featureImage)) {
				faceInfo = new HashMap<ByteBuffer, FaceBean>();
				faceInfo.put(featureImage, featureFaceBean);
			}
		}
		return _savePerson(bean, idPhoto, _addFeature(feature, bean, faceInfo, deviceBean), null);
	}
	/**
	 * 删除personId指定的人员(person)记录及关联的所有记录
	 * @param personId
	 * @return 返回删除的记录数量
	 */
	protected static int _deletePerson(Integer personId) {
		PersonBean personBean = _getPerson(personId);
		if(null == personBean)return 0;
		// 删除标准照
		_deleteImage(personBean.getImageMd5());
		_deleteAllFeaturesByPersonId(personId,true);
		return personManager.deleteByPrimaryKey(personId);
	}
	/**
	 *  删除collection指定的人员(person)记录及关联的所有记录
	 * @param collection personId集合
	 * @return 返回删除的 person 记录数量
	 */
	protected static int _deletePerson(Collection<Integer> collection) {
		if(null == collection)return 0;
		int count =0;
		for(Integer personId:collection){
			count += _deletePerson(personId);
		}
		return count;
	}

/*	protected static List<PersonBean> loadPersonByUpdate(Date timeStamp)throws ServiceRuntime {
			//return personManager.loadByWhereAsList(where);
	}*/
	/**
	 * 返回personId指定的人员记录
	 * @param personId
	 * @return
	 * @throws ServiceRuntime
	 */
	public PersonBean getPerson(int personId)throws ServiceRuntime {
		try{
			return _getPerson(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 返回 list 指定的人员记录
	 * @param list 人员id列表
	 * @return
	 * @throws ServiceRuntime
	 */
	public List<PersonBean> getPerson(List<Integer> list)throws ServiceRuntime {
		try{
			return _getPerson(list);
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 删除personId指定的人员记录
	 * @param personId
	 * @return
	 * @throws ServiceRuntime
	 */
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
	/**
	 * 删除 list 指定的人员记录
	 * @param list 人员id列表
	 * @return
	 * @throws ServiceRuntime
	 */
	public int deletePerson(final List<Integer> list)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePerson(list);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch(Exception e){
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 判断是否存在personId指定的人员记录
	 * @param persionId
	 * @return
	 * @throws ServiceRuntime
	 */
	public boolean existsPerson(int persionId)throws ServiceRuntime {
		try{
			return personManager.existsPrimaryKey(persionId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 判断 personId 指定的人员记录是否过期
	 * @param personId
	 * @return
	 * @throws ServiceRuntime
	 */
	public boolean isDisable(int personId)throws ServiceRuntime{
		try{
			PersonBean personBean = getPerson(personId);
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
	/** @see #_setRefPersonOfFeature(String, Integer) */
	public void disablePerson(int personId)throws ServiceRuntime{
		setPersonExpiryDate(personId,new Date());
	}
	/**
	 * 修改 personId 指定的人员记录的有效期
	 * @param personId
	 * @param expiryDate
	 * @throws ServiceRuntime
	 */
	public void setPersonExpiryDate(int personId,Date expiryDate)throws ServiceRuntime{
		try{
			PersonBean personBean = getPerson(personId);
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
	/**
	 * 根据证件号码返回人员记录
	 * @param papersNum
	 * @return
	 * @throws ServiceRuntime
	 */
	public PersonBean getPersonByPapersNum(String papersNum)throws ServiceRuntime  {
		try{
			if(Judge.isEmpty(papersNum))return null;
			return personManager.loadByIndexPapersNum(papersNum);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 返回 persionId 关联的所有人脸特征记录
	 * @param personId fl_person.id
	 * @return
	 * @throws ServiceRuntime
	 */
	public List<FeatureBean> getFeatureBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return personManager.getFeatureBeansByPersonIdAsList(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 返回 persionId 关联的所有日志记录
	 * @param personId fl_person.id
	 * @return
	 * @throws ServiceRuntime
	 */
	public List<LogBean> getLogBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return personManager.getLogBeansByPersonIdAsList(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 返回所有人员记录
	 * @return
	 * @throws ServiceRuntime
	 */
	public List<PersonBean> loadAllPerson()throws ServiceRuntime {
		try{
			return personManager.loadAllAsList();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 返回 where 指定的所有人员记录
	 * @param where SQL条件语句
	 * @return
	 * @throws ServiceRuntime
	 */
	public List<PersonBean> loadPersonByWhere(String where)throws ServiceRuntime {
		try{
			return personManager.loadByWhereAsList(where);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	/**
	 * 保存人员(person)记录
	 * @param bean
	 * @return
	 * @throws ServiceRuntime
	 */
	public PersonBean savePerson(PersonBean bean)throws ServiceRuntime {
		try{
			return personManager.save(bean);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 保存人员(person)记录
	 * @param beans 
	 * @throws ServiceRuntime
	 */
	public void savePerson(List<PersonBean> beans)throws ServiceRuntime  {
		try{
			personManager.saveAsTransaction(beans);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhoto 标准照图像对象,可为null
	 * @return
	 * @throws ServiceRuntime
	 */
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, null,null);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 保存人员信息记录(包含标准照)
	 * @param persons
	 * @return
	 * @throws ServiceRuntime
	 */
	public Integer savePerson(final Map<ByteBuffer,PersonBean> persons)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _savePerson(persons);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhoto 标准照图像对象,可为null
	 * @param featureBean 用于验证的人脸特征数据对象,可为null
	 * @return
	 * @throws ServiceRuntime
	 */
	public PersonBean savePerson(final PersonBean bean, final ImageBean idPhoto, final FeatureBean featureBean)
			throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, Arrays.asList(featureBean));
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	
	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param imageData 标准照图像,可为null
	 * @param featureBean 用于验证的人脸特征数据对象,可为null
	 * @param deviceId 标准照图像来源设备id,可为null
	 * @return
	 * @throws ServiceRuntime
	 */
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer imageData, final FeatureBean featureBean,
			final Integer deviceId) throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, imageData, featureBean, _getDevice(deviceId));
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param imageData 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据,可为null,不可重复, 参见 {@link #addFeature(ByteBuffer, Integer, List)}
	 * @param faceBeans 参见 {@link #addFeature(ByteBuffer, Integer, List)}
	 * @return
	 * @throws ServiceRuntime
	 */
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer imageData, final ByteBuffer feature,
			final List<FaceBean> faceBeans) throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, imageData, _addFeature(feature, bean, faceBeans), null);
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 保存人员信息记录
	 * @param bean 
	 * @param imageData 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据,可为null 
	 * @param faceInfo 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @param deviceId faceInfo 图像来源设备id,可为null 
	 * @return bean 保存的{@link PersonBean}对象
	 * @throws ServiceRuntime
	 */
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer imageData, final ByteBuffer feature,
			final Map<ByteBuffer, FaceBean> faceInfo, final Integer deviceId) throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, imageData, feature, faceInfo, _getDevice(deviceId));
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 
	 * @param bean 人员信息对象
	 * @param idPhoto 标准照图像
	 * @param feature 人脸特征数据
	 * @param featureImage 提取特征源图像,为null 时,默认使用idPhoto
	 * @param featureFaceBean 人脸位置对象,为null 时,不保存人脸数据
	 * @param deviceBean featureImage来源设备对象
	 * @return
	 */
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final ByteBuffer featureImage, final FaceBean featureFaceBean, final Integer deviceId)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean,idPhoto,feature,featureImage,featureFaceBean,_getDevice(deviceId));
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
	 * @param personId 人员记录id
	 * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
	 * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
	 * @throws ServiceRuntime
	 */
	public void replaceFeature(final Integer personId, final String featureMd5, final boolean deleteOldFeatureImage)
			throws ServiceRuntime {
		try {
			personManager.runAsTransaction(new Runnable() {
				@Override
				public void run() {
					_replaceFeature(personId, featureMd5, deleteOldFeatureImage);
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	public void addLog(LogBean bean)throws ServiceRuntime {
		try{
			logManager.save(bean);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	
	public void addLog(List<LogBean> beans)throws ServiceRuntime {
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
    public boolean existsImage(String md5) throws ServiceRuntime {
		try{
			return imageManager.existsPrimaryKey(md5);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 保存图像数据,如果图像数据已经存在，则抛出异常
	 * @param imageData 图像数据
	 * @param deviceId 图像来源设备id,可为null
	 * @param faceBean 关联的人脸信息对象,可为null
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @return
	 * @throws ServiceRuntime
	 * @see {@link #_addImage(ByteBuffer, DeviceBean, List, List)}
	 */
	public ImageBean addImage(ByteBuffer imageData,Integer deviceId
			, FaceBean faceBean , Integer personId) throws ServiceRuntime{
		try{
			return _addImage(imageData,_getDevice(deviceId),Arrays.asList(faceBean),Arrays.asList(_getPerson(personId)));		
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
    public boolean existsFeature(String md5) throws ServiceRuntime {
		try{
			return featureManager.existsPrimaryKey(md5);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 增加一个人脸特征记录，如果记录已经存在则抛出异常
	 * @param feature 特征数据
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param faecBeans 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws ServiceRuntime
	 */
	public FeatureBean addFeature(ByteBuffer feature,Integer personId,List<FaceBean> faecBeans)throws ServiceRuntime{
		try{
			return _addFeature(feature, _getPerson(personId), faecBeans);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/**
	 * 增加一个人脸特征记录,特征数据由faceInfo指定的多张图像合成，如果记录已经存在则抛出异常
	 * @param feature 特征数据
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param faceInfo 生成特征数据的图像及人脸信息对象(每张图对应一张人脸),可为null
	 * @param deviceId 图像来源设备id,可为null
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws ServiceRuntime
	 */
	public FeatureBean addFeature(ByteBuffer feature,Integer personId,Map<ByteBuffer, FaceBean> faceInfo,Integer deviceId)throws ServiceRuntime{
		try{
			return _addFeature(feature, _getPerson(personId), faceInfo, _getDevice(deviceId));
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
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
	public ImageBean getImage(String imageMD5)throws ServiceRuntime{
		try{
			return _getImage(imageMD5);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/**
	 * 根据MD5校验码返回人脸特征数据记录
	 * @param md5
	 * @return 如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntime
	 */
	public FeatureBean getFeature(String md5)throws ServiceRuntime{
		try{
			return _getFeature(md5);
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
	public ByteBuffer getFeatureBytes(String md5)throws ServiceRuntime{
		try{
			FeatureBean featureBean = _getFeature(md5);
			return null ==featureBean?null:featureBean.getFeature();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
    public boolean existsDevice(int id) throws ServiceRuntime {
		try{
			return deviceManager.existsPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
}
