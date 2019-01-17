package net.gdface.facelog;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.*;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
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
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.thrift.exception.ServiceRuntimeException;
import net.gdface.facelog.DuplicateRecordException;
import net.gdface.facelog.TokenMangement.Enable;
import redis.clients.jedis.exceptions.JedisException;

/**
 * IFaceLog 服务实现
 * @author guyadong
 *
 */
public class FaceLogImpl implements IFaceLog,ServiceConstant {
	/** redis 服务器管理对象，负责初始化全局连接池对象，要放在redis lisetner对象初始化前完成初始化 */
	private final RedisManagement rm = new RedisManagement();
	/** 数据库操作对象，提供所有数据库访问 */
	private final DaoManagement dm = new DaoManagement();
	/** 令牌管理模块对象 */
	private final TokenMangement tm = new TokenMangement(dm);
	private final TokenValidatorPersonListener tokenValidatorPersonListener = new TokenValidatorPersonListener(dm);
	private final TokenValidatorPersonGroupListener tokenValidatorPersonGroupListener = new TokenValidatorPersonGroupListener(dm);
	private final TokenValidatorDeviceListener tokenValidatorDeviceListener = new TokenValidatorDeviceListener(dm);
	private final TokenValidatorDeviceGroupListener tokenValidatorDeviceGroupListener = new TokenValidatorDeviceGroupListener(dm);

	private final RedisPersonListener redisPersonListener = new RedisPersonListener();
	private final RedisFeatureListener redisFeatureListener = new RedisFeatureListener();
	private final RedisPermitListener redisPermitListener = new RedisPermitListener();
	private final RedisLogListener redisLogListener = new RedisLogListener(rm.getRedisParameters().get(MQParam.HB_MONITOR_CHANNEL));

	//private final RedisLogConsumer redisLogConsumer  = new RedisLogConsumer(this);

	/**
	 * 构造方法
	 */
	public FaceLogImpl() {
		initListener();
	}
	private void initListener(){
		// 注册安全验证侦听器
		BaseDao.getPersonManager().registerListener(tokenValidatorPersonListener);
		BaseDao.getPersonGroupManager().registerListener(tokenValidatorPersonGroupListener);
		BaseDao.getDeviceManager().registerListener(tokenValidatorDeviceListener);
		BaseDao.getDeviceGroupManager().registerListener(tokenValidatorDeviceGroupListener);

		// 注册REDIS侦听器
		BaseDao.getPersonManager().registerListener(redisPersonListener);
		BaseDao.getFeatureManager().registerListener(redisFeatureListener);
		BaseDao.getPermitManager().registerListener(redisPermitListener);
		if(CONFIG.getBoolean(MONITOR_LOG)){
			BaseDao.getLogManager().registerListener(redisLogListener);
		}

		// 注册系统日志侦听器
		BaseDao.getPersonManager().registerListener(BaseSysLogLisener.PERSON_LOG_LISTENER);
		BaseDao.getPersonGroupManager().registerListener(BaseSysLogLisener.PERSON_GROUP_LOG_LISTENER);
		BaseDao.getDeviceManager().registerListener(BaseSysLogLisener.DEVICE_LOG_LISTENER);
		BaseDao.getDeviceGroupManager().registerListener(BaseSysLogLisener.DEVICE_GROUP_LOG_LISTENER);
		BaseDao.getPermitManager().registerListener(BaseSysLogLisener.PERMIT_LOG_LISTENER);
	}

	////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 将封装在{@link RuntimeException}中的{@link ServiceSecurityException}剥离出来单独抛出<br>
	 * 将其他的{@link RuntimeException}封装在{@link ServiceRuntimeException}抛出，
	 * @param e
	 * @return
	 * @throws ServiceSecurityException
	 */
	protected static final <T> T throwServiceException(RuntimeException e) 
			throws ServiceSecurityException{
		if(null != e.getCause()){
			try{
				throw e.getCause();
			}catch(ServiceSecurityException se){
				throw se;
			} catch (Throwable e1) {
				// do nothing
			}
		}
		if(e instanceof RuntimeDaoException){
			throw new ServiceRuntimeException(ExceptionType.DAO.ordinal(),e); 
		}else if(e instanceof JedisException){
			throw new ServiceRuntimeException(ExceptionType.REDIS_ERROR.ordinal(),e);
		}
		throw new ServiceRuntimeException(e); 
	}
	@Override
	public PersonBean getPerson(int personId) {
		return dm.daoGetPerson(personId);
	}
	@Override
	public List<PersonBean> getPersons(List<Integer> idList) {
		return dm.daoGetPersons(idList);
	}
	@Override
	public PersonBean getPersonByPapersNum(String papersNum)  {
		return dm.daoGetPersonByIndexPapersNum(papersNum);
	}

