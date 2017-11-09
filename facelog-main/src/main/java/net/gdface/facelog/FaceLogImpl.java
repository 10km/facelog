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

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.image.LazyImage;
import net.gdface.image.NotImage;
import net.gdface.image.UnsupportedFormat;
import net.gdface.utils.Assert;
import net.gdface.utils.FaceUtilits;
import net.gdface.utils.Judge;

/**
 * IFaceLog 服务实现
 * @author guyadong
 *
 */
public class FaceLogImpl extends FaceLogDefinition  {
	private final RedisPersonListener redisPersonListener = new RedisPersonListener();
	private final RedisImageListener redisImageListener = new RedisImageListener(redisPersonListener,this);
	private final RedisFeatureListener redisFeatureListener = new RedisFeatureListener();
	private final RedisPermitListener redisPermitListener = new RedisPermitListener();
	//private final RedisLogConsumer redisLogConsumer  = new RedisLogConsumer();
	public FaceLogImpl() {
		init();
	}
	private void init(){
		getPersonManager().registerListener(redisPersonListener);
		getImageManager().registerListener(redisImageListener);
		getFeatureManager().registerListener(redisFeatureListener);
		getPermitManager().registerListener(redisPermitListener);
	}
	protected StoreBean _makeStoreBean(ByteBuffer imageBytes,String md5,String encodeing){
		if(Judge.isEmpty(imageBytes))return null;
		if(null == md5)
			md5 = FaceUtilits.getMD5String(imageBytes);
		StoreBean storeBean = new StoreBean();
		storeBean.setData(imageBytes);
		storeBean.setMd5(md5);
		if(!Strings.isNullOrEmpty(encodeing))
			storeBean.setEncoding(encodeing);
		return storeBean;
	}

