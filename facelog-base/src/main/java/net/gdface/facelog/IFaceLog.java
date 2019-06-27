package net.gdface.facelog;

import java.util.List;
import java.util.Map;
import net.gdface.annotation.DeriveMethod;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.TableManager;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.LogLightBean;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
import net.gdface.facelog.db.exception.RuntimeDaoException;

/**
 * FaceLog 服务接口<br>
 * <ul>
 * <li>所有标明为图像数据的参数,是指具有特定图像格式的图像数据(如jpg,png...),而非无格式的原始点阵位图</li>
 * <li>所有{@link RuntimeException}异常会被封装在{@code ServiceRuntimeException}抛出,
 * client端可以通过{@code ServiceRuntimeException#getType()}获取异常类型.<br>
 * 异常类型定义参见{@link CommonConstant.ExceptionType},<br>
 * 例如: 在执行涉及数据库操作的异常{@link RuntimeDaoException}，
 * 被封装到{@code ServiceRuntimeException}抛出时type为{@link ExceptionType#DAO}</li>
 * <li>所有数据库对象(Java Bean,比如 {@link PersonBean}),在执行保存操作(save)时,
 * 如果为新增记录({@link PersonBean#isNew()}为true),则执行insert操作,否则执行update操作,
 * 如果数据库已经存在指定的记录而{@code isNew()}为{@code true},则那么执行insert操作数据库就会抛出异常，
 * 所以请在执行save时特别注意{@code isNew()}状态</li>
 * <li>对于以add为前缀的添加记录方法,在添加记录前会检查数据库中是否有(主键)相同记录,
 * 如果有则会抛出异常{@link DuplicateRecordException}</li>
 * <li>所有带{@link Token}参数的方法都需要提供访问令牌,访问令牌分为人员令牌,设备令牌和root令牌(仅用于root帐户),
 * 注释中标注为{@code PERSON_ONLY}的方法只接受人员令牌,
 * 注释中标注为{@code DEVICE_ONLY}的方法只接受设备令牌,
 * 注释中标注为{@code ROOT_ONLY}的方法只接受root令牌,
 * 关于令牌申请和释放参见{@link #applyPersonToken(int, String, boolean)},{@link #releasePersonToken(Token)},{@link #online(DeviceBean)},{@link #offline(Token)}</li>
 * </ul>
 * @author guyadong
 */
public interface IFaceLog{

	/**
	 * 返回personId指定的人员记录
	 * @param personId
	 * @return
	 */
	public PersonBean getPerson(int personId) ;

	/**
	 * 返回 list 指定的人员记录
	 * @param idList 人员id列表
	 * @return
	 */
	public List<PersonBean> getPersons(List<Integer> idList);

	/**
	 * 根据证件号码返回人员记录
	 * @param papersNum
	 * @return
	 */
	public PersonBean getPersonByPapersNum(String papersNum);

	/**
	 * 返回 persionId 关联的所有人脸特征记录
	 * @param personId 人员id(fl_person.id)
	 * @return 返回 fl_feature.md5  列表
	 */
	public List<String> getFeaturesByPersonId(int personId);
	
	/**
	 * 返回 persionId 关联的指定SDK的人脸特征记录
	 * @param personId 人员id(fl_person.id)
	 * @param sdkVersion 算法(SDK)版本号
	 * @return 返回 fl_feature.md5  列表
	 */
	public List<String> getFeaturesByPersonIdAndSdkVersion(int personId, String sdkVersion);
	/**
	 * 返回在指定设备上允许通行的所有特征记录<br>
	 * 此方法主要设计用于不能通过长连接侦听redis频道的设备(如人脸锁)。
	 * @param deviceId 设备ID
	 * @param ignoreSchedule 是否忽略时间过滤器(fl_permit.schedule字段)的限制
	 * @param sdkVersion 特征版本号
	 * @param excludeFeatureIds 要排除的特征记录id(MD5) ,可为{@code null}
	 * @return 返回 fl_feature.md5  列表
	 */
	public List<FeatureBean> getFeaturesPermittedOnDevice(int deviceId, boolean ignoreSchedule, String sdkVersion, List<String> excludeFeatureIds);

	/**
	 * 删除personId指定的人员(person)记录及关联的所有记录
	 * <br>{@code PERSON_ONLY}
	 * @param personId
	 * @param token 访问令牌
	 * @return
	 */
	public int deletePerson(int personId, Token token);

	/**
	 * 删除personIdList指定的人员(person)记录及关联的所有记录
	 * <br>{@code PERSON_ONLY}
	 * @param personIdList 人员id列表
	 * @param token 访问令牌
	 * @return 返回删除的 person 记录数量
	 */
	public int deletePersons(List<Integer> personIdList, Token token);

	/**
	 * 删除papersNum指定的人员(person)记录及关联的所有记录
	 * <br>{@code PERSON_ONLY}
	 * @param papersNum 证件号码
	 * @param token 访问令牌
	 * @return 返回删除的 person 记录数量
	 * @see #deletePerson(int, Token)
	 */
	public int deletePersonByPapersNum(String papersNum, Token token);

	/**
	 * 删除papersNum指定的人员(person)记录及关联的所有记录
	 * <br>{@code PERSON_ONLY}
	 * @param papersNumlist 证件号码列表
	 * @param token 访问令牌
	 * @return 返回删除的 person 记录数量
	 */
	public int deletePersonsByPapersNum(List<String> papersNumlist, Token token);

	/**
	 * 判断是否存在personId指定的人员记录
	 * @param persionId
	 * @return
	 */
	public boolean existsPerson(int persionId);

	/**
	 * 判断 personId 指定的人员记录是否过期
	 * @param personId
	 * @return
	 */
	public boolean isDisable(int personId);

	/**
	 * 设置 personId 指定的人员为禁止状态<br>
	 * 将{@code fl_person.expiry_date}设置为昨天
	 * <br>{@code PERSON_ONLY}
	 * @param personId 
	 * @param moveToGroupId 将用户移动到指定的用户组，为{@code null}则不移动
	 * @param deletePhoto 为{@code true}删除用户标准照
	 * @param deleteFeature 为{@code true}删除用户所有的人脸特征数据(包括照片)
	 * @param deleteLog 为{@code true}删除用户所有通行日志
	 * @param token 访问令牌
	 */
	public void disablePerson(int personId, Integer moveToGroupId, boolean deletePhoto, boolean deleteFeature, boolean deleteLog, Token token);

	/**
	 * 修改 personId 指定的人员记录的有效期
	 * <br>{@code PERSON_ONLY}
	 * @param personId
	 * @param expiryDate 失效日期
	 * @param token 访问令牌
	 */
	public void setPersonExpiryDate(int personId, long expiryDate, Token token) ;

