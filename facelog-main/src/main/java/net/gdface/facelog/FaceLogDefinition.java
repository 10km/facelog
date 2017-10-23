package net.gdface.facelog;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.PersonBean;

// 由于Java语言的限制,导致swift无法从interface中获取参数名信息，所以采用interface定义生成的thrift IDL文件中service中的方法
// 无法生成正确的参数名称(只能是无意义的arg0,arg1...)<br>
// 所以这里采用抽象类来定义服务接口,如果抽象类中的方法是抽象的，也无法获取参数名，所以这里所有方法都有一个空的函数体。

/**
 * 定义 FaceLog 服务接口<br>
 * @author guyadong
 */
@ThriftService("IFaceLog")
public abstract class FaceLogDefinition {

	/**
	 * 返回personId指定的人员记录
	 * @param personId
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public PersonBean getPerson(int personId) throws ServiceRuntime {
		return null;
	}

	/**
	 * 返回 list 指定的人员记录
	 * @param idList 人员id列表
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<PersonBean> getPersons(List<Integer> idList) throws ServiceRuntime {
		return null;
	}

	/**
	 * 根据证件号码返回人员记录
	 * @param papersNum
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public PersonBean getPersonByPapersNum(String papersNum) throws ServiceRuntime {
		return null;
	}

	/**
	 * 返回 persionId 关联的所有人脸特征记录
	 * @param personId fl_person.id
	 * @return 返回 fl_feature.md5  列表
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<String> getFeatureBeansByPersonId(int personId) throws ServiceRuntime {
		return null;
	}

	/**
	 * 删除personId指定的人员(person)记录及关联的所有记录
	 * @param personId
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public int deletePerson(int personId) throws ServiceRuntime {
		return 0;
	}

	/**
	 * 删除personIdList指定的人员(person)记录及关联的所有记录
	 * @param personIdList 人员id列表
	 * @return 返回删除的 person 记录数量
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public int deletePersons(List<Integer> personIdList) throws ServiceRuntime {
		return 0;
	}

	/**
	 * 删除papersNum指定的人员(person)记录及关联的所有记录
	 * @param papersNum 证件号码
	 * @return 返回删除的 person 记录数量
	 * @throws ServiceRuntime
	 * @see {@link #deletePerson(int)}
	 */
	@ThriftMethod
	public int deletePersonByPapersNum(String papersNum) throws ServiceRuntime {
		return 0;
	}

	/**
	 * 删除papersNum指定的人员(person)记录及关联的所有记录
	 * @param papersNumlist 证件号码列表
	 * @return 返回删除的 person 记录数量
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public int deletePersonsByPapersNum(List<String> papersNumlist) throws ServiceRuntime {
		return 0;
	}

	/**
	 * 判断是否存在personId指定的人员记录
	 * @param persionId
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public boolean existsPerson(int persionId) throws ServiceRuntime {
		return false;
	}

	/**
	 * 判断 personId 指定的人员记录是否过期
	 * @param personId
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public boolean isDisable(int personId) throws ServiceRuntime {
		return false;
	}

	/**
	 * 设置 personId 指定的人员为禁止状态
	 * @param personId
	 * @throws ServiceRuntime
	 * @see #setPersonExpiryDate(int, long)
	 */
	@ThriftMethod
	public void disablePerson(int personId) throws ServiceRuntime {
	}