	/////////////////////PERMIT////////////////////
	protected boolean _getGroupPermit(Integer deviceId,Integer personGroupId){
		PersonGroupBean personGroup;
		DeviceBean device;
		if(null == deviceId
			|| null == personGroupId 
			|| null ==(device = _getDevice(deviceId))
			|| null == (personGroup = _getPersonGroup(personGroupId)))
			return false;
		DeviceGroupBean deviceGroup = _getDeviceGroup(device.getGroupId());
		List<PersonGroupBean> personGroupList = _listOfParentForPersonGroup(personGroup);
		
		if(null == deviceGroup || personGroupList.isEmpty())
			return false;
		// person group 及其parent,任何一个在permit表中就返回true
		for(PersonGroupBean group:personGroupList){
			if(_existsPermit(deviceGroup.getId(), group.getId()))
				return true;
		}
		return false;
	}
	protected boolean _getPersonPermit(Integer deviceId,Integer personId){
		PersonBean person;
		if( null == personId || null == (person = _getPerson(personId)))
			return false;
		return _getGroupPermit(deviceId,person.getGroupId());
	}
	protected List<Boolean> _getGroupPermit(final Integer deviceId,List<Integer> personGroupIdList){
		if(null == deviceId || null == personGroupIdList)
			return ImmutableList.<Boolean>of();
		return Lists.newArrayList(Lists.transform(personGroupIdList, new Function<Integer,Boolean>(){
			@Override
			public Boolean apply(Integer input) {
				return _getGroupPermit(deviceId,input);
			}}));
	}
	protected List<Boolean> _getPermit(final Integer deviceId,List<Integer> personIdList){
		if(null == deviceId || null == personIdList)
			return ImmutableList.<Boolean>of();
		return Lists.newArrayList(Lists.transform(personIdList, new Function<Integer,Boolean>(){
			@Override
			public Boolean apply(Integer input) {
				return _getPersonPermit(deviceId,input);
			}}));
	}
	////////////////////////////////////////////
	protected Pair<ImageBean, StoreBean> _makeImageBean(ByteBuffer imageBytes,String md5) throws NotImage, UnsupportedFormat{
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
	protected ImageBean _addImage(ByteBuffer imageBytes,DeviceBean refFlDevicebyDeviceId
	        , Collection<FaceBean> impFlFacebyImgMd5 , Collection<PersonBean> impFlPersonbyImageMd5) throws DuplicateReord{
		if(Judge.isEmpty(imageBytes))return null;
		String md5 = FaceUtilits.getMD5String(imageBytes);
		_checkDuplicateImage(md5);
		Pair<ImageBean, StoreBean> pair;
		try {
			pair = _makeImageBean(imageBytes,md5);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		_addStore(pair.getValue1());
		return _addImage(pair.getValue0(), refFlDevicebyDeviceId, impFlFacebyImgMd5, impFlPersonbyImageMd5);
	}
	/**
	 * (递归)删除imageMd5指定图像及其缩略图
	 * @param imageMd5
	 * @return
	 */
	@Override
	protected int _deleteImage(String imageMd5){
		if(Strings.isNullOrEmpty(imageMd5))return 0;
		_deleteStore(imageMd5);
		ImageBean imageBean = _getImage(imageMd5);
		if(null == imageBean)return 0;
		String thumbMd5 = imageBean.getThumbMd5();
		if( !Strings.isNullOrEmpty(thumbMd5)&& !imageBean.getMd5().equals(thumbMd5))
			_deleteImage(thumbMd5);
		return super._deleteImage(imageMd5);
	}

	protected FeatureBean _makeFeature(ByteBuffer feature){
		Assert.notEmpty(feature, "feature");
		FeatureBean featureBean = new FeatureBean();		
		featureBean.setMd5(FaceUtilits.getMD5String(feature));
		featureBean.setFeature(feature);
		return featureBean;
	}
	protected FeatureBean _addFeature(ByteBuffer feature,PersonBean refPersonByPersonId, Collection<FaceBean> impFaceByFeatureMd5) throws DuplicateReord{
		return _addFeature(_makeFeature(feature), refPersonByPersonId, impFaceByFeatureMd5, null);
	}
	protected FeatureBean _addFeature(ByteBuffer feature,PersonBean personBean,Map<ByteBuffer, FaceBean> faceInfo,DeviceBean deviceBean) throws DuplicateReord{
		if(null != faceInfo){
			for(Entry<ByteBuffer, FaceBean> entry:faceInfo.entrySet()){
				ByteBuffer imageBytes = entry.getKey();
				FaceBean faceBean = entry.getValue();
				_addImage(imageBytes, deviceBean, Arrays.asList(faceBean), Arrays.asList(personBean));
			}
		}
		return _addFeature(feature, personBean, null == faceInfo?null:faceInfo.values());
	}

	protected List<String> _deleteFeature(String featureMd5,boolean deleteImage){
		List<String> imageKeys = _getImageKeysImportedByFeatureMd5(featureMd5);
		if(deleteImage){
			for(Iterator<String> itor = imageKeys.iterator();itor.hasNext();){
				String md5 = itor.next();
				_deleteImage(md5);
				itor.remove();
			}
		}else{
			_deleteFaceBeansByFeatureMd5OnFeature(featureMd5);
		}
		_deleteFeature(featureMd5);
		return imageKeys;
	}
	protected int _deleteAllFeaturesByPersonId(Integer personId,boolean deleteImage){
		int count = 0;
		for(FeatureBean featureBean: _getFeatureBeansByPersonIdOnPerson(personId)){
			_deleteFeature(featureBean.getMd5(),deleteImage);
			++count;
		}
		return count;
	}
	protected Integer _getDeviceIdOfFeature(String featureMd5){
		for(String imageMd5: _getImageKeysImportedByFeatureMd5(featureMd5)){
			ImageBean imageBean = _getImage(imageMd5);
			if(null !=imageBean)
				return imageBean.getDeviceId();
		}
		return null;
	}
	protected PersonBean _setRefPersonOfFeature(String featureMd5,Integer personId){
		PersonBean personBean = _getPerson(personId);
		FeatureBean featureBean = _getFeature(featureMd5);
		if(null == personBean || null == featureBean)return null;
		return _setReferencedByPersonIdOnFeature(featureBean, personBean);
	}

	protected List<String> _getImageKeysImportedByFeatureMd5(String featureMd5){
		return Lists.transform(_getFaceBeansByFeatureMd5OnFeature(featureMd5), this._castFaceToImageMd5);
	}
	protected PersonBean _replaceFeature(Integer personId,String featureMd5,boolean deleteImage){		
		_deleteAllFeaturesByPersonId(personId, deleteImage);
		return _setRefPersonOfFeature(featureMd5,personId);
	}

	protected int _savePerson(Map<ByteBuffer,PersonBean> persons) throws DuplicateReord {
		if(null == persons )return 0;
		int count = 0;
		PersonBean personBean ;
		for(Entry<ByteBuffer, PersonBean> entry:persons.entrySet()){
			personBean = _savePerson(entry.getValue(),_addImage(entry.getKey(),null,null,null),null);
			if(null != personBean)++count;
		}
		return count;
	}
	protected PersonBean _savePerson(PersonBean personBean, ImageBean idPhotoBean,
			Collection<FeatureBean> featureBean) {
		_deleteImage(_getReferencedByImageMd5OnPerson(personBean));// delete old photo if exists
		return _savePerson(personBean, idPhotoBean, null, featureBean, null);
	}

	protected PersonBean _savePerson(PersonBean bean, ByteBuffer idPhoto, FeatureBean featureBean,
			DeviceBean deviceBean) throws DuplicateReord {
		ImageBean imageBean = _addImage(idPhoto, deviceBean, null, null);
		return _savePerson(bean, imageBean, Arrays.asList(featureBean));
	}

	protected PersonBean _savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			Map<ByteBuffer, FaceBean> faceInfo, DeviceBean deviceBean) throws DuplicateReord {
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
	 * @throws DuplicateReord 
	 */
	protected PersonBean _savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			ByteBuffer featureImage, FaceBean featureFaceBean, DeviceBean deviceBean) throws DuplicateReord {
		Map<ByteBuffer, FaceBean> faceInfo = null;
		if (null != featureFaceBean) {
			if (Judge.isEmpty(featureImage))
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
	@Override
	protected int _deletePerson(Integer personId) {
		PersonBean personBean = _getPerson(personId);
		if(null == personBean)return 0;
		// 删除标准照
		_deleteImage(personBean.getImageMd5());
		_deleteAllFeaturesByPersonId(personId,true);
		return super._deletePerson(personId);
	}
	protected int _deletePersonByPapersNum(String papersNum) {
		PersonBean personBean = _getPersonByIndexPapersNum(papersNum);
		return null == personBean ? 0 : _deletePerson(personBean.getId());
	}
	protected int _deletePersonByPapersNum(Collection<String> collection) {
		if(null == collection)return 0;
		int count =0;
		for(String papersNum:collection){
			count += _deletePersonByPapersNum(papersNum);
		}
		return count;
	}
	protected boolean _isDisable(int personId){
		PersonBean personBean = _getPerson(personId);
		if(null == personBean)
			return true;
		Date expiryDate = personBean.getExpiryDate();
		return null== expiryDate?false:expiryDate.before(new Date());
	}
	protected  void _setPersonExpiryDate(PersonBean personBean,Date expiryDate){
		if(null == personBean)
			return ;
		personBean.setExpiryDate(expiryDate);
		_savePerson(personBean);
	}
	protected  void _setPersonExpiryDate(Collection<Integer> personIdList,Date expiryDate){
		if(null == personIdList)return;
		for(Integer personId : personIdList){
			_setPersonExpiryDate(_getPerson(personId),expiryDate);
		}
	}
	protected List<PersonBean> _loadUpdatedPersons(Date timestamp) {
		List<PersonBean> persons =_loadPersonByUpdateTime(timestamp);		
		Map<Integer,PersonBean>m = Maps.uniqueIndex(persons,this._castPersonToPk);
		Integer refPerson;
		for(FeatureBean feature:_loadFeatureByUpdateTime(timestamp)){
			refPerson = feature.getPersonId();
			if(null != refPerson && m.containsKey(refPerson))
				m.put(refPerson, _getPerson(refPerson));
		}
		return new ArrayList<PersonBean>(m.values());
	}
	@Override
	public PersonBean getPerson(int personId)throws ServiceRuntime {
		try{
			return _getPerson(personId);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<PersonBean> getPersons(List<Integer> idList)throws ServiceRuntime {
		try{
			return _getPersons(idList);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public PersonBean getPersonByPapersNum(String papersNum)throws ServiceRuntime  {
		try{
			if(Strings.isNullOrEmpty(papersNum))return null;
			return _getPersonByIndexPapersNum(papersNum);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<String> getFeatureBeansByPersonId(int personId)throws ServiceRuntime {
		try{
			return _toPrimaryKeyListFromFeatures(_getFeatureBeansByPersonIdOnPerson(personId));
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public int deletePerson(final int personId)throws ServiceRuntime {
		try{
			return _runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePerson(personId);
				}});
		}catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public int deletePersons(final List<Integer> personIdList)throws ServiceRuntime {
		try{
			return _runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePersonsByPrimaryKey(personIdList);
				}});
		}catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public int deletePersonByPapersNum(final String papersNum)throws ServiceRuntime  {
		try{	
			return _runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePersonByPapersNum(papersNum);
				}});
		}catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public  int deletePersonsByPapersNum(final List<String> papersNumlist)throws ServiceRuntime {
		try{		
			return _runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _deletePersonByPapersNum(papersNumlist);
				}});
		}catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public boolean existsPerson(int persionId)throws ServiceRuntime {
		try{
			return _existsPerson(persionId);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public boolean isDisable(int personId)throws ServiceRuntime{
		try{
			return _isDisable(personId);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public void disablePerson(int personId)throws ServiceRuntime{
		try{
			_setPersonExpiryDate(_getPerson(personId),new Date());
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public void setPersonExpiryDate(int personId,long expiryDate)throws ServiceRuntime{
		try{
			_setPersonExpiryDate(_getPerson(personId),new Date(expiryDate));
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public  void setPersonExpiryDate(final List<Integer> personIdList,final long expiryDate)throws ServiceRuntime{
		try{		
			_runAsTransaction(new Runnable(){
				@Override
				public void run() {
					_setPersonExpiryDate(personIdList,new Date(expiryDate));
				}});			
		}catch (RuntimeException e) {
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
			return _getLogBeansByPersonIdOnPerson(personId);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<Integer> loadAllPerson()throws ServiceRuntime {
		try{
			return _loadPersonIdByWhere(null);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<Integer> loadPersonIdByWhere(String where)throws ServiceRuntime {
		try{
			return _loadPersonIdByWhere(where);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}
	@Override
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows) throws ServiceRuntime {
		try{
			return _loadPersonByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}
	@Override
	public int countPersonByWhere(String where)throws ServiceRuntime {
		try{
			return _countPersonByWhere(where);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}
	@Override
	public PersonBean savePerson(PersonBean bean)throws ServiceRuntime {
		try{
			return _savePerson(bean);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public void savePersons(List<PersonBean> beans)throws ServiceRuntime  {
		try{
			_savePersonsAsTransaction(beans);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto)throws ServiceRuntime {
		try{
			return _runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, null,null);
				}});
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public Integer savePerson(final Map<ByteBuffer,PersonBean> persons)throws ServiceRuntime {
		try{
			return _runAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return _savePerson(persons);
				}});
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final String idPhotoMd5, final String featureMd5)
			throws ServiceRuntime {
		try {
			return _runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, _getImage(idPhotoMd5), Arrays.asList(_getFeature(featureMd5)));
				}
			});
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	
	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final FeatureBean featureBean,
			final Integer deviceId) throws ServiceRuntime {
		try {
			return _runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, featureBean, _getDevice(deviceId));
				}
			});
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final List<FaceBean> faceBeans) throws ServiceRuntime {
		try {
			return _runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, _addFeature(feature, bean, faceBeans), null);
				}
			});
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final Map<ByteBuffer, FaceBean> faceInfo, final Integer deviceId) throws ServiceRuntime {
		try {
			return _runAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean, idPhoto, feature, faceInfo, _getDevice(deviceId));
				}
			});
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final ByteBuffer featureImage, final FaceBean featureFaceBean, final Integer deviceId)throws ServiceRuntime {
		try{
			return _runAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return _savePerson(bean,idPhoto,feature,featureImage,featureFaceBean,_getDevice(deviceId));
				}});
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public void replaceFeature(final Integer personId, final String featureMd5, final boolean deleteOldFeatureImage)
			throws ServiceRuntime {
		try {
			_runAsTransaction(new Runnable() {
				@Override
				public void run() {
					_replaceFeature(personId, featureMd5, deleteOldFeatureImage);
				}
			});
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<Integer> loadUpdatedPersons(long timestamp)throws ServiceRuntime {
		try{
			return _toPrimaryKeyListFromPersons(_loadUpdatedPersons(new Date(timestamp)));
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<Integer> loadPersonIdByUpdateTime(long timestamp)throws ServiceRuntime {
		try{
			return _loadPersonIdByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(long timestamp)throws ServiceRuntime {
		try{		
			return _loadFeatureMd5ByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public void addLog(LogBean bean)throws ServiceRuntime, DuplicateReord {
		try{
			_addLog(bean);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public void addLogs(List<LogBean> beans)throws ServiceRuntime, DuplicateReord {
		try{
			_addLogsAsTransaction(beans);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows) throws ServiceRuntime {
		try{
			return _loadLogByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows) throws ServiceRuntime {
		try{
			return _loadLogLightByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public int countLogLightByWhere(String where) throws ServiceRuntime {
		try{         
			return _countLogLightByWhere(where);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public int countLogByWhere(String where) throws ServiceRuntime {
		try{
			return _countLogByWhere(where);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}
	@Override
    public List<LogLightBean> loadLogLightByVerifyTime(long timestamp,int startRow, int numRows)throws ServiceRuntime{
		try{
			return _loadLogLightByVerifyTime(new Date(timestamp),startRow,numRows);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
    @Override
    public int countLogLightByVerifyTime(long timestamp)throws ServiceRuntime{
		try{
			return _countLogLightByVerifyTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
    @Override
	public boolean existsImage(String md5) throws ServiceRuntime {
		try{
			return _existsImage(md5);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public ImageBean addImage(ByteBuffer imageData,Integer deviceId
			, FaceBean faceBean , Integer personId) throws ServiceRuntime, DuplicateReord{
		try{
			return _addImage(imageData,_getDevice(deviceId),Arrays.asList(faceBean),Arrays.asList(_getPerson(personId)));		
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

    @Override
	public boolean existsFeature(String md5) throws ServiceRuntime {
		try{
			return _existsFeature(md5);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature,Integer personId,List<FaceBean> faecBeans)throws ServiceRuntime, DuplicateReord{
		try{
			return _addFeature(feature, _getPerson(personId), faecBeans);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, Map<ByteBuffer, FaceBean> faceInfo,
			Integer deviceId) throws ServiceRuntime, DuplicateReord {
		try {
			return _addFeature(feature, _getPerson(personId), faceInfo, _getDevice(deviceId));
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<String> deleteFeature(String featureMd5,boolean deleteImage)throws ServiceRuntime{
		try{
			return _deleteFeature(featureMd5,deleteImage);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public int deleteAllFeaturesByPersonId(int personId,boolean deleteImage)throws ServiceRuntime{
		try{
			return _deleteAllFeaturesByPersonId(personId,deleteImage);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public FeatureBean getFeature(String md5)throws ServiceRuntime{
		try{
			return _getFeature(md5);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<FeatureBean> getFeatures(List<String> md5)throws ServiceRuntime{
		try{
			return _getFeatures(md5);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<String> getFeaturesOfPerson(int personId)throws ServiceRuntime{
		try{			
			return Lists.transform(
					_getFeatureBeansByPersonIdOnPerson(personId),
					_castFeatureToPk); 
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public ByteBuffer getFeatureBytes(String md5)throws ServiceRuntime{
		try{
			FeatureBean featureBean = _getFeature(md5);
			return null ==featureBean?null:featureBean.getFeature();
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public ByteBuffer getImageBytes(String imageMD5)throws ServiceRuntime{
		try{
			StoreBean storeBean = _getStore(imageMD5);
			return null ==storeBean?null:storeBean.getData();
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public ImageBean getImage(String imageMD5)throws ServiceRuntime{
		try{
			return _getImage(imageMD5);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}

	@Override
	public List<String> getImagesAssociatedByFeature(String featureMd5)throws ServiceRuntime{
		try{
			return _getImageKeysImportedByFeatureMd5(featureMd5);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public Integer getDeviceIdOfFeature(String featureMd5) throws ServiceRuntime{
		try{
			return _getDeviceIdOfFeature(featureMd5);
		}catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public int deleteImage(String imageMd5)throws ServiceRuntime{
		try{
			return _deleteImage(imageMd5);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

    @Override
	public boolean existsDevice(int id) throws ServiceRuntime {
		try{
			return _existsDevice(id);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

    @Override
    public DeviceBean saveDevice(DeviceBean deviceBean)throws ServiceRuntime{
    	try{
    		return _saveDevice(deviceBean);
    	} catch (RuntimeException e) {
    		throw new ServiceRuntime(e);
    	} 
    }

	@Override
	public DeviceBean getDevice(int deviceId)throws ServiceRuntime{
    	try{
    		return _getDevice(deviceId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}

	@Override
	public List<DeviceBean> getDevices(List<Integer> idList)throws ServiceRuntime{
		try{
			return _getDevices(idList);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		} 
	}
	@Override
	public List<DeviceBean> loadDeviceByWhere(String where,int startRow, int numRows)throws ServiceRuntime{
		try{
			return this._loadDeviceByWhere(where, startRow, numRows);
		}catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public int countDeviceByWhere(String where)throws ServiceRuntime{
		try{
			return this._countDeviceByWhere(where);
		}catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<Integer> loadDeviceIdByWhere(String where)throws ServiceRuntime{
		try{
			return this._loadDeviceIdByWhere(where);
		}catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}
	////////////////////////////////DeviceGroupBean/////////////
	@Override
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean)throws ServiceRuntime {
		try{
			return _saveDeviceGroup(deviceGroupBean);
		} catch(RuntimeException e){
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public DeviceGroupBean getDeviceGroup(int deviceGroupId)throws ServiceRuntime {
		try{
			return _getDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList)throws ServiceRuntime {
		try{
			return _getDeviceGroups(groupIdList); 
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public int deleteDeviceGroup(int deviceGroupId)throws ServiceRuntime {
		try{
			return _deleteDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<DeviceGroupBean> getSubDeviceGroup(int deviceGroupId)throws ServiceRuntime {
		try{
			return _getSubDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<DeviceBean> getDevicesOfGroup(int deviceGroupId)throws ServiceRuntime {
		try{
			return _getDevicesOfGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	////////////////////////////////PersonGroupBean/////////////
	@Override
	public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean)throws ServiceRuntime {
		try{
			return _savePersonGroup(personGroupBean);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public PersonGroupBean getPersonGroup(int personGroupId)throws ServiceRuntime {
		try{
			return _getPersonGroup(personGroupId); 
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<PersonGroupBean> getPersonGroups(Collection<Integer> groupIdList)throws ServiceRuntime {
		try{
			return _getPersonGroups(groupIdList);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public int deletePersonGroup(int personGroupId)throws ServiceRuntime {
		try{
			return _deletePersonGroup(personGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<PersonGroupBean> getSubPersonGroup(int personGroupId)throws ServiceRuntime {
		try{
			return _getSubPersonGroup(personGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<PersonBean> getPersonsOfGroup(int personGroupId)throws ServiceRuntime {
		try{
			return _getPersonsOfGroup(personGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
    @Override
    public List<DeviceGroupBean> loadDeviceGroupByWhere(String where,int startRow, int numRows)throws ServiceRuntime{
		try{
			return _loadDeviceGroupByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
    @Override
    public int countDeviceGroupByWhere(String where)throws ServiceRuntime{
		try{
			return _countDeviceGroupByWhere(where);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
    @Override
    public List<Integer> loadDeviceGroupIdByWhere(String where)throws ServiceRuntime{
    	try{
    		return _loadDeviceGroupIdByWhere(where);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
	/////////////////////PERMIT/////
	@Override
	public void addPermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup)throws ServiceRuntime {
		try{
			_addPermit(deviceGroup, personGroup);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public void addPermit(int deviceGroupId,int personGroupId)throws ServiceRuntime{
		try{
			_addPermit(deviceGroupId, personGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public int deletePermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup)throws ServiceRuntime {
		try{
			return _deletePermit(deviceGroup, personGroup);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public boolean getGroupPermit(int deviceId,int personGroupId)throws ServiceRuntime {
		try{
			return _getGroupPermit(deviceId,personGroupId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public boolean getPersonPermit(int deviceId,int personId)throws ServiceRuntime {
		try{
			return _getPersonPermit(deviceId,personId);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<Boolean> getGroupPermits(int deviceId,List<Integer> personGroupIdList)throws ServiceRuntime {
		try{
			return _getGroupPermit(deviceId, personGroupIdList);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<Boolean> getPersonPermits(int deviceId,List<Integer> personIdList)throws ServiceRuntime {
		try{
			return _getPermit(deviceId, personIdList);
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
	@Override
	public List<PermitBean> loadPermitByUpdate(long timestamp)throws ServiceRuntime {
		try{
			return _loadPermitByCreateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
	}
    @Override
    public List<PersonGroupBean> loadPersonGroupByWhere(String where,int startRow, int numRows)throws ServiceRuntime{
    	try{
    		return _loadPersonGroupByWhere(where, startRow, numRows);
    	} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
    @Override
    public int countPersonGroupByWhere(String where)throws ServiceRuntime{
    	try{
    		return _countPersonGroupByWhere(where);
    	} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
    @Override
    public List<Integer> loadPersonGroupIdByWhere(String where)throws ServiceRuntime{
    	try{
    		return _loadPersonGroupIdByWhere(where);
    	} catch (RuntimeException e) {
			throw new ServiceRuntime(e);
		}
    }
}
