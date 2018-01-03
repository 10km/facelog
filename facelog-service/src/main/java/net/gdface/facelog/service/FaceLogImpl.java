package net.gdface.facelog.service;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.tuple.Pair;
import com.google.common.base.Predicates;

import static com.google.common.base.Preconditions.*;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
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
import net.gdface.facelog.service.RedisManagement.MQParam;
import net.gdface.facelog.service.TokenMangement.Enable;
import net.gdface.image.LazyImage;
import net.gdface.image.NotImage;
import net.gdface.image.UnsupportedFormat;
import net.gdface.utils.Assert;
import net.gdface.utils.FaceUtilits;
import net.gdface.utils.Judge;
import redis.clients.jedis.exceptions.JedisException;

/**
 * IFaceLog 服务实现
 * @author guyadong
 *
 */
public class FaceLogImpl extends BaseFaceLog implements ServiceConstant {
	/** redis 服务器管理模块负责初始化全局连接池对象，要放在redis lisetner对象初始化前完成初始化 */
	private final RedisManagement rm = new RedisManagement();
	///////////////// TOKEN MANAGEMENT///////
	
	private final TokenMangement tm = new TokenMangement(this);
	private final TokenValidatorPersonListener tokenValidatorPersonListener = new TokenValidatorPersonListener(this);
	private final TokenValidatorPersonGroupListener tokenValidatorPersonGroupListener = new TokenValidatorPersonGroupListener(this);
	private final TokenValidatorDeviceListener tokenValidatorDeviceListener = new TokenValidatorDeviceListener(this);
	private final TokenValidatorDeviceGroupListener tokenValidatorDeviceGroupListener = new TokenValidatorDeviceGroupListener(this);

	private final RedisPersonListener redisPersonListener = new RedisPersonListener();
	private final RedisFeatureListener redisFeatureListener = new RedisFeatureListener();
	private final RedisPermitListener redisPermitListener = new RedisPermitListener();
	private final RedisLogListener redisLogListener = new RedisLogListener(rm.getRedisParameters().get(MQParam.HB_MONITOR_CHANNEL));

	//private final RedisLogConsumer redisLogConsumer  = new RedisLogConsumer(this);

	public FaceLogImpl() {
		initListener();
	}
	private void initListener(){
		// 注册安全验证侦听器
		getPersonManager().registerListener(tokenValidatorPersonListener);
		getPersonGroupManager().registerListener(tokenValidatorPersonGroupListener);
		getDeviceManager().registerListener(tokenValidatorDeviceListener);
		getDeviceGroupManager().registerListener(tokenValidatorDeviceGroupListener);

		// 注册REDIS侦听器
		getPersonManager().registerListener(redisPersonListener);
		getFeatureManager().registerListener(redisFeatureListener);
		getPermitManager().registerListener(redisPermitListener);
		if(CONFIG.getBoolean(MONITOR_LOG)){
			getLogManager().registerListener(redisLogListener);
		}

		// 注册系统日志侦听器
		getPersonManager().registerListener(BaseSysLogLisener.PERSON_LOG_LISTENER);
		getPersonGroupManager().registerListener(BaseSysLogLisener.PERSON_GROUP_LOG_LISTENER);
		getDeviceManager().registerListener(BaseSysLogLisener.DEVICE_LOG_LISTENER);
		getDeviceGroupManager().registerListener(BaseSysLogLisener.DEVICE_GROUP_LOG_LISTENER);
		getPermitManager().registerListener(BaseSysLogLisener.PERMIT_LOG_LISTENER);
	}
	/** 检查姓名是否有效,不允许使用保留字{@code root} ,无效抛出{@link IllegalArgumentException} 异常 */
	protected static void checkPersonName(PersonBean personBean){
		checkArgument(null == personBean || !ROOT_NAME.equals(personBean.getName()),
				"INVALID person name:%s, reserved word",ROOT_NAME);
	}
	/** 
	 * 增加人员姓名检查,参见 {@link #checkPersonName(PersonBean)}<br>
	 * 增加 password密文更新
	 * @throws IllegalStateException {@code password}不是有效的MD5字符串
	 */
	@Override
	protected PersonBean daoSavePerson(PersonBean personBean) throws RuntimeDaoException {
		checkPersonName(personBean);
		if(personBean.checkPasswordModified()){
			String password = personBean.getPassword();
			if(null != password){
				checkState(FaceUtilits.validMd5(password),"password field must be MD5 string(32 char,lower case)");
				// 重新生成password加盐密文
				personBean.setPassword(tm.generate(password, true));
			}
		}
		return super.daoSavePerson(personBean);
	}

