package net.gdface.facelog;

import static com.google.common.base.Preconditions.*;
import static net.gdface.facelog.FeatureConfig.*;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.gdface.facelog.db.Constant;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.FeatureComparator;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.StoreBean;
import net.gdface.facelog.db.exception.ObjectRetrievalException;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.image.LazyImage;
import net.gdface.image.NotImageException;
import net.gdface.image.UnsupportedFormatException;
import net.gdface.utils.Assert;
import net.gdface.utils.FaceUtilits;
import net.gdface.utils.Judge;

/**
 * 数据库操作扩展
 * @author guyadong
 *
 */
public class DaoManagement extends BaseDao implements ServiceConstant,Constant{
	private final CryptographGenerator cg;
	public DaoManagement(CryptographGenerator cg) {
		this.cg = checkNotNull(cg,"cg is null");
	}
	public DaoManagement() {
		this(CryptographBySalt.INSTANCE);
	}
	public CryptographGenerator getCryptographGenerator() {
		return cg;
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
				personBean.setPassword(cg.cryptograph(password, true));
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
	
	/**
	 * 获取人员组通行权限<br>
	 * 返回{@code personGroupId}指定的人员组在{@code deviceGroupId}指定的设备组上是否允许通行,
	 * 本方法会对{@code personGroupId}的父结点向上回溯：
	 * {@code personGroupId } 及其父结点,任何一个在permit表存在与{@code deviceId}所属设备级的关联记录中就返回true，
	 * 输入参数为{@code null}或找不到指定的记录则返回false
	 * @param deviceGroupId
	 * @param personGroupId
	 * @return 允许通行返回指定的{@link PermitBean}记录，否则返回{@code null}
	 */
	protected PermitBean daoGetGroupPermitOnDeviceGroup(final Integer deviceGroupId,Integer personGroupId){
		PersonGroupBean personGroup;
		if(null == deviceGroupId
			|| null == personGroupId 
			|| null == (personGroup = daoGetPersonGroup(personGroupId))){
			return null;
		}
		List<PersonGroupBean> personGroupList = daoListOfParentForPersonGroup(personGroup);
		// first is self
		Collections.reverse(personGroupList);
		// person group 及其parent,任何一个在permit表中就返回true
		Optional<PersonGroupBean> found = Iterators.tryFind(personGroupList.iterator(), new Predicate<PersonGroupBean>(){
			@Override
			public boolean apply(PersonGroupBean input) {
					return daoExistsPermit(deviceGroupId, input.getId());
			}});
		return found.isPresent() ?
			daoGetPermit(deviceGroupId, found.get().getId())
			: null;
	}
	/**
	 * 获取人员组通行权限<br>
	 * 返回{@code personGroupId}指定的人员组在{@code deviceId}设备上是否允许通行,
	 * 本方法会对{@code personGroupId}的父结点向上回溯：
	 * {@code personGroupId } 及其父结点,任何一个在permit表存在与{@code deviceId}所属设备级的关联记录中就返回true，
	 * 输入参数为{@code null}或找不到指定的记录则返回false
	 * @param deviceId
	 * @param personGroupId
	 * @return 允许通行返回指定的{@link PermitBean}记录，否则返回{@code null}
	 * @see #daoGetGroupPermitOnDeviceGroup(Integer, Integer)
	 */
	protected PermitBean daoGetGroupPermit(Integer deviceId,Integer personGroupId){
		DeviceBean device;
		if(null == deviceId || null ==(device = daoGetDevice(deviceId))){
			return null;
		}
		return daoGetGroupPermitOnDeviceGroup(device.getGroupId(),personGroupId);
	}
	protected PermitBean daoGetPersonPermit(Integer deviceId,Integer personId){
		PersonBean person;
		if( null == personId || null == (person = daoGetPerson(personId))){
			return null;
		}
		return daoGetGroupPermit(deviceId,person.getGroupId());
	}
	protected List<PermitBean> daoGetGroupPermit(final Integer deviceId,List<Integer> personGroupIdList){
		if(null == deviceId || null == personGroupIdList){
			return Collections.emptyList();
		}
		return Lists.newArrayList(Lists.transform(personGroupIdList, new Function<Integer,PermitBean>(){
			@Override
			public PermitBean apply(Integer input) {
				return daoGetGroupPermit(deviceId,input);
			}}));
	}
	protected List<PermitBean> daoGetPermit(final Integer deviceId,List<Integer> personIdList){
		if(null == deviceId || null == personIdList){
			return Collections.emptyList();
		}
		return Lists.newArrayList(Lists.transform(personIdList, new Function<Integer,PermitBean>(){
			@Override
			public PermitBean apply(Integer input) {
				return daoGetPersonPermit(deviceId,input);
			}}));
	}

	/**
	 * 从permit表返回允许在{@code deviceGroupId}指定的设备组通过的所有人员组({@link PersonGroupBean})对象的id
	 * @param deviceGroupId 为{@code null}返回空表
	 * @return
	 */
	protected List<Integer> daoGetPersonGroupsPermittedBy(Integer deviceGroupId){
		if(deviceGroupId == null){
			return Collections.emptyList();
		}
		List<PermitBean> permits = daoGetPermitBeansByDeviceGroupIdOnDeviceGroup(deviceGroupId);		
		return Lists.transform(permits, daoCastPermitToPersonGroupId);
	}
	/**
	 * 从permit表返回允许{@code personGroupId}指定的人员组通过的所有设备组({@link DeviceGroupBean})对象的id
	 * @param personGroupId 为{@code null}返回空表
	 * @return
	 */
	protected List<Integer> daoGetDeviceGroupsPermittedBy(Integer personGroupId){
		if(personGroupId == null){
			return Collections.emptyList();
		}
		List<PermitBean> permits = daoGetPermitBeansByPersonGroupIdOnPersonGroup(personGroupId);
		return Lists.transform(permits, daoCastPermitToDeviceGroupId);
	}
	/**
	 * 从permit表返回允许在{@code personGroupId}指定的人员组通过的所有设备组({@link DeviceGroupBean})的id<br>
	 * 不排序,不包含重复id
	 * @param personGroupId 为{@code null}返回空表
	 * @return
	 */
	protected List<Integer> daoGetDeviceGroupsPermit(Integer personGroupId){
		if(personGroupId == null){
			return Collections.emptyList();
		}
		PermitBean template = PermitBean.builder().personGroupId(personGroupId).build();
		List<PermitBean> permits = daoLoadPermitUsingTemplate(template, 1, -1);
		HashSet<Integer> groups = Sets.newHashSet();
		for (PermitBean bean : permits) {
			groups.addAll(Lists.transform(daoListOfParentForDeviceGroup(bean.getDeviceGroupId()),daoCastDeviceGroupToPk));
		}
		return Lists.newArrayList(groups);
	}
	
	/**
	 * 从permit表删除指定{@code personGroupId}指定人员组的在所有设备上的通行权限
	 * @param personGroupId 为{@code null}返回0
	 * @return 删除的记录条数
	 */
	protected int daoDeletePersonGroupPermit(Integer personGroupId) {
		if(personGroupId == null){
			return 0;
		}
		PermitBean template = PermitBean.builder().personGroupId(personGroupId).build();
		return getPermitManager().deleteUsingTemplate(template);
	}
	/**
	 * 从permit表删除指定{@code deviceGroupId}指定设备组上的人员通行权限
	 * @param deviceGroupId 为{@code null}返回0
	 * @return 删除的记录条数
	 */
	protected int daoDeleteGroupPermitOnDeviceGroup(Integer deviceGroupId) {
		if(deviceGroupId == null){
			return 0;
		}
		PermitBean template = PermitBean.builder().deviceGroupId(deviceGroupId).build();
		return getPermitManager().deleteUsingTemplate(template);
	}
	////////////////////////////////////////////
	
	/**
	 * 创建{@link ImageBean}对象,填充图像基本信息,同时创建对应的{@link StoreBean}对象
	 * @param imageBytes
	 * @param md5
	 * @return 返回 {@link ImageBean}和{@link StoreBean}对象
	 * @throws NotImageException
	 * @throws UnsupportedFormatException
	 */
	protected static Pair<ImageBean, StoreBean> makeImageBean(ByteBuffer imageBytes,String md5) throws NotImageException, UnsupportedFormatException{
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

	protected FeatureBean daoMakeFeature(ByteBuffer feature, String featureVersion){
		Assert.notEmpty(feature, "feature");
		// featureVersion不可为空
		checkArgument(!Strings.isNullOrEmpty(featureVersion),"featureVersion is null or empty");
	    // featureVersion内容只允许字母,数字,-,.,_符号
	    checkArgument(featureVersion.matches(SDK_VERSION_REGEX), "invalid sdk version format");
		return FeatureBean.builder()
				.md5(FaceUtilits.getMD5String(feature))	
				.feature(feature)
				.version(featureVersion)
				.build();
	}
	
	/**
	 * 返回 persionId 关联的指定SDK的人脸特征记录
	 * @param personId 人员id(fl_person.id)
	 * @param featureVersion 算法(SDK)版本号
	 * @return 返回 fl_feature.md5  列表
	 */
	protected List<FeatureBean> daoGetFeaturesByPersonIdAndSdkVersion(int personId,String featureVersion) {
		FeatureBean tmpl = FeatureBean.builder().personId(personId).version(featureVersion).build();
		return daoLoadFeatureUsingTemplate(tmpl, 1, -1);
	}
	/**
	 * 返回在指定设备上允许通行的所有人员记录<br>
	 * @param deviceId 设备ID
	 * @param ignoreSchedule 是否忽略时间过滤器(fl_permit.schedule字段)的限制
	 * @return 返回的用户对象列表中，过滤所有有效期失效的用户<br>
	 */
	protected Set<PersonBean> 
	daoGetPersonsPermittedOnDevice(int deviceId, boolean ignoreSchedule) {
			DeviceBean deviceBean = daoGetDeviceChecked(deviceId);
			Set<PersonGroupBean> permittedGroups = Sets.newHashSet();
			DateTimeJsonFilter shedule = new DateTimeJsonFilter();
			final Date date = new Date();
			for(PermitBean permit : daoGetPermitBeansByDeviceGroupIdOnDeviceGroup(deviceBean.getGroupId())){
				if(ignoreSchedule || shedule.apply(date,permit.getSchedule())){
					permittedGroups.addAll(daoChildListByParentForPersonGroup(permit.getPersonGroupId()));
				}
			}
			Set<PersonBean> persons = Sets.newHashSet();
			for(PersonGroupBean group : permittedGroups){
				persons.addAll(daoGetPersonsOfGroup(group.getId()));
			}
			// 过滤所有有效期失效的用户
			return Sets.filter(persons, 
					new Predicate<PersonBean>() {		
						@Override
						public boolean apply(PersonBean input) {
							Date expiryDate = input.getExpiryDate();
							return null== expiryDate ? true : expiryDate.after(date);
						}
			});
	}
	/**
	 * 返回在指定设备上允许通行的所有特征记录
	 * @param deviceId 设备ID
	 * @param ignoreSchedule 是否忽略时间过滤器(fl_permit.schedule字段)的限制
	 * @param sdkVersion 特征版本号
	 * @param excludeFeatureIds 要排除的特征记录id(MD5),可为{@code null}
	 * @return
	 */
	protected Set<FeatureBean> 
	daoGetFeaturesPermittedOnDevice(int deviceId,boolean ignoreSchedule, String sdkVersion,final Collection<String> excludeFeatureIds) {
			checkArgument(!Strings.isNullOrEmpty(sdkVersion),"sdkVersion is null or empty");
			
			Set<FeatureBean> features = Sets.newHashSet();
			for(PersonBean person:daoGetPersonsPermittedOnDevice(deviceId, ignoreSchedule)){
				features.addAll(daoGetFeaturesByPersonIdAndSdkVersion(person.getId(),sdkVersion));
			}
			return Sets.filter(features,new Predicate<FeatureBean>() {
				Set<String> excludeIds = Sets.newHashSet(MoreObjects.firstNonNull(excludeFeatureIds, Collections.<String>emptySet()));
				@Override
				public boolean apply(FeatureBean input) {
					return ! excludeIds.contains(input.getMd5());
				}
			});
	}
	/**
	 * 添加人脸特征数据到数据库<br>
	 * 如果用户的特征记录数量超过限制，且没有开启自动更新机制则抛出异常,
	 * 如果已有特征数量超过限制，且开启了自动特征更新机制则删除最老的记录,确保记录总数不超过限制
	 * @param feature 人脸特征数据
	 * @param featureVersion SDK版本号
	 * @param refPersonByPersonId 所属的人员记录
	 * @param impFaceByFeatureMd5 关联的人脸信息记录
	 * @return
	 * @throws DuplicateRecordException
	 */
	protected FeatureBean daoAddFeature(ByteBuffer feature,String featureVersion, 
			PersonBean refPersonByPersonId, Collection<FaceBean> impFaceByFeatureMd5) throws DuplicateRecordException{
		FEATURE_CONFIG.checkSdkVersion(featureVersion);
		boolean removeOld = false;
		List<FeatureBean> features = null;
		if(null != refPersonByPersonId && refPersonByPersonId.getId() != null){
			Integer personId = refPersonByPersonId.getId();
			features = daoGetFeaturesByPersonIdAndSdkVersion(personId,featureVersion);
			int count = features.size();
			int limitCount = FEATURE_CONFIG.getFeatureLimitPerPerson(featureVersion);
			// 如果用户的特征记录数量超过限制，且没有开启自动更新机制则抛出异常
			checkState(count < limitCount || FEATURE_CONFIG.featureAutoUpdateEnabled(),
					"person(id=%s)'s  %s feature count  exceed max limit(%s)",personId,featureVersion,limitCount);
			// 如果已有特征数量超过限制，且开启了自动特征更新机制则删除最老的记录
			if(count >= limitCount && FEATURE_CONFIG.featureAutoUpdateEnabled()){
				removeOld = true;
			}
		}
		FeatureBean newFeature = daoAddFeature(daoMakeFeature(feature, featureVersion), refPersonByPersonId, impFaceByFeatureMd5, null);
		// 放在成功添加记录之后再执行删除，以防止因为添加特征抛出异常而导致发送错误的通知消息
		if(removeOld){
			int limitCount = FEATURE_CONFIG.getFeatureLimitPerPerson(featureVersion);			
			// 以update_time字段排序,删除最旧的记录
			Collections.sort(features, new FeatureComparator(FL_FEATURE_ID_UPDATE_TIME,/** 降序 */true));
			for(int i = limitCount-1;i < features.size() -1 ;++i){
				daoDeleteFeature(features.get(i).getMd5(), true);
				logger.info("AUTOUPDATE:remove feature [{}] and associated image",features.get(i).getMd5());
			}
		}
		return newFeature;
	}
	protected FeatureBean daoAddFeature(ByteBuffer feature,String featureVersion,PersonBean personBean,Map<ByteBuffer, FaceBean> faceInfo, DeviceBean deviceBean) throws DuplicateRecordException{
		if(null != faceInfo){
			for(Entry<ByteBuffer, FaceBean> entry:faceInfo.entrySet()){
				ByteBuffer imageBytes = entry.getKey();
				FaceBean faceBean = entry.getValue();
				daoAddImage(imageBytes, deviceBean, Arrays.asList(faceBean), null);
			}
		}
		return daoAddFeature(feature, featureVersion, personBean, null == faceInfo?null:faceInfo.values());
	}

	/**
	 * 删除指定的特征
	 * @param featureMd5
	 * @param deleteImage 为{@code true}则删除关联的 image记录(如果该图像还关联其他特征则不删除)
	 * @return 返回删除的特征记录关联的图像(image)记录的MD5
	 */
	protected List<String> daoDeleteFeature(final String featureMd5,boolean deleteImage){
		List<String> imageKeys = daoGetImageKeysImportedByFeatureMd5(featureMd5);
		if(deleteImage){
			for(Iterator<String> itor = imageKeys.iterator();itor.hasNext();){
				String md5 = itor.next();
				// 如果该图像还关联其他特征则不删除
				if(!Iterables.tryFind(daoGetFaceBeansByImageMd5OnImage(md5), new Predicate<FaceBean>(){

					@Override
					public boolean apply(FaceBean input) {
						return !featureMd5.equals(input.getFeatureMd5());
					}}).isPresent()){
					daoDeleteImage(md5);	
					itor.remove();
				}
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
		if(null == persons ){
			return 0;
		}
		int count = 0;
		PersonBean personBean ;
		for(Entry<ByteBuffer, PersonBean> entry:persons.entrySet()){
			personBean = daoSavePerson(entry.getValue(),daoAddImage(entry.getKey(),null,null,null),null);
			if(null != personBean){++count;}
		}
		return count;
	}
	/**
	 * 保存人员记录<br>
	 * 如果提供了新的身份照片({@code idPhotoBean}不为{@code null}),则删除旧照片用新照片代替
	 * @param personBean
	 * @param idPhotoBean 新的身份照片
	 * @param featureBean
	 * @return
	 */
	protected PersonBean daoSavePerson(PersonBean personBean, ImageBean idPhotoBean,
			Collection<FeatureBean> featureBean) {
		// delete old photo if exists
		if(idPhotoBean != null){
			daoDeleteImage(daoGetReferencedByImageMd5OnPerson(personBean));
		}
		return daoSavePerson(personBean, idPhotoBean, null, featureBean, null);
	}

	protected PersonBean daoSavePerson(PersonBean bean, ByteBuffer idPhoto, FeatureBean featureBean,
			DeviceBean deviceBean) throws DuplicateRecordException {
		ImageBean imageBean = daoAddImage(idPhoto, deviceBean, null, null);
		return daoSavePerson(bean, imageBean, featureBean == null ? null : Arrays.asList(featureBean));
	}

	protected PersonBean daoSavePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			String featureVersion, Map<ByteBuffer, FaceBean> faceInfo, DeviceBean deviceBean) throws DuplicateRecordException {
		return daoSavePerson(bean, idPhoto, daoAddFeature(feature, featureVersion, bean, faceInfo, deviceBean), deviceBean);
	}

	/**
	 * 
	 * @param bean 人员信息对象
	 * @param idPhoto 标准照图像
	 * @param feature 人脸特征数据
	 * @param featureVersion 特征(SDk)版本号
	 * @param featureImage 提取特征源图像,为null 时,默认使用idPhoto
	 * @param featureFaceBean 人脸位置对象,为null 时,不保存人脸数据
	 * @param deviceBean featureImage来源设备对象
	 * @return
	 * @throws DuplicateRecordException 
	 */
	protected PersonBean daoSavePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature,
			String featureVersion, ByteBuffer featureImage, FaceBean featureFaceBean, DeviceBean deviceBean) throws DuplicateRecordException {
		Map<ByteBuffer, FaceBean> faceInfo = null;
		if (null != featureFaceBean) {
			if (Judge.isEmpty(featureImage)){
				featureImage = idPhoto;
			}
			if (!Judge.isEmpty(featureImage)) {
				faceInfo = ImmutableMap.of(featureImage, featureFaceBean);
			}
		}
		return daoSavePerson(bean, idPhoto, daoAddFeature(feature, featureVersion, bean, faceInfo, deviceBean), deviceBean);
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
	/**
	 * 创建管理边界<br>
	 * 设置fl_person_group.root_group和fl_device_group.root_group字段互相指向,
	 * 以事务操作方式更新数据库
	 * @param personGroupId 人员组id
	 * @param deviceGroupId 设备组id
	 * @throws ObjectRetrievalException 没有找到personGroupId或deviceGroupId指定的记录
	 */
	protected void daoBindBorder(Integer personGroupId,Integer deviceGroupId) throws ObjectRetrievalException{
		final PersonGroupBean personGroup = daoGetPersonGroupChecked(personGroupId);
		final DeviceGroupBean deviceGroup = daoGetDeviceGroupChecked(deviceGroupId);
		personGroup.setRootGroup(deviceGroup.getId());
		deviceGroup.setRootGroup(personGroup.getId());
		daoRunAsTransaction(new Runnable() {
			@Override
			public void run() {
				daoSavePersonGroup(personGroup);
				daoSaveDeviceGroup(deviceGroup);
			}
		});
	}
	/**
	 * 删除管理边界<br>
	 * 删除fl_person_group.root_group和fl_device_group.root_group字段的互相指向,设置为{@code null},
	 * 以事务操作方式更新数据库<br>
	 * 如果personGroupId和deviceGroupId不存在绑定关系则跳过
	 * @param personGroupId 人员组id
	 * @param deviceGroupId 设备组id
	 * @throws ObjectRetrievalException 没有找到personGroupId或deviceGroupId指定的记录
	 */
	protected void daoUnbindBorder(Integer personGroupId,Integer deviceGroupId) throws ObjectRetrievalException{
		final PersonGroupBean personGroup = daoGetPersonGroupChecked(personGroupId);
		final DeviceGroupBean deviceGroup = daoGetDeviceGroupChecked(deviceGroupId);
		if(deviceGroup.getId().equals(personGroup.getRootGroup())
				|| personGroup.getId().equals(deviceGroup.getRootGroup())){
			personGroup.setRootGroup(null);
			deviceGroup.setRootGroup(null);
			daoRunAsTransaction(new Runnable() {
				@Override
				public void run() {
					daoSavePersonGroup(personGroup);
					daoSaveDeviceGroup(deviceGroup);
				}
			});
		}
	}
	/**
	 * 返回personId所属的管理边界人员组id<br>
	 * 在personId所属组的所有父节点中自顶向下查找第一个{@code fl_person_group.root_group}字段不为空的人员组，返回此记录组id
	 * @param personId
	 * @return {@code fl_person_group.root_group}字段不为空的记录id,没有找到则返回{@code null}
	 * @throws ObjectRetrievalException 没有找到personId指定的记录
	 */
	protected Integer daoRootGroupOfPerson(Integer personId) throws ObjectRetrievalException{
		Integer groupId = daoGetPersonChecked(personId).getGroupId();
		if(groupId == null || groupId ==DEFAULT_GROUP_ID){
			return null;
		}
		// 循环引用检查
		getPersonGroupManager().checkCycleOfParent(groupId);
		List<PersonGroupBean> parents = daoListOfParentForPersonGroup(groupId);
		Optional<PersonGroupBean> top = Iterables.tryFind(parents, new Predicate<PersonGroupBean>() {

			@Override
			public boolean apply(PersonGroupBean input) {
				return input.getRootGroup()!=null;
			}
		});
		return top.isPresent() ? top.get().getId() : null;
	}
	/**
	 * 返回deviceId所属的管理边界设备组id<br>
	 * 在deviceId所属组的所有父节点中自顶向下查找第一个{@code fl_device_group.root_group}字段不为空的组，返回此记录id
	 * @param deviceId
	 * @return {@code fl_device_group.root_group}字段不为空的记录id,没有找到则返回{@code null}
	 * @throws ObjectRetrievalException 没有找到deviceId指定的记录
	 */
	protected Integer daoRootGroupOfDevice(Integer deviceId) throws ObjectRetrievalException{
		Integer groupId = daoGetDeviceChecked(deviceId).getGroupId();
		if(groupId == null || groupId ==DEFAULT_GROUP_ID){
			return null;
		}
		// 循环引用检查
		getDeviceGroupManager().checkCycleOfParent(groupId);
		List<DeviceGroupBean> parents = daoListOfParentForDeviceGroup(groupId);
		Optional<DeviceGroupBean> top = Iterables.tryFind(parents, new Predicate<DeviceGroupBean>() {

			@Override
			public boolean apply(DeviceGroupBean input) {
				return input.getRootGroup()!=null;
			}
		});
		return top.isPresent() ? top.get().getId() : null;
	}
	
	private LogBean checkLogBean(LogBean logBean){
        if(logBean.getPersonId()==null){
        	String featureId = checkNotNull(Strings.emptyToNull(logBean.getVerifyFeature()),
        			"NOT FOUND valid person id caused by fl_log.verify_feature is null");
    		FeatureBean featureBean = checkNotNull(daoGetFeature(featureId),
    				"NOT FOUND valid person id caused by invalid feature id %s",featureId);
    		logBean.setPersonId(checkNotNull(featureBean.getPersonId(),
    				"NOT FOUND valid person id caused by fl_feature.person_id is null"));
        } /*else {
        	// 检查verifyFeature记录所属的person_id是否与log的person_id相等
        	String featureId = logBean.getVerifyFeature();
        	if(!Strings.isNullOrEmpty(featureId)){
        		FeatureBean featureBean = checkNotNull(dm.daoGetFeature(featureId),
        				"INVALID feature id %s",featureId);
        		checkArgument(logBean.getPersonId().equals(featureBean.getPersonId()),
        				"MISMATCH person id for VerifyFeature");
        	}
        }*/
        return logBean;
	}
	@Override
	protected LogBean daoAddLog(LogBean logBean) throws RuntimeDaoException, DuplicateRecordException {
        return super.daoAddLog(checkLogBean(logBean));
	}
	@Override
	protected LogBean daoAddLog(LogBean logBean, DeviceBean refDeviceByDeviceId, FaceBean refFaceByCompareFace,
			FeatureBean refFeatureByVerifyFeature, PersonBean refPersonByPersonId)
			throws RuntimeDaoException, DuplicateRecordException {
		return super.daoAddLog(checkLogBean(logBean), refDeviceByDeviceId, refFaceByCompareFace, refFeatureByVerifyFeature,
				refPersonByPersonId);
	}
	/**
	 * 添加一条验证日志记录
	 * <br>{@code DEVICE_ONLY}
	 * @param logBean 日志记录对象
	 * @param faceBean 需要保存到数据库的提取人脸特征的人脸信息对象
	 * @param featureImage 需要保存到数据库的现场采集人脸特征的照片
	 * @throws DuplicateRecordException 数据库中存在相同记录
	 */
	protected LogBean	daoAddLog(LogBean logBean,FaceBean faceBean,ByteBuffer featureImage) throws DuplicateRecordException {
		if(logBean == null || faceBean == null || featureImage == null){
			return null;
		}
		PersonBean personBean = daoGetReferencedByPersonIdOnLog(logBean);
		DeviceBean deviceBean = daoGetReferencedByDeviceIdOnLog(logBean);
		daoAddImage(featureImage, deviceBean, 
				faceBean == null ? null : Arrays.asList(faceBean), null);
		return daoAddLog(logBean, deviceBean, faceBean, null, personBean);
	}
	/**
	 * 设置 personId 指定的人员为禁止状态<br>
	 * @param personId 
	 * @param moveToGroupId 将用户移动到指定的用户组，为{@code null}则不移动
	 * @param deletePhoto 为{@code true}删除用户标准照
	 * @param deleteFeature 为{@code true}删除用户所有的人脸特征数据(包括照片)
	 * @param deleteLog 为{@code true}删除用户所有通行日志
	 */
	protected void daoDisablePerson(int personId, Integer moveToGroupId, 
			boolean deletePhoto, boolean deleteFeature, boolean deleteLog){
		PersonBean personBean = daoGetPerson(personId);
		if(personBean == null){
			return;
		}
		// 有效期设置为昨天
		Date yesterday = new Date(System.currentTimeMillis() - 86400000L);
		personBean.setExpiryDate(yesterday);
		if(moveToGroupId != null){
			personBean.setGroupId(moveToGroupId);
		}
		daoSavePerson(personBean);
		if(deletePhoto){
			// 删除标准照
			daoDeleteImage(personBean.getImageMd5());
		}
		if(deleteFeature){
			daoDeleteAllFeaturesByPersonId(personId,true);
		}
		if(deleteLog){
			daoDeleteLogBeansByPersonIdOnPerson(personId);
		}
	}
	/**
	 * 如果记录不存在则创建deviceGroupId和personGroupId之间的MANY TO MANY 联接表(fl_permit)记录,
	 * 否则修改指定记录的通行时间安排表<br>
     * @param deviceGroupId 设备组id
     * @param personGroupId 人员组id
     * @param schedule 通行时间安排表,为{@code null}则不限制通行时间
     * @return (fl_permit)记录
     */
	protected PermitBean daoSavePermit(int deviceGroupId,int personGroupId,String schedule){
		PermitBean permitBean = daoGetPermit(deviceGroupId, personGroupId);
		if(permitBean == null){
			permitBean = PermitBean.builder()
				.deviceGroupId(deviceGroupId)
				.personGroupId(personGroupId)
				.schedule(schedule).build();
		}else{
			permitBean.setSchedule(schedule);
		}
		return daoSavePermit(permitBean);
	}
}
