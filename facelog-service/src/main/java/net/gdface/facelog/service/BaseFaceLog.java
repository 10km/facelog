package net.gdface.facelog.service;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.facebook.swift.service.ThriftException;
import com.facebook.swift.service.ThriftMethod;
import com.facebook.swift.service.ThriftService;

import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.IPersonGroupManager;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.exception.RuntimeDaoException;
import net.gdface.facelog.service.Dao;
import net.gdface.facelog.service.CommonConstant.ExceptionType;
import net.gdface.facelog.service.RedisManagement.MQParam;

// 由于Java语言的限制,导致swift无法从interface中获取参数名信息，所以采用interface定义生成的thrift IDL文件中service中的方法
// 无法生成正确的参数名称(只能是无意义的arg0,arg1...)<br>
// 所以这里采用抽象类来定义服务接口,如果抽象类中的方法是抽象的，也无法获取参数名，所以这里所有方法都有一个空的函数体。

/**
 * FaceLog 服务接口<br>
 * <ul>
 * <li>所有标明为图像数据的参数,是指具有特定图像格式的图像数据(如jpg,png...),而非无格式的原始点阵位图</li>
 * <li>所有{@link RuntimeException}异常会被封装在{@link ServiceRuntimeException}抛出,
 * client端可以通过{@link ServiceRuntimeException#getType()}获取异常类型.<br>
 * 异常类型定义参见{@link CommonConstant.ExceptionType},<br>
 * 例如: 在执行涉及数据库操作的异常{@link RuntimeDaoException}，
 * 被封装到{@link ServiceRuntimeException}抛出时type为{@link ExceptionType#DAO}</li>
 * <li>所有数据库对象(Java Bean,比如 {@link PersonBean}),在执行保存操作(save)时,
 * 如果为新增记录({@link PersonBean#isNew()}为true),则执行insert操作,否则执行update操作,
 * 如果数据库已经存在指定的记录而{@code isNew()}为{@code true},则那么执行insert操作数据库就会抛出异常，
 * 所以请在执行save时特别注意{@code isNew()}状态</li>
 * <li>对于以add为前缀的添加记录方法,在添加记录前会检查数据库中是否有(主键)相同记录,
 * 如果有则会抛出异常{@link DuplicateRecordException}</li>
 * <li>所有带{@link Token}参数的方法都需要提供访问令牌,访问令牌分为人员令牌和设备令牌,
 * 注释中标注为{@code PERSON_ONLY}的方法只接受人员令牌,
 * 注释中标注为{@code DEVICE_ONLY}的方法只接受设备令牌,
 * 关于令牌申请和释放参见{@link #applyPersonToken(int)},{@link #releasePersonToken(Token)},{@link #online(DeviceBean)},{@link #offline(Token)}</li>
 * </ul>
 * @author guyadong
 */
@ThriftService("IFaceLog")
public abstract class BaseFaceLog extends Dao{

	/**
	 * 返回personId指定的人员记录
	 * @param personId
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public PersonBean getPerson(int personId) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 返回 list 指定的人员记录
	 * @param idList 人员id列表
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<PersonBean> getPersons(List<Integer> idList) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 根据证件号码返回人员记录
	 * @param papersNum
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public PersonBean getPersonByPapersNum(String papersNum) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 返回 persionId 关联的所有人脸特征记录
	 * @param personId fl_person.id
	 * @return 返回 fl_feature.md5  列表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<String> getFeatureBeansByPersonId(int personId) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 删除personId指定的人员(person)记录及关联的所有记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personId
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deletePerson(int personId, Token token) throws ServiceRuntimeException {
		return 0;
	}

	/**
	 * 删除personIdList指定的人员(person)记录及关联的所有记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personIdList 人员id列表
	 * @param token 访问令牌
	 * @return 返回删除的 person 记录数量
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deletePersons(List<Integer> personIdList, Token token) throws ServiceRuntimeException {
		return 0;
	}

	/**
	 * 删除papersNum指定的人员(person)记录及关联的所有记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param papersNum 证件号码
	 * @param token 访问令牌
	 * @return 返回删除的 person 记录数量
	 * @throws ServiceRuntimeException
	 * @see {@link #deletePerson(int, Token)}
	 */
	@ThriftMethod
	public int deletePersonByPapersNum(String papersNum, Token token) throws ServiceRuntimeException {
		return 0;
	}