	/**
	 * 修改 personId 指定的人员记录的有效期
	 * <br>{@code PERSON_ONLY}
	 * @param personId
	 * @param expiryDate 失效日期,{@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @param token 访问令牌
	 */
	@DeriveMethod(methodSuffix="TimeStr")
	public void setPersonExpiryDate(int personId, String expiryDate, Token token);

	/**
	 * 修改 personIdList 指定的人员记录的有效期
	 * <br>{@code PERSON_ONLY}
	 * @param personIdList 人员id列表
	 * @param expiryDate 失效日期 
	 * @param token 访问令牌
	 * @
	 */
	@DeriveMethod(methodSuffix="List")
	public void setPersonExpiryDate(List<Integer> personIdList, long expiryDate, Token token);

	/**
	 * 设置 personIdList 指定的人员为禁止状态
	 * <br>{@code PERSON_ONLY}
	 * @param personIdList 人员id列表
	 * @param token 访问令牌
	 */
	@DeriveMethod(methodSuffix="List")
	public void disablePerson(List<Integer> personIdList, Token token);

	/**
	 * 返回 persionId 关联的所有日志记录
	 * @param personId fl_person.id
	 * @return
	 */
	public List<LogBean> getLogBeansByPersonId(int personId);

	/**
	 * 返回所有人员记录
	 * @return
	 */
	public List<Integer> loadAllPerson();

	/**
	 * 返回 where 指定的所有人员记录
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}或空时加载所有记录
	 * @return 返回 fl_person.id 列表
	 */
	public List<Integer> loadPersonIdByWhere(String where);
	/**
	 * 返回 where 指定的所有人员记录
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}或空时加载所有记录
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
	 * @return 人员记录列表
	 */
	public List<PersonBean> loadPersonByWhere(String where, int startRow, int numRows);
	/**
	 * 返回满足{@code where}条件的日志记录(fl_person)数目
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
	 * @return 返回满足{@code where}条件的日志记录(fl_person)数目
	 */
	public int countPersonByWhere(String where);
	
	/**
	 * 保存人员(person)记录
	 * @param personBean {@code fl_person}表记录
	 * @param token 访问令牌
	 * @return 保存的{@link PersonBean}
	 */
	public PersonBean savePerson(PersonBean personBean, Token token);

	/**
	 * 保存人员(person)记录
	 * <br>{@code PERSON_ONLY}
	 * @param persons {@code fl_person}表记录
	 * @param token 访问令牌
	 */
	public void savePersons(List<PersonBean> persons, Token token);

	/**
	 * 保存人员信息记录
	 * @param personBean {@code fl_person}表记录
	 * @param idPhoto 标准照图像对象,可为null
	 * @param token 访问令牌
	 * @return 保存的{@link PersonBean}
	 */
	@DeriveMethod(methodSuffix="WithPhoto")
	public PersonBean savePerson(PersonBean personBean, byte[] idPhoto, Token token);

	/**
	 * 保存人员信息记录(包含标准照)<br>
	 * 每一张照片对应一个{@code PersonBean}记录, {@code photos}元素不可重复
	 * {@code photos}与{@code persons}列表一一对应
	 * {@code PERSON_ONLY}
	 * @param photos 照片列表 
	 * @param persons 人员记录对象列表
	 * @param token 访问令牌
	 * @return 保存的{@link PersonBean}记录条数
	 */
	@DeriveMethod(methodSuffix="WithPhoto")
	public int savePersons(List<byte[]> photos, List<PersonBean> persons, Token token);

	/**
	 * 保存人员信息记录
	 * @param personBean {@code fl_person}表记录
	 * @param idPhotoMd5 标准照图像对象,可为null
	 * @param featureMd5 用于验证的人脸特征数据对象,可为null
	 * @param token 访问令牌
	 * @return 保存的{@link PersonBean}
	 */
	@DeriveMethod(methodSuffix="WithPhotoAndFeatureSaved")
	public PersonBean savePerson(PersonBean personBean, String idPhotoMd5, String featureMd5, Token token);

	/**
	 * 保存人员信息记录
	 * <br>{@code DEVICE_ONLY}
	 * @param personBean {@code fl_person}表记录
	 * @param idPhoto 标准照图像,可为null
	 * @param featureBean 用于验证的人脸特征数据对象,可为null
	 * @param token 访问令牌
	 * @return 保存的{@link PersonBean}
	 */
	@DeriveMethod(methodSuffix="WithPhotoAndFeature")
	public PersonBean savePerson(PersonBean personBean, byte[] idPhoto, FeatureBean featureBean, Token token);

	/**
	 * 保存人员信息记录
	 * <br>{@code DEVICE_ONLY}
	 * @param personBean {@code fl_person}表记录
	 * @param idPhoto 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据,不可重复, 参见 {@link #addFeature(byte[], String, Integer, List, Token)}
	 * @param featureVersion 特征(SDk)版本号
	 * @param faceBeans 可为{@code null},参见 {@link #addFeature(byte[], String, Integer, List, Token)}
	 * @param token 访问令牌
	 * @return 保存的{@link PersonBean}
	 */
	@DeriveMethod(methodSuffix="WithPhotoAndFeatureMultiFaces")
	public PersonBean savePerson(PersonBean personBean, byte[] idPhoto, byte[] feature, String featureVersion, List<FaceBean> faceBeans, Token token);

	/**
	 * 保存人员信息记录<br>
	 * {@code photos}与{@code faces}为提取特征{@code feature}的人脸照片对应的人脸位置对象，必须一一对应,
	 * 该方法用于多张照片合成一个人脸特征的算法
	 * <br>{@code DEVICE_ONLY}
	 * @param personBean {@code fl_person}表记录
	 * @param idPhoto 标准照图像,可为null
	 * @param feature 用于验证的人脸特征数据 
	 * @param featureVersion 特征(SDk)版本号
	 * @param photos 检测到人脸的照片列表
	 * @param faces 检测人脸信息列表
	 * @param token (设备)访问令牌
	 * @return 保存的{@link PersonBean}对象
	 */
	@DeriveMethod(methodSuffix="WithPhotoAndFeatureMultiImage")
	public PersonBean savePerson(PersonBean personBean, byte[] idPhoto, byte[] feature, String featureVersion,
			List<byte[]> photos, List<FaceBean> faces, Token token);

	/**
	 * 保存人员信息记录<br>
	 * {@code DEVICE_ONLY}
	 * @param personBean 人员信息对象,{@code fl_person}表记录
	 * @param idPhoto 标准照图像,可以为{@code null}
	 * @param feature 人脸特征数据,可以为{@code null}
	 * @param featureVersion 特征(SDk)版本号
	 * @param featureImage 提取特征源图像,为null 时,默认使用idPhoto
	 * @param featureFaceBean 人脸位置对象,为null 时,不保存人脸数据
	 * @param token (设备)访问令牌
	 * @return 保存的{@link PersonBean}
	 */
	@DeriveMethod(methodSuffix="Full")
	public PersonBean savePerson(PersonBean personBean, byte[] idPhoto, byte[] feature, String featureVersion,
			byte[] featureImage, FaceBean featureFaceBean, Token token);

