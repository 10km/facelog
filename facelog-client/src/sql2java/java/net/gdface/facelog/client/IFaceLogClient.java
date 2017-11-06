// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// JDBC driver used at code generation time: com.mysql.jdbc.Driver
// template: service.client.java.vm
// ______________________________________________________
package net.gdface.facelog.client;
import static com.google.common.base.Preconditions.checkNotNull;
import java.nio.ByteBuffer;
import java.util.*;
/**
 * 定义 FaceLog 服务接口<br>
 * <ul>
 * <li>所有标明为图像数据的参数,是指具有特定图像格式的图像数据(如jpg,png...),而非无格式的原始点阵位图</li>
 * <li>在执行涉及数据库操作的方法时如果数据库发生异常，则会被封装到{@link net.gdface.facelog.db.exception.WrapDAOException}抛出，
 * 所有非{@link RuntimeException}异常会被封装在{@link ServiceRuntime}抛出</li>
 * <li>所有数据库对象(Java Bean,比如 {@link PersonBean}),在执行保存操作(save)时,
 * 如果为新增记录({@link PersonBean#isNew()}为true),则执行insert操作,否则执行update操作,
 * 如果数据库已经存在指定的记录而{@code isNew()}为{@code true},则那么执行insert操作数据库就会抛出异常，所以请在执行save时特别注意{@code isNew()}状态</li>
 * </ul>
 * remote implementation of the service IFaceLog<br>
 * all method comments be copied from {@code net.gdface.facelog.FaceLogDefinition.java}<br>
 * <b>NOTE:</b>methods with 'Generic' suffix support generic type argument for {@code byte[]}.<br>
 * @author guyadong
 */
class IFaceLogClient implements Constant{
    
    /** bean converter between {@link DeviceBean} and corresponding thrift bean */
    private IBeanConverter<DeviceBean,net.gdface.facelog.client.thrift.DeviceBean> converterDeviceBean = ThriftConverter.converterDeviceBean;
    /** bean converter between {@link DeviceGroupBean} and corresponding thrift bean */
    private IBeanConverter<DeviceGroupBean,net.gdface.facelog.client.thrift.DeviceGroupBean> converterDeviceGroupBean = ThriftConverter.converterDeviceGroupBean;
    /** bean converter between {@link FaceBean} and corresponding thrift bean */
    private IBeanConverter<FaceBean,net.gdface.facelog.client.thrift.FaceBean> converterFaceBean = ThriftConverter.converterFaceBean;
    /** bean converter between {@link FeatureBean} and corresponding thrift bean */
    private IBeanConverter<FeatureBean,net.gdface.facelog.client.thrift.FeatureBean> converterFeatureBean = ThriftConverter.converterFeatureBean;
    /** bean converter between {@link ImageBean} and corresponding thrift bean */
    private IBeanConverter<ImageBean,net.gdface.facelog.client.thrift.ImageBean> converterImageBean = ThriftConverter.converterImageBean;
    /** bean converter between {@link LogBean} and corresponding thrift bean */
    private IBeanConverter<LogBean,net.gdface.facelog.client.thrift.LogBean> converterLogBean = ThriftConverter.converterLogBean;
    /** bean converter between {@link PermitBean} and corresponding thrift bean */
    private IBeanConverter<PermitBean,net.gdface.facelog.client.thrift.PermitBean> converterPermitBean = ThriftConverter.converterPermitBean;
    /** bean converter between {@link PersonBean} and corresponding thrift bean */
    private IBeanConverter<PersonBean,net.gdface.facelog.client.thrift.PersonBean> converterPersonBean = ThriftConverter.converterPersonBean;
    /** bean converter between {@link PersonGroupBean} and corresponding thrift bean */
    private IBeanConverter<PersonGroupBean,net.gdface.facelog.client.thrift.PersonGroupBean> converterPersonGroupBean = ThriftConverter.converterPersonGroupBean;
    /** bean converter between {@link LogLightBean} and corresponding thrift bean */
    private IBeanConverter<LogLightBean,net.gdface.facelog.client.thrift.LogLightBean> converterLogLightBean = ThriftConverter.converterLogLightBean;