	/**
	 * 删除papersNum指定的人员(person)记录及关联的所有记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param papersNumlist 证件号码列表
	 * @param token 访问令牌
	 * @return 返回删除的 person 记录数量
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deletePersonsByPapersNum(List<String> papersNumlist, Token token) throws ServiceRuntimeException {
		return 0;
	}

	/**
	 * 判断是否存在personId指定的人员记录
	 * @param persionId
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public boolean existsPerson(int persionId) throws ServiceRuntimeException {
		return false;
	}

	/**
	 * 判断 personId 指定的人员记录是否过期
	 * @param personId
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public boolean isDisable(int personId) throws ServiceRuntimeException {
		return false;
	}

	/**
	 * 设置 personId 指定的人员为禁止状态
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personId
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 * @see #setPersonExpiryDate(int, long, Token)
	 */
	@ThriftMethod
	public void disablePerson(int personId, Token token) throws ServiceRuntimeException {
	}

	/**
	 * 修改 personId 指定的人员记录的有效期
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personId
	 * @param expiryDate 失效日期
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public void setPersonExpiryDate(int personId, @TargetType(java.util.Date.class)long expiryDate, Token token) throws ServiceRuntimeException {
	}

	/**
	 * 修改 personIdList 指定的人员记录的有效期
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personIdList 人员id列表
	 * @param expiryDate 失效日期 
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("setPersonExpiryDateList")
	public void setPersonExpiryDate(List<Integer> personIdList, @TargetType(java.util.Date.class)long expiryDate, Token token) throws ServiceRuntimeException {
	}

	/**
	 * 设置 personIdList 指定的人员为禁止状态
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personIdList 人员id列表
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("disablePersonList")
	public void disablePerson(List<Integer> personIdList, Token token) throws ServiceRuntimeException {
	}

	/**
	 * 返回 persionId 关联的所有日志记录
	 * @param personId fl_person.id
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<LogBean> getLogBeansByPersonId(int personId) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 返回所有人员记录
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> loadAllPerson() throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 返回 where 指定的所有人员记录
	 * @param where SQL条件语句
	 * @return 返回 fl_person.id 列表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> loadPersonIdByWhere(String where) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回 where 指定的所有人员记录
	 * @param where SQL条件语句
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回满足{@code where}条件的日志记录(fl_person)数目
	 * @param where 为{@code null}时返回所有记录
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int countPersonByWhere(String where)throws ServiceRuntimeException{
		return 0;
	}
	
	/**
	 * 保存人员(person)记录
	 * @param bean
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public PersonBean savePerson(PersonBean bean, Token token) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 保存人员(person)记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param beans 
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public void savePersons(List<PersonBean> beans, Token token) throws ServiceRuntimeException {
	}

	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhoto 标准照图像对象,可为null
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("savePersonWithPhoto")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, Token token) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 保存人员信息记录(包含标准照)
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param persons
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("savePersonsWithPhoto")
	public int savePerson(Map<ByteBuffer, PersonBean> persons, Token token) throws ServiceRuntimeException {
		return 0;
	}

	/**
	 * 保存人员信息记录
	 * @param bean
	 * @param idPhotoMd5 标准照图像对象,可为null
	 * @param featureMd5 用于验证的人脸特征数据对象,可为null
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("savePersonWithPhotoAndFeatureSaved")
	public PersonBean savePerson(PersonBean bean, String idPhotoMd5, String featureMd5, Token token) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 保存人员信息记录
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param bean
	 * @param idPhoto 标准照图像,可为null
	 * @param featureBean 用于验证的人脸特征数据对象,可为null
	 * @param deviceId 标准照图像来源设备id,可为null
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("savePersonWithPhotoAndFeature")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, FeatureBean featureBean, Integer deviceId, Token token)
			throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 保存人员信息记录
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param bean
	 * @param idPhoto 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据,可为null,不可重复, 参见 {@link #addFeature(ByteBuffer, Integer, List, Token)}
	 * @param faceBeans 参见 {@link #addFeature(ByteBuffer, Integer, List, Token)}
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("savePersonWithPhotoAndFeatureMultiFaces")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature, List<FaceBean> faceBeans, Token token)
			throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 保存人员信息记录
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param bean 
	 * @param idPhoto 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据,可为null 
	 * @param faceInfo 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @param deviceId faceInfo 图像来源设备id,可为null 
	 * @param token 访问令牌
	 * @return bean 保存的{@link PersonBean}对象
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod("savePersonWithPhotoAndFeatureMultiImage")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature, Map<ByteBuffer, FaceBean> faceInfo,
			Integer deviceId, Token token) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param bean 人员信息对象
	 * @param idPhoto 标准照图像
	 * @param feature 人脸特征数据
	 * @param featureImage 提取特征源图像,为null 时,默认使用idPhoto
	 * @param featureFaceBean 人脸位置对象,为null 时,不保存人脸数据
	 * @param token 访问令牌
	 * @param deviceBean featureImage来源设备对象
	 * @return
	 */
	@ThriftMethod("savePersonFull")
	public PersonBean savePerson(PersonBean bean, ByteBuffer idPhoto, ByteBuffer feature, ByteBuffer featureImage,
			FaceBean featureFaceBean, Integer deviceId, Token token) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
	 * @param personId 人员记录id
	 * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
	 * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public void replaceFeature(Integer personId, String featureMd5, boolean deleteOldFeatureImage, Token token) throws ServiceRuntimeException {
	}