	/**
	 * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
	 * @param personId 人员记录id,{@code fl_person.id}
	 * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
	 * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
	 * @param token 访问令牌
	 */
	public void replaceFeature(Integer personId, String featureMd5, boolean deleteOldFeatureImage, Token token);
	/**
	 * (主动更新机制实现)<br>
	 * 返回fl_person.update_time字段大于指定时间戳( {@code timestamp} )的所有fl_person记录<br>
	 * 同时包含fl_feature更新记录引用的fl_person记录
	 * @param timestamp
	 * @return 返回fl_person.id 列表
	 */
	public List<Integer> loadUpdatedPersons(long timestamp);
	/**
	 * (主动更新机制实现)<br>
	 * 返回fl_person.update_time字段大于指定时间戳( {@code timestamp} )的所有fl_person记录<br>
	 * 同时包含fl_feature更新记录引用的fl_person记录
	 * @param timestamp 时间戳,{@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @return 返回fl_person.id 列表
	 */
	@DeriveMethod(methodSuffix="Timestr")
	public List<Integer> loadUpdatedPersons(String timestamp);

	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_person.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_person记录
	 * @param timestamp
	 * @return 返回fl_person.id 列表
	 */
	public List<Integer> loadPersonIdByUpdateTime(long timestamp);
	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_person.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_person记录
	 * @param timestamp 时间戳,{@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @return 返回fl_person.id 列表
	 */
	@DeriveMethod(methodSuffix="TimeStr")
	public List<Integer> loadPersonIdByUpdateTime(String timestamp);

	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_feature.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_feature记录
	 * @param timestamp
	 * @return 返回 fl_feature.md5 列表
	 */
	public List<String> loadFeatureMd5ByUpdate(long timestamp);

	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_feature.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_feature记录
	 * @param timestamp 时间戳,{@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @return 返回 fl_feature.md5 列表
	 */
	@DeriveMethod(methodSuffix="TimeStr")
	public List<String> loadFeatureMd5ByUpdate(String timestamp);

	/**
	 * 添加一条验证日志记录
	 * <br>{@code DEVICE_ONLY}
	 * @param logBean 日志记录对象
	 * @param token 访问令牌
	 * @throws DuplicateRecordException 数据库中存在相同记录
	 */
	public void addLog(LogBean logBean, Token token) throws DuplicateRecordException;

	/**
	 * 添加一条验证日志记录
	 * <br>{@code DEVICE_ONLY}
	 * {@code faceBean}和{@code featureImage}必须全不为{@code null},否则抛出异常
	 * @param logBean 日志记录对象
	 * @param faceBean 用于保存到数据库的提取人脸特征的人脸信息对象
	 * @param featureImage 用于保存到数据库的现场采集人脸特征的照片
	 * @param token 访问令牌
	 * @throws DuplicateRecordException 数据库中存在相同记录
	 */
	@DeriveMethod(methodSuffix="Full")
	public void addLog(final LogBean logBean, final FaceBean faceBean, final byte[] featureImage, Token token) throws DuplicateRecordException;
	
	/**
	 * 添加一组验证日志记录(事务存储)
	 * <br>{@code DEVICE_ONLY}
	 * @param beans
	 * @param token 访问令牌
	 * @throws DuplicateRecordException 数据库中存在相同记录
	 */
	public void addLogs(List<LogBean> beans, Token token) throws DuplicateRecordException;
	/**
	 * 添加一组验证日志记录(事务存储)<br>
	 * 所有输入参数的list长度必须一致(不能有{@code null})元素,每3个相同索引位置元素为一组关联的日志记录，参见{@link #addLog(LogBean, FaceBean, byte[], Token)}
	 * @param logBeans 日志记录对象
	 * @param faceBeans 为用于保存到数据库的提取人脸特征的人脸信息对象
	 * @param featureImages 用于保存到数据库的现场采集人脸特征的照片
	 * @param token 访问令牌
	 * @throws DuplicateRecordException 数据库中存在相同记录
	 */
	@DeriveMethod(methodSuffix="Full")
	void addLogs(final List<LogBean> logBeans, final List<FaceBean> faceBeans, final List<byte[]> featureImages, Token token) throws DuplicateRecordException;

	/**
	 * 日志查询<br>
	 * 根据{@code where}指定的查询条件查询日志记录
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
	 * @return
	 */
	public List<LogBean> loadLogByWhere(String where, int startRow, int numRows);
	
	/**
	 * 日志查询<br>
	 * 根据{@code where}指定的查询条件查询日志记录{@link LogLightBean}
	 * @param where 'WHERE'开头的SQL条件语句
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
	 * @return
	 */
	public List<LogLightBean> loadLogLightByWhere(String where, int startRow, int numRows);
	/**
	 * 返回符合{@code where}条件的记录条数
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
	 * @return 返回符合{@code where}条件的记录条数
	 */
	public int countLogLightByWhere(String where);
	/**
	 * 返回满足{@code where}条件的日志记录(fl_log)数目
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
	 * @return 返回满足{@code where}条件的日志记录(fl_log)数目
	 */
	public int countLogByWhere(String where);
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的所有记录
	 * @param timestamp 时间戳,{@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
     */
	public List<LogLightBean> loadLogLightByVerifyTime(long timestamp,int startRow, int numRows);
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的所有记录
     * @param timestamp 时间戳
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
     */
	@DeriveMethod(methodSuffix="Timestr")
	public List<LogLightBean> loadLogLightByVerifyTime(String timestamp, int startRow, int numRows);

    /**
     * 返回fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的记录总数
     * @param timestamp 时间戳
     * @return 满足条件的记录条数
     * @see #countLogLightByWhere(String)
     */
	public int countLogLightByVerifyTime(long timestamp);
    /**
     * 返回fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的记录总数
     * @param timestamp 时间戳,{@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
     * @return 满足条件的记录条数
     * @see #countLogLightByWhere(String)
     */
	@DeriveMethod(methodSuffix="Timestr")
	public int countLogLightByVerifyTime(String timestamp);

	/**
	 * 判断{@code md5}指定的图像记录是否存在
	 * @param md5 图像的MD5校验码
	 * @return 记录存在返回{@code true},否则返回{@code false}
	 */
	public boolean existsImage(String md5);

	/**
	 * 保存图像数据,如果图像数据已经存在，则抛出异常
	 * @param imageData 图像数据
	 * @param deviceId 图像来源设备id,可为null
	 * @param faceBean 关联的人脸信息对象,可为null
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param token 访问令牌
	 * @return 保存的图像记录
	 * @throws DuplicateRecordException 数据库中已经存在要保存的图像数据
	 */
	public ImageBean addImage(byte[] imageData, Integer deviceId, FaceBean faceBean, Integer personId, Token token)
			throws DuplicateRecordException;

	/**
	 * 判断md5指定的特征记录是否存在
	 * @param md5
	 * @return
	 */
	public boolean existsFeature(String md5);

	/**
	 * 增加一个人脸特征记录，如果记录已经存在则抛出异常<br>
	 * {@code DEVICE_ONLY}
	 * @param feature 人脸特征数据
	 * @param featureVersion 特征(SDk)版本号
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param faecBeans 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @param token (设备)访问令牌
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws DuplicateRecordException 
	 */
	public FeatureBean addFeature(byte[] feature, String featureVersion, Integer personId, List<FaceBean> faecBeans, Token token) throws DuplicateRecordException;
	
	/**
	 * 增加一个人脸特征记录，如果记录已经存在则抛出异常<br>
	 * 适用于一张人脸图像提取一个人脸特征的算法<br>
	 * {@code DEVICE_ONLY}
	 * @param feature 特征数据
	 * @param featureVersion 特征(SDk)版本号
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param asIdPhotoIfAbsent 如果{@code personId}指定的记录没指定身份照片,
	 * 是否用{@code featurePhoto}作为身份照片,{@code featurePhoto}为{@code null}时无效
	 * @param featurePhoto 生成人脸特征的原始照片,如果不要求保留原始照片可为null
	 * @param faceBean 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
	 * @param token (设备)访问令牌
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws DuplicateRecordException
	 * @since 2.1.2
	 */
	@DeriveMethod(methodSuffix="WithImage")
	FeatureBean addFeature(final byte[] feature, String featureVersion, final Integer personId, final boolean asIdPhotoIfAbsent, final byte[] featurePhoto, final FaceBean faceBean, Token token)throws DuplicateRecordException;

	/**
	 * 增加一个人脸特征记录,特征数据由faceInfo指定的多张图像合成，如果记录已经存在则抛出异常<br>
	 * {@code photos}与{@code faces}为提取特征{@code feature}的人脸照片对应的人脸位置对象，必须一一对应
	 * <br>{@code DEVICE_ONLY}
	 * @param feature 特征数据
	 * @param featureVersion 特征(SDk)版本号
	 * @param personId 关联的人员id(fl_person.id),可为null
	 * @param photos 检测到人脸的照片列表
	 * @param faces 检测人脸信息列表
	 * @param token (设备)访问令牌
	 * @return 保存的人脸特征记录{@link FeatureBean}
	 * @throws DuplicateRecordException 
	 */
	@DeriveMethod(methodSuffix="Multi")
	public FeatureBean addFeature(byte[] feature, String featureVersion, Integer personId, List<byte[]> photos, List<FaceBean> faces, Token token)
			throws DuplicateRecordException;

	/**
	 * 删除featureMd5指定的特征记录及关联的face记录
	 * @param featureMd5
	 * @param deleteImage 为{@code true}则删除关联的 image记录(如果该图像还关联其他特征则不删除)
	 * @param token 访问令牌
	 * @return 返回删除的特征记录关联的图像(image)记录的MD5<br>
	 */
	public List<String> deleteFeature(String featureMd5, boolean deleteImage, Token token);

	/**
	 * 删除 personId 关联的所有特征(feature)记录
	 * @param personId
	 * @param deleteImage 是否删除关联的 image记录
	 * @param token 访问令牌
	 * @return
	 * @see #deleteFeature(String, boolean, Token)
	 */
	public int deleteAllFeaturesByPersonId(int personId, boolean deleteImage, Token token);
	/**
	 * 根据MD5校验码返回人脸特征数据记录
	 * @param md5
	 * @return 如果数据库中没有对应的数据则返回null
	 */
	public FeatureBean getFeature(String md5);

	/**
	 * 根据MD5校验码返回人脸特征数据记录
	 * @param md5 md5列表
	 * @return {@link FeatureBean}列表
	 */
	public List<FeatureBean> getFeatures(List<String> md5);
	/**
	 * 返回指定人员{@code personId}关联的所有特征<br>
	 * @param personId
	 * @return
	 */
	public List<String> getFeaturesOfPerson(int personId);
	/**
	 * 根据MD5校验码返回人脸特征数据
	 * @param md5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 */
	public byte[] getFeatureBytes(String md5);

	/**
	 * 根据图像的MD5校验码返回图像数据
	 * @param imageMD5
	 * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
	 */
	public byte[] getImageBytes(String imageMD5);

	/**
	 * 根据图像的MD5校验码返回图像记录
	 * @param imageMD5
	 * @return {@link ImageBean} ,如果没有对应记录则返回null
	 */
	public ImageBean getImage(String imageMD5);

	/**
	 * 返回featureMd5的人脸特征记录关联的所有图像记录id(MD5) 
	 * @param featureMd5 人脸特征id(MD5)
	 * @return
	 */
	public List<String> getImagesAssociatedByFeature(String featureMd5);
	
	/**
	 * 返回faceId指定的人脸信息记录
	 * @param faceId
	 * @return {@link FaceBean} ,如果没有对应记录则返回null
	 */
	public FaceBean getFace(int faceId);
	/**
	 * 返回featureMd5的人脸特征记录关联的设备id<br>
	 * @param featureMd5
	 * @return 如果没有关联的设备则返回{@code null}
	 */
	public Integer getDeviceIdOfFeature(String featureMd5);
	/**
	 * 删除imageMd5指定图像及其缩略图
	 * @param imageMd5
	 * @param token 访问令牌
	 * @return 删除成功返回1,否则返回0
	 */
	public int deleteImage(String imageMd5, Token token);

	/**
	 * 判断id指定的设备记录是否存在
	 * @param id
	 * @return
	 */
	public boolean existsDevice(int id);
	/**
	 * 保存设备记录
	 * <br>{@code PERSON_ONLY}
	 * @param deviceBean
	 * @param token 访问令牌
	 * @return
	 */
	public DeviceBean saveDevice(DeviceBean deviceBean, Token token);
	/**
	 * 更新设备记录(必须是已经存在的设备记录，否则抛出异常)
	 * @param deviceBean
	 * @param token 访问令牌
	 * @return 返回设备记录
	 */
	public DeviceBean updateDevice(DeviceBean deviceBean, Token token);
	/**
	 * 返回{@code deviceId}指定的设备记录
	 * @param deviceId
	 * @return 返回设备记录
	 */
	public DeviceBean getDevice(int deviceId);
	/**
	 * 返回 {@code idList} 指定的设备记录
	 * @param idList
	 * @return 返回设备记录列表
	 */
	public List<DeviceBean> getDevices(List<Integer> idList);
	/**
	 * 根据{@code where}指定的查询条件查询设备记录
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
	 * @param startRow 记录起始行号 (first row = 1, last row = -1)
	 * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
	 * @return 返回设备记录列表
	 */
	public List<DeviceBean> loadDeviceByWhere(String where,int startRow, int numRows);
	/**
	 * 返回满足{@code where} SQL条件语句的fl_device记录总数
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
	 * @return 返回设备ID列表
	 */
	public int countDeviceByWhere(String where);
	/**
	 * 根据{@code where}指定的查询条件查询设备记录
	 * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
	 * @return 返回设备ID列表
	 */
	public List<Integer> loadDeviceIdByWhere(String where);
	////////////////////////////////DeviceGroupBean/////////////
	/**
	 * 保存设备组记录
	 * <br>{@code PERSON_ONLY}
	 * @param deviceGroupBean
	 * @param token 访问令牌
	 * @return
	 * @throws RuntimeDaoException
	 */
	public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean, Token token);
	/**
	 * 根据设备组id返回数据库记录
	 * @param deviceGroupId
	 * @return
	 * @throws RuntimeDaoException
	 */
	public DeviceGroupBean getDeviceGroup(int deviceGroupId);
	/**
	 * 返回设备组id列表指定的数据库记录
	 * @param groupIdList
	 * @return
	 * @throws RuntimeDaoException
	 */
	public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList);
	/**
	 * 删除{@code deviceGroupId}指定的设备组<br>
	 * 组删除后，所有子节点记录不会被删除，但parent字段会被自动默认为{@code null}
	 * <br>{@code PERSON_ONLY}
	 * @param deviceGroupId
	 * @param token 访问令牌
	 * @return  返回删除的记录条数
	 * @throws RuntimeDaoException
	 */
	public int deleteDeviceGroup(int deviceGroupId, Token token);
	/**
	 * 返回{@code deviceGroupId}指定的设备组下的所有子节点(设备组)<br>
	 * 如果没有子节点则返回空表
	 * @param deviceGroupId
	 * @return 设备组ID列表
	 * @throws RuntimeDaoException
	 */
	public List<Integer> getSubDeviceGroup(int deviceGroupId);
	/**
	 * 返回{@code deviceGroupId}指定的设备组下属的所有设备记录<br>
	 * 如果没有下属设备记录则返回空表
	 * @param deviceGroupId
	 * @return
	 * @throws RuntimeDaoException
	 */
	public List<Integer> getDevicesOfGroup(int deviceGroupId);
    /**
     * 返回({@code deviceGroupId})指定的fl_device_group记录的所有的父节点(包括自己)<br>
     * 自引用字段:fl_device_group(parent)
	 * @param deviceGroupId
	 * @return  如果{@code deviceGroupId}无效则返回空表
     */
	public List<Integer> listOfParentForDeviceGroup(int deviceGroupId);
	
	/**
     * 返回(deviceGroupId))指定的fl_device_group记录的所有的子节点(包括自己)<br>
     * 自引用字段:fl_device_group(parent)
	 * @param deviceGroupId
	 * @return 如果{@code deviceGroupId}无效则返回空表
	 * @since 2.1.2
	 */
	public List<Integer> childListForDeviceGroup(int deviceGroupId);

	/**
     * 返回({@code deviceId})指定的设备所属所有设备组<br>
	 * @param deviceId
	 * @return 如果{@code deviceId}无效则返回空表
	 * @see #listOfParentForDeviceGroup(int)
	 */
	public List<Integer> getDeviceGroupsBelongs(int deviceId);
	////////////////////////////////PersonGroupBean/////////////
	/**
	 * 保存人员组记录
	 * <br>{@code PERSON_ONLY}
	 * @param personGroupBean
	 * @param token 访问令牌
	 * @return
	 * @throws RuntimeDaoException
	 */
	public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean, Token token);
	/**
	 * 根据人员组id返回数据库记录
	 * @param personGroupId
	 * @return
	 * @throws RuntimeDaoException
	 */
	public PersonGroupBean getPersonGroup(int personGroupId);
	/**
	 * 返回人员组id列表指定的数据库记录
	 * @param groupIdList
	 * @return
	 * @throws RuntimeDaoException
	 */
	public List<PersonGroupBean> getPersonGroups(List<Integer> groupIdList);
	/**
	 * 删除{@code personGroupId}指定的人员组<br>
	 * 组删除后，所有子节点记录不会被删除，但parent字段会被自动默认为{@code null}
	 * <br>{@code PERSON_ONLY}
	 * @param personGroupId
	 * @param token 访问令牌
	 * @return 
	 * @throws RuntimeDaoException
	 */
	public int deletePersonGroup(int personGroupId, Token token);
	/**
	 * 返回{@code personGroupId}指定的人员组下的所有子节点(人员组)<br>
	 * 如果没有子节点则返回空表
	 * @param personGroupId
	 * @return 人员组ID列表
	 * @throws RuntimeDaoException
	 */
	public List<Integer> getSubPersonGroup(int personGroupId);
	/**
	 * 返回{@code deviceGroupId}指定的人员组下属的所有人员记录<br>
	 * 如果没有下属人员记录则返回空表
	 * @param personGroupId
	 * @return 人员ID列表
	 * @throws RuntimeDaoException
	 */
	public List<Integer> getPersonsOfGroup(int personGroupId);
    /**
     * 返回({@code personGroupId})指定的fl_person_group记录的所有的父节点(包括自己)<br>
     * 自引用字段:fl_person_group(parent)
	 * @param personGroupId
	 * @return  如果{@code personGroupId}无效则返回空表
     */
	public List<Integer> listOfParentForPersonGroup(int personGroupId);
	