	/**
	 * 修改 personId 指定的人员记录的有效期
	 * @param personId
	 * @param expiryDate 失效日期
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public void setPersonExpiryDate(int personId, @TargetType(java.util.Date.class)long expiryDate) throws ServiceRuntime {
	}

	/**
	 * 修改 personIdList 指定的人员记录的有效期
	 * @param personIdList 人员id列表
	 * @param expiryDate 失效日期 
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("setPersonExpiryDateList")
	public void setPersonExpiryDate(List<Integer> personIdList, @TargetType(java.util.Date.class)long expiryDate) throws ServiceRuntime {
	}

	/**
	 * 设置 personIdList 指定的人员为禁止状态
	 * @param personIdList 人员id列表
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("disablePersonList")
	public void disablePerson(List<Integer> personIdList) throws ServiceRuntime {
	}

	/**
	 * 返回 persionId 关联的所有日志记录
	 * @param personId fl_person.id
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<LogBean> getLogBeansByPersonId(int personId) throws ServiceRuntime {
		return null;
	}

	/**
	 * 返回所有人员记录
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<Integer> loadAllPerson() throws ServiceRuntime {
		return null;
	}

	/**
	 * 返回 where 指定的所有人员记录
	 * @param where SQL条件语句
	 * @return 返回 fl_person.id 列表
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<Integer> loadPersonByWhere(String where) throws ServiceRuntime {
		return null;
	}

	/**
	 * 保存人员(person)记录
	 * @param bean
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public PersonBean savePerson(PersonBean bean) throws ServiceRuntime {
		return null;
	}

	/**
	 * 保存人员(person)记录
	 * @param beans 
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("savePersonList")
	public void savePerson(List<PersonBean> beans) throws ServiceRuntime {
	}

	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhoto 标准照图像对象,可为null
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("savePersonWithPhoto")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto) throws ServiceRuntime {
		return null;
	}

	/**
	 * 保存人员信息记录(包含标准照)
	 * @param persons
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("savePersonsWithPhoto")
	public Integer savePerson(Map<ByteBuffer, PersonBean> persons) throws ServiceRuntime {
		return null;
	}

	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhotoMd5 标准照图像对象,可为null
	 * @param featureMd5 用于验证的人脸特征数据对象,可为null
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("savePersonWithPhotoAndFeatureSaved")
	public PersonBean savePerson(PersonBean bean, String idPhotoMd5, String featureMd5) throws ServiceRuntime {
		return null;
	}

	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhoto 标准照图像,可为null
	 * @param featureBean 用于验证的人脸特征数据对象,可为null
	 * @param deviceId 标准照图像来源设备id,可为null
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("savePersonWithPhotoAndFeature")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, FeatureBean featureBean, Integer deviceId)
			throws ServiceRuntime {
		return null;
	}

	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhoto 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据,可为null,不可重复, 参见 {@link #addFeature(ByteBuffer, Integer, List)}
	 * @param faceBeans 参见 {@link #addFeature(ByteBuffer, Integer, List)}
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("savePersonWithPhotoAndFeatureMultiFaces")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature, List<FaceBean> faceBeans)
			throws ServiceRuntime {
		return null;
	}

	/**
	 * 保存人员信息记录
	 * @param bean 
	 * @param idPhoto 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据,可为null 
	 * @param faceInfo 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @param deviceId faceInfo 图像来源设备id,可为null 
	 * @return bean 保存的{@link PersonBean}对象
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("savePersonWithPhotoAndFeatureMultiImage")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature, Map<ByteBuffer, FaceBean> faceInfo,
			Integer deviceId) throws ServiceRuntime {
		return null;
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
	@ThriftMethod("savePersonFull")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature, ByteBuffer featureImage,
			FaceBean featureFaceBean, Integer deviceId) throws ServiceRuntime {
		return null;
	}

	/**
	 * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
	 * @param personId 人员记录id
	 * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
	 * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public void replaceFeature(Integer personId, String featureMd5, boolean deleteOldFeatureImage) throws ServiceRuntime {
	}

	/**
	 * (主动更新机制实现)<br>
	 * 返回fl_person.update_time字段大于指定时间戳( timestamp )的所有fl_person记录<br>
	 * 同时包含fl_feature更新记录引用的fl_person记录
	 * @param timestamp
	 * @return 返回fl_person.id 列表
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<Integer> loadUpdatePersons(@TargetType(java.util.Date.class)long timestamp) throws ServiceRuntime {
		return null;
	}

	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_person.update_time 字段大于指定时间戳( timestamp )的所有fl_person记录
	 * @param timestamp
	 * @return 返回fl_person.id 列表
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<Integer> loadPersonIdByUpdate(@TargetType(java.util.Date.class)long timestamp) throws ServiceRuntime {
		return null;
	}

	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_feature.update_time 字段大于指定时间戳( timestamp )的所有fl_feature记录
	 * @param timestamp
	 * @return 返回 fl_feature.md5 列表
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<String> loadFeatureMd5ByUpdate(@TargetType(java.util.Date.class)long timestamp) throws ServiceRuntime {
		return null;
	}

	/**
	 * 添加一条验证日志记录
	 * @param bean
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public void addLog(LogBean bean) throws ServiceRuntime {
	}

	/**
	 * 添加一组验证日志记录(事务存储)
	 * @param beans
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("addLogList")
	public void addLog(List<LogBean> beans) throws ServiceRuntime {
	}
	@ThriftMethod
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows)
			throws ServiceRuntime {
		return null;
	}
	
	@ThriftMethod
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows) throws ServiceRuntime {
		return null;
	}
	@ThriftMethod
	public int countLogLightWhere(String where) throws ServiceRuntime {
		return 0;
	}
	/**
	 * 返回满足{@code where}条件的日志记录(fl_log)数目
	 * @param where 为{@code null}时返回所有记录
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public int countLogWhere(String where) throws ServiceRuntime {
		return 0;
	}

	/**
	 * 判断md5指定的图像记录是否存在
	 * @param md5
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public boolean existsImage(String md5) throws ServiceRuntime {
		return false;
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
	@ThriftMethod
	public ImageBean addImage(ByteBuffer imageData, Integer deviceId, FaceBean faceBean, Integer personId)
			throws ServiceRuntime {
		return null;
	}

	/**
	 * 判断md5指定的特征记录是否存在
	 * @param md5
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public boolean existsFeature(String md5) throws ServiceRuntime {
		return false;
	}

	/**
	 * 增加一个人脸特征记录，如果记录已经存在则抛出异常
	 * @param feature 特征数据
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param faecBeans 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, List<FaceBean> faecBeans) throws ServiceRuntime {
		return null;
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
	@ThriftMethod("addFeatureMulti")
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, Map<ByteBuffer, FaceBean> faceInfo, Integer deviceId)
			throws ServiceRuntime {
		return null;
	}

	/**
	 * 删除featureMd5指定的特征记录及关联的face记录
	 * @param featureMd5
	 * @param deleteImage 是否删除关联的 image记录
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<String> deleteFeature(String featureMd5, boolean deleteImage) throws ServiceRuntime {
		return null;
	}

	/**
	 * 删除 personId 关联的所有特征(feature)记录
	 * @param personId
	 * @param deleteImage 是否删除关联的 image记录
	 * @return
	 * @see #deleteFeature(String, boolean)
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public int deleteAllFeaturesByPersonId(int personId, boolean deleteImage) throws ServiceRuntime {
		return 0;
	}

	/**
	 * 根据MD5校验码返回人脸特征数据记录
	 * @param md5
	 * @return 如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public FeatureBean getFeature(String md5) throws ServiceRuntime {
		return null;
	}

	/**
	 * 根据MD5校验码返回人脸特征数据记录
	 * @param md5 md5列表
	 * @return {@link FeatureBean}列表
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("getFeatureList")
	public List<FeatureBean> getFeature(List<String> md5) throws ServiceRuntime {
		return null;
	}

	/**
	 * 根据MD5校验码返回人脸特征数据
	 * @param md5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public ByteBuffer getFeatureBytes(String md5) throws ServiceRuntime {
		return null;
	}

	/**
	 * 根据图像的MD5校验码返回图像数据
	 * @param imageMD5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntime
	 * @see {@link #getBinary(String)}
	 */
	@ThriftMethod
	public ByteBuffer getImageBytes(String imageMD5) throws ServiceRuntime {
		return null;
	}

	/**
	 * 根据图像的MD5校验码返回图像记录
	 * @param imageMD5
	 * @return {@link ImageBean} ,如果没有对应记录则返回null
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public ImageBean getImage(String imageMD5) throws ServiceRuntime {
		return null;
	}

	/**
	 * 返回featureMd5的人脸特征记录关联的所有图像记录id(MD5) 
	 * @param featureMd5 人脸特征id(MD5)
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public List<String> getImagesAssociatedByFeature(String featureMd5) throws ServiceRuntime {
		return null;
	}

	/**
	 * 删除imageMd5指定图像及其缩略图
	 * @param imageMd5
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public int deleteImage(String imageMd5) throws ServiceRuntime {
		return 0;
	}

	/**
	 * 判断id指定的设备记录是否存在
	 * @param id
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public boolean existsDevice(int id) throws ServiceRuntime {
		return false;
	}
	/**
	 * 保存设备记录
	 * @param deviceBean
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public DeviceBean saveDevice(DeviceBean deviceBean) throws ServiceRuntime {
		return null;
	}
	/**
	 * 返回{@code deviceId}指定的设备记录
	 * @param deviceId
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod
	public DeviceBean getDevice(Integer deviceId) throws ServiceRuntime {
		return null;
	}
	/**
	 * 返回 {@code idList} 指定的设备记录
	 * @param idList
	 * @return
	 * @throws ServiceRuntime
	 */
	@ThriftMethod("getDeviceList")
	public List<DeviceBean> getDevice(List<Integer> idList) throws ServiceRuntime {
		return null;
	}
}