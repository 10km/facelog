package net.gdface.facelog.service;

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
import net.gdface.facelog.service.DuplicateRecordException;
import net.gdface.facelog.service.ServiceRuntimeException;
import net.gdface.facelog.service.TokenMangement.Enable;
import redis.clients.jedis.exceptions.JedisException;

/**
 * IFaceLog 服务实现
 * @author guyadong
 *
 */
public class FaceLogImpl extends BaseFaceLog implements ServiceConstant {
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
	 * 将封装在{@link RuntimeException}中的{@link ServiceSecurityException}剥离出来封装到{@link ServiceRuntimeException}
	 * @param e
	 * @return
	 * @see #throwServiceException(RuntimeException)
	 */
	protected static final ServiceRuntimeException wrapServiceRuntimeException(RuntimeException e){
		try{
			throwServiceException(e);
			// dead code
			return new ServiceRuntimeException(e); 
		} catch(ServiceSecurityException se){
			return new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),se);
		} catch(ServiceRuntimeException se){
			return se;
		}
	}
	/**
	 * 将封装在{@link RuntimeException}中的{@link ServiceSecurityException}剥离出来单独抛出<br>
	 * 将其他的{@link RuntimeException}封装在{@link ServiceRuntimeException}抛出，
	 * @param e
	 * @return
	 * @throws ServiceRuntimeException
	 * @throws ServiceSecurityException
	 */
	protected static final <T> T throwServiceException(RuntimeException e) 
			throws ServiceRuntimeException, ServiceSecurityException{
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
	public PersonBean getPerson(int personId)throws ServiceRuntimeException {
		try{
			return dm.daoGetPerson(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PersonBean> getPersons(List<Integer> idList)throws ServiceRuntimeException {
		try{
			return dm.daoGetPersons(idList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PersonBean getPersonByPapersNum(String papersNum)throws ServiceRuntimeException  {
		try{
			return dm.daoGetPersonByIndexPapersNum(papersNum);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> getFeatureBeansByPersonId(int personId)throws ServiceRuntimeException {
		try{
			return dm.daoToPrimaryKeyListFromFeatures(dm.daoGetFeatureBeansByPersonIdOnPerson(personId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public int deletePerson(final int personId, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePerson(personId);
				}});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int deletePersons(final List<Integer> personIdList, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePersonsByPrimaryKey(personIdList);
				}});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int deletePersonByPapersNum(final String papersNum, Token token)throws ServiceRuntimeException  {
		try{	
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePersonByPapersNum(papersNum);
				}});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public  int deletePersonsByPapersNum(final List<String> papersNumlist, Token token)throws ServiceRuntimeException {
		try{		
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoDeletePersonByPapersNum(papersNumlist);
				}});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public boolean existsPerson(int persionId)throws ServiceRuntimeException {
		try{
			return dm.daoExistsPerson(persionId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public boolean isDisable(int personId)throws ServiceRuntimeException{
		try{
			return dm.daoIsDisable(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void disablePerson(int personId, Token token)throws ServiceRuntimeException{
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSetPersonExpiryDate(dm.daoGetPerson(personId),new Date());
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void setPersonExpiryDate(int personId,long expiryDate, Token token)throws ServiceRuntimeException{
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSetPersonExpiryDate(dm.daoGetPerson(personId),new Date(expiryDate));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public  void setPersonExpiryDate(final List<Integer> personIdList,final long expiryDate, Token token)throws ServiceRuntimeException{
		try{		
			Enable.PERSON_ONLY.check(tm, token);
			BaseDao.daoRunAsTransaction(new Runnable(){
				@Override
				public void run() {
					dm.daoSetPersonExpiryDate(personIdList,new Date(expiryDate));
				}});			
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public  void disablePerson(final List<Integer> personIdList, Token token)throws ServiceRuntimeException{
		setPersonExpiryDate(personIdList,System.currentTimeMillis(), token);
	}

	@Override
	public List<LogBean> getLogBeansByPersonId(int personId)throws ServiceRuntimeException {
		try{
			return dm.daoGetLogBeansByPersonIdOnPerson(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<Integer> loadAllPerson()throws ServiceRuntimeException {
		try{
			return dm.daoLoadPersonIdByWhere(null);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<Integer> loadPersonIdByWhere(String where)throws ServiceRuntimeException {
		try{
			return dm.daoLoadPersonIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		try{
			return dm.daoLoadPersonByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int countPersonByWhere(String where)throws ServiceRuntimeException {
		try{
			return dm.daoCountPersonByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PersonBean savePerson(PersonBean bean, Token token)throws ServiceRuntimeException {
		try{
			Enable.ALL.check(tm, token);
			return dm.daoSavePerson(bean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void savePersons(List<PersonBean> beans, Token token)throws ServiceRuntimeException  {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSavePersonsAsTransaction(beans);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, Token token)throws ServiceRuntimeException {
		try{
			Enable.ALL.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, null,null);
				}});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int savePerson(final Map<ByteBuffer,PersonBean> persons, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoSavePerson(persons);
				}});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final String idPhotoMd5, final String featureMd5, Token token)
			throws ServiceRuntimeException {
		try {
			Enable.ALL.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, dm.daoGetImage(idPhotoMd5), Arrays.asList(dm.daoGetFeature(featureMd5)));
				}
			});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	
	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final FeatureBean featureBean,
			final Integer deviceId, Token token) throws ServiceRuntimeException {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, featureBean, dm.daoGetDevice(deviceId));
				}
			});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final List<FaceBean> faceBeans, Token token) throws ServiceRuntimeException {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, dm.daoAddFeature(feature, bean, faceBeans), null);
				}
			});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final Map<ByteBuffer, FaceBean> faceInfo, final Integer deviceId, Token token) throws ServiceRuntimeException {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean, idPhoto, feature, faceInfo, dm.daoGetDevice(deviceId));
				}
			});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean bean, final ByteBuffer idPhoto, final ByteBuffer feature,
			final ByteBuffer featureImage, final FaceBean featureFaceBean, final Integer deviceId, Token token)throws ServiceRuntimeException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(bean,idPhoto,feature,featureImage,featureFaceBean,dm.daoGetDevice(deviceId));
				}});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void replaceFeature(final Integer personId, final String featureMd5, final boolean deleteOldFeatureImage, Token token)
			throws ServiceRuntimeException {
		try {
			Enable.ALL.check(tm, token);
			BaseDao.daoRunAsTransaction(new Runnable() {
				@Override
				public void run() {
					dm.daoReplaceFeature(personId, featureMd5, deleteOldFeatureImage);
				}
			});
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public List<Integer> loadUpdatedPersons(long timestamp)throws ServiceRuntimeException {
		try{
			return dm.daoLoadUpdatedPersons(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<Integer> loadPersonIdByUpdateTime(long timestamp)throws ServiceRuntimeException {
		try{
			return dm.daoLoadPersonIdByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(long timestamp)throws ServiceRuntimeException {
		try{		
			return dm.daoLoadFeatureMd5ByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void addLog(LogBean bean, Token token)throws ServiceRuntimeException, DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			dm.daoAddLog(bean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public void addLogs(List<LogBean> beans, Token token)throws ServiceRuntimeException, DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			dm.daoAddLogsAsTransaction(beans);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		try{
			return dm.daoLoadLogByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		try{
			return dm.daoLoadLogLightByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public int countLogLightByWhere(String where) throws ServiceRuntimeException {
		try{         
			return dm.daoCountLogLightByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public int countLogByWhere(String where) throws ServiceRuntimeException {
		try{
			return dm.daoCountLogByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
    public List<LogLightBean> loadLogLightByVerifyTime(long timestamp,int startRow, int numRows)throws ServiceRuntimeException{
		try{
			return dm.daoLoadLogLightByVerifyTime(new Date(timestamp),startRow,numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countLogLightByVerifyTime(long timestamp)throws ServiceRuntimeException{
		try{
			return dm.daoCountLogLightByVerifyTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
	public boolean existsImage(String md5) throws ServiceRuntimeException {
		try{
			return dm.daoExistsImage(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ImageBean addImage(ByteBuffer imageData,Integer deviceId
			, FaceBean faceBean , Integer personId, Token token) throws ServiceRuntimeException, DuplicateRecordException{
		try{
			Enable.ALL.check(tm, token);
			return dm.daoAddImage(imageData,dm.daoGetDevice(deviceId),Arrays.asList(faceBean),Arrays.asList(dm.daoGetPerson(personId)));		
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

    @Override
	public boolean existsFeature(String md5) throws ServiceRuntimeException {
		try{
			return dm.daoExistsFeature(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature,Integer personId,List<FaceBean> faecBeans, Token token)throws ServiceRuntimeException, DuplicateRecordException{
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			return dm.daoAddFeature(feature, dm.daoGetPerson(personId), faecBeans);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, Map<ByteBuffer, FaceBean> faceInfo,
			Integer deviceId, Token token) throws ServiceRuntimeException, DuplicateRecordException {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			return dm.daoAddFeature(feature, dm.daoGetPerson(personId), faceInfo, dm.daoGetDevice(deviceId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public List<String> deleteFeature(String featureMd5,boolean deleteImage, Token token)throws ServiceRuntimeException{
		try{
			Enable.ALL.check(tm, token);
			return dm.daoDeleteFeature(featureMd5,deleteImage);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public int deleteAllFeaturesByPersonId(int personId,boolean deleteImage, Token token)throws ServiceRuntimeException{
		try{
			Enable.ALL.check(tm, token);
			return dm.daoDeleteAllFeaturesByPersonId(personId,deleteImage);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public FeatureBean getFeature(String md5)throws ServiceRuntimeException{
		try{
			return dm.daoGetFeature(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<FeatureBean> getFeatures(List<String> md5)throws ServiceRuntimeException{
		try{
			return dm.daoGetFeatures(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<String> getFeaturesOfPerson(int personId)throws ServiceRuntimeException{
		try{			
			return Lists.transform(
					dm.daoGetFeatureBeansByPersonIdOnPerson(personId),
					dm.daoCastFeatureToPk); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public ByteBuffer getFeatureBytes(String md5)throws ServiceRuntimeException{
		try{
			FeatureBean featureBean = dm.daoGetFeature(md5);
			return null ==featureBean?null:featureBean.getFeature();
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ByteBuffer getImageBytes(String imageMD5)throws ServiceRuntimeException{
		try{
			StoreBean storeBean = dm.daoGetStore(imageMD5);
			return null ==storeBean?null:storeBean.getData();
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ImageBean getImage(String imageMD5)throws ServiceRuntimeException{
		try{
			return dm.daoGetImage(imageMD5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> getImagesAssociatedByFeature(String featureMd5)throws ServiceRuntimeException{
		try{
			return dm.daoGetImageKeysImportedByFeatureMd5(featureMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public Integer getDeviceIdOfFeature(String featureMd5) throws ServiceRuntimeException{
		try{
			return dm.daoGetDeviceIdOfFeature(featureMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deleteImage(String imageMd5, Token token)throws ServiceRuntimeException{
		try{
			Enable.ALL.check(tm, token);
			return dm.daoDeleteImage(imageMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

    @Override
	public boolean existsDevice(int id) throws ServiceRuntimeException {
		try{
			return dm.daoExistsDevice(id);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public DeviceBean saveDevice(DeviceBean deviceBean, Token token)throws ServiceRuntimeException{
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSaveDevice(deviceBean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceBean updateDevice(DeviceBean deviceBean, Token token)throws ServiceRuntimeException{
		try{
			Enable.ALL.check(tm, token);
			checkArgument(null != deviceBean && !deviceBean.isNew(),
					"require the device must be exists record");
			return dm.daoSaveDevice(deviceBean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceBean getDevice(int deviceId)throws ServiceRuntimeException{
		try{
			return dm.daoGetDevice(deviceId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<DeviceBean> getDevices(List<Integer> idList)throws ServiceRuntimeException{
		try{
			return dm.daoGetDevices(idList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public List<DeviceBean> loadDeviceByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
		try{
			return this.dm.daoLoadDeviceByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int countDeviceByWhere(String where)throws ServiceRuntimeException{
		try{
			return this.dm.daoCountDeviceByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> loadDeviceIdByWhere(String where)throws ServiceRuntimeException{
		try{
			return this.dm.daoLoadDeviceIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	////////////////////////////////DeviceGroupBean/////////////
	
	@Override
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSaveDeviceGroup(deviceGroupBean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceGroupBean getDeviceGroup(int deviceGroupId)throws ServiceRuntimeException {
		try{
			return dm.daoGetDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList)throws ServiceRuntimeException {
		try{
			return dm.daoGetDeviceGroups(groupIdList); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deleteDeviceGroup(int deviceGroupId, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeleteDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public List<Integer> getSubDeviceGroup(int deviceGroupId)throws ServiceRuntimeException {
		try{
			return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoGetSubDeviceGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getDevicesOfGroup(int deviceGroupId)throws ServiceRuntimeException {
		try{
			return dm.daoToPrimaryKeyListFromDevices(dm.daoGetDevicesOfGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> listOfParentForDeviceGroup(int deviceGroupId)throws ServiceRuntimeException{
		try{
			return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoListOfParentForDeviceGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getDeviceGroupsBelongs(int deviceId)throws ServiceRuntimeException{
		try{
			DeviceBean deviceBean = dm.daoGetDevice(deviceId);
			return null == deviceBean 
						? ImmutableList.<Integer>of()
						: listOfParentForDeviceGroup(deviceBean.getGroupId());
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	////////////////////////////////PersonGroupBean/////////////
	
	@Override
	public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSavePersonGroup(personGroupBean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public PersonGroupBean getPersonGroup(int personGroupId)throws ServiceRuntimeException {
		try{
			return dm.daoGetPersonGroup(personGroupId); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PersonGroupBean> getPersonGroups(Collection<Integer> groupIdList)throws ServiceRuntimeException {
		try{
			return dm.daoGetPersonGroups(groupIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deletePersonGroup(int personGroupId, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeletePersonGroup(personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public List<Integer> getSubPersonGroup(int personGroupId)throws ServiceRuntimeException {
		try{
			return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoGetSubPersonGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getPersonsOfGroup(int personGroupId)throws ServiceRuntimeException {
		try{
			return this.dm.daoToPrimaryKeyListFromPersons(dm.daoGetPersonsOfGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> listOfParentForPersonGroup(int personGroupId)throws ServiceRuntimeException{
		try{
			return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoListOfParentForPersonGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getPersonGroupsBelongs(int personId)throws ServiceRuntimeException{
		try{
			PersonBean personBean = dm.daoGetPerson(personId);
			return null == personBean 
						? ImmutableList.<Integer>of()
						: listOfParentForPersonGroup(personBean.getGroupId());
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
    @Override
    public List<Integer> loadDeviceGroupByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
		try{
			return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoLoadDeviceGroupByWhere(where, startRow, numRows));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countDeviceGroupByWhere(String where)throws ServiceRuntimeException{
		try{
			return dm.daoCountDeviceGroupByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public List<Integer> loadDeviceGroupIdByWhere(String where)throws ServiceRuntimeException{
    	try{
    		return dm.daoLoadDeviceGroupIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
	/////////////////////PERMIT/////
    
	@Override
	public void addPermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoAddPermit(deviceGroup, personGroup);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public void addPermit(int deviceGroupId,int personGroupId, Token token)throws ServiceRuntimeException{
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoAddPermit(deviceGroupId, personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public int deletePermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeletePermit(deviceGroup, personGroup);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public boolean getGroupPermit(int deviceId,int personGroupId)throws ServiceRuntimeException {
		try{
			return dm.daoGetGroupPermit(deviceId,personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public boolean getPersonPermit(int deviceId,int personId)throws ServiceRuntimeException {
		try{
			return dm.daoGetPersonPermit(deviceId,personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Boolean> getGroupPermits(int deviceId,List<Integer> personGroupIdList)throws ServiceRuntimeException {
		try{
			return dm.daoGetGroupPermit(deviceId, personGroupIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Boolean> getPersonPermits(int deviceId,List<Integer> personIdList)throws ServiceRuntimeException {
		try{
			return dm.daoGetPermit(deviceId, personIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PermitBean> loadPermitByUpdate(long timestamp)throws ServiceRuntimeException {
		try{
			return dm.daoLoadPermitByCreateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
    @Override
    public List<Integer> loadPersonGroupByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
    	try{
    		return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoLoadPersonGroupByWhere(where, startRow, numRows));
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countPersonGroupByWhere(String where)throws ServiceRuntimeException{
    	try{
    		return dm.daoCountPersonGroupByWhere(where);
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public List<Integer> loadPersonGroupIdByWhere(String where)throws ServiceRuntimeException{
    	try{
    		return dm.daoLoadPersonGroupIdByWhere(where);
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public DeviceBean registerDevice(DeviceBean newDevice) throws ServiceRuntimeException, ServiceSecurityException{
    	try{
    		return tm.registerDevice(newDevice);
    	} catch (RuntimeException e) {
			return  throwServiceException(e);
		}
    }
    @Override
	public void unregisterDevice(int deviceId,Token token)
			throws ServiceRuntimeException,ServiceSecurityException{
    	try{
    		tm.unregisterDevice(deviceId,token);
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
    @Override
	public Token online(DeviceBean device)
			throws ServiceRuntimeException, ServiceSecurityException{
    	try{
    		return tm.applyDeviceToken(device);
    	} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
    @Override
	public void offline(Token token)
			throws ServiceRuntimeException, ServiceSecurityException{
    	try{
    		tm.releaseDeviceToken(token);
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
    @Override
	public Token applyPersonToken(int personId, String password, boolean isMd5)
			throws ServiceRuntimeException, ServiceSecurityException{
    	try{
    		return tm.applyPersonToken(personId, password, isMd5);
    	} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
    @Override
	public void releasePersonToken(Token token)
			throws ServiceRuntimeException, ServiceSecurityException{
    	try{
    		tm.releasePersonToken(token);
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
    @Override
	public Token applyRootToken(String password, boolean isMd5)
			throws ServiceRuntimeException, ServiceSecurityException{
		try{
			return tm.applyRootToken(password, isMd5);
		} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
	@Override
	public void releaseRootToken(Token token)
			throws ServiceRuntimeException, ServiceSecurityException{
    	try{
    		tm.releaseRootToken(token);
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
	@Override
	public boolean isValidPassword(String userId,String password, boolean isMd5, Token token) 
			throws ServiceRuntimeException, ServiceSecurityException {
    	try{
			Enable.PERSON_ONLY.check(tm, token);
    		return tm.isValidPassword(userId, password, isMd5);
    	} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
    @Override
    public String applyAckChannel(Token token) throws ServiceRuntimeException{
    	return applyAckChannel(token,0L);
	}
    @Override
    public String applyAckChannel(Token token, long duration) throws ServiceRuntimeException{
    	try {
			return tm.applyAckChannel(token, duration);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 
	}
    @Override
    public long applyCmdSn(Token token) throws ServiceRuntimeException{
    	try {
			return tm.applyCmdSn(token);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 
	}
    @Override
    public boolean isValidCmdSn(long cmdSn) throws ServiceRuntimeException{
    	try {
			return tm.isValidCmdSn(cmdSn);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
    @Override
    public boolean isValidAckChannel(String ackChannel) throws ServiceRuntimeException{
    	try {
			return tm.isValidAckChannel(ackChannel);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
    @Override
    public Map<MQParam,String> getRedisParameters(Token token)throws ServiceRuntimeException{
    	try {
			Enable.ALL.check(tm, token);
			return rm.getRedisParameters();
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 
    }
    @Override
    public String getProperty(String key,Token token)throws ServiceRuntimeException{
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			return GlobalConfig.getProperty(key);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public Map<String,String> getServiceConfig(Token token)throws ServiceRuntimeException{
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			return GlobalConfig.toMap(CONFIG);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public void setProperty(String key,String value,Token token)throws ServiceRuntimeException{
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.setProperty(key,value);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public void setProperties(Map<String,String> config,Token token)throws ServiceRuntimeException{
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.setProperties(config);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} 	
    }
    @Override
    public void saveServiceConfig(Token token)throws ServiceRuntimeException{
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.persistence();
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
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