	/**
     * 返回(personGroupId))指定的fl_person_group记录的所有的子节点(包括自己)<br>
     * 自引用字段:fl_person_group(parent)
	 * @param personGroupId
	 * @return 如果{@code personGroupId}无效则返回空表
	 * @since 2.1.2
	 */
	public List<Integer> childListForPersonGroup(int personGroupId);
	/**
     * 返回({@code personId})指定的人员所属所有人员组<br>
	 * @param personId
	 * @return 如果{@code personId}无效则返回空表
	 * @see #listOfParentForPersonGroup(int)
	 */
	public List<Integer> getPersonGroupsBelongs(int personId);
    /**
     * 查询{@code where} SQL条件语句指定的记录
     * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
     * @param startRow 返回记录的起始行(首行=1,尾行=-1)
     * @param numRows 返回记录条数(小于0时返回所有记录)
     * @return 设备组ID列表
     */
    public List<Integer> loadDeviceGroupByWhere(String where,int startRow, int numRows);
    /**
     * 返回满足{@code where} SQL条件语句的fl_device_group记录总数
     * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
     * @return 返回满足{@code where} SQL条件语句的fl_device_group记录总数
     */
    public int countDeviceGroupByWhere(String where);
    /** 
     * 查询{@code where}条件指定的记录
     * @param where 'WHERE'开头的SQL条件语句,为{@code null}时返回所有记录
     * @return 返回查询结果记录的主键
     * @see 设备组ID列表
     */
    public List<Integer> loadDeviceGroupIdByWhere(String where);
    