	@Override
	public List<String> getFeatureBeansByPersonId(int personId) {
		return dm.daoToPrimaryKeyListFromFeatures(dm.daoGetFeatureBeansByPersonIdOnPerson(personId));
	}

	@Override
	public int deletePerson(final int personId, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePerson(personId);
				}});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int deletePersons(final List<Integer> personIdList, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePersonsByPrimaryKey(personIdList);
				}});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int deletePersonByPapersNum(final String papersNum, Token token)  {
		try{	
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePersonByPapersNum(papersNum);
				}});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public  int deletePersonsByPapersNum(final List<String> papersNumlist, Token token) {
		try{		
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePersonByPapersNum(papersNumlist);
				}});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public boolean existsPerson(int persionId) {
		return dm.daoExistsPerson(persionId);
	}

	@Override
	public boolean isDisable(int personId){
		return dm.daoIsDisable(personId);
	}

	@Override
	public void disablePerson(int personId, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSetPersonExpiryDate(dm.daoGetPerson(personId),new Date());
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void setPersonExpiryDate(int personId,long expiryDate, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSetPersonExpiryDate(dm.daoGetPerson(personId),new Date(expiryDate));
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public  void setPersonExpiryDate(final List<Integer> personIdList,final long expiryDate, Token token){
		try{		
			Enable.PERSON_ONLY.check(tm, token);
			BaseDao.daoRunAsTransaction(new Runnable(){
				@Override
				public void run() {
					dm.daoSetPersonExpiryDate(personIdList,new Date(expiryDate));
				}});			
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public  void disablePerson(final List<Integer> personIdList, Token token){
		setPersonExpiryDate(personIdList,System.currentTimeMillis(), token);
	}

	@Override
	public List<LogBean> getLogBeansByPersonId(int personId) {
		return dm.daoGetLogBeansByPersonIdOnPerson(personId);
	}

	@Override
	public List<Integer> loadAllPerson() {
		return dm.daoLoadPersonIdByWhere(null);
	}

	@Override
	public List<Integer> loadPersonIdByWhere(String where) {
			return dm.daoLoadPersonIdByWhere(where);
	}
	@Override
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows)  {
			return dm.daoLoadPersonByWhere(where, startRow, numRows);
	}
	@Override
	public int countPersonByWhere(String where) {
			return dm.daoCountPersonByWhere(where);
	}
	@Override
	public PersonBean savePerson(PersonBean bean, Token token) {
		try{
			Enable.ALL.check(tm, token);
			return dm.daoSavePerson(bean);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void savePersons(List<PersonBean> beans, Token token)  {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSavePersonsAsTransaction(beans);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, Token token) {
		try{
			Enable.ALL.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, null,null);
				}});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int savePerson(final Map<ByteBuffer,PersonBean> persons, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoSavePerson(persons);
				}});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final String idPhotoMd5, final String featureMd5, Token token)
			 {
		try {
			Enable.ALL.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, dm.daoGetImage(idPhotoMd5), Arrays.asList(dm.daoGetFeature(featureMd5)));
				}
			});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	
	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final FeatureBean featureBean,
			final Integer deviceId, Token token)  {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, featureBean, dm.daoGetDevice(deviceId));
				}
			});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final List<FaceBean> faceBeans, Token token)  {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, dm.daoAddFeature(feature, bean, faceBeans), null);
				}
			});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final Map<ByteBuffer, FaceBean> faceInfo, final Integer deviceId, Token token)  {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, feature, faceInfo, dm.daoGetDevice(deviceId));
				}
			});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final ByteBuffer featureImage, final FaceBean featureFaceBean, final Integer deviceId, Token token) {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean,idPhoto,feature,featureImage,featureFaceBean,dm.daoGetDevice(deviceId));
				}});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void replaceFeature(final Integer personId, final String featureMd5, final boolean deleteOldFeatureImage, Token token)
			 {
		try {
			Enable.ALL.check(tm, token);
			BaseDao.daoRunAsTransaction(new Runnable() {
				@Override
				public void run() {
					dm.daoReplaceFeature(personId, featureMd5, deleteOldFeatureImage);
				}
			});
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public List<Integer> loadUpdatedPersons(long timestamp) {
		return dm.daoLoadUpdatedPersons(new Date(timestamp));
	}

	@Override
	public List<Integer> loadPersonIdByUpdateTime(long timestamp) {
		return dm.daoLoadPersonIdByUpdateTime(new Date(timestamp));
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(long timestamp) {
		return dm.daoLoadFeatureMd5ByUpdateTime(new Date(timestamp));
	}

	@Override
	public void addLog(LogBean bean, Token token)throws DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			dm.daoAddLog(bean);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void addLogs(List<LogBean> beans, Token token)throws DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			dm.daoAddLogsAsTransaction(beans);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows)  {
		return dm.daoLoadLogByWhere(where, startRow, numRows);
	}

	@Override
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows)  {
		return dm.daoLoadLogLightByWhere(where, startRow, numRows);
	}

	@Override
	public int countLogLightByWhere(String where)  {
		return dm.daoCountLogLightByWhere(where);
	}

	@Override
	public int countLogByWhere(String where)  {
		return dm.daoCountLogByWhere(where);
	}
	@Override
    public List<LogLightBean> loadLogLightByVerifyTime(long timestamp,int startRow, int numRows){
		return dm.daoLoadLogLightByVerifyTime(new Date(timestamp),startRow,numRows);
    }
    @Override
    public int countLogLightByVerifyTime(long timestamp){
		return dm.daoCountLogLightByVerifyTime(new Date(timestamp));
    }
    @Override
	public boolean existsImage(String md5)  {
		return dm.daoExistsImage(md5);
	}

	@Override
	public ImageBean addImage(ByteBuffer imageData,Integer deviceId
			, FaceBean faceBean , Integer personId, Token token) throws DuplicateRecordException{
		try{
			Enable.ALL.check(tm, token);
			return dm.daoAddImage(imageData,dm.daoGetDevice(deviceId),Arrays.asList(faceBean),Arrays.asList(dm.daoGetPerson(personId)));		
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

    @Override
	public boolean existsFeature(String md5)  {
		return dm.daoExistsFeature(md5);
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature,Integer personId,List<FaceBean> faecBeans, Token token)throws DuplicateRecordException{
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			return dm.daoAddFeature(feature, dm.daoGetPerson(personId), faecBeans);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, Map<ByteBuffer, FaceBean> faceInfo,
			Integer deviceId, Token token) throws DuplicateRecordException {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return dm.daoAddFeature(feature, dm.daoGetPerson(personId), faceInfo, dm.daoGetDevice(deviceId));
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public List<String> deleteFeature(String featureMd5,boolean deleteImage, Token token){
		try{
			Enable.ALL.check(tm, token);
			return dm.daoDeleteFeature(featureMd5,deleteImage);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int deleteAllFeaturesByPersonId(int personId,boolean deleteImage, Token token){
		try{
			Enable.ALL.check(tm, token);
			return dm.daoDeleteAllFeaturesByPersonId(personId,deleteImage);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public FeatureBean getFeature(String md5){
		return dm.daoGetFeature(md5);
	}

	@Override
	public List<FeatureBean> getFeatures(List<String> md5){
		return dm.daoGetFeatures(md5);
	}
	@Override
	public List<String> getFeaturesOfPerson(int personId){
		return Lists.transform(
				dm.daoGetFeatureBeansByPersonIdOnPerson(personId),
				dm.daoCastFeatureToPk); 
	}
	@Override
	public byte[] getFeatureBytes(String md5){
		FeatureBean featureBean = dm.daoGetFeature(md5);
		return null ==featureBean?null:featureBean.getFeature();
	}

	@Override
	public ByteBuffer getImageBytes(String imageMD5){
		StoreBean storeBean = dm.daoGetStore(imageMD5);
		return null ==storeBean?null:storeBean.getData();
	}

	@Override
	public ImageBean getImage(String imageMD5){
		return dm.daoGetImage(imageMD5);
	}

	@Override
	public List<String> getImagesAssociatedByFeature(String featureMd5){
		return dm.daoGetImageKeysImportedByFeatureMd5(featureMd5);
	}
	@Override
	public Integer getDeviceIdOfFeature(String featureMd5) {
		return dm.daoGetDeviceIdOfFeature(featureMd5);
	}
	@Override
	public int deleteImage(String imageMd5, Token token){
		try{
			Enable.ALL.check(tm, token);
			return dm.daoDeleteImage(imageMd5);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

    @Override
	public boolean existsDevice(int id)  {
		return dm.daoExistsDevice(id);
	}

	@Override
	public DeviceBean saveDevice(DeviceBean deviceBean, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSaveDevice(deviceBean);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceBean updateDevice(DeviceBean deviceBean, Token token){
		try{
			Enable.ALL.check(tm, token);
			checkArgument(null != deviceBean && !deviceBean.isNew(),
					"require the device must be exists record");
			return dm.daoSaveDevice(deviceBean);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceBean getDevice(int deviceId){
		return dm.daoGetDevice(deviceId);
	}

	@Override
	public List<DeviceBean> getDevices(List<Integer> idList){
		return dm.daoGetDevices(idList);
	}
	@Override
	public List<DeviceBean> loadDeviceByWhere(String where,int startRow, int numRows){
		return this.dm.daoLoadDeviceByWhere(where, startRow, numRows);
	}
	@Override
	public int countDeviceByWhere(String where){
		return this.dm.daoCountDeviceByWhere(where);
	}
	@Override
	public List<Integer> loadDeviceIdByWhere(String where){
		return this.dm.daoLoadDeviceIdByWhere(where);
	}
	////////////////////////////////DeviceGroupBean/////////////
	
	@Override
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSaveDeviceGroup(deviceGroupBean);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceGroupBean getDeviceGroup(int deviceGroupId) {
		return dm.daoGetDeviceGroup(deviceGroupId);
	}
	@Override
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList) {
		return dm.daoGetDeviceGroups(groupIdList); 
	}
	@Override
	public int deleteDeviceGroup(int deviceGroupId, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeleteDeviceGroup(deviceGroupId);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public List<Integer> getSubDeviceGroup(int deviceGroupId) {
		return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoGetSubDeviceGroup(deviceGroupId));
	}
	@Override
	public List<Integer> getDevicesOfGroup(int deviceGroupId) {
		return dm.daoToPrimaryKeyListFromDevices(dm.daoGetDevicesOfGroup(deviceGroupId));
	}
	@Override
	public List<Integer> listOfParentForDeviceGroup(int deviceGroupId){
		return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoListOfParentForDeviceGroup(deviceGroupId));
	}
	@Override
	public List<Integer> getDeviceGroupsBelongs(int deviceId){
		DeviceBean deviceBean = dm.daoGetDevice(deviceId);
		return null == deviceBean 
					? ImmutableList.<Integer>of()
					: listOfParentForDeviceGroup(deviceBean.getGroupId());
	}
	////////////////////////////////PersonGroupBean/////////////
	
	@Override
	public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSavePersonGroup(personGroupBean);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public PersonGroupBean getPersonGroup(int personGroupId) {
		return dm.daoGetPersonGroup(personGroupId); 
	}
	@Override
	public List<PersonGroupBean> getPersonGroups(Collection<Integer> groupIdList) {
		return dm.daoGetPersonGroups(groupIdList);
	}
	@Override
	public int deletePersonGroup(int personGroupId, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeletePersonGroup(personGroupId);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public List<Integer> getSubPersonGroup(int personGroupId) {
		return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoGetSubPersonGroup(personGroupId));
	}
	@Override
	public List<Integer> getPersonsOfGroup(int personGroupId) {
		return this.dm.daoToPrimaryKeyListFromPersons(dm.daoGetPersonsOfGroup(personGroupId));
	}
	@Override
	public List<Integer> listOfParentForPersonGroup(int personGroupId){
		return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoListOfParentForPersonGroup(personGroupId));
	}
	@Override
	public List<Integer> getPersonGroupsBelongs(int personId){
		PersonBean personBean = dm.daoGetPerson(personId);
		return null == personBean 
					? ImmutableList.<Integer>of()
					: listOfParentForPersonGroup(personBean.getGroupId());
	}
    @Override
    public List<Integer> loadDeviceGroupByWhere(String where,int startRow, int numRows){
		return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoLoadDeviceGroupByWhere(where, startRow, numRows));
    }
    @Override
    public int countDeviceGroupByWhere(String where){
		return dm.daoCountDeviceGroupByWhere(where);
    }
    @Override
    public List<Integer> loadDeviceGroupIdByWhere(String where){
   		return dm.daoLoadDeviceGroupIdByWhere(where);
    }
	/////////////////////PERMIT/////
    
	@Override
	public void addPermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoAddPermit(deviceGroup, personGroup);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public void addPermit(int deviceGroupId,int personGroupId, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoAddPermit(deviceGroupId, personGroupId);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public int deletePermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeletePermit(deviceGroup, personGroup);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public boolean getGroupPermit(int deviceId,int personGroupId) {
		return dm.daoGetGroupPermit(deviceId,personGroupId);
	}
	@Override
	public boolean getPersonPermit(int deviceId,int personId) {
		return dm.daoGetPersonPermit(deviceId,personId);
	}
	@Override
	public List<Boolean> getGroupPermits(int deviceId,List<Integer> personGroupIdList) {
		return dm.daoGetGroupPermit(deviceId, personGroupIdList);
	}
	@Override
	public List<Boolean> getPersonPermits(int deviceId,List<Integer> personIdList) {
		return dm.daoGetPermit(deviceId, personIdList);
	}
	@Override
	public List<PermitBean> loadPermitByUpdate(long timestamp) {
		return dm.daoLoadPermitByCreateTime(new Date(timestamp));
	}
    @Override
    public List<Integer> loadPersonGroupByWhere(String where,int startRow, int numRows){
   		return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoLoadPersonGroupByWhere(where, startRow, numRows));
    }
    @Override
    public int countPersonGroupByWhere(String where){
   		return dm.daoCountPersonGroupByWhere(where);
    	
    }
    @Override
    public List<Integer> loadPersonGroupIdByWhere(String where){
   		return dm.daoLoadPersonGroupIdByWhere(where);
    }
    @Override
    public DeviceBean registerDevice(DeviceBean newDevice) throws ServiceSecurityException{
   		return tm.registerDevice(newDevice);
    }
    @Override
	public void unregisterDevice(int deviceId,Token token)
			throws ServiceSecurityException{
   		tm.unregisterDevice(deviceId,token);
	}
    @Override
	public Token online(DeviceBean device)
			throws ServiceSecurityException{
   		return tm.applyDeviceToken(device);
	}
    @Override
	public void offline(Token token)
			throws ServiceSecurityException{
   		tm.releaseDeviceToken(token);
	}
    @Override
	public Token applyPersonToken(int personId, String password, boolean isMd5)
			throws ServiceSecurityException{
   		return tm.applyPersonToken(personId, password, isMd5);
	}
    @Override
	public void releasePersonToken(Token token)
			throws ServiceSecurityException{
		tm.releasePersonToken(token);
	}
    @Override
	public Token applyRootToken(String password, boolean isMd5)
			throws ServiceSecurityException{
		return tm.applyRootToken(password, isMd5);
	}
	@Override
	public void releaseRootToken(Token token)
			throws ServiceSecurityException{
   		tm.releaseRootToken(token);
	}
	@Override
	public boolean isValidPassword(String userId,String password, boolean isMd5, Token token) 
			throws ServiceSecurityException {
		Enable.PERSON_ONLY.check(tm, token);
		return tm.isValidPassword(userId, password, isMd5);
	}
    @Override
    public String applyAckChannel(Token token) {
    	return applyAckChannel(token,0L);
	}
    @Override
    public String applyAckChannel(Token token, long duration) {
    	try {
			return tm.applyAckChannel(token, duration);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 
	}
    @Override
    public long applyCmdSn(Token token) {
    	try {
			return tm.applyCmdSn(token);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 
	}
    @Override
    public boolean isValidCmdSn(long cmdSn) {
		return tm.isValidCmdSn(cmdSn);
	}
    @Override
    public boolean isValidAckChannel(String ackChannel) {
		return tm.isValidAckChannel(ackChannel);
	}
    @Override
    public Map<MQParam,String> getRedisParameters(Token token){
    	try {
			Enable.ALL.check(tm, token);
			return rm.getRedisParameters();
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 
    }
    @Override
    public String getProperty(String key,Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			return GlobalConfig.getProperty(key);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public Map<String,String> getServiceConfig(Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			return GlobalConfig.toMap(CONFIG);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public void setProperty(String key,String value,Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.setProperty(key,value);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public void setProperties(Map<String,String> config,Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.setProperties(config);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public void saveServiceConfig(Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.persistence();
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
    }
    @Override
	public String version(){
		return Version.VERSION;
	}
    @Override
    public Map<String, String> versionInfo(){
		return Version.INFO;
	}
}
