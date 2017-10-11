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
import java.util.concurrent.ConcurrentHashMap;

import org.javatuples.Pair;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.image.LazyImage;
import net.gdface.image.NotImage;
import net.gdface.image.UnsupportedFormat;
import net.gdface.utils.Assert;
import net.gdface.utils.FaceUtilits;
import net.gdface.utils.Judge;

public class FaceLogDbLocal extends FaceLogDefinition implements CommonConstant,
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
	protected static List<DeviceBean> _getDevice(Collection<Integer> collection){
		return deviceManager.loadByPrimaryKey(collection); 
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
	protected static List<FeatureBean> _getFeature(Collection<String> collection){
		return featureManager.loadByPrimaryKey(collection); 
	}
	protected static List<FeatureBean> _getFeatureBeansByPersonId(Integer personId){
		return personManager.getFeatureBeansByPersonIdAsList(personId);
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
	protected static int _deleteAllFeaturesByPersonId(Integer personId,boolean deleteImage){
		int count = 0;
		for(FeatureBean featureBean: _getFeatureBeansByPersonId(personId)){
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
	protected static List<ImageBean> _getImage(Collection<String> collection){
		return imageManager.loadByPrimaryKey(collection); 
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
	protected static List<PersonBean> _getPersonByPapersNum(Collection<String> collection){
		return personManager.loadByIndexPapersNum(collection);
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
	protected static int _deletePersonByPapersNum(String papersNum) {
		PersonBean personBean = _getPersonByPapersNum(papersNum);
		return null == personBean ? 0 : _deletePerson(personBean.getId());
	}
	protected static int _deletePersonByPapersNum(Collection<String> collection) {
		if(null == collection)return 0;
		int count =0;
		for(String papersNum:collection){
			count += _deletePersonByPapersNum(papersNum);
		}
		return count;
	}
	protected static boolean _isDisable(int personId){
		PersonBean personBean = _getPerson(personId);
		if(null == personBean)
			return true;
		Date expiryDate = personBean.getExpiryDate();
		return null== expiryDate?false:expiryDate.before(new Date());
	}
	protected static  void _setPersonExpiryDate(PersonBean personBean,Date expiryDate){
		if(null == personBean)
			return ;
		personBean.setExpiryDate(expiryDate);
		personManager.save(personBean);
	}
	protected static  void _setPersonExpiryDate(Collection<Integer> personIdList,Date expiryDate){
		if(null == personIdList)return;
		for(Integer personId : personIdList){
			_setPersonExpiryDate(_getPerson(personId),expiryDate);
		}
	}
	protected static List<PersonBean> _loadUpdatePersons(Date timestamp) {
			List<PersonBean> persons =_loadPersonByUpdate(timestamp);
			ConcurrentHashMap<Integer, PersonBean> m = new ConcurrentHashMap<Integer,PersonBean>(persons.size());
			for(PersonBean person:persons){
				m.put(person.getId(), person);
			}
			Integer refPerson;
			for(FeatureBean feature:_loadFeatureByUpdate(timestamp)){
				refPerson = feature.getPersonId();
				if(null != refPerson)
					m.putIfAbsent(refPerson, _getPerson(refPerson));
			}
			return new ArrayList<PersonBean>(m.values());
	}
	protected static List<PersonBean> _loadPersonByUpdate(Date timestamp) {
		String where = String.format("WHERE update_time >'%s'", timestampFormatter.format(timestamp));		
		return _loadPersonByWhere(where);
	}
	protected static List<FeatureBean> _loadFeatureByUpdate(Date timestamp) {
		String where = String.format("WHERE update_time >'%s'", timestampFormatter.format(timestamp));
		return _loadFeatureByWhere(where);
	}
	protected static List<FeatureBean> _loadFeatureByWhere(String where) {
		return featureManager.loadByWhereAsList(where);
}
	protected static  List<PersonBean> _loadPersonByWhere(String where){
			return personManager.loadByWhereAsList(where);
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#getPerson(int)
	 */
	@Override
	public PersonBean getPerson(int personId)throws ServiceRuntime {
		try{
			return _getPerson(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#getPersons(java.util.List)
	 */
	@Override
	public List<PersonBean> getPersons(List<Integer> idList)throws ServiceRuntime {
		try{
			return _getPerson(idList);
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#getPersonByPapersNum(java.lang.String)
	 */
	@Override
	public PersonBean getPersonByPapersNum(String papersNum)throws ServiceRuntime  {
		try{
			if(Judge.isEmpty(papersNum))return null;
			return _getPersonByPapersNum(papersNum);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#getFeatureBeansByPersonId(int)
	 */
	@Override
	public List<String> getFeatureBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return featureManager.toPrimaryKeyList(_getFeatureBeansByPersonId(personId));
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#deletePerson(int)
	 */
	@Override
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
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#deletePersons(java.util.List)
	 */
	@Override
	public int deletePersons(final List<Integer> personIdList)throws ServiceRuntime {
		try{
			return personManager.runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePerson(personIdList);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch(Exception e){
			throw new ServiceRuntime(e);
		}
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#deletePersonByPapersNum(java.lang.String)
	 */
	@Override
	public int deletePersonByPapersNum(final String papersNum)throws ServiceRuntime  {
		try{	
			return personManager.runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePersonByPapersNum(papersNum);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch(Exception e){
			throw new ServiceRuntime(e);
		}
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#deletePersonsByPapersNum(java.util.List)
	 */
	@Override
	public  int deletePersonsByPapersNum(final List<String> papersNumlist)throws ServiceRuntime {
		try{		
			return personManager.runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePersonByPapersNum(papersNumlist);
				}});
		}catch(ServiceRuntime e){
			throw e;
		}catch(Exception e){
			throw new ServiceRuntime(e);
		}
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#existsPerson(int)
	 */
	@Override
	public boolean existsPerson(int persionId)throws ServiceRuntime {
		try{
			return personManager.existsPrimaryKey(persionId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#isDisable(int)
	 */
	@Override
	public boolean isDisable(int personId)throws ServiceRuntime{
		try{
			return _isDisable(personId);
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	/* （非 Javadoc）
	 * @see net.gdface.facelog.IFaceLog#disablePerson(int)
	 */
	@Override
	public void disablePerson(int personId)throws ServiceRuntime{
		try{
			_setPersonExpiryDate(_getPerson(personId),new Date());
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public void setPersonExpiryDate(int personId,long expiryDate)throws ServiceRuntime{
		try{
			_setPersonExpiryDate(_getPerson(personId),new Date(expiryDate));
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public  void setPersonExpiryDate(final List<Integer> personIdList,final long expiryDate)throws ServiceRuntime{
		try{		
			personManager.runAsTransaction(new Runnable(){
				@Override
				public void run() {
					_setPersonExpiryDate(personIdList,new Date(expiryDate));
				}});			
		}catch(ServiceRuntime e){
			throw e;
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public  void disablePerson(final List<Integer> personIdList)throws ServiceRuntime{
		setPersonExpiryDate(personIdList,new Date().getTime());
	}

	@Override
	public List<LogBean> getLogBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return personManager.getLogBeansByPersonIdAsList(personId);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<Integer> loadAllPerson()throws ServiceRuntime {
		try{
			return personManager.toPrimaryKeyList(_loadPersonByWhere(null));
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<Integer> loadPersonByWhere(String where)throws ServiceRuntime {
		try{
			return personManager.toPrimaryKeyList(personManager.loadByWhereAsList(where));
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public PersonBean savePerson(PersonBean bean)throws ServiceRuntime {
		try{
			return personManager.save(bean);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public void savePerson(List<PersonBean> beans)throws ServiceRuntime  {
		try{
			personManager.saveAsTransaction(beans);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
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

	@Override
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

	@Override
	public PersonBean savePerson(final PersonBean bean, final String idPhotoMd5, final String featureMd5)
			throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, _getImage(idPhotoMd5), Arrays.asList(_getFeature(featureMd5)));
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}
	
	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final FeatureBean featureBean,
			final Integer deviceId) throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, featureBean, _getDevice(deviceId));
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final List<FaceBean> faceBeans) throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, _addFeature(feature, bean, faceBeans), null);
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final Map<ByteBuffer, FaceBean> faceInfo, final Integer deviceId) throws ServiceRuntime {
		try {
			return personManager.runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, feature, faceInfo, _getDevice(deviceId));
				}
			});
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
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

	@Override
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

	@Override
	public List<Integer> loadUpdatePersons(long timestamp)throws ServiceRuntime {
		try{
			return personManager.toPrimaryKeyList(_loadUpdatePersons(new Date(timestamp)));
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<Integer> loadPersonIdByUpdate(long timestamp)throws ServiceRuntime {
		try{
			return personManager.toPrimaryKeyList(_loadPersonByUpdate(new Date(timestamp)));
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(long timestamp)throws ServiceRuntime {
		try{
			return featureManager.toPrimaryKeyList(_loadFeatureByUpdate(new Date(timestamp)));
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public void addLog(LogBean bean)throws ServiceRuntime {
		try{
			logManager.save(bean);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public void addLog(List<LogBean> beans)throws ServiceRuntime {
		try{
			logManager.saveAsTransaction(beans);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows) throws ServiceRuntime {
		try{
			return logManager.loadByWhereAsList(where, null, startRow, numRows);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows) throws ServiceRuntime {
		try{
			return logLightManager.loadByWhereAsList(where, null, startRow, numRows);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}	

	@Override
	public int countLogLightWhere(String where) throws ServiceRuntime {
		try{
			return logLightManager.countWhere(where);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public int countLogWhere(String where) throws ServiceRuntime {
		try{
			return logManager.countWhere(where);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

    @Override
	public boolean existsImage(String md5) throws ServiceRuntime {
		try{
			return imageManager.existsPrimaryKey(md5);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
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

    @Override
	public boolean existsFeature(String md5) throws ServiceRuntime {
		try{
			return featureManager.existsPrimaryKey(md5);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature,Integer personId,List<FaceBean> faecBeans)throws ServiceRuntime{
		try{
			return _addFeature(feature, _getPerson(personId), faecBeans);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, Map<ByteBuffer, FaceBean> faceInfo,
			Integer deviceId) throws ServiceRuntime {
		try {
			return _addFeature(feature, _getPerson(personId), faceInfo, _getDevice(deviceId));
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<String> deleteFeature(String featureMd5,boolean deleteImage)throws ServiceRuntime{
		try{
			return _deleteFeature(featureMd5,deleteImage);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public int deleteAllFeaturesByPersonId(int personId,boolean deleteImage)throws ServiceRuntime{
		try{
			return _deleteAllFeaturesByPersonId(personId,deleteImage);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public FeatureBean getFeature(String md5)throws ServiceRuntime{
		try{
			return _getFeature(md5);
		}catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<FeatureBean> getFeature(List<String> md5)throws ServiceRuntime{
		try{
			return _getFeature(md5);
		}catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public ByteBuffer getFeatureBytes(String md5)throws ServiceRuntime{
		try{
			FeatureBean featureBean = _getFeature(md5);
			return null ==featureBean?null:featureBean.getFeature();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public ByteBuffer getImageBytes(String imageMD5)throws ServiceRuntime{
		try{
			StoreBean storeBean = _getStore(imageMD5);
			return null ==storeBean?null:storeBean.getData();
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public ImageBean getImage(String imageMD5)throws ServiceRuntime{
		try{
			return _getImage(imageMD5);
		}catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<String> getImagesAssociatedByFeature(String featureMd5)throws ServiceRuntime{
		try{
			return _getImageKeysImportedByFeatureMd5(featureMd5);
		}catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public int deleteImage(String imageMd5)throws ServiceRuntime{
		try{
			return _deleteImage(imageMd5);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

    @Override
	public boolean existsDevice(int id) throws ServiceRuntime {
		try{
			return deviceManager.existsPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		}
	}

    @Override
	public DeviceBean saveDevice(DeviceBean deviceBean)throws ServiceRuntime{
    	try{
		return _saveDevice(deviceBean);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public DeviceBean getDevice(Integer deviceId)throws ServiceRuntime{
    	try{
		return _getDevice(deviceId);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<DeviceBean> getDevice(List<Integer> deviceId)throws ServiceRuntime{
    	try{
		return _getDevice(deviceId);
		} catch (ServiceRuntime e) {
			throw e;
		} catch (Exception e) {
			throw new ServiceRuntime(e);
		} 
	}
}