	/////////////////////MANAGEMENT BORDER/////
    /**
	 * 创建管理边界<br>
	 * 设置fl_person_group.root_group和fl_device_group.root_group字段互相指向<br>
	 * 没有找到personGroupId或deviceGroupId指定的记录抛出异常,
	 * 以事务操作方式更新数据库<br>
	 * <br>{@link TokenMangement.Enable#ROOT}<br>
	 * @param personGroupId 人员组id
     * @param deviceGroupId 设备组id
     * @param token 访问令牌
	 */
	public void bindBorder(Integer personGroupId, Integer deviceGroupId, Token token);
	/**
	 * 删除管理边界<br>
	 * 删除fl_person_group.root_group和fl_device_group.root_group字段的互相指向,设置为{@code null},
	 * 以事务操作方式更新数据库<br>
	 * 如果personGroupId和deviceGroupId不存在绑定关系则跳过,
	 * 没有找到personGroupId或deviceGroupId指定的记录抛出异常<br>
	 * <br>{@link TokenMangement.Enable#ROOT}<br>
	 * @param personGroupId 人员组id
	 * @param deviceGroupId 设备组id
	 * @param token 访问令牌
	 */
	public void unbindBorder(Integer personGroupId, Integer deviceGroupId, Token token);
    /**
	 * 返回personId所属的管理边界人员组id<br>
	 * 在personId所属组的所有父节点中自顶向下查找第一个{@code fl_person_group.root_group}字段不为空的人员组，返回此记录组id<br>
	 * 没有找到personId指定的记录抛出异常
	 * @param personId
	 * @return {@code fl_person_group.root_group}字段不为空的记录id,没有找到则返回{@code null}
	 */
	public Integer rootGroupOfPerson(Integer personId);
	/**
	 * 返回deviceId所属的管理边界设备组id<br>
	 * 在deviceId所属组的所有父节点中自顶向下查找第一个{@code fl_device_group.root_group}字段不为空的组，返回此记录id<br>
	 * 没有找到deviceId指定的记录抛出异常
	 * @param deviceId
	 * @return {@code fl_device_group.root_group}字段不为空的记录id,没有找到则返回{@code null}
	 */
	public Integer rootGroupOfDevice(Integer deviceId);