	protected static StoreBean makeStoreBean(ByteBuffer imageBytes,String md5,String encodeing){
		if(Judge.isEmpty(imageBytes)){
			return null;
		}
		if(null == md5){
			md5 = FaceUtilits.getMD5String(imageBytes);
		}
		StoreBean storeBean = new StoreBean();
		storeBean.setData(imageBytes);
		storeBean.setMd5(md5);
		if(!Strings.isNullOrEmpty(encodeing)){
			storeBean.setEncoding(encodeing);
		}
		return storeBean;
	}

	/////////////////////PERMIT////////////////////
	
	protected boolean daoGetGroupPermit(Integer deviceId,Integer personGroupId){
		PersonGroupBean personGroup;
		final DeviceBean device;
		if(null == deviceId
			|| null == personGroupId 
			|| null ==(device = daoGetDevice(deviceId))
			|| null == (personGroup = daoGetPersonGroup(personGroupId))){
			return false;
		}
		List<PersonGroupBean> personGroupList = daoListOfParentForPersonGroup(personGroup);		

		// person group 及其parent,任何一个在permit表中就返回true
		return Iterators.tryFind(personGroupList.iterator(), new Predicate<PersonGroupBean>(){
			@Override
			public boolean apply(PersonGroupBean input) {
				return daoExistsPermit(device.getGroupId(), input.getId());
			}}).isPresent();
	}
	protected boolean daoGetPersonPermit(Integer deviceId,Integer personId){
		PersonBean person;
		if( null == personId || null == (person = daoGetPerson(personId))){
			return false;
		}
		return daoGetGroupPermit(deviceId,person.getGroupId());
	}
	protected List<Boolean> daoGetGroupPermit(final Integer deviceId,List<Integer> personGroupIdList){
		if(null == deviceId || null == personGroupIdList){
			return ImmutableList.<Boolean>of();
		}
		return Lists.newArrayList(Lists.transform(personGroupIdList, new Function<Integer,Boolean>(){
			@Override
			public Boolean apply(Integer input) {
				return daoGetGroupPermit(deviceId,input);
			}}));
	}
	protected List<Boolean> daoGetPermit(final Integer deviceId,List<Integer> personIdList){
		if(null == deviceId || null == personIdList){
			return ImmutableList.<Boolean>of();
		}
		return Lists.newArrayList(Lists.transform(personIdList, new Function<Integer,Boolean>(){
			@Override
			public Boolean apply(Integer input) {
				return daoGetPersonPermit(deviceId,input);
			}}));
	}
	////////////////////////////////////////////
	
