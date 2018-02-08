package net.gdface.facelog.service;

import static com.google.common.base.Preconditions.*;

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

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.StoreBean;
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
public class DaoManagement extends BaseDao {
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
}