	/////////////////////PERMIT/////

    /**
     * 保存通行权限(permit)记录
     * <br>{@code PERSON_ONLY}
	 * @param permitBean 要修改或增加的fl_permit记录
	 * @param token 访问令牌
	 * @return 保存的{@link PermitBean}
	 */
	public PermitBean savePermit(PermitBean permitBean, Token token);
	/**
	 * 如果记录不存在则创建deviceGroupId和personGroupId之间的MANY TO MANY 联接表(fl_permit)记录,
	 * 否则修改指定记录的通行时间安排表<br>
	 * <br>{@code PERSON_ONLY}
     * @param deviceGroupId 设备组id
     * @param personGroupId 人员组id
     * @param schedule 通行时间安排表,为{@code null}则不限制通行时间
     * @param token 访问令牌
     * @return (fl_permit)记录
     */
	@DeriveMethod(methodSuffix="WithSchedule")
	public PermitBean savePermit(int deviceGroupId,int personGroupId, String schedule, Token token);
	/**
	 * 删除fl_device_group和fl_person_group之间的MANY TO MANY 联接表(fl_permit)记录<br>
	 * @param deviceGroupId 设备组id
	 * @param personGroupId 人员组id
	 * @param token
	 * @return 删除成功返回1,否则返回0
	 * @since 2.1.2
	 */
	@DeriveMethod(methodSuffix="ById")
	int deletePermit(int deviceGroupId, int personGroupId, Token token);

	/**
	 * 从permit表删除指定{@code personGroupId}指定人员组的在所有设备上的通行权限
	 * @param personGroupId 
	 * @param token 令牌
	 * @return 删除的记录条数
	 */
	int deletePersonGroupPermit(int personGroupId, Token token);

	/**
	 * 从permit表删除指定{@code deviceGroupId}指定设备组上的人员通行权限
	 * @param deviceGroupId 
	 * @param token 令牌
	 * @return 删除的记录条数
	 */
	int deleteGroupPermitOnDeviceGroup(int deviceGroupId, Token token);
	/**
	 * 获取人员组通行权限<br>
	 * 返回{@code personGroupId}指定的人员组在{@code deviceGroupId}指定的设备组上是否允许通行,
	 * 本方法会对{@code personGroupId}的父结点向上回溯：
	 * {@codepersonGroupId } 及其父结点,任何一个在permit表存在与{@code deviceId}所属设备级的关联记录中就返回true，
	 * 输入参数为{@code null}或找不到指定的记录则返回false
	 * @param deviceGroupId
	 * @param personGroupId
	 * @return 允许通行返回指定的{@link PermitBean}记录，否则返回{@code null}
	 */
	PermitBean getGroupPermitOnDeviceGroup(int deviceGroupId, int personGroupId);
	/**
	 * 获取人员组通行权限<br>
	 * 返回{@code personGroupId}指定的人员组在{@code deviceId}设备上是否允许通行,
	 * 本方法会对{@code personGroupId}的父结点向上回溯：
	 * {@codepersonGroupId } 及其父结点,任何一个在permit表存在与{@code deviceId}所属设备级的关联记录中就返回true，
	 * 输入参数为{@code null}或找不到指定的记录则返回false
	 * @param deviceId
	 * @param personGroupId
	 * @return 允许通行返回指定的{@link PermitBean}记录，否则返回{@code null}
	 * @throws RuntimeDaoException
	 */
	public PermitBean getGroupPermit(int deviceId,int personGroupId);
	/**
	 * 获取人员通行权限<br>
	 * 返回{@code personId}指定的人员在{@code deviceId}设备上是否允许通行
	 * @param deviceId
	 * @param personId
	 * @return 允许通行返回指定的{@link PermitBean}记录，否则返回{@code null}
	 * @throws RuntimeDaoException
	 */
	public PermitBean getPersonPermit(int deviceId,int personId);
	/**
	 * 参见 {@link #getGroupPermit(int, int)}
	 * @param deviceId
	 * @param personGroupIdList
	 * @return
	 */
	public List<PermitBean> getGroupPermits(int deviceId,List<Integer> personGroupIdList);
	/**
	 * 参见 {@link #getPersonPermit(int, int) }
	 * @param deviceId
	 * @param personIdList
	 * @return
	 */
	public List<PermitBean> getPersonPermits(int deviceId,List<Integer> personIdList);

