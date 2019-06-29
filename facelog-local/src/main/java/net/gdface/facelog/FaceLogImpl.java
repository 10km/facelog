package net.gdface.facelog;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Strings;
import com.google.common.base.Throwables;
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
import net.gdface.facelog.ServiceSecurityException;
import net.gdface.facelog.Token.TokenType;
import net.gdface.thrift.exception.ServiceRuntimeException;
import net.gdface.utils.BaseTypeTransformer;
import net.gdface.utils.CollectionUtils;
import net.gdface.utils.FaceUtilits;
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
	private final DtalkCmd dc = new DtalkCmd(rm.getRedisParameters(), tm);
	private final TokenValidatorPersonListener tokenValidatorPersonListener = new TokenValidatorPersonListener(dm);
	private final TokenValidatorPersonGroupListener tokenValidatorPersonGroupListener = new TokenValidatorPersonGroupListener(dm);
	private final TokenValidatorDeviceListener tokenValidatorDeviceListener = new TokenValidatorDeviceListener(dm);
	private final TokenValidatorDeviceGroupListener tokenValidatorDeviceGroupListener = new TokenValidatorDeviceGroupListener(dm);

	private final RedisPersonListener redisPersonListener = new RedisPersonListener(dm);
	private final RedisPersonGroupListener redisPersonGroupListener = new RedisPersonGroupListener(dm);
	private final RedisDeviceListener redisDeviceListener = new RedisDeviceListener(dm);
	private final RedisDeviceGroupListener redisDeviceGroupListener = new RedisDeviceGroupListener(dm);
	private final RedisFeatureListener redisFeatureListener = new RedisFeatureListener();
	private final RedisPermitListener redisPermitListener = new RedisPermitListener(dm);
	private final RedisLogListener redisLogListener = new RedisLogListener(rm.getRedisParameters().get(MQParam.LOG_MONITOR_CHANNEL));

	private final BaseTypeTransformer typeTransformer = new BaseTypeTransformer();
	//private final RedisLogConsumer redisLogConsumer  = new RedisLogConsumer(this);
	static{
		LocalTokenContextOp.initCurrentTokenContextOp();
	}
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
		BaseDao.getPersonGroupManager().registerListener(redisPersonGroupListener);
		BaseDao.getDeviceManager().registerListener(redisDeviceListener);
		BaseDao.getDeviceGroupManager().registerListener(redisDeviceGroupListener);
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
	 * @param error
	 * @return
	 * @see #throwServiceException(RuntimeException)
	 */
	protected static final ServiceRuntimeException wrapServiceRuntimeException(Exception error){
		try{
			if(error instanceof RuntimeException){
				throwServiceException((RuntimeException)error);
			}else 
				throw error;
			// dead code
			return new ServiceRuntimeException(error); 
		} catch(ServiceSecurityException e){
			return new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} catch(ServiceRuntimeException e){
			return e;
		} catch (Exception e) {
			return new ServiceRuntimeException(error);
		}
	}
	/**
	 * 将封装在{@link Exception}中的{@link ServiceSecurityException}剥离出来单独抛出<br>
	 * 将其他的{@link Exception}封装在{@link ServiceRuntimeException}抛出，
	 * @param error
	 * @return
	 * @throws ServiceSecurityException
	 */
	protected static final <T> T throwServiceException(RuntimeException error) 
			throws ServiceSecurityException{
		if(null != error.getCause()){
			try{
				throw error.getCause();
			}catch(ServiceSecurityException e){
				throw e;
			} catch (Throwable e) {
				// do nothing
			}
		}
		if(error instanceof RuntimeDaoException){
			if(error.getCause() instanceof ServiceSecurityException){
				throw (ServiceSecurityException)error.getCause();
			}
			throw new ServiceRuntimeException(ExceptionType.DAO.ordinal(),error); 
		}else if(error instanceof JedisException){
			throw new ServiceRuntimeException(ExceptionType.REDIS_ERROR.ordinal(),error);
		}
		throw new ServiceRuntimeException(error); 
	}
	
	protected static final <T extends Exception> void throwCauseIfInstanceOf(Exception error,Class<T> expType) throws T {
		if(null != error.getCause()){
			Throwables.throwIfInstanceOf(error.getCause(),expType);
		}
	}
	
	/**
	 * 如果{@code token}为设备令牌则返回对应的设备信息对象{@link DeviceBean},否则返回{@code null}
	 * @param token 令牌
	 * @return {@link DeviceBean}对象或{@code null}
	 */
	private DeviceBean getDeviceOrNull(Token token){
		if(token == null){
			return null;
		}
		return token.getType() == TokenType.DEVICE ? dm.daoGetDevice(token.getId()) : null;
	}
	private static Date toDate(String date) throws ParseException{
		if(Strings.isNullOrEmpty(date)){
			return null;
		}
		try {
			return new SimpleDateFormat(TIMESTAMP_FORMATTER_STR).parse(date);
		} catch (ParseException e) {
			try {
				return new SimpleDateFormat(DATE_FORMATTER_STR).parse(date);				
			} catch (ParseException e2) {
				return new SimpleDateFormat(ISO8601_FORMATTER_STR).parse(date);
			}
		}
	}
	@Override
	public PersonBean getPerson(int personId) {
		try{
			return dm.daoGetPerson(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PersonBean> getPersons(List<Integer> idList) {
		try{
			return dm.daoGetPersons(idList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PersonBean getPersonByPapersNum(String papersNum)  {
		try{
			return dm.daoGetPersonByIndexPapersNum(papersNum);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> getFeaturesByPersonId(int personId) {
		try{
			return dm.daoToPrimaryKeyListFromFeatures(dm.daoGetFeatureBeansByPersonIdOnPerson(personId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<String> getFeaturesByPersonIdAndSdkVersion(int personId,String sdkVersion) {
		try{
			return dm.daoToPrimaryKeyListFromFeatures(dm.daoGetFeaturesByPersonIdAndSdkVersion(personId,sdkVersion));			
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<FeatureBean> getFeaturesPermittedOnDevice(int deviceId,boolean ignoreSchedule, String sdkVersion,List<String> excludeFeatureIds) {
		try{
			Set<FeatureBean> features = dm.daoGetFeaturesPermittedOnDevice(deviceId, ignoreSchedule, sdkVersion, excludeFeatureIds);
			return Lists.newArrayList(features);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
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
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
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
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
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
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
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
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public boolean existsPerson(int persionId) {
		try{
			return dm.daoExistsPerson(persionId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public boolean isDisable(int personId){
		try{
			return dm.daoIsDisable(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void disablePerson(final int personId, final Integer moveToGroupId, 
			final boolean deletePhoto, final boolean deleteFeature, final boolean deleteLog, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			BaseDao.daoRunAsTransaction(new Runnable(){
				@Override
				public void run() {
					dm.daoDisablePerson(personId, moveToGroupId, deletePhoto, deleteFeature, deleteLog);
				}});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void setPersonExpiryDate(int personId,long expiryDate, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSetPersonExpiryDate(dm.daoGetPerson(personId),new Date(expiryDate));
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public void setPersonExpiryDate(int personId,String expiryDate, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSetPersonExpiryDate(dm.daoGetPerson(personId),toDate(checkNotNull(expiryDate,"timestamp is null")));
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
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
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public  void disablePerson(final List<Integer> personIdList, Token token){
		setPersonExpiryDate(personIdList,System.currentTimeMillis(), token);
	}

	@Override
	public List<LogBean> getLogBeansByPersonId(int personId) {
		try{
			return dm.daoGetLogBeansByPersonIdOnPerson(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<Integer> loadAllPerson() {
		try{
			return dm.daoLoadPersonIdByWhere(null);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<Integer> loadPersonIdByWhere(String where) {
		try{
			return dm.daoLoadPersonIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows)  {
		try{
			return dm.daoLoadPersonByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int countPersonByWhere(String where) {
		try{
			return dm.daoCountPersonByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PersonBean savePerson(PersonBean personBean, Token token) {
		try{
			Enable.ALL.check(tm, token);
			return dm.daoSavePerson(personBean);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void savePersons(List<PersonBean> persons, Token token)  {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			dm.daoSavePersonsAsTransaction(persons);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean personBean, final byte[] idPhoto, final Token token) {
		try{
			Enable.ALL.check(tm, token);
			checkArgument(null != personBean, "personBean is null");
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(personBean, FaceUtilits.getByteBufferOrNull(idPhoto), null,getDeviceOrNull(token));
				}});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public int savePersons(final List<byte[]> photos, final List<PersonBean> persons, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			final List<ByteBuffer> buffers = Lists.transform(photos, typeTransformer.getTransformer(byte[].class, ByteBuffer.class));
			return BaseDao.daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return dm.daoSavePerson(CollectionUtils.merge(buffers, persons));
				}});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean personBean, final String idPhotoMd5, final String featureMd5, Token token)
			 {
		try {
			Enable.ALL.check(tm, token);
			checkArgument(null != personBean, "personBean is null");
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					FeatureBean featureBean = dm.daoGetFeature(featureMd5);
					return dm.daoSavePerson(personBean, dm.daoGetImage(idPhotoMd5), 
							featureBean == null ? null : Arrays.asList(featureBean));
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	
	@Override
	public PersonBean savePerson(final PersonBean personBean, final byte[] idPhoto, final FeatureBean featureBean,
			final Token token)  {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(null != personBean, "personBean is null");
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(personBean, 
							FaceUtilits.getByteBufferOrNull(idPhoto), 
							featureBean, 
							getDeviceOrNull(token));
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean personBean, final byte[] idPhoto, final byte[] feature,
			final String featureVersion, final List<FaceBean> faceBeans, final Token token)  {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(null != personBean, "personBean is null");
			checkArgument(null != feature, "feature is null");
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(personBean, FaceUtilits.getByteBufferOrNull(idPhoto), 
							dm.daoAddFeature(FaceUtilits.getByteBuffer(feature), 
									featureVersion, personBean, faceBeans), 
							getDeviceOrNull(token));
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean personBean, final byte[] idPhoto, final byte[] feature,
			final String featureVersion, List<byte[]> photos, final List<FaceBean> faces, final Token token)  {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(null != personBean, "personBean is null");
			checkArgument(null != feature, "feature is null");
			final List<ByteBuffer> buffers = Lists.transform(photos, typeTransformer.getTransformer(byte[].class, ByteBuffer.class));
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(personBean, 
							FaceUtilits.getByteBufferOrNull(idPhoto), 
							FaceUtilits.getByteBuffer(feature), 
							featureVersion, 
							CollectionUtils.merge(buffers, faces), 
							getDeviceOrNull(token));
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public PersonBean savePerson(final PersonBean personBean, final byte[] idPhoto, final byte[] feature,
			final String featureVersion, final byte[] featureImage, final FaceBean featureFaceBean, final Token token) {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(null != personBean,"personBean is null");
			return BaseDao.daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return dm.daoSavePerson(personBean,
							FaceUtilits.getByteBufferOrNull(idPhoto),
							FaceUtilits.getByteBufferOrNull(feature),
							featureVersion,
							FaceUtilits.getByteBufferOrNull(featureImage),
							featureFaceBean, 
							getDeviceOrNull(token));
				}});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void replaceFeature(final Integer personId, final String featureMd5, final boolean deleteOldFeatureImage, Token token)	{
		try {
			Enable.ALL.check(tm, token);
			BaseDao.daoRunAsTransaction(new Runnable() {
				@Override
				public void run() {
					dm.daoReplaceFeature(personId, featureMd5, deleteOldFeatureImage);
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<Integer> loadUpdatedPersons(long timestamp) {
		try{
			return dm.daoLoadUpdatedPersons(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<Integer> loadUpdatedPersons(String timestamp) {
		try{
			return dm.daoLoadUpdatedPersons(toDate(timestamp));
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> loadPersonIdByUpdateTime(long timestamp) {
		try{
			return dm.daoLoadPersonIdByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	
	@Override
	public List<Integer> loadPersonIdByUpdateTime(String timestamp) {
		try{
			return dm.daoLoadPersonIdByUpdateTime(toDate(checkNotNull(timestamp,"timestamp is null")));
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(long timestamp) {
		try{		
			return dm.daoLoadFeatureMd5ByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(String timestamp) {
		try{		
			return dm.daoLoadFeatureMd5ByUpdateTime(toDate(checkNotNull(timestamp,"timestamp is null")));
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public void addLog(LogBean logBean, Token token)throws DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(logBean != null,"logBean is null");
			dm.daoAddLog(logBean);
		} catch (Exception e) {
			Throwables.throwIfInstanceOf(e, DuplicateRecordException.class);
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void addLog(final LogBean logBean,final FaceBean faceBean,final byte[] featureImage,Token token) throws DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(logBean != null,"logBean is null");
			checkArgument(faceBean != null,"faceBean is null");
			checkArgument(featureImage != null,"featureImage is null");
			BaseDao.daoRunAsTransaction(new Callable<LogBean>() {
				@Override
				public LogBean call() throws Exception {
					return dm.daoAddLog(logBean, 
							faceBean, 
							FaceUtilits.getByteBufferNotEmpty(featureImage));
				}});
		} catch (Exception e) {
			throwCauseIfInstanceOf(e,DuplicateRecordException.class);
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public void addLogs(final List<LogBean> logBeans,final List<FaceBean> faceBeans,final List<byte[]> featureImages,Token token) throws DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(logBeans != null,"logBeans is null");
			checkArgument(faceBeans != null,"faceBeans is null");
			checkArgument(featureImages != null,"featureImages is null");
			checkArgument(logBeans.size() == faceBeans.size() && logBeans.size() == featureImages.size(),
					"size of logBeans,faceBeans,featureImages must be same");
			BaseDao.daoRunAsTransaction(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					for(int i = 0; i<logBeans.size(); ++i){
						dm.daoAddLog(
								checkNotNull(logBeans.get(i),"logBeans has null element [%s]",i), 
								checkNotNull(faceBeans.get(i),"faceBeans has null element [%s]",i), 
								FaceUtilits.getByteBufferNotEmpty(checkNotNull(featureImages.get(i),"featureImages has null element [%s]",i)));
					}
					return logBeans.size();
				}
			});
		} catch (Exception e) {
			throwCauseIfInstanceOf(e,DuplicateRecordException.class);
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public void addLogs(List<LogBean> beans, Token token)throws DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			dm.daoAddLogsAsTransaction(beans);
		} catch (Exception e) {
			Throwables.throwIfInstanceOf(e,DuplicateRecordException.class);
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows)  {
		try{
			return dm.daoLoadLogByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows)  {
		try{
			return dm.daoLoadLogLightByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public int countLogLightByWhere(String where)  {
		try{         
			return dm.daoCountLogLightByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public int countLogByWhere(String where)  {
		try{
			return dm.daoCountLogByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
    public List<LogLightBean> loadLogLightByVerifyTime(long timestamp,int startRow, int numRows){
		try{
			return dm.daoLoadLogLightByVerifyTime(new Date(timestamp),startRow,numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
	
    @Override
	public List<LogLightBean> loadLogLightByVerifyTime(String timestamp,int startRow, int numRows){
		try{
			return dm.daoLoadLogLightByVerifyTime(toDate(checkNotNull(timestamp,"timestamp is null")),startRow,numRows);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
    }
	
    @Override
    public int countLogLightByVerifyTime(long timestamp){
		try{
			return dm.daoCountLogLightByVerifyTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
	public int countLogLightByVerifyTime(String timestamp){
		try{
			return dm.daoCountLogLightByVerifyTime(toDate(checkNotNull(timestamp,"timestamp is null")));
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
	public boolean existsImage(String md5)  {
		try{
			return dm.daoExistsImage(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ImageBean addImage(byte[] imageData,Integer deviceId
			, FaceBean faceBean , Integer personId, Token token) throws DuplicateRecordException{
		try{
			Enable.ALL.check(tm, token);
			checkArgument( null != imageData,"imageData is null");
			return dm.daoAddImage(FaceUtilits.getByteBuffer(imageData),
					dm.daoGetDevice(deviceId),Arrays.asList(faceBean),Arrays.asList(dm.daoGetPerson(personId)));		
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		} catch (IOException e) {
			throw new ServiceRuntimeException(ExceptionType.IMAGE_ERROR.ordinal(),e);
		} 
	}

    @Override
	public boolean existsFeature(String md5)  {
		try{
			return dm.daoExistsFeature(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public FeatureBean addFeature(final byte[] feature,final String featureVersion,final Integer personId, final List<FaceBean> faecBeans, Token token)
			throws DuplicateRecordException{
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument( null != feature,"feature is null");
			return BaseDao.daoRunAsTransaction(new Callable<FeatureBean>() {

				@Override
				public FeatureBean call() throws Exception {
					return dm.daoAddFeature(FaceUtilits.getByteBuffer(feature), 
							featureVersion, 
							dm.daoGetPerson(personId), 
							faecBeans);
				}
			});
		} catch (Exception e) {
			throwCauseIfInstanceOf(e,DuplicateRecordException.class);
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public FeatureBean addFeature(final byte[] feature,
			final String featureVersion,
			final Integer personId,
			final boolean asIdPhotoIfAbsent,
			final byte[] featurePhoto,
			final FaceBean faceBean, final Token token)throws DuplicateRecordException{
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(feature != null,"feature is null");

			return BaseDao.daoRunAsTransaction(new Callable<FeatureBean>() {
				@Override
				public FeatureBean call() throws Exception {
					PersonBean personBean = dm.daoGetPerson(personId);
					List<FaceBean> faceList = faceBean == null ? null : Arrays.asList(faceBean);
					ImageBean imageBean = dm.daoAddImage(FaceUtilits.getByteBufferOrNull(featurePhoto), 
							getDeviceOrNull(token),faceList, null);
					if(personBean != null && imageBean != null 
							&& personBean.getImageMd5() == null && asIdPhotoIfAbsent){
						personBean.setImageMd5(imageBean.getMd5());
					}
					return dm.daoAddFeature(ByteBuffer.wrap(feature), featureVersion, personBean, faceList);					
				}
			});
		} catch (Exception e) {
			throwCauseIfInstanceOf(e,DuplicateRecordException.class);
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public FeatureBean addFeature(final byte[] feature, final String featureVersion, final Integer personId,
			List<byte[]> photos, final List<FaceBean> faces, final Token token) throws DuplicateRecordException {
		try {
			Enable.DEVICE_ONLY.check(tm, token);
			checkArgument(feature != null,"feature is null");
			final List<ByteBuffer> buffers = Lists.transform(photos, typeTransformer.getTransformer(byte[].class, ByteBuffer.class));
			return BaseDao.daoRunAsTransaction(new Callable<FeatureBean>() {

				@Override
				public FeatureBean call() throws Exception {
					return dm.daoAddFeature(FaceUtilits.getByteBuffer(feature), 
							featureVersion, dm.daoGetPerson(personId), 
							CollectionUtils.merge(buffers, faces), 
							getDeviceOrNull(token));
				}
			});
		} catch (Exception e) {
			throwCauseIfInstanceOf(e,DuplicateRecordException.class);
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<String> deleteFeature(final String featureMd5,final boolean deleteImage, Token token){
		try{
			Enable.ALL.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<List<String>>() {
				@Override
				public List<String> call() throws Exception {
					return dm.daoDeleteFeature(featureMd5,deleteImage);
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public int deleteAllFeaturesByPersonId(final int personId,final boolean deleteImage, Token token){
		try{
			Enable.ALL.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					return dm.daoDeleteAllFeaturesByPersonId(personId,deleteImage);
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public FeatureBean getFeature(String md5){
		try{
			return dm.daoGetFeature(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<FeatureBean> getFeatures(List<String> md5){
		try{
			return dm.daoGetFeatures(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<String> getFeaturesOfPerson(int personId){
		try{			
			return Lists.transform(
					dm.daoGetFeatureBeansByPersonIdOnPerson(personId),
					dm.daoCastFeatureToPk); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public byte[] getFeatureBytes(String md5){		
		try {
			FeatureBean featureBean = dm.daoGetFeature(md5);
			return null ==featureBean?null:FaceUtilits.getBytes(featureBean.getFeature());
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public byte[] getImageBytes(String imageMD5){
		try {
			StoreBean storeBean = dm.daoGetStore(imageMD5);
			return null ==storeBean?null:FaceUtilits.getBytes(storeBean.getData());
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ImageBean getImage(String imageMD5){
		try{
			return dm.daoGetImage(imageMD5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> getImagesAssociatedByFeature(String featureMd5){
		try{
			return dm.daoGetImageKeysImportedByFeatureMd5(featureMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	
	@Override
	public FaceBean getFace(int faceId){
		try{
			return dm.daoGetFace(faceId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public Integer getDeviceIdOfFeature(String featureMd5) {
		try{
			return dm.daoGetDeviceIdOfFeature(featureMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deleteImage(final String imageMd5, Token token){
		try{
			Enable.ALL.check(tm, token);
			return BaseDao.daoRunAsTransaction(new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					return dm.daoDeleteImage(imageMd5);
				}
			});
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}

    @Override
	public boolean existsDevice(int id)  {
		try{
			return dm.daoExistsDevice(id);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public DeviceBean saveDevice(DeviceBean deviceBean, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSaveDevice(deviceBean);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public DeviceBean updateDevice(DeviceBean deviceBean, Token token){
		try{
			Enable.ALL.check(tm, token);
			checkArgument(null != deviceBean && !deviceBean.isNew(),
					"require the device must be exists record");
			return dm.daoSaveDevice(deviceBean);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public DeviceBean getDevice(int deviceId){
		try{
			return dm.daoGetDevice(deviceId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<DeviceBean> getDevices(List<Integer> idList){
		try{
			return dm.daoGetDevices(idList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public List<DeviceBean> loadDeviceByWhere(String where,int startRow, int numRows){
		try{
			return this.dm.daoLoadDeviceByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int countDeviceByWhere(String where){
		try{
			return this.dm.daoCountDeviceByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> loadDeviceIdByWhere(String where){
		try{
			return this.dm.daoLoadDeviceIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	////////////////////////////////DeviceGroupBean/////////////
	
	@Override
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSaveDeviceGroup(deviceGroupBean);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public DeviceGroupBean getDeviceGroup(int deviceGroupId) {
		try{
			return dm.daoGetDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList) {
		try{
			return dm.daoGetDeviceGroups(groupIdList); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deleteDeviceGroup(int deviceGroupId, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeleteDeviceGroup(deviceGroupId);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getSubDeviceGroup(int deviceGroupId) {
		try{
			return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoGetSubDeviceGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getDevicesOfGroup(int deviceGroupId) {
		try{
			return dm.daoToPrimaryKeyListFromDevices(dm.daoGetDevicesOfGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> listOfParentForDeviceGroup(int deviceGroupId){
		try{
			return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoListOfParentForDeviceGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> childListForDeviceGroup(int deviceGroupId){
		try{
			return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoChildListByParentForDeviceGroup(deviceGroupId));
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
	public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSavePersonGroup(personGroupBean);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PersonGroupBean getPersonGroup(int personGroupId) {
		try{
			return dm.daoGetPersonGroup(personGroupId); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PersonGroupBean> getPersonGroups(List<Integer> groupIdList) {
		try{
			return dm.daoGetPersonGroups(groupIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deletePersonGroup(int personGroupId, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeletePersonGroup(personGroupId);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getSubPersonGroup(int personGroupId) {
		try{
			return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoGetSubPersonGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getPersonsOfGroup(int personGroupId) {
		try{
			return this.dm.daoToPrimaryKeyListFromPersons(dm.daoGetPersonsOfGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> listOfParentForPersonGroup(int personGroupId){
		try{
			return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoListOfParentForPersonGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> childListForPersonGroup(int personGroupId){
		try{
			return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoChildListByParentForPersonGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getPersonGroupsBelongs(int personId){
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
    public List<Integer> loadDeviceGroupByWhere(String where,int startRow, int numRows){
		try{
			return dm.daoToPrimaryKeyListFromDeviceGroups(dm.daoLoadDeviceGroupByWhere(where, startRow, numRows));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countDeviceGroupByWhere(String where){
		try{
			return dm.daoCountDeviceGroupByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public List<Integer> loadDeviceGroupIdByWhere(String where){
    	try{
    		return dm.daoLoadDeviceGroupIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
	/////////////////////MANAGEMENT BORDER/////

	@Override
	public void bindBorder(Integer personGroupId,Integer deviceGroupId, Token token) {
    	try{
    		dm.daoBindBorder(personGroupId,deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void unbindBorder(Integer personGroupId,Integer deviceGroupId, Token token) {
    	try{
    		dm.daoUnbindBorder(personGroupId,deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public Integer rootGroupOfPerson(Integer personId){
    	try{
    		return dm.daoRootGroupOfPerson(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public Integer rootGroupOfDevice(Integer deviceId){
    	try{
    		return dm.daoRootGroupOfDevice(deviceId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	/////////////////////PERMIT/////
	@Override
	public PermitBean savePermit(PermitBean permitBean, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSavePermit(permitBean);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	
	@Override
	public PermitBean savePermit(int deviceGroupId,int personGroupId, String schedule, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoSavePermit(deviceGroupId, personGroupId,schedule);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deletePermit(int deviceGroupId,int personGroupId, Token token) {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeletePermit(deviceGroupId, personGroupId);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deletePersonGroupPermit(int personGroupId,Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeletePersonGroupPermit(personGroupId);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deleteGroupPermitOnDeviceGroup(int deviceGroupId, Token token){
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return dm.daoDeleteGroupPermitOnDeviceGroup(deviceGroupId);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PermitBean getGroupPermitOnDeviceGroup(int deviceGroupId,int personGroupId) {
		try{
			return dm.daoGetGroupPermitOnDeviceGroup(deviceGroupId,personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PermitBean getGroupPermit(int deviceId,int personGroupId) {
		try{
			return dm.daoGetGroupPermit(deviceId,personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PermitBean getPersonPermit(int deviceId,int personId) {
		try{
			return dm.daoGetPersonPermit(deviceId,personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PermitBean> getGroupPermits(int deviceId,List<Integer> personGroupIdList) {
		try{
			return dm.daoGetGroupPermit(deviceId, personGroupIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PermitBean> getPersonPermits(int deviceId,List<Integer> personIdList) {
		try{
			return dm.daoGetPermit(deviceId, personIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getPersonGroupsPermittedBy(int deviceGroupId){
		try{
			return dm.daoGetPersonGroupsPermittedBy(deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getDeviceGroupsPermittedBy(int personGroupId){
		try{
			return dm.daoGetDeviceGroupsPermittedBy(personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getDeviceGroupsPermit(int personGroupId){
		try{
			return dm.daoGetDeviceGroupsPermit(personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<PermitBean> loadPermitByUpdate(long timestamp) {
		try{
			return dm.daoLoadPermitByCreateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PermitBean> loadPermitByUpdate(String timestamp) {
		try{
			return dm.daoLoadPermitByCreateTime(toDate(checkNotNull(timestamp,"timestamp is null")));
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		}
	}
    @Override
    public List<Integer> loadPersonGroupByWhere(String where,int startRow, int numRows){
    	try{
    		return dm.daoToPrimaryKeyListFromPersonGroups(dm.daoLoadPersonGroupByWhere(where, startRow, numRows));
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countPersonGroupByWhere(String where){
    	try{
    		return dm.daoCountPersonGroupByWhere(where);
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public List<Integer> loadPersonGroupIdByWhere(String where){
    	try{
    		return dm.daoLoadPersonGroupIdByWhere(where);
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public DeviceBean registerDevice(DeviceBean newDevice) throws ServiceSecurityException{
    	try{
    		return tm.registerDevice(newDevice);
    	} catch (RuntimeException e) {
			return  throwServiceException(e);
		}
    }
    @Override
	public void unregisterDevice(Token token)
			throws ServiceSecurityException{
    	try{
    		Enable.DEVICE_ONLY.check(tm, token);
    		checkArgument(token != null, "token is null");
    		tm.unregisterDevice(token.getId());
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
    @Override
	public Token online(DeviceBean device)
			throws ServiceSecurityException{
    	try{
    		return tm.applyDeviceToken(device);
    	} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
    @Override
	public void offline(Token token)
			throws ServiceSecurityException{
    	try{
    		tm.releaseDeviceToken(token);
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
    @Override
	public Token applyPersonToken(int personId, String password, boolean isMd5)
			throws ServiceSecurityException{
    	try{
    		return tm.applyPersonToken(personId, password, isMd5);
    	} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
    @Override
	public void releasePersonToken(Token token)
			throws ServiceSecurityException{
    	try{
    		tm.releasePersonToken(token);
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
    @Override
	public Token applyRootToken(String password, boolean isMd5)
			throws ServiceSecurityException{
		try{
			return tm.applyRootToken(password, isMd5);
		} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
	@Override
	public void releaseRootToken(Token token)
			throws ServiceSecurityException{
    	try{
    		tm.releaseRootToken(token);
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
	@Override
	public Token applyUserToken(int userid,String password,boolean isMd5) throws ServiceSecurityException{
		try{
			if(userid == -1){
				return tm.applyRootToken(password, isMd5);
			}else{
				return tm.applyPersonToken(userid, password, isMd5);
			}
		} catch (RuntimeException e) {
			return throwServiceException(e);
		}
	}
	@Override
	public void releaseUserToken(Token token)
			throws ServiceSecurityException{
    	try{
    		if(token != null){
    			switch (token.getType()) {
				case PERSON:
					tm.releasePersonToken(token);
					break;
				case ROOT:
					tm.releaseRootToken(token);
					break;
				default:
					throw new ServiceSecurityException("UNSUPPORTED TOKEN TYPE " + token.getType());
				}	
    		}
    	} catch (RuntimeException e) {
			throwServiceException(e);
		}
	}
	@Override
	public boolean isValidPassword(String userId,String password, boolean isMd5) {
    	try{
    		return tm.isValidPassword(userId, password, isMd5);
    	} catch (ServiceSecurityException e) {
			return false;
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
    @Override
    public String applyAckChannel(Token token) {
    	return applyAckChannel(0,token);
	}
    @Override
    public String applyAckChannel(int duration, Token token) {
    	try {
    		Enable.PERSON_ONLY.check(tm, token);
			return tm.applyAckChannel(token.getId(), duration);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
    @Override
    public int applyCmdSn(Token token) {
    	try {
    		Enable.PERSON_ONLY.check(tm, token);
			return tm.applyCmdSn(token.getId());
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
    @Override
    public boolean isValidCmdSn(int cmdSn) {
    	try {
			return tm.isValidCmdSn(cmdSn);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
    @Override
    public boolean isValidAckChannel(String ackChannel) {
    	try {
			return tm.isValidAckChannel(ackChannel);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public boolean isValidDeviceToken(Token token){
    	try {
			return tm.isValidDeviceToken(token);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public boolean isValidPersonToken(Token token){
    	try {
			return tm.isValidPersonToken(token);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public boolean isValidRootToken(Token token){
    	try {
			return tm.isValidRootToken(token);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	
	@Override
	public boolean isValidUserToken(Token token){
    	try {
   			return tm.isValidUserToken(token);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public boolean isValidToken(Token token){
    	try {
   			return tm.isValidToken(token);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
    @Override
    public Map<MQParam,String> getRedisParameters(Token token){
    	try {
			Enable.ALL.check(tm, token);
			return rm.getRedisParameters();
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
    }

	@Override
	public String taskQueueOf(String task,Token token) {	
    	try {
			Enable.ALL.check(tm, token);
			return rm.taskQueueOf(task);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public String sdkTaskQueueOf(String task,String sdkVersion,Token token) {	
    	try {
			Enable.ALL.check(tm, token);
			return rm.sdkTaskQueueOf(task,sdkVersion);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	
	@Override
	public int runCmd(List<Integer>target,boolean group,String cmdpath,String jsonArgs,String ackChannel,Token token){
		try {
			Enable.PERSON_ONLY.check(tm, token);
			checkArgument(tm.isUserToken(token),"type of token must be PERSON or ROOT");			
			return dc.doRunCmd(target, group, cmdpath, jsonArgs, ackChannel, token.getId()); 
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public boolean runTask(String taskQueue,String cmdpath,String jsonArgs,String ackChannel,Token token){
		try {
			Enable.PERSON_ONLY.check(tm, token);
			checkArgument(tm.isUserToken(token),"type of token must be PERSON or ROOT");
			return dc.doRunTask(taskQueue, cmdpath, jsonArgs, ackChannel, token.getId()); 
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
    @Override
    public String getProperty(String key,Token token){
    	try {
			Enable.ALL.check(tm, token);			
			if(!TokenType.ROOT.equals(token.getType())){
				PropertyWhiteList.INSTANCE.checkAccess(key);
			}
			return GlobalConfig.getProperty(key);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 	
    }
    @Override
	public Map<String, String> getProperties(String prefix,Token token){
    	try {
			Enable.ALL.check(tm, token);
			if(!TokenType.ROOT.equals(token.getType())){
				PropertyWhiteList.INSTANCE.checkAccess(prefix);
			}
			return GlobalConfig.getProperties(prefix);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 	
    }
    @Override
    public Map<String,String> getServiceConfig(Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			return GlobalConfig.toMap(CONFIG);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 	
    }
    @Override
    public void setProperty(String key,String value,Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.setProperty(key,value);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 	
    }
    @Override
    public void setProperties(Map<String,String> config,Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.setProperties(config);
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
		} 	
    }
    @Override
    public void saveServiceConfig(Token token){
    	try {
			Enable.ROOT_ONLY.check(tm, token);
			GlobalConfig.persistence();
		} catch (Exception e) {
			throw wrapServiceRuntimeException(e);
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
	@Override
	public boolean isLocal() {
		return true;
	}
}