	/**
	 * 创建{@link ImageBean}对象,填充图像基本信息,同时创建对应的{@link StoreBean}对象
	 * @param imageBytes
	 * @param md5
	 * @return 返回 {@link ImageBean}和{@link StoreBean}对象
	 * @throws NotImage
	 * @throws UnsupportedFormat
	 */
	protected static Pair<ImageBean, StoreBean> makeImageBean(ByteBuffer imageBytes,String md5) throws NotImage, UnsupportedFormat{
		if(Judge.isEmpty(imageBytes)){
			return null;
		}
		LazyImage image = LazyImage.create(imageBytes);
		if(null == md5){
			md5 = FaceUtilits.getMD5String(imageBytes);
		}
		ImageBean imageBean = new ImageBean();
		imageBean.setMd5(md5);
		imageBean.setWidth(image.getWidth());
		imageBean.setHeight(image.getHeight());
		imageBean.setFormat(image.getSuffix());
		StoreBean storeBean = makeStoreBean(imageBytes, md5, null);
		return Pair.of(imageBean, storeBean);
	}
	protected ImageBean daoAddImage(ByteBuffer imageBytes,DeviceBean refFlDevicebyDeviceId
	        , Collection<FaceBean> impFlFacebyImgMd5 , Collection<PersonBean> impFlPersonbyImageMd5) throws DuplicateRecordException{
		if(Judge.isEmpty(imageBytes)){
			return null;
		}
		String md5 = FaceUtilits.getMD5String(imageBytes);
		daoCheckDuplicateImage(md5);
		Pair<ImageBean, StoreBean> pair;
		try {
			pair = makeImageBean(imageBytes,md5);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		daoAddStore(pair.getRight());
		return daoAddImage(pair.getLeft(), refFlDevicebyDeviceId, impFlFacebyImgMd5, impFlPersonbyImageMd5);
	}
	/**
	 * (递归)删除imageMd5指定图像及其缩略图
	 * @param imageMd5
	 * @return
	 */
	@Override
	protected int daoDeleteImage(String imageMd5){
		if(Strings.isNullOrEmpty(imageMd5)){
			return 0;
		}
		daoDeleteStore(imageMd5);
		ImageBean imageBean = daoGetImage(imageMd5);
		if(null == imageBean){return 0;}
		String thumbMd5 = imageBean.getThumbMd5();
		if( !Strings.isNullOrEmpty(thumbMd5)&& !imageBean.getMd5().equals(thumbMd5)){
			daoDeleteImage(thumbMd5);
		}
		return super.daoDeleteImage(imageMd5);
	}

	protected FeatureBean daoMakeFeature(ByteBuffer feature){
		Assert.notEmpty(feature, "feature");
		FeatureBean featureBean = new FeatureBean();		
		featureBean.setMd5(FaceUtilits.getMD5String(feature));
		featureBean.setFeature(feature);
		return featureBean;
	}
	protected FeatureBean daoAddFeature(ByteBuffer feature,PersonBean refPersonByPersonId, Collection<FaceBean> impFaceByFeatureMd5) throws DuplicateRecordException{
		return daoAddFeature(daoMakeFeature(feature), refPersonByPersonId, impFaceByFeatureMd5, null);
	}
	protected FeatureBean daoAddFeature(ByteBuffer feature,PersonBean personBean,Map<ByteBuffer, FaceBean> faceInfo,DeviceBean deviceBean) throws DuplicateRecordException{
		if(null != faceInfo){
			for(Entry<ByteBuffer, FaceBean> entry:faceInfo.entrySet()){
				ByteBuffer imageBytes = entry.getKey();
				FaceBean faceBean = entry.getValue();
				daoAddImage(imageBytes, deviceBean, Arrays.asList(faceBean), Arrays.asList(personBean));
			}
		}
		return daoAddFeature(feature, personBean, null == faceInfo?null:faceInfo.values());
	}

	protected List<String> daoDeleteFeature(String featureMd5,boolean deleteImage){
		List<String> imageKeys = daoGetImageKeysImportedByFeatureMd5(featureMd5);
		if(deleteImage){
			for(Iterator<String> itor = imageKeys.iterator();itor.hasNext();){
				String md5 = itor.next();
				daoDeleteImage(md5);
				itor.remove();
			}
		}else{
			daoDeleteFaceBeansByFeatureMd5OnFeature(featureMd5);
		}
		daoDeleteFeature(featureMd5);
		return imageKeys;
	}
	protected int daoDeleteAllFeaturesByPersonId(Integer personId,boolean deleteImage){
		int count = 0;
		for(FeatureBean featureBean: daoGetFeatureBeansByPersonIdOnPerson(personId)){
			daoDeleteFeature(featureBean.getMd5(),deleteImage);
			++count;
		}
		return count;
	}
	protected Integer daoGetDeviceIdOfFeature(String featureMd5){
		for(String imageMd5: daoGetImageKeysImportedByFeatureMd5(featureMd5)){
			ImageBean imageBean = daoGetImage(imageMd5);
			if(null !=imageBean){
				return imageBean.getDeviceId();
			}
		}
		return null;
	}
	protected PersonBean daoSetRefPersonOfFeature(String featureMd5,Integer personId){
		PersonBean personBean = daoGetPerson(personId);
		FeatureBean featureBean = daoGetFeature(featureMd5);
		return (null == personBean || null == featureBean)
				? null
				: daoSetReferencedByPersonIdOnFeature(featureBean, personBean);
	}

	protected List<String> daoGetImageKeysImportedByFeatureMd5(String featureMd5){
		return Lists.transform(daoGetFaceBeansByFeatureMd5OnFeature(featureMd5), this.daoCastFaceToImageMd5);
	}
	protected PersonBean daoReplaceFeature(Integer personId,String featureMd5,boolean deleteImage){		
		daoDeleteAllFeaturesByPersonId(personId, deleteImage);
		return daoSetRefPersonOfFeature(featureMd5,personId);
	}

	protected int daoSavePerson(Map<ByteBuffer,PersonBean> persons) throws DuplicateRecordException {
		if(null == persons ){return 0;}
		int count = 0;
		PersonBean personBean ;
		for(Entry<ByteBuffer, PersonBean> entry:persons.entrySet()){
			personBean = daoSavePerson(entry.getValue(),daoAddImage(entry.getKey(),null,null,null),null);
			if(null != personBean){++count;}
		}
		return count;
	}
	protected PersonBean daoSavePerson(PersonBean personBean, ImageBean idPhotoBean,
			Collection<FeatureBean> featureBean) {
		// delete old photo if exists
		daoDeleteImage(daoGetReferencedByImageMd5OnPerson(personBean)); 
		return daoSavePerson(personBean, idPhotoBean, null, featureBean, null);
	}

	protected PersonBean daoSavePerson(PersonBean bean, ByteBuffer idPhoto, FeatureBean featureBean,
			DeviceBean deviceBean) throws DuplicateRecordException {
		ImageBean imageBean = daoAddImage(idPhoto, deviceBean, null, null);
		return daoSavePerson(bean, imageBean, Arrays.asList(featureBean));
	}

	protected PersonBean daoSavePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			Map<ByteBuffer, FaceBean> faceInfo, DeviceBean deviceBean) throws DuplicateRecordException {
		return daoSavePerson(bean, idPhoto, daoAddFeature(feature, bean, faceInfo, deviceBean), null);
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
	 * @throws DuplicateRecordException 
	 */
	protected PersonBean daoSavePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			ByteBuffer featureImage, FaceBean featureFaceBean, DeviceBean deviceBean) throws DuplicateRecordException {
		Map<ByteBuffer, FaceBean> faceInfo = null;
		if (null != featureFaceBean) {
			if (Judge.isEmpty(featureImage)){
				featureImage = idPhoto;
			}
			if (!Judge.isEmpty(featureImage)) {
				faceInfo = new HashMap<ByteBuffer, FaceBean>(16);
				faceInfo.put(featureImage, featureFaceBean);
			}
		}
		return daoSavePerson(bean, idPhoto, daoAddFeature(feature, bean, faceInfo, deviceBean), null);
	}
	/**
	 * 删除personId指定的人员(person)记录及关联的所有记录
	 * @param personId
	 * @return 返回删除的记录数量
	 */
	@Override
	protected int daoDeletePerson(Integer personId) {
		PersonBean personBean = daoGetPerson(personId);
		if(null == personBean){
			return 0;
		}
		// 删除标准照
		daoDeleteImage(personBean.getImageMd5());
		daoDeleteAllFeaturesByPersonId(personId,true);
		return super.daoDeletePerson(personId);
	}
	protected int daoDeletePersonByPapersNum(String papersNum) {
		PersonBean personBean = daoGetPersonByIndexPapersNum(papersNum);
		return null == personBean ? 0 : daoDeletePerson(personBean.getId());
	}
	protected int daoDeletePersonByPapersNum(Collection<String> collection) {
		int count =0;
		if(null != collection){
			for(String papersNum:collection){
				count += daoDeletePersonByPapersNum(papersNum);
			}
		}
		return count;
	}
	protected boolean daoIsDisable(int personId){
		PersonBean personBean = daoGetPerson(personId);
		if(null == personBean){
			return true;
		}
		Date expiryDate = personBean.getExpiryDate();
		return null== expiryDate?false:expiryDate.before(new Date());
	}
	protected  void daoSetPersonExpiryDate(PersonBean personBean,Date expiryDate){
		if(null != personBean){
			personBean.setExpiryDate(expiryDate);
			daoSavePerson(personBean);
		}
	}
	protected  void daoSetPersonExpiryDate(Collection<Integer> personIdList,Date expiryDate){
		if(null != personIdList){
			for(Integer personId : personIdList){
				daoSetPersonExpiryDate(daoGetPerson(personId),expiryDate);
			}
		}
	}
	protected List<Integer> daoLoadUpdatedPersons(Date timestamp) {
		LinkedHashSet<Integer> updatedPersons = Sets.newLinkedHashSet(daoLoadPersonIdByUpdateTime(timestamp));
		List<Integer> idList = Lists.transform(
				daoLoadFeatureByUpdateTime(timestamp), 
				daoCastFeatureToPersonId);
		// 两个collection 合并去除重复
		@SuppressWarnings("unused")
		boolean b = Iterators.addAll(updatedPersons, Iterators.filter(idList.iterator(), Predicates.notNull()));
		return Lists.newArrayList(updatedPersons);
	}

	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 将封装在{@link RuntimeException}中的{@link ServiceSecurityException}剥离出来封装到{@link ServiceRuntimeException}
	 * @param e
	 * @return
	 * @see {@link #throwServiceException(RuntimeDaoException)}
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
			return daoGetPerson(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PersonBean> getPersons(List<Integer> idList)throws ServiceRuntimeException {
		try{
			return daoGetPersons(idList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PersonBean getPersonByPapersNum(String papersNum)throws ServiceRuntimeException  {
		try{
			return daoGetPersonByIndexPapersNum(papersNum);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> getFeatureBeansByPersonId(int personId)throws ServiceRuntimeException {
		try{
			return daoToPrimaryKeyListFromFeatures(daoGetFeatureBeansByPersonIdOnPerson(personId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public int deletePerson(final int personId, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return daoDeletePerson(personId);
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
			return daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return daoDeletePersonsByPrimaryKey(personIdList);
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
			return daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return daoDeletePersonByPapersNum(papersNum);
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
			return daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return daoDeletePersonByPapersNum(papersNumlist);
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
			return daoExistsPerson(persionId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public boolean isDisable(int personId)throws ServiceRuntimeException{
		try{
			return daoIsDisable(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void disablePerson(int personId, Token token)throws ServiceRuntimeException{
		try{
			Enable.PERSON_ONLY.check(tm, token);
			daoSetPersonExpiryDate(daoGetPerson(personId),new Date());
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
			daoSetPersonExpiryDate(daoGetPerson(personId),new Date(expiryDate));
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
			daoRunAsTransaction(new Runnable(){
				@Override
				public void run() {
					daoSetPersonExpiryDate(personIdList,new Date(expiryDate));
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
			return daoGetLogBeansByPersonIdOnPerson(personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<Integer> loadAllPerson()throws ServiceRuntimeException {
		try{
			return daoLoadPersonIdByWhere(null);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<Integer> loadPersonIdByWhere(String where)throws ServiceRuntimeException {
		try{
			return daoLoadPersonIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		try{
			return daoLoadPersonByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int countPersonByWhere(String where)throws ServiceRuntimeException {
		try{
			return daoCountPersonByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public PersonBean savePerson(PersonBean bean, Token token)throws ServiceRuntimeException {
		try{
			Enable.ALL.check(tm, token);
			return daoSavePerson(bean);
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
			daoSavePersonsAsTransaction(beans);
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
			return daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return daoSavePerson(bean, idPhoto, null,null);
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
			return daoRunAsTransaction(new Callable<Integer>(){
				@Override
				public Integer call() throws Exception {
					return daoSavePerson(persons);
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
			return daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return daoSavePerson(bean, daoGetImage(idPhotoMd5), Arrays.asList(daoGetFeature(featureMd5)));
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
			return daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return daoSavePerson(bean, idPhoto, featureBean, daoGetDevice(deviceId));
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
			return daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return daoSavePerson(bean, idPhoto, daoAddFeature(feature, bean, faceBeans), null);
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
			return daoRunAsTransaction(new Callable<PersonBean>() {
				@Override
				public PersonBean call() throws Exception {
					return daoSavePerson(bean, idPhoto, feature, faceInfo, daoGetDevice(deviceId));
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
			return daoRunAsTransaction(new Callable<PersonBean>(){
				@Override
				public PersonBean call() throws Exception {
					return daoSavePerson(bean,idPhoto,feature,featureImage,featureFaceBean,daoGetDevice(deviceId));
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
			daoRunAsTransaction(new Runnable() {
				@Override
				public void run() {
					daoReplaceFeature(personId, featureMd5, deleteOldFeatureImage);
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
			return daoLoadUpdatedPersons(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<Integer> loadPersonIdByUpdateTime(long timestamp)throws ServiceRuntimeException {
		try{
			return daoLoadPersonIdByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> loadFeatureMd5ByUpdate(long timestamp)throws ServiceRuntimeException {
		try{		
			return daoLoadFeatureMd5ByUpdateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public void addLog(LogBean bean, Token token)throws ServiceRuntimeException, DuplicateRecordException {
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			daoAddLog(bean);
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
			daoAddLogsAsTransaction(beans);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		try{
			return daoLoadLogByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		try{
			return daoLoadLogLightByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public int countLogLightByWhere(String where) throws ServiceRuntimeException {
		try{         
			return daoCountLogLightByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public int countLogByWhere(String where) throws ServiceRuntimeException {
		try{
			return daoCountLogByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
    public List<LogLightBean> loadLogLightByVerifyTime(long timestamp,int startRow, int numRows)throws ServiceRuntimeException{
		try{
			return daoLoadLogLightByVerifyTime(new Date(timestamp),startRow,numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countLogLightByVerifyTime(long timestamp)throws ServiceRuntimeException{
		try{
			return daoCountLogLightByVerifyTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
	public boolean existsImage(String md5) throws ServiceRuntimeException {
		try{
			return daoExistsImage(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ImageBean addImage(ByteBuffer imageData,Integer deviceId
			, FaceBean faceBean , Integer personId, Token token) throws ServiceRuntimeException, DuplicateRecordException{
		try{
			Enable.ALL.check(tm, token);
			return daoAddImage(imageData,daoGetDevice(deviceId),Arrays.asList(faceBean),Arrays.asList(daoGetPerson(personId)));		
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

    @Override
	public boolean existsFeature(String md5) throws ServiceRuntimeException {
		try{
			return daoExistsFeature(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public FeatureBean addFeature(ByteBuffer feature,Integer personId,List<FaceBean> faecBeans, Token token)throws ServiceRuntimeException, DuplicateRecordException{
		try{
			Enable.DEVICE_ONLY.check(tm, token);
			return daoAddFeature(feature, daoGetPerson(personId), faecBeans);
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
			return daoAddFeature(feature, daoGetPerson(personId), faceInfo, daoGetDevice(deviceId));
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
			return daoDeleteFeature(featureMd5,deleteImage);
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
			return daoDeleteAllFeaturesByPersonId(personId,deleteImage);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

	@Override
	public FeatureBean getFeature(String md5)throws ServiceRuntimeException{
		try{
			return daoGetFeature(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<FeatureBean> getFeatures(List<String> md5)throws ServiceRuntimeException{
		try{
			return daoGetFeatures(md5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<String> getFeaturesOfPerson(int personId)throws ServiceRuntimeException{
		try{			
			return Lists.transform(
					daoGetFeatureBeansByPersonIdOnPerson(personId),
					daoCastFeatureToPk); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public ByteBuffer getFeatureBytes(String md5)throws ServiceRuntimeException{
		try{
			FeatureBean featureBean = daoGetFeature(md5);
			return null ==featureBean?null:featureBean.getFeature();
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ByteBuffer getImageBytes(String imageMD5)throws ServiceRuntimeException{
		try{
			StoreBean storeBean = daoGetStore(imageMD5);
			return null ==storeBean?null:storeBean.getData();
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public ImageBean getImage(String imageMD5)throws ServiceRuntimeException{
		try{
			return daoGetImage(imageMD5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}

	@Override
	public List<String> getImagesAssociatedByFeature(String featureMd5)throws ServiceRuntimeException{
		try{
			return daoGetImageKeysImportedByFeatureMd5(featureMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public Integer getDeviceIdOfFeature(String featureMd5) throws ServiceRuntimeException{
		try{
			return daoGetDeviceIdOfFeature(featureMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deleteImage(String imageMd5, Token token)throws ServiceRuntimeException{
		try{
			Enable.ALL.check(tm, token);
			return daoDeleteImage(imageMd5);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}

    @Override
	public boolean existsDevice(int id) throws ServiceRuntimeException {
		try{
			return daoExistsDevice(id);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public DeviceBean saveDevice(DeviceBean deviceBean, Token token)throws ServiceRuntimeException{
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return daoSaveDevice(deviceBean);
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
			return daoSaveDevice(deviceBean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceBean getDevice(int deviceId)throws ServiceRuntimeException{
		try{
			return daoGetDevice(deviceId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}

	@Override
	public List<DeviceBean> getDevices(List<Integer> idList)throws ServiceRuntimeException{
		try{
			return daoGetDevices(idList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} 
	}
	@Override
	public List<DeviceBean> loadDeviceByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
		try{
			return this.daoLoadDeviceByWhere(where, startRow, numRows);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int countDeviceByWhere(String where)throws ServiceRuntimeException{
		try{
			return this.daoCountDeviceByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> loadDeviceIdByWhere(String where)throws ServiceRuntimeException{
		try{
			return this.daoLoadDeviceIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	////////////////////////////////DeviceGroupBean/////////////
	
	@Override
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return daoSaveDeviceGroup(deviceGroupBean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public DeviceGroupBean getDeviceGroup(int deviceGroupId)throws ServiceRuntimeException {
		try{
			return daoGetDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList)throws ServiceRuntimeException {
		try{
			return daoGetDeviceGroups(groupIdList); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deleteDeviceGroup(int deviceGroupId, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return daoDeleteDeviceGroup(deviceGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public List<Integer> getSubDeviceGroup(int deviceGroupId)throws ServiceRuntimeException {
		try{
			return daoToPrimaryKeyListFromDeviceGroups(daoGetSubDeviceGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getDevicesOfGroup(int deviceGroupId)throws ServiceRuntimeException {
		try{
			return daoToPrimaryKeyListFromDevices(daoGetDevicesOfGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> listOfParentForDeviceGroup(int deviceGroupId)throws ServiceRuntimeException{
		try{
			return daoToPrimaryKeyListFromDeviceGroups(daoListOfParentForDeviceGroup(deviceGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getDeviceGroupsBelongs(int deviceId)throws ServiceRuntimeException{
		try{
			DeviceBean deviceBean = daoGetDevice(deviceId);
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
			return daoSavePersonGroup(personGroupBean);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public PersonGroupBean getPersonGroup(int personGroupId)throws ServiceRuntimeException {
		try{
			return daoGetPersonGroup(personGroupId); 
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PersonGroupBean> getPersonGroups(Collection<Integer> groupIdList)throws ServiceRuntimeException {
		try{
			return daoGetPersonGroups(groupIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public int deletePersonGroup(int personGroupId, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			return daoDeletePersonGroup(personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public List<Integer> getSubPersonGroup(int personGroupId)throws ServiceRuntimeException {
		try{
			return daoToPrimaryKeyListFromPersonGroups(daoGetSubPersonGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getPersonsOfGroup(int personGroupId)throws ServiceRuntimeException {
		try{
			return this.daoToPrimaryKeyListFromPersons(daoGetPersonsOfGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> listOfParentForPersonGroup(int personGroupId)throws ServiceRuntimeException{
		try{
			return daoToPrimaryKeyListFromPersonGroups(daoListOfParentForPersonGroup(personGroupId));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Integer> getPersonGroupsBelongs(int personId)throws ServiceRuntimeException{
		try{
			PersonBean personBean = daoGetPerson(personId);
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
			return daoToPrimaryKeyListFromDeviceGroups(daoLoadDeviceGroupByWhere(where, startRow, numRows));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countDeviceGroupByWhere(String where)throws ServiceRuntimeException{
		try{
			return daoCountDeviceGroupByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public List<Integer> loadDeviceGroupIdByWhere(String where)throws ServiceRuntimeException{
    	try{
    		return daoLoadDeviceGroupIdByWhere(where);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
	/////////////////////PERMIT/////
    
	@Override
	public void addPermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup, Token token)throws ServiceRuntimeException {
		try{
			Enable.PERSON_ONLY.check(tm, token);
			daoAddPermit(deviceGroup, personGroup);
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
			daoAddPermit(deviceGroupId, personGroupId);
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
			return daoDeletePermit(deviceGroup, personGroup);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		} catch (ServiceSecurityException e) {
			throw new ServiceRuntimeException(ExceptionType.SECURITY_ERROR.ordinal(),e);
		}
	}
	@Override
	public boolean getGroupPermit(int deviceId,int personGroupId)throws ServiceRuntimeException {
		try{
			return daoGetGroupPermit(deviceId,personGroupId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public boolean getPersonPermit(int deviceId,int personId)throws ServiceRuntimeException {
		try{
			return daoGetPersonPermit(deviceId,personId);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Boolean> getGroupPermits(int deviceId,List<Integer> personGroupIdList)throws ServiceRuntimeException {
		try{
			return daoGetGroupPermit(deviceId, personGroupIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<Boolean> getPersonPermits(int deviceId,List<Integer> personIdList)throws ServiceRuntimeException {
		try{
			return daoGetPermit(deviceId, personIdList);
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
	@Override
	public List<PermitBean> loadPermitByUpdate(long timestamp)throws ServiceRuntimeException {
		try{
			return daoLoadPermitByCreateTime(new Date(timestamp));
		} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
	}
    @Override
    public List<Integer> loadPersonGroupByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
    	try{
    		return daoToPrimaryKeyListFromPersonGroups(daoLoadPersonGroupByWhere(where, startRow, numRows));
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public int countPersonGroupByWhere(String where)throws ServiceRuntimeException{
    	try{
    		return daoCountPersonGroupByWhere(where);
    	} catch (RuntimeException e) {
			throw wrapServiceRuntimeException(e);
		}
    }
    @Override
    public List<Integer> loadPersonGroupIdByWhere(String where)throws ServiceRuntimeException{
    	try{
    		return daoLoadPersonGroupIdByWhere(where);
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
}