	/**
	 * 从permit表返回允许在{@code deviceGroupId}指定的设备组通过的所有人员组{@link PersonGroupBean}对象的id<br>
	 *  不排序,不包含重复id,本方法不会对{@link PersonGroupBean}的父结点向上回溯
	 * @param deviceGroupId
	 * @return
	 */
	List<Integer> getPersonGroupsPermittedBy(int deviceGroupId);
	/**
	 * 从permit表返回允许在{@code personGroupId}指定的人员组通过的所有设备组({@link DeviceGroupBean})的id<br>
	 * 不排序,不包含重复id,本方法不会对{@code personGroupId}的父结点向上回溯
	 * @param personGroupId
	 * @return
	 */
	List<Integer> getDeviceGroupsPermittedBy(int personGroupId);

	/**
	 * 从permit表返回允许在{@code personGroupId}指定的人员组通过的所有设备组({@link DeviceGroupBean})的id<br>
	 * 不排序,不包含重复id
	 * @param personGroupId 为{@code null}返回空表
	 * @return
	 */
	List<Integer> getDeviceGroupsPermit(int personGroupId);
	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_permit.create_time 字段大于指定时间戳( {@code timestamp} )的所有fl_permit记录
	 * @param timestamp
	 * @return
	 */
	public List<PermitBean> loadPermitByUpdate(long timestamp);
	/**
	 * (主动更新机制实现)<br>
	 * 返回 fl_permit.create_time 字段大于指定时间戳( {@code timestamp} )的所有fl_permit记录
	 * @param timestamp 时间戳,,{@code yyyy-MM-dd}或{@code yyyy-MM-dd HH:mm:ss}或{@code yyyy-MM-dd'T'HH:mm:ss.SSS'Z'}(ISO8601)格式日期字符串
	 * @return
	 */
	@DeriveMethod(methodSuffix="Timestr")
	public List<PermitBean> loadPermitByUpdate(String timestamp);

    /**
     * 查询{@code where} SQL条件语句指定的记录
     * @param where 'WHERE'开头的SQL条件语句,为{@code null}或空时加载所有记录
     * @param startRow 返回记录的起始行(首行=1,尾行=-1)
     * @param numRows 返回记录条数(小于0时返回所有记录)
     * @return 人员组ID列表
     */
    public List<Integer> loadPersonGroupByWhere(String where,int startRow, int numRows);
    /**
     * 返回满足{@code where} SQL条件语句的 fl_person_group 记录总数
     * @param where 'WHERE'开头的SQL条件语句,为{@code null}或空时加载所有记录
     * @return 返回满足{@code where} SQL条件语句的 fl_person_group 记录总数
     * @see TableManager#countWhere(String)
     */
    public int countPersonGroupByWhere(String where);
    /** 
     * 查询{@code where}条件指定的记录
     * @param where 'WHERE'开头的SQL条件语句,为{@code null}或空时加载所有记录
     * @return 返回人员组列表
     * @see #loadPersonGroupByWhere(String,int,int)
     */
    public List<Integer> loadPersonGroupIdByWhere(String where);
	/**
	 * 新设备注册,如果设备已经注册则返回注册设备记录<br>
	 * 注册时必须提供设备MAC地址,是否提供序列号,根据应用需要选择
	 * @param newDevice 设备记录,_isNew字段必须为{@code true},{@code id}字段不要指定,数据库会自动分配,保存在返回值中
	 * @return
	 * @throws ServiceSecurityException
	 */
	public DeviceBean registerDevice(DeviceBean newDevice) throws ServiceSecurityException;
	/**
	 * (设备端)删除当前设备<br>
	 * 从fl_device表中删除当前设备记录
	 * <br>{@code DEVICE_ONLY}
	 * @param token 设备验证令牌
	 * @throws ServiceSecurityException
	 */
	public void unregisterDevice(Token token)
			throws ServiceSecurityException;
	/**
	 * 设备申请上线,每次调用都会产生一个新的令牌
	 * @param device 上线设备信息，必须提供{@code id, mac, serialNo}字段
	 * @return 设备访问令牌
	 * @throws ServiceSecurityException
	 */
	public Token online(DeviceBean device)
			throws ServiceSecurityException;
	/**
	 * 设备申请离线,删除设备令牌
	 * <br>{@code DEVICE_ONLY}
	 * @param token 当前持有的令牌
	 * @throws ServiceSecurityException
	 */
	public void offline(Token token)	throws ServiceSecurityException;
	/**
	 * 申请人员访问令牌
	 * @param personId 用户ID
	 * @param password 密码
	 * @param isMd5 为{@code false}代表{@code password}为明文,{@code true}指定{@code password}为32位MD5密文(小写)
	 * @return 返回申请的令牌
	 * @throws ServiceSecurityException
	 */
	public Token applyPersonToken(int personId, String password, boolean isMd5)
			throws ServiceSecurityException;
	/**
	 * 释放人员访问令牌
	 * <br>{@code PERSON_ONLY}
	 * @param token 当前持有的令牌
	 * @throws ServiceSecurityException
	 */
	public void releasePersonToken(Token token)
			throws ServiceSecurityException;
	/**
	 * 申请root访问令牌
	 * @param password root用户密码
	 * @param isMd5 为{@code false}代表{@code password}为明文,{@code true}指定{@code password}为32位MD5密文(小写)
	 * @return 返回申请的令牌
	 * @throws ServiceSecurityException
	 */
	public Token applyRootToken(String password, boolean isMd5)
			throws ServiceSecurityException;

	/**
	 * 释放root访问令牌
	 * <br>{@code ROOT_ONLY}
	 * @param token 当前持有的令牌
	 * @throws ServiceSecurityException
	 */
	public void releaseRootToken(Token token)
			throws ServiceSecurityException;
	/**
	 * 申请person/root访问令牌
	 * @param userid 用户ID(为-1时为root)
	 * @param password 用户密码
	 * @param isMd5 为{@code false}代表{@code password}为明文,{@code true}指定{@code password}为32位MD5密文(小写)
	 * @return 返回申请的令牌
	 * @throws ServiceSecurityException
	 * @since 2.1.1
	 */
	Token applyUserToken(int userid, String password, boolean isMd5) throws ServiceSecurityException;

	/**
	 * 释放person/root访问令牌
	 * @param token 要释放的令牌,如果令牌类型非{@link net.gdface.facelog.Token.TokenType#PERSON}或{@link net.gdface.facelog.Token.TokenType#ROOT}则抛出{@link ServiceSecurityException}异常
	 * @throws ServiceSecurityException
	 * @since 2.1.1
	 */
	void releaseUserToken(Token token) throws ServiceSecurityException;