    private final net.gdface.facelog.client.thrift.IFaceLog service;
    /**
     * constructor 
     * @param service a instance of net.gdface.facelog.client.thrift.IFaceLog created by Swift, must not be null
     */
    IFaceLogClient(net.gdface.facelog.client.thrift.IFaceLog service){
        checkNotNull(service,"service is null");
        this.service = service;
    }
    /**
     * 增加一个人脸特征记录，如果记录已经存在则抛出异常
     * @param feature 特征数据
     * @param personId 关联的人员id(fl_person.id),可为null
     * @param faecBeans 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
     * @return 保存的人脸特征记录{@link FeatureBean}
     */
    // 1 SERIVCE PORT : addFeature
    public FeatureBean addFeature(
            byte[] feature,
            int personId,
            List<FaceBean> faecBeans){
        return converterFeatureBean.fromRight(service.addFeature(
                    feature,
                    personId,
                    converterFaceBean.toRight(faecBeans)));
    }
    /** 
     * Generic version of {@link #addFeature(byte[],int,List)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 1 GENERIC
    public FeatureBean addFeatureGeneric(
            Object feature,
            int personId,
            List<FaceBean> faecBeans){
        return converterFeatureBean.fromRight(service.addFeature(
                    GenericUtils.toBytes(feature),
                    personId,
                    converterFaceBean.toRight(faecBeans)));
    }
    /**
     * 增加一个人脸特征记录,特征数据由faceInfo指定的多张图像合成，如果记录已经存在则抛出异常
     * @param feature 特征数据
     * @param personId 关联的人员id(fl_person.id),可为null
     * @param faceInfo 生成特征数据的图像及人脸信息对象(每张图对应一张人脸),可为null
     * @param deviceId 图像来源设备id,可为null
     * @return 保存的人脸特征记录{@link FeatureBean}
     */
    // 2 SERIVCE PORT : addFeatureMulti
    public FeatureBean addFeature(
            byte[] feature,
            int personId,
            Map<ByteBuffer, FaceBean> faceInfo,
            int deviceId){
        return converterFeatureBean.fromRight(service.addFeatureMulti(
                    feature,
                    personId,
                    GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                    deviceId));
    }
    /** 
     * Generic version of {@link #addFeature(byte[],int,Map,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 2 GENERIC
    public FeatureBean addFeatureGeneric(
            Object feature,
            int personId,
            Map<ByteBuffer, FaceBean> faceInfo,
            int deviceId){
        return converterFeatureBean.fromRight(service.addFeatureMulti(
                    GenericUtils.toBytes(feature),
                    personId,
                    GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                    deviceId));
    }
    /**
     * 保存图像数据,如果图像数据已经存在，则抛出异常
     * @param imageData 图像数据
     * @param deviceId 图像来源设备id,可为null
     * @param faceBean 关联的人脸信息对象,可为null
     * @param personId 关联的人员id(fl_person.id),可为null
     * @return 
     */
    // 3 SERIVCE PORT : addImage
    public ImageBean addImage(
            byte[] imageData,
            int deviceId,
            FaceBean faceBean,
            int personId){
        return converterImageBean.fromRight(service.addImage(
                    imageData,
                    deviceId,
                    converterFaceBean.toRight(faceBean),
                    personId));
    }
    /** 
     * Generic version of {@link #addImage(byte[],int,FaceBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 3 GENERIC
    public ImageBean addImageGeneric(
            Object imageData,
            int deviceId,
            FaceBean faceBean,
            int personId){
        return converterImageBean.fromRight(service.addImage(
                    GenericUtils.toBytes(imageData),
                    deviceId,
                    converterFaceBean.toRight(faceBean),
                    personId));
    }
    /**
     * 添加一条验证日志记录
     * @param bean
     */
    // 4 SERIVCE PORT : addLog
    public void addLog(LogBean bean){
        service.addLog(converterLogBean.toRight(bean));
    }
    /**
     * 添加一组验证日志记录(事务存储)
     * @param beans
     */
    // 5 SERIVCE PORT : addLogs
    public void addLogs(List<LogBean> beans){
        service.addLogs(converterLogBean.toRight(beans));
    }
    /**
     * 添加一个(允许)通行关联记录:允许{@code personGroup}指定的人员组在
     * {@code deviceGroup}指定的设备组下属的所有设备通行
     * @param deviceGroup
     * @param personGroup
     */
    // 6 SERIVCE PORT : addPermit
    public void addPermit(
            DeviceGroupBean deviceGroup,
            PersonGroupBean personGroup){
        service.addPermit(
                    converterDeviceGroupBean.toRight(deviceGroup),
                    converterPersonGroupBean.toRight(personGroup));
    }

    // 7 SERIVCE PORT : addPermitById
    public void addPermit(
            int deviceGroupId,
            int personGroupId){
        service.addPermitById(
                    deviceGroupId,
                    personGroupId);
    }
    /**
     * 返回满足{@code where}条件的日志记录(fl_log)数目
     * @param where 为{@code null}时返回所有记录
     * @return 
     */
    // 8 SERIVCE PORT : countLogByWhere
    public int countLogByWhere(String where){
        return service.countLogByWhere(where);
    }
    /**
     * 返回fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的记录总数
     * @see #countLogLightByWhere(String)
     */
    // 9 SERIVCE PORT : countLogLightByVerifyTime
    public int countLogLightByVerifyTime(Date timestamp){
        return service.countLogLightByVerifyTime(GenericUtils.toLong(timestamp,Date.class));
    }
    /**
     * 返回符合{@code where}条件的记录条数
     * @param where
     * @return 
     */
    // 10 SERIVCE PORT : countLogLightByWhere
    public int countLogLightByWhere(String where){
        return service.countLogLightByWhere(where);
    }
    /**
     * 删除 personId 关联的所有特征(feature)记录
     * @param personId
     * @param deleteImage 是否删除关联的 image记录
     * @return 
     * @see #deleteFeature(String, boolean)
     */
    // 11 SERIVCE PORT : deleteAllFeaturesByPersonId
    public int deleteAllFeaturesByPersonId(
            int personId,
            boolean deleteImage){
        return service.deleteAllFeaturesByPersonId(
                    personId,
                    deleteImage);
    }
    /**
     * 删除{@code deviceGroupId}指定的设备组<br>
     * 组删除后，所有子节点记录不会被删除，但parent字段会被自动默认为{@code null}
     * @param deviceGroupId
     * @return 返回删除的记录条数
     */
    // 12 SERIVCE PORT : deleteDeviceGroup
    public int deleteDeviceGroup(int deviceGroupId){
        return service.deleteDeviceGroup(deviceGroupId);
    }
    /**
     * 删除featureMd5指定的特征记录及关联的face记录
     * @param featureMd5
     * @param deleteImage 是否删除关联的 image记录
     * @return 返回删除的特征记录关联的图像(image)记录的MD5<br>
     * {@code deleteImage}为{@code true}时返回空表
     */
    // 13 SERIVCE PORT : deleteFeature
    public List<String> deleteFeature(
            String featureMd5,
            boolean deleteImage){
        return service.deleteFeature(
                    featureMd5,
                    deleteImage);
    }
    /**
     * 删除imageMd5指定图像及其缩略图
     * @param imageMd5
     * @return 
     */
    // 14 SERIVCE PORT : deleteImage
    public int deleteImage(String imageMd5){
        return service.deleteImage(imageMd5);
    }
    /**
     * 删除通行关联记录,参见{@link #addPermit(DeviceGroupBean, PersonGroupBean)}
     * @param deviceGroup
     * @param personGroup
     * @return 删除成功返回1,否则返回0
     */
    // 15 SERIVCE PORT : deletePermit
    public int deletePermit(
            DeviceGroupBean deviceGroup,
            PersonGroupBean personGroup){
        return service.deletePermit(
                    converterDeviceGroupBean.toRight(deviceGroup),
                    converterPersonGroupBean.toRight(personGroup));
    }
    /**
     * 删除personId指定的人员(person)记录及关联的所有记录
     * @param personId
     * @return 
     */
    // 16 SERIVCE PORT : deletePerson
    public int deletePerson(int personId){
        return service.deletePerson(personId);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNum 证件号码
     * @return 返回删除的 person 记录数量
     * @see {@link #deletePerson(int)}
     */
    // 17 SERIVCE PORT : deletePersonByPapersNum
    public int deletePersonByPapersNum(String papersNum){
        return service.deletePersonByPapersNum(papersNum);
    }
    /**
     * 删除{@code personGroupId}指定的人员组<br>
     * 组删除后，所有子节点记录不会被删除，但parent字段会被自动默认为{@code null}
     * @param personGroupId
     * @return 
     */
    // 18 SERIVCE PORT : deletePersonGroup
    public int deletePersonGroup(int personGroupId){
        return service.deletePersonGroup(personGroupId);
    }
    /**
     * 删除personIdList指定的人员(person)记录及关联的所有记录
     * @param personIdList 人员id列表
     * @return 返回删除的 person 记录数量
     */
    // 19 SERIVCE PORT : deletePersons
    public int deletePersons(List<Integer> personIdList){
        return service.deletePersons(personIdList);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNumlist 证件号码列表
     * @return 返回删除的 person 记录数量
     */
    // 20 SERIVCE PORT : deletePersonsByPapersNum
    public int deletePersonsByPapersNum(List<String> papersNumlist){
        return service.deletePersonsByPapersNum(papersNumlist);
    }
    /**
     * 设置 personId 指定的人员为禁止状态
     * @param personId
     * @see #setPersonExpiryDate(int, long)
     */
    // 21 SERIVCE PORT : disablePerson
    public void disablePerson(int personId){
        service.disablePerson(personId);
    }
    /**
     * 设置 personIdList 指定的人员为禁止状态
     * @param personIdList 人员id列表
     */
    // 22 SERIVCE PORT : disablePersonList
    public void disablePerson(List<Integer> personIdList){
        service.disablePersonList(personIdList);
    }
    /**
     * 判断id指定的设备记录是否存在
     * @param id
     * @return 
     */
    // 23 SERIVCE PORT : existsDevice
    public boolean existsDevice(int id){
        return service.existsDevice(id);
    }
    /**
     * 判断md5指定的特征记录是否存在
     * @param md5
     * @return 
     */
    // 24 SERIVCE PORT : existsFeature
    public boolean existsFeature(String md5){
        return service.existsFeature(md5);
    }
    /**
     * 判断md5指定的图像记录是否存在
     * @param md5
     * @return 
     */
    // 25 SERIVCE PORT : existsImage
    public boolean existsImage(String md5){
        return service.existsImage(md5);
    }
    /**
     * 判断是否存在personId指定的人员记录
     * @param persionId
     * @return 
     */
    // 26 SERIVCE PORT : existsPerson
    public boolean existsPerson(int persionId){
        return service.existsPerson(persionId);
    }
    /**
     * 返回{@code deviceId}指定的设备记录
     * @param deviceId
     * @return 
     */
    // 27 SERIVCE PORT : getDevice
    public DeviceBean getDevice(int deviceId){
        return converterDeviceBean.fromRight(service.getDevice(deviceId));
    }
    /**
     * 根据设备组id返回数据库记录
     * @param deviceGroupId
     * @return 
     */
    // 28 SERIVCE PORT : getDeviceGroup
    public DeviceGroupBean getDeviceGroup(int deviceGroupId){
        return converterDeviceGroupBean.fromRight(service.getDeviceGroup(deviceGroupId));
    }
    /**
     * 返回设备组id列表指定的数据库记录
     * @param groupIdList
     * @return 
     */
    // 29 SERIVCE PORT : getDeviceGroups
    public List<DeviceGroupBean> getDeviceGroups(List<Integer> groupIdList){
        return converterDeviceGroupBean.fromRight(service.getDeviceGroups(groupIdList));
    }
    /**
     * 返回 {@code idList} 指定的设备记录
     * @param idList
     * @return 
     */
    // 30 SERIVCE PORT : getDevices
    public List<DeviceBean> getDevices(List<Integer> idList){
        return converterDeviceBean.fromRight(service.getDevices(idList));
    }
    /**
     * 返回{@code deviceGroupId}指定的设备组下属的所有设备记录<br>
     * 如果没有下属设备记录则返回空表
     * @param deviceGroupId
     * @return 
     */
    // 31 SERIVCE PORT : getDevicesOfGroup
    public List<DeviceBean> getDevicesOfGroup(int deviceGroupId){
        return converterDeviceBean.fromRight(service.getDevicesOfGroup(deviceGroupId));
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5
     * @return 如果数据库中没有对应的数据则返回null
     */
    // 32 SERIVCE PORT : getFeature
    public FeatureBean getFeature(String md5){
        return converterFeatureBean.fromRight(service.getFeature(md5));
    }
    /**
     * 返回 persionId 关联的所有人脸特征记录
     * @param personId fl_person.id
     * @return 返回 fl_feature.md5  列表
     */
    // 33 SERIVCE PORT : getFeatureBeansByPersonId
    public List<String> getFeatureBeansByPersonId(int personId){
        return service.getFeatureBeansByPersonId(personId);
    }
    /**
     * 根据MD5校验码返回人脸特征数据
     * @param md5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     */
    // 34 SERIVCE PORT : getFeatureBytes
    public byte[] getFeatureBytes(String md5){
        return service.getFeatureBytes(md5);
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5 md5列表
     * @return {@link FeatureBean}列表
     */
    // 35 SERIVCE PORT : getFeatures
    public List<FeatureBean> getFeatures(List<String> md5){
        return converterFeatureBean.fromRight(service.getFeatures(md5));
    }
    /**
     * 获取人员组通行权限<br>
     * 返回{@code personGroupId}指定的人员组在{@code deviceId}设备上是否允许通行
     * @param deviceId
     * @param personGroupId
     * @return 
     */
    // 36 SERIVCE PORT : getGroupPermit
    public boolean getGroupPermit(
            int deviceId,
            int personGroupId){
        return service.getGroupPermit(
                    deviceId,
                    personGroupId);
    }
    /**
     * 参见 {@link #getGroupPermit(Integer, Integer) }
     */
    // 37 SERIVCE PORT : getGroupPermits
    public List<Boolean> getGroupPermits(
            int deviceId,
            List<Integer> personGroupIdList){
        return service.getGroupPermits(
                    deviceId,
                    personGroupIdList);
    }
    /**
     * 根据图像的MD5校验码返回图像记录
     * @param imageMD5
     * @return {@link ImageBean} ,如果没有对应记录则返回null
     */
    // 38 SERIVCE PORT : getImage
    public ImageBean getImage(String imageMD5){
        return converterImageBean.fromRight(service.getImage(imageMD5));
    }
    /**
     * 根据图像的MD5校验码返回图像数据
     * @param imageMD5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     * @see {@link #getBinary(String)}
     */
    // 39 SERIVCE PORT : getImageBytes
    public byte[] getImageBytes(String imageMD5){
        return service.getImageBytes(imageMD5);
    }
    /**
     * 返回featureMd5的人脸特征记录关联的所有图像记录id(MD5)
     * @param featureMd5 人脸特征id(MD5)
     * @return 
     */
    // 40 SERIVCE PORT : getImagesAssociatedByFeature
    public List<String> getImagesAssociatedByFeature(String featureMd5){
        return service.getImagesAssociatedByFeature(featureMd5);
    }
    /**
     * 返回 persionId 关联的所有日志记录
     * @param personId fl_person.id
     * @return 
     */
    // 41 SERIVCE PORT : getLogBeansByPersonId
    public List<LogBean> getLogBeansByPersonId(int personId){
        return converterLogBean.fromRight(service.getLogBeansByPersonId(personId));
    }
    /**
     * 返回personId指定的人员记录
     * @param personId
     * @return 
     */
    // 42 SERIVCE PORT : getPerson
    public PersonBean getPerson(int personId){
        return converterPersonBean.fromRight(service.getPerson(personId));
    }
    /**
     * 根据证件号码返回人员记录
     * @param papersNum
     * @return 
     */
    // 43 SERIVCE PORT : getPersonByPapersNum
    public PersonBean getPersonByPapersNum(String papersNum){
        return converterPersonBean.fromRight(service.getPersonByPapersNum(papersNum));
    }
    /**
     * 根据人员组id返回数据库记录
     * @param personGroupId
     * @return 
     */
    // 44 SERIVCE PORT : getPersonGroup
    public PersonGroupBean getPersonGroup(int personGroupId){
        return converterPersonGroupBean.fromRight(service.getPersonGroup(personGroupId));
    }
    /**
     * 返回人员组id列表指定的数据库记录
     * @param groupIdList
     * @return 
     */
    // 45 SERIVCE PORT : getPersonGroups
    public List<PersonGroupBean> getPersonGroups(List<Integer> groupIdList){
        return converterPersonGroupBean.fromRight(service.getPersonGroups(groupIdList));
    }
    /**
     * 获取人员通行权限<br>
     * 返回{@code personId}指定的人员在{@code deviceId}设备上是否允许通行
     * @param deviceId
     * @param personId
     * @return 
     */
    // 46 SERIVCE PORT : getPersonPermit
    public boolean getPersonPermit(
            int deviceId,
            int personId){
        return service.getPersonPermit(
                    deviceId,
                    personId);
    }
    /**
     * 参见 {@link #getPersonPermit(Integer, Integer) }
     */
    // 47 SERIVCE PORT : getPersonPermits
    public List<Boolean> getPersonPermits(
            int deviceId,
            List<Integer> personIdList){
        return service.getPersonPermits(
                    deviceId,
                    personIdList);
    }
    /**
     * 返回 list 指定的人员记录
     * @param idList 人员id列表
     * @return 
     */
    // 48 SERIVCE PORT : getPersons
    public List<PersonBean> getPersons(List<Integer> idList){
        return converterPersonBean.fromRight(service.getPersons(idList));
    }
    /**
     * 返回{@code deviceGroupId}指定的人员组下属的所有人员记录<br>
     * 如果没有下属人员记录则返回空表
     * @param deviceGroupId
     * @return 
     */
    // 49 SERIVCE PORT : getPersonsOfGroup
    public List<PersonBean> getPersonsOfGroup(int personGroupId){
        return converterPersonBean.fromRight(service.getPersonsOfGroup(personGroupId));
    }
    /**
     * 返回{@code deviceGroupId}指定的设备组下的所有子节点<br>
     * 如果没有子节点则返回空表
     * @param deviceGroupId
     * @return 
     */
    // 50 SERIVCE PORT : getSubDeviceGroup
    public List<DeviceGroupBean> getSubDeviceGroup(int deviceGroupId){
        return converterDeviceGroupBean.fromRight(service.getSubDeviceGroup(deviceGroupId));
    }
    /**
     * 返回{@code personGroupId}指定的人员组下的所有子节点<br>
     * 如果没有子节点则返回空表
     * @param personGroupId
     * @return 
     */
    // 51 SERIVCE PORT : getSubPersonGroup
    public List<PersonGroupBean> getSubPersonGroup(int personGroupId){
        return converterPersonGroupBean.fromRight(service.getSubPersonGroup(personGroupId));
    }
    /**
     * 判断 personId 指定的人员记录是否过期
     * @param personId
     * @return 
     */
    // 52 SERIVCE PORT : isDisable
    public boolean isDisable(int personId){
        return service.isDisable(personId);
    }
    /**
     * 返回所有人员记录
     * @return 
     */
    // 53 SERIVCE PORT : loadAllPerson
    public List<Integer> loadAllPerson(){
        return service.loadAllPerson();
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_feature.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_feature记录
     * @param timestamp
     * @return 返回 fl_feature.md5 列表
     */
    // 54 SERIVCE PORT : loadFeatureMd5ByUpdate
    public List<String> loadFeatureMd5ByUpdate(Date timestamp){
        return service.loadFeatureMd5ByUpdate(GenericUtils.toLong(timestamp,Date.class));
    }
    /**
     * 日志查询<br>
     * 根据{@code where}指定的查询条件查询日志记录
     * @param where
     * @param startRow 记录起始行号 (first row = 1, last row = -1)
     * @param numRows 返回记录条数 为负值是返回{@code startRow}开始的所有行
     * @return 
     */
    // 55 SERIVCE PORT : loadLogByWhere
    public List<LogBean> loadLogByWhere(
            String where,
            int startRow,
            int numRows){
        return converterLogBean.fromRight(service.loadLogByWhere(
                    where,
                    startRow,
                    numRows));
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_log_light.verify_time 字段大于指定时间戳({@code timestamp})的所有记录
     * @see #loadLogLightByWhere(String,int,int)
     */
    // 56 SERIVCE PORT : loadLogLightByVerifyTime
    public List<LogLightBean> loadLogLightByVerifyTime(
            Date timestamp,
            int startRow,
            int numRows){
        return converterLogLightBean.fromRight(service.loadLogLightByVerifyTime(
                    GenericUtils.toLong(timestamp,Date.class),
                    startRow,
                    numRows));
    }
    /**
     * 日志查询<br>
     * 根据{@code where}指定的查询条件查询日志记录{@link LogLightBean}
     * @param where
     * @param startRow
     * @param numRows
     * @return 
     */
    // 57 SERIVCE PORT : loadLogLightByWhere
    public List<LogLightBean> loadLogLightByWhere(
            String where,
            int startRow,
            int numRows){
        return converterLogLightBean.fromRight(service.loadLogLightByWhere(
                    where,
                    startRow,
                    numRows));
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_permit.create_time 字段大于指定时间戳( {@code timestamp} )的所有fl_permit记录
     * @param timestamp
     * @return 
     */
    // 58 SERIVCE PORT : loadPermitByUpdate
    public List<PermitBean> loadPermitByUpdate(Date timestamp){
        return converterPermitBean.fromRight(service.loadPermitByUpdate(GenericUtils.toLong(timestamp,Date.class)));
    }
    /**
     * 返回 where 指定的所有人员记录
     * @param where SQL条件语句
     * @return 返回 fl_person.id 列表
     */
    // 59 SERIVCE PORT : loadPersonByWhere
    public List<Integer> loadPersonByWhere(String where){
        return service.loadPersonByWhere(where);
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_person.update_time 字段大于指定时间戳( {@code timestamp} )的所有fl_person记录
     * @param timestamp
     * @return 返回fl_person.id 列表
     */
    // 60 SERIVCE PORT : loadPersonIdByUpdateTime
    public List<Integer> loadPersonIdByUpdateTime(Date timestamp){
        return service.loadPersonIdByUpdateTime(GenericUtils.toLong(timestamp,Date.class));
    }
    /**
     * (主动更新机制实现)<br>
     * 返回fl_person.update_time字段大于指定时间戳( {@code timestamp} )的所有fl_person记录<br>
     * 同时包含fl_feature更新记录引用的fl_person记录
     * @param timestamp
     * @return 返回fl_person.id 列表
     */
    // 61 SERIVCE PORT : loadUpdatedPersons
    public List<Integer> loadUpdatedPersons(Date timestamp){
        return service.loadUpdatedPersons(GenericUtils.toLong(timestamp,Date.class));
    }
    /**
     * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
     * @param personId 人员记录id
     * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
     * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
     */
    // 62 SERIVCE PORT : replaceFeature
    public void replaceFeature(
            int personId,
            String featureMd5,
            boolean deleteOldFeatureImage){
        service.replaceFeature(
                    personId,
                    featureMd5,
                    deleteOldFeatureImage);
    }
    /**
     * 保存设备记录
     * @param deviceBean
     * @return 
     */
    // 63 SERIVCE PORT : saveDevice
    public DeviceBean saveDevice(DeviceBean deviceBean){
        return converterDeviceBean.fromRight(service.saveDevice(converterDeviceBean.toRight(deviceBean)));
    }
    /**
     * 保存设备组记录
     * @param deviceGroupBean
     * @return 
     */
    // 64 SERIVCE PORT : saveDeviceGroup
    public DeviceGroupBean saveDeviceGroup(DeviceGroupBean deviceGroupBean){
        return converterDeviceGroupBean.fromRight(service.saveDeviceGroup(converterDeviceGroupBean.toRight(deviceGroupBean)));
    }
    /**
     * 保存人员(person)记录
     * @param bean
     * @return 
     */
    // 65 SERIVCE PORT : savePerson
    public PersonBean savePerson(PersonBean bean){
        return converterPersonBean.fromRight(service.savePerson(converterPersonBean.toRight(bean)));
    }
    /**
     * @param bean 人员信息对象
     * @param idPhoto 标准照图像
     * @param feature 人脸特征数据
     * @param featureImage 提取特征源图像,为null 时,默认使用idPhoto
     * @param featureFaceBean 人脸位置对象,为null 时,不保存人脸数据
     * @param deviceBean featureImage来源设备对象
     * @return 
     */
    // 66 SERIVCE PORT : savePersonFull
    public PersonBean savePerson(
            PersonBean bean,
            byte[] idPhoto,
            byte[] feature,
            byte[] featureImage,
            FaceBean featureFaceBean,
            int deviceId){
        return converterPersonBean.fromRight(service.savePersonFull(
                    converterPersonBean.toRight(bean),
                    idPhoto,
                    feature,
                    featureImage,
                    converterFaceBean.toRight(featureFaceBean),
                    deviceId));
    }
    /** 
     * Generic version of {@link #savePerson(PersonBean,byte[],byte[],byte[],FaceBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 66 GENERIC
    public PersonBean savePersonGeneric(
            PersonBean bean,
            Object idPhoto,
            Object feature,
            Object featureImage,
            FaceBean featureFaceBean,
            int deviceId){
        return converterPersonBean.fromRight(service.savePersonFull(
                    converterPersonBean.toRight(bean),
                    GenericUtils.toBytes(idPhoto),
                    GenericUtils.toBytes(feature),
                    GenericUtils.toBytes(featureImage),
                    converterFaceBean.toRight(featureFaceBean),
                    deviceId));
    }
    /**
     * 保存人员组记录
     * @param personGroupBean
     * @return 
     */
    // 67 SERIVCE PORT : savePersonGroup
    public PersonGroupBean savePersonGroup(PersonGroupBean personGroupBean){
        return converterPersonGroupBean.fromRight(service.savePersonGroup(converterPersonGroupBean.toRight(personGroupBean)));
    }
    /**
     * 保存人员(person)记录
     * @param beans
     */
    // 68 SERIVCE PORT : savePersons
    public void savePersons(List<PersonBean> beans){
        service.savePersons(converterPersonBean.toRight(beans));
    }
    /**
     * 保存人员信息记录(包含标准照)
     * @param persons
     * @return 
     */
    // 69 SERIVCE PORT : savePersonsWithPhoto
    public int savePerson(Map<ByteBuffer, PersonBean> persons){
        return service.savePersonsWithPhoto(GenericUtils.toBytesKey(converterPersonBean.toRightValue(persons)));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像对象,可为null
     * @return 
     */
    // 70 SERIVCE PORT : savePersonWithPhoto
    public PersonBean savePerson(
            PersonBean bean,
            byte[] idPhoto){
        return converterPersonBean.fromRight(service.savePersonWithPhoto(
                    converterPersonBean.toRight(bean),
                    idPhoto));
    }
    /** 
     * Generic version of {@link #savePerson(PersonBean,byte[])}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 70 GENERIC
    public PersonBean savePersonGeneric(
            PersonBean bean,
            Object idPhoto){
        return converterPersonBean.fromRight(service.savePersonWithPhoto(
                    converterPersonBean.toRight(bean),
                    GenericUtils.toBytes(idPhoto)));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像,可为null
     * @param featureBean 用于验证的人脸特征数据对象,可为null
     * @param deviceId 标准照图像来源设备id,可为null
     * @return 
     */
    // 71 SERIVCE PORT : savePersonWithPhotoAndFeature
    public PersonBean savePerson(
            PersonBean bean,
            byte[] idPhoto,
            FeatureBean featureBean,
            int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeature(
                    converterPersonBean.toRight(bean),
                    idPhoto,
                    converterFeatureBean.toRight(featureBean),
                    deviceId));
    }
    /** 
     * Generic version of {@link #savePerson(PersonBean,byte[],FeatureBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 71 GENERIC
    public PersonBean savePersonGeneric(
            PersonBean bean,
            Object idPhoto,
            FeatureBean featureBean,
            int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeature(
                    converterPersonBean.toRight(bean),
                    GenericUtils.toBytes(idPhoto),
                    converterFeatureBean.toRight(featureBean),
                    deviceId));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像,可为null
     * @param feature 用于验证的人脸特征数据,可为null,不可重复, 参见 {@link #addFeature(ByteBuffer, Integer, List)}
     * @param faceBeans 参见 {@link #addFeature(ByteBuffer, Integer, List)}
     * @return 
     */
    // 72 SERIVCE PORT : savePersonWithPhotoAndFeatureMultiFaces
    public PersonBean savePerson(
            PersonBean bean,
            byte[] idPhoto,
            byte[] feature,
            List<FaceBean> faceBeans){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiFaces(
                    converterPersonBean.toRight(bean),
                    idPhoto,
                    feature,
                    converterFaceBean.toRight(faceBeans)));
    }
    /** 
     * Generic version of {@link #savePerson(PersonBean,byte[],byte[],List)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 72 GENERIC
    public PersonBean savePersonGeneric(
            PersonBean bean,
            Object idPhoto,
            Object feature,
            List<FaceBean> faceBeans){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiFaces(
                    converterPersonBean.toRight(bean),
                    GenericUtils.toBytes(idPhoto),
                    GenericUtils.toBytes(feature),
                    converterFaceBean.toRight(faceBeans)));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像,可为null
     * @param feature 用于验证的人脸特征数据,可为null
     * @param faceInfo 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
     * @param deviceId faceInfo 图像来源设备id,可为null
     * @return bean 保存的{@link PersonBean}对象
     */
    // 73 SERIVCE PORT : savePersonWithPhotoAndFeatureMultiImage
    public PersonBean savePerson(
            PersonBean bean,
            byte[] idPhoto,
            byte[] feature,
            Map<ByteBuffer, FaceBean> faceInfo,
            int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiImage(
                    converterPersonBean.toRight(bean),
                    idPhoto,
                    feature,
                    GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                    deviceId));
    }
    /** 
     * Generic version of {@link #savePerson(PersonBean,byte[],byte[],Map,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 73 GENERIC
    public PersonBean savePersonGeneric(
            PersonBean bean,
            Object idPhoto,
            Object feature,
            Map<ByteBuffer, FaceBean> faceInfo,
            int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiImage(
                    converterPersonBean.toRight(bean),
                    GenericUtils.toBytes(idPhoto),
                    GenericUtils.toBytes(feature),
                    GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                    deviceId));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhotoMd5 标准照图像对象,可为null
     * @param featureMd5 用于验证的人脸特征数据对象,可为null
     * @return 
     */
    // 74 SERIVCE PORT : savePersonWithPhotoAndFeatureSaved
    public PersonBean savePerson(
            PersonBean bean,
            String idPhotoMd5,
            String featureMd5){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureSaved(
                    converterPersonBean.toRight(bean),
                    idPhotoMd5,
                    featureMd5));
    }
    /**
     * 修改 personId 指定的人员记录的有效期
     * @param personId
     * @param expiryDate 失效日期
     */
    // 75 SERIVCE PORT : setPersonExpiryDate
    public void setPersonExpiryDate(
            int personId,
            Date expiryDate){
        service.setPersonExpiryDate(
                    personId,
                    GenericUtils.toLong(expiryDate,Date.class));
    }
    /**
     * 修改 personIdList 指定的人员记录的有效期
     * @param personIdList 人员id列表
     * @param expiryDate 失效日期
     */
    // 76 SERIVCE PORT : setPersonExpiryDateList
    public void setPersonExpiryDate(
            List<Integer> personIdList,
            Date expiryDate){
        service.setPersonExpiryDateList(
                    personIdList,
                    GenericUtils.toLong(expiryDate,Date.class));
    }
}