	/**
	 * (主动更新机制实现)<br>
	 * 返回fl_person.update_time字段大于指定时间戳( {@code timestamp} )的所有fl_person记录<br>
	 * 同时包含fl_feature更新记录引用的fl_person记录
	 * @param timestamp
	 * @return 返回fl_person.id 列表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> loadUpdatedPersons(@TargetType(java.util.Date.class)long timestamp) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_person.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_person记录
	 * @param timestamp
	 * @return 返回fl_person.id 列表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> loadPersonIdByUpdateTime(@TargetType(java.util.Date.class)long timestamp) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_feature.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_feature记录
	 * @param timestamp
	 * @return 返回 fl_feature.md5 列表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<String> loadFeatureMd5ByUpdate(@TargetType(java.util.Date.class)long timestamp) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 添加一条验证日志记录
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param bean
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 * @throws DuplicateRecordException 数据库中存在相同记录
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=DuplicateRecordException.class, id=2)
			})
	public void addLog(LogBean bean, Token token) throws ServiceRuntimeException, DuplicateRecordException {
	}

	/**
	 * 添加一组验证日志记录(事务存储)
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param beans
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 * @throws DuplicateRecordException 数据库中存在相同记录
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=DuplicateRecordException.class, id=2)
			})
	public void addLogs(List<LogBean> beans, Token token) throws ServiceRuntimeException, DuplicateRecordException {
	}
	/**
	 * 日志查询<br>
	 * 根据{@code where}指定的查询条件查询日志记录
	 * @param where
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows)
			throws ServiceRuntimeException {
		return null;
	}
	
	/**
	 * 日志查询<br>
	 * 根据{@code where}指定的查询条件查询日志记录{@link LogLightBean}
	 * @param where
	 * @param startRow
	 * @param numRows
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回符合{@code where}条件的记录条数
	 * @param where
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int countLogLightByWhere(String where) throws ServiceRuntimeException {
		return 0;
	}
	/**
	 * 返回满足{@code where}条件的日志记录(fl_log)数目
	 * @param where 为{@code null}时返回所有记录
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int countLogByWhere(String where) throws ServiceRuntimeException {
		return 0;
	}
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的所有记录
     * @see #loadLogLightByWhere(String,int,int)
     * @throws IllegalArgumentException {@code timestamp}为{@code null}时
     * @throws ServiceRuntimeException
     */
	@ThriftMethod
	public List<LogLightBean> loadLogLightByVerifyTime(@TargetType(java.util.Date.class)long timestamp,int startRow, int numRows)throws ServiceRuntimeException{
		return null;
	}
    /**
     * 返回fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的记录总数
     * @see #countLogLightByWhere(String)
     * @throws ServiceRuntimeException
     */
	@ThriftMethod
	 public int countLogLightByVerifyTime(@TargetType(java.util.Date.class)long timestamp)throws ServiceRuntimeException{
		return 0;
	 }
	/**
	 * 判断md5指定的图像记录是否存在
	 * @param md5
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public boolean existsImage(String md5) throws ServiceRuntimeException {
		return false;
	}

	/**
	 * 保存图像数据,如果图像数据已经存在，则抛出异常
	 * @param imageData 图像数据
	 * @param deviceId 图像来源设备id,可为null
	 * @param faceBean 关联的人脸信息对象,可为null
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param token 访问令牌
	 * @return
	 * @throws DuplicateRecordException 数据库中已经存在要保存的图像数据
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=DuplicateRecordException.class, id=2)
			})
	public ImageBean addImage(ByteBuffer imageData, Integer deviceId, FaceBean faceBean, Integer personId, Token token)
			throws ServiceRuntimeException, DuplicateRecordException {
		return null;
	}

	/**
	 * 判断md5指定的特征记录是否存在
	 * @param md5
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public boolean existsFeature(String md5) throws ServiceRuntimeException {
		return false;
	}

	/**
	 * 增加一个人脸特征记录，如果记录已经存在则抛出异常
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param feature 特征数据
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param faecBeans 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @param token 访问令牌
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws ServiceRuntimeException
	 * @throws DuplicateRecordException 
	 */
	@ThriftMethod(
			exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=DuplicateRecordException.class, id=2)
			}
		)
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, List<FaceBean> faecBeans, Token token) throws ServiceRuntimeException, DuplicateRecordException {
		return null;
	}

	/**
	 * 增加一个人脸特征记录,特征数据由faceInfo指定的多张图像合成，如果记录已经存在则抛出异常
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param feature 特征数据
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param faceInfo 生成特征数据的图像及人脸信息对象(每张图对应一张人脸),可为null
	 * @param deviceId 图像来源设备id,可为null
	 * @param token 访问令牌
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws ServiceRuntimeException
	 * @throws DuplicateRecordException 
	 */
	@ThriftMethod(value = "addFeatureMulti",
			exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=DuplicateRecordException.class, id=2)
			})
	public FeatureBean addFeature(ByteBuffer feature, Integer personId, Map<ByteBuffer, FaceBean> faceInfo, Integer deviceId, Token token)
			throws ServiceRuntimeException, DuplicateRecordException {
		return null;
	}

	/**
	 * 删除featureMd5指定的特征记录及关联的face记录
	 * @param featureMd5
	 * @param deleteImage 是否删除关联的 image记录
	 * @param token 访问令牌
	 * @return 返回删除的特征记录关联的图像(image)记录的MD5<br>
	 *                {@code deleteImage}为{@code true}时返回空表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<String> deleteFeature(String featureMd5, boolean deleteImage, Token token) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 删除 personId 关联的所有特征(feature)记录
	 * @param personId
	 * @param deleteImage 是否删除关联的 image记录
	 * @param token 访问令牌
	 * @return
	 * @see #deleteFeature(String, boolean, Token)
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deleteAllFeaturesByPersonId(int personId, boolean deleteImage, Token token) throws ServiceRuntimeException {
		return 0;
	}

	/**
	 * 根据MD5校验码返回人脸特征数据记录
	 * @param md5
	 * @return 如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public FeatureBean getFeature(String md5) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 根据MD5校验码返回人脸特征数据记录
	 * @param md5 md5列表
	 * @return {@link FeatureBean}列表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<FeatureBean> getFeatures(List<String> md5) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回指定人员{@code personId}关联的所有特征<br>
	 * @param personId
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<String> getFeaturesOfPerson(int personId)throws ServiceRuntimeException{
		return null;
	}
	/**
	 * 根据MD5校验码返回人脸特征数据
	 * @param md5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public ByteBuffer getFeatureBytes(String md5) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 根据图像的MD5校验码返回图像数据
	 * @param imageMD5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 * @throws ServiceRuntimeException
	 * @see {@link #getBinary(String)}
	 */
	@ThriftMethod
	public ByteBuffer getImageBytes(String imageMD5) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 根据图像的MD5校验码返回图像记录
	 * @param imageMD5
	 * @return {@link ImageBean} ,如果没有对应记录则返回null
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public ImageBean getImage(String imageMD5) throws ServiceRuntimeException {
		return null;
	}

	/**
	 * 返回featureMd5的人脸特征记录关联的所有图像记录id(MD5) 
	 * @param featureMd5 人脸特征id(MD5)
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<String> getImagesAssociatedByFeature(String featureMd5) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回featureMd5的人脸特征记录关联的设备id<br>
	 * @param featureMd5
	 * @return 如果没有关联的设备则返回{@code null}
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public Integer getDeviceIdOfFeature(String featureMd5) throws ServiceRuntimeException{
		return null;
	}
	/**
	 * 删除imageMd5指定图像及其缩略图
	 * @param imageMd5
	 * @param token 访问令牌
	 * @return 删除成功返回1,否则返回0
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deleteImage(String imageMd5, Token token) throws ServiceRuntimeException {
		return 0;
	}

	/**
	 * 判断id指定的设备记录是否存在
	 * @param id
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public boolean existsDevice(int id) throws ServiceRuntimeException {
		return false;
	}
	/**
	 * 保存设备记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param deviceBean
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public DeviceBean saveDevice(DeviceBean deviceBean, Token token) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 更新设备记录(必须是已经存在的设备记录，否则抛出异常)
	 * @param deviceBean
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public DeviceBean updateDevice(DeviceBean deviceBean, Token token) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回{@code deviceId}指定的设备记录
	 * @param deviceId
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public DeviceBean getDevice(int deviceId) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回 {@code idList} 指定的设备记录
	 * @param idList
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<DeviceBean> getDevices(List<Integer> idList) throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 根据{@code where}指定的查询条件查询设备记录
	 * @param where SQL 条件语句
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<DeviceBean> loadDeviceByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
		return null;
	}
	/**
	 * 返回满足{@code where} SQL条件语句的fl_device记录总数
	 * @param where
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int countDeviceByWhere(String where)throws ServiceRuntimeException{
		return 0;
	}
	/**
	 * 根据{@code where}指定的查询条件查询设备记录
	 * @param where
	 * @return 返回设备ID列表
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> loadDeviceIdByWhere(String where)throws ServiceRuntimeException{
		return null;
		
	}
	////////////////////////////////DeviceGroupBean/////////////
	/**
	 * 保存设备组记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param deviceGroupBean
	 * @param token 访问令牌
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean, Token token)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 根据设备组id返回数据库记录
	 * @param deviceGroupId
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public DeviceGroupBean getDeviceGroup(int deviceGroupId)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回设备组id列表指定的数据库记录
	 * @param groupIdList
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod()
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 删除{@code deviceGroupId}指定的设备组<br>
	 * 组删除后，所有子节点记录不会被删除，但parent字段会被自动默认为{@code null}
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param deviceGroupId
	 * @param token 访问令牌
	 * @return  返回删除的记录条数
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deleteDeviceGroup(int deviceGroupId, Token token)throws ServiceRuntimeException {
		return 0;
	}
	/**
	 * 返回{@code deviceGroupId}指定的设备组下的所有子节点(设备组)<br>
	 * 如果没有子节点则返回空表
	 * @param deviceGroupId
	 * @return 设备组ID列表
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> getSubDeviceGroup(int deviceGroupId)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回{@code deviceGroupId}指定的设备组下属的所有设备记录<br>
	 * 如果没有下属设备记录则返回空表
	 * @param deviceGroupId
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> getDevicesOfGroup(int deviceGroupId)throws ServiceRuntimeException {
		return null;
	}
    /**
     * 返回({@code deviceGroupId})指定的fl_device_group记录的所有的父节点(包括自己)<br>
     * 自引用字段:fl_device_group(parent)
	 * @param deviceGroupId
	 * @return  如果{@code deviceGroupId}无效则返回空表
     * @throws ServiceRuntimeException
     */
	@ThriftMethod
	public List<Integer> listOfParentForDeviceGroup(int deviceGroupId)throws ServiceRuntimeException{
		return null;
	}
	/**
     * 返回({@code deviceId})指定的设备所属所有设备组<br>
	 * @param deviceId
	 * @return 如果{@code deviceId}无效则返回空表
	 * @throws ServiceRuntimeException
	 * @see {@link #listOfParentForDeviceGroup(int)}
	 */
	@ThriftMethod
	public List<Integer> getDeviceGroupsBelongs(int deviceId)throws ServiceRuntimeException{
		return null;
	}
	////////////////////////////////PersonGroupBean/////////////
	/**
	 * 保存人员组记录
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personGroupBean
	 * @param token 访问令牌
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean, Token token)throws ServiceRuntimeException {
		return personGroupBean;
	}
	/**
	 * 根据人员组id返回数据库记录
	 * @param personGroupId
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public PersonGroupBean getPersonGroup(int personGroupId)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回人员组id列表指定的数据库记录
	 * @param groupIdList
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<PersonGroupBean> getPersonGroups(Collection<Integer> groupIdList)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 删除{@code personGroupId}指定的人员组<br>
	 * 组删除后，所有子节点记录不会被删除，但parent字段会被自动默认为{@code null}
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param personGroupId
	 * @param token 访问令牌
	 * @return 
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deletePersonGroup(int personGroupId, Token token)throws ServiceRuntimeException {
		return personGroupId;
	}
	/**
	 * 返回{@code personGroupId}指定的人员组下的所有子节点(人员组)<br>
	 * 如果没有子节点则返回空表
	 * @param personGroupId
	 * @return 人员组ID列表
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> getSubPersonGroup(int personGroupId)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * 返回{@code deviceGroupId}指定的人员组下属的所有人员记录<br>
	 * 如果没有下属人员记录则返回空表
	 * @param deviceGroupId
	 * @return 人员ID列表
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<Integer> getPersonsOfGroup(int personGroupId)throws ServiceRuntimeException {
		return null;
	}
    /**
     * 返回({@code personGroupId})指定的fl_person_group记录的所有的父节点(包括自己)<br>
     * 自引用字段:fl_person_group(parent)
	 * @param personGroupId
	 * @return  如果{@code personGroupId}无效则返回空表
     * @throws ServiceRuntimeException
     */
	@ThriftMethod
	public List<Integer> listOfParentForPersonGroup(int personGroupId)throws ServiceRuntimeException{
		return null;
	}
	/**
     * 返回({@code personId})指定的人员所属所有人员组<br>
	 * @param personId
	 * @return 如果{@code personId}无效则返回空表
	 * @throws ServiceRuntimeException
	 * @see {@link #listOfParentForPersonGroup(int)}
	 */
	@ThriftMethod
	public List<Integer> getPersonGroupsBelongs(int personId)throws ServiceRuntimeException{
		return null;}
    /**
     * 查询{@code where} SQL条件语句指定的记录
     * @param where SQL 条件语句,为{@code null}或空时加载所有记录
     * @param startRow 返回记录的起始行(首行=1,尾行=-1)
     * @param numRows 返回记录条数(<0时返回所有记录)
     * @return 设备组ID列表
     */
	@ThriftMethod
    public List<Integer> loadDeviceGroupByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
		return null;
    }
    /**
     * 返回满足{@code where} SQL条件语句的fl_device_group记录总数
     */
    @ThriftMethod
    public int countDeviceGroupByWhere(String where)throws ServiceRuntimeException{
		return 0;
    }
    /** 
     * 查询{@code where}条件指定的记录
     * @return 返回查询结果记录的主键
     * @see #loadDeviceGroupByWhere(String,int,int)
     */
    @ThriftMethod
    public List<Integer> loadDeviceGroupIdByWhere(String where)throws ServiceRuntimeException{
		return null;
    }
	/////////////////////PERMIT/////
	/**
	 * 添加一个(允许)通行关联记录:允许{@code personGroup}指定的人员组在
	 * {@code deviceGroup}指定的设备组下属的所有设备通行
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param deviceGroup
	 * @param personGroup
	 * @param token 访问令牌
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public void addPermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup, Token token)throws ServiceRuntimeException {}
    /**
     * 创建fl_device_group和fl_person_group之间的MANY TO MANY 联接表(fl_permit)记录<br>
     * 如果记录已经存在则返回已有记录,如果输入的参数为{@code null}或记录不存在则返回{@code null}
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
     * @param deviceGroupId 外键,设备组id
     * @param personGroupId 外键,人员组id
     * @param token 访问令牌
     * @see #addPermit(DeviceGroupBean,PersonGroupBean, Token)
     */
	@ThriftMethod("addPermitById")
	public void addPermit(int deviceGroupId,int personGroupId, Token token)throws ServiceRuntimeException{}
	/**
	 * 删除通行关联记录,参见{@link #addPermit(DeviceGroupBean, PersonGroupBean, Token)}
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param deviceGroup
	 * @param personGroup
	 * @param token 访问令牌
	 * @return 删除成功返回1,否则返回0
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public int deletePermit(DeviceGroupBean deviceGroup,PersonGroupBean personGroup, Token token)throws ServiceRuntimeException {
		return 0;
	}
	/**
	 * 获取人员组通行权限<br>
	 * 返回{@code personGroupId}指定的人员组在{@code deviceId}设备上是否允许通行
	 * @param deviceId
	 * @param personGroupId
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public boolean getGroupPermit(int deviceId,int personGroupId)throws ServiceRuntimeException {
		return false;
	}
	/**
	 * 获取人员通行权限<br>
	 * 返回{@code personId}指定的人员在{@code deviceId}设备上是否允许通行
	 * @param deviceId
	 * @param personId
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public boolean getPersonPermit(int deviceId,int personId)throws ServiceRuntimeException {
		return false;
	}
	/** 参见 {@link #getGroupPermit(Integer, Integer) } */
	@ThriftMethod
	public List<Boolean> getGroupPermits(int deviceId,List<Integer> personGroupIdList)throws ServiceRuntimeException {
		return null;		
	}
	/** 参见 {@link #getPersonPermit(Integer, Integer) } */
	@ThriftMethod
	public List<Boolean> getPersonPermits(int deviceId,List<Integer> personIdList)throws ServiceRuntimeException {
		return null;
	}
	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_permit.create_time 字段大于指定时间戳( {@code timestamp} )的所有fl_permit记录
	 * @param timestamp
	 * @return
	 * @throws RuntimeDaoException
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public List<PermitBean> loadPermitByUpdate(@TargetType(java.util.Date.class)long timestamp)throws ServiceRuntimeException {
		return null;
	}
    /**
     * 查询{@code where} SQL条件语句指定的记录
     * @param where SQL 条件语句,为{@code null}或空时加载所有记录
     * @param startRow 返回记录的起始行(首行=1,尾行=-1)
     * @param numRows 返回记录条数(<0时返回所有记录)
     * @return 人员组ID列表
     */
	@ThriftMethod
    public List<Integer> loadPersonGroupByWhere(String where,int startRow, int numRows)throws ServiceRuntimeException{
		return null;    	
    }
    /**
     * 返回满足{@code where} SQL条件语句的 fl_person_group 记录总数
     * @see {@link IPersonGroupManager#Where(String)}
     */
	@ThriftMethod
    public int countPersonGroupByWhere(String where)throws ServiceRuntimeException{
		return 0;
    }
    /** 
     * 查询{@code where}条件指定的记录
     * @return 返回查询结果记录的主键
     * @see #loadPersonGroupByWhere(String,int,int)
     * @throws ServiceRuntimeException
     */
	@ThriftMethod
    public List<Integer> loadPersonGroupIdByWhere(String where)throws ServiceRuntimeException{
		return null;
    }
	/**
	 * 新设备注册,如果设备已经注册则返回注册设备记录<br>
	 * 注册时必须提供设备MAC地址,是否提供序列号,根据应用需要选择
	 * @param newDevice
	 * @return
	 * @throws ServiceRuntimeException
	 * @throws ServiceSecurityException
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=ServiceSecurityException.class, id=2)
			})
	public DeviceBean registerDevice(DeviceBean newDevice) throws ServiceRuntimeException, ServiceSecurityException{
		return newDevice;
	}
	/**
	 * (设备端)设备删除
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param deviceId
	 * @param token 设备验证令牌
	 * @throws ServiceRuntimeException
	 * @throws ServiceSecurityException
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=ServiceSecurityException.class, id=2)
			})
	public void unregisterDevice(int deviceId,Token token)
			throws ServiceRuntimeException,ServiceSecurityException{	
	}
	/**
	 * 设备申请上线,每次调用都会产生一个新的令牌
	 * @param device 上线设备信息，必须提供{@code id, mac, serialNo}字段
	 * @return 设备访问令牌
	 * @throws ServiceRuntimeException
	 * @throws ServiceSecurityException
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=ServiceSecurityException.class, id=2)
			})
	public Token online(DeviceBean device)
			throws ServiceRuntimeException, ServiceSecurityException{
				return null;
	}
	/**
	 * 设备申请离线,删除设备令牌
	 * <br>{@link TokenMangement.Enable#DEVICE_ONLY}
	 * @param token 当前持有的令牌
	 * @throws ServiceRuntimeException
	 * @throws ServiceSecurityException
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=ServiceSecurityException.class, id=2)
			})
	public void offline(Token token)
			throws ServiceRuntimeException, ServiceSecurityException{
	}
	/**
	 * 申请人员访问令牌
	 * @param personId
	 * @return
	 * @throws ServiceRuntimeException
	 * @throws ServiceSecurityException
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=ServiceSecurityException.class, id=2)
			})
	public Token applyPersonToken(int personId)
			throws ServiceRuntimeException, ServiceSecurityException{
				return null;		
	}
	/**
	 * 释放人员访问令牌
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param token 当前持有的令牌
	 * @throws ServiceRuntimeException
	 * @throws ServiceSecurityException
	 */
	@ThriftMethod(exception = {
            @ThriftException(type=ServiceRuntimeException.class, id=1),
            @ThriftException(type=ServiceSecurityException.class, id=2)
			})
	public void releasePersonToken(Token token)
			throws ServiceRuntimeException, ServiceSecurityException{
	}
	/**
	 * 申请一个唯一的命令响应通道
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public String applyAckChannel(Token token) throws ServiceRuntimeException{
		return null;
	}
	/**
	 * 申请一个唯一的命令序列号
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public long applyCmdSn(Token token) throws ServiceRuntimeException {
		return 0;
	}
    /**
     * 返回redis访问基本参数:<br>
     * <ul>
     * <li>redis服务器地址</li>
     * <li>设备命令通道名</li>
     * <li>人员验证实时监控通道名</li>
     * <li>设备心跳实时监控通道名</li>
     * <li>设备心跳包间隔时间(秒)</li>
     * <li>设备心跳包失效时间(秒)</li>
     * </ul>
     * 参见{@link MQParam}定义
     * @param token 访问令牌
     * @return
     * @throws ServiceRuntimeException
     */
	@ThriftMethod
    public Map<MQParam,String> getRedisParameters(Token token)throws ServiceRuntimeException{
		return null;
    }
	/**
	 * 获取服务的所有配置参数
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param token 访问令牌
	 * @return
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
	public Map<String,String> getServiceConfig(Token token)throws ServiceRuntimeException{
		return null;
	}
	/**
	 * 修改/增加指定的配置参数
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param key 参数名
	 * @param value 参数值
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
    public void setProperty(String key,String value,Token token)throws ServiceRuntimeException{
    }
    /**
     * 修改一组配置参数
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
     * @param config 参数名-参数值对
     * @param token 访问令牌
     * @throws ServiceRuntimeException
     */
	@ThriftMethod
    public void setProperties(Map<String,String> config,Token token)throws ServiceRuntimeException{
    	
    }
	/**
	 * 配置参数持久化<br>
	 * 保存修改的配置到自定义配置文件
	 * <br>{@link TokenMangement.Enable#PERSON_ONLY}
	 * @param token 访问令牌
	 * @throws ServiceRuntimeException
	 */
	@ThriftMethod
    public void saveServiceConfig(Token token)throws ServiceRuntimeException{
    }

    	
}