	/**
	 * 验证用户密码是否匹配
	 * @param userId 用户id字符串,root用户id即为{@link CommonConstant#ROOT_NAME}
	 * @param password 用户密码
	 * @param isMd5 为{@code false}代表{@code password}为明文,{@code true}指定{@code password}为32位MD5密文(小写)
	 * @return {@code true}密码匹配
	 */
	public boolean isValidPassword(String userId,String password, boolean isMd5) ;
	/**
	 * 申请一个唯一的命令响应通道(默认有效期)<br>
	 * <br>{@code PERSON_ONLY}
	 * @param token 访问令牌
	 * @return
	 */
	public String applyAckChannel(Token token);
	/**
	 * 申请一个唯一的命令响应通道<br>
	 * <br>{@code PERSON_ONLY}
	 * @param duration 通道有效时间(秒) 大于0有效,否则使用默认的有效期
	 * @param token 访问令牌
	 * @return
	 */
	@DeriveMethod(methodSuffix="WithDuration")
	public String applyAckChannel(int duration, Token token);
	/**
	 * 申请一个唯一的命令序列号
	 * <br>{@code PERSON_ONLY}
	 * @param token 访问令牌
	 * @return
	 */
	public int applyCmdSn(Token token);
	/**
	 * 判断命令序列号是否有效<br>
	 * 序列号过期或不存在都返回{@code false}
	 * @param cmdSn
	 * @return
	 */
	public boolean isValidCmdSn(int cmdSn);
	/**
	 * 判断命令响应通道是否有效<br>
	 * 通道过期或不存在都返回{@code false}
	 * @param ackChannel
	 * @return
	 */
	public boolean isValidAckChannel(String ackChannel);
    /**
	 * 验证设备令牌是否有效
	 * @param token
	 * @return 令牌有效返回{@code true},否则返回{@code false}
	 */
	boolean isValidDeviceToken(Token token);

	/**
	 * 验证人员令牌是否有效
	 * @param token
	 * @return  令牌有效返回{@code true},否则返回{@code false}
	 */
	boolean isValidPersonToken(Token token);

	/**
	 * 验证root令牌是否有效
	 * @param token
	 * @return 令牌有效返回{@code true},否则返回{@code false}
	 */
	boolean isValidRootToken(Token token);
	/**
	 * 验证PERSON/ROOT令牌是否有效
	 * @param token
	 * @return 令牌有效返回{@code true},否则返回{@code false}
	 * @since 2.1.1
	 */
	boolean isValidUserToken(Token token);

	/**
	 * 验证令牌是否有效
	 * @param token
	 * @return 令牌有效返回{@code true},否则返回{@code false}
	 * @since 2.1.1
	 */
	boolean isValidToken(Token token);

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
     */
    public Map<MQParam,String> getRedisParameters(Token token);

	/**
	 * 根据任务名返回redis队列名
	 * @param task 任务名
	 * @param token 访问令牌
	 * @return 返回redis队列名,队列不存在则返回{@code null}
	 */
	public String taskQueueOf(String task, Token token);
	
	/**
	 * 返回sdk任务队列名
	 * @param task 任务名,可选值:{@link CommonConstant#TASK_FACEAPI_BASE},{@link CommonConstant#TASK_REGISTER_BASE}
	 * @param sdkVersion sdk版本号
	 * @param token 访问令牌
	 * @return  返回sdk任务队列名，参数错误返回{@code null}
	 */
	public String sdkTaskQueueOf(String task, String sdkVersion, Token token);
	/**
	 * (异步)执行cmdpath指定的设备命令<br>
	 * <br>{@code PERSON_ONLY}
	 * @param target 命令目标设备/设备组id
	 * @param group target中的元素是否为设备组id
	 * @param cmdpath 设备命令的dtalk路径
	 * @param jsonArgs 设备命令参数(JSON)
	 * @param ackChannel 设备命令响应频道,不需要接收命令响应时设置为{@code null}
	 * @param token 访问令牌
	 * @return 收到命令的客户端数目
	 */
	int runCmd(List<Integer>target, boolean group, String cmdpath, Map<String, String> jsonArgs, String ackChannel, Token token);
	/**
	 * (异步)执行cmdpath指定的任务<br>
	 * <br>{@code PERSON_ONLY}
	 * @param taskQueue 任务队列名称
	 * @param cmdpath 设备命令的dtalk路径
	 * @param jsonArgs 设备命令参数(JSON)
	 * @param ackChannel 设备命令响应频道,不需要接收命令响应时设置为{@code null}
	 * @param token 访问令牌
	 * @return 成功提交任务返回{@code true},否则返回{@code false}
	 */
	boolean runTask(String taskQueue, String cmdpath, Map<String, String> jsonArgs, String ackChannel, Token token);
	/**
	 * 返回指定的参数,如果参数没有定义则返回{@code null}<br>
	 * 非root令牌只能访问指定范围的参数,否则会抛出异常<br>
	 * root令牌不受限制<br>
	 * @param key
	 * @param token 访问令牌
	 * @return 返回{@code key}指定的参数值
	 */
	public String getProperty(String key,Token token);
	/**
	 * 获取服务的所有配置参数
	 * <br>{@code ROOT_ONLY}
	 * @param token 访问令牌
	 * @return
	 */
	public Map<String,String> getServiceConfig(Token token);
	/**
	 * 修改/增加指定的配置参数
	 * <br>{@code ROOT_ONLY}
	 * @param key 参数名
	 * @param value 参数值
	 * @param token 访问令牌
	 */
    public void setProperty(String key,String value,Token token);
    /**
     * 修改一组配置参数
	 * <br>{@code ROOT_ONLY}
     * @param config 参数名-参数值对
     * @param token 访问令牌
     */
    public void setProperties(Map<String,String> config,Token token);
	/**
	 * 配置参数持久化<br>
	 * 保存修改的配置到自定义配置文件
	 * <br>{@code ROOT_ONLY}
	 * @param token 访问令牌
	 */
    public void saveServiceConfig(Token token);
	/**
	 * 返回服务版本号
	 * @return
	 */
	public String version();
	/**
	 * 返回服务版本的详细信息<br>
	 * <ul>
	 * <li>{@code VERSION} -- 服务版本号</li>
	 * <li>{@code SCM_REVISION} -- GIT修订版本号</li>
	 * <li>{@code SCM_BRANCH} -- GIT分支</li>
	 * <li>{@code TIMESTAMP} -- 时间戳</li>
	 * </ul>
	 * 
	 * @return
	 */
	public Map<String, String> versionInfo();
	/**
	 * 是否为本地实现
	 * @return
	 */
	public boolean isLocal();

}