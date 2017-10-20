// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: service.client.java.vm
// ______________________________________________________
package net.gdface.facelog.client;
import static com.google.common.base.Preconditions.checkNotNull;
import java.nio.ByteBuffer;
import java.util.*;
/**
 * remote implementation of the service IFaceLog<br>
 * all method comments be copied from {@code net.gdface.facelog.FaceLogDefinition.java}<br>
 * <b>NOTE:</b>methods with 'Generic' suffix support generic type argument for {@code byte[]}.<br>
 * @author guyadong
 */
class IFaceLogClient implements Constant{
    
    /** bean converter between {@link DeviceBean} and corresponding thrift bean */
    private IBeanConverter<DeviceBean,net.gdface.facelog.client.thrift.DeviceBean> converterDeviceBean = ThriftConverter.converterDeviceBean;
    /** bean converter between {@link FaceBean} and corresponding thrift bean */
    private IBeanConverter<FaceBean,net.gdface.facelog.client.thrift.FaceBean> converterFaceBean = ThriftConverter.converterFaceBean;
    /** bean converter between {@link FeatureBean} and corresponding thrift bean */
    private IBeanConverter<FeatureBean,net.gdface.facelog.client.thrift.FeatureBean> converterFeatureBean = ThriftConverter.converterFeatureBean;
    /** bean converter between {@link ImageBean} and corresponding thrift bean */
    private IBeanConverter<ImageBean,net.gdface.facelog.client.thrift.ImageBean> converterImageBean = ThriftConverter.converterImageBean;
    /** bean converter between {@link LogBean} and corresponding thrift bean */
    private IBeanConverter<LogBean,net.gdface.facelog.client.thrift.LogBean> converterLogBean = ThriftConverter.converterLogBean;
    /** bean converter between {@link PersonBean} and corresponding thrift bean */
    private IBeanConverter<PersonBean,net.gdface.facelog.client.thrift.PersonBean> converterPersonBean = ThriftConverter.converterPersonBean;
    /** bean converter between {@link LogLightBean} and corresponding thrift bean */
    private IBeanConverter<LogLightBean,net.gdface.facelog.client.thrift.LogLightBean> converterLogLightBean = ThriftConverter.converterLogLightBean;    private final net.gdface.facelog.client.thrift.IFaceLog service;
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
    public FeatureBean addFeature(byte[] feature,int personId,List<FaceBean> faecBeans){
        return converterFeatureBean.fromRight(service.addFeature(feature,
                personId,
                converterFaceBean.toRight(faecBeans)));
    }
    /** 
     * generic version of {@link #addFeature(byte[],int,List<FaceBean>)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 1 GENERIC
    public FeatureBean addFeatureGeneric(Object feature,int personId,List<FaceBean> faecBeans){
        return converterFeatureBean.fromRight(service.addFeature(GenericUtils.toBytes(feature),
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
    public FeatureBean addFeature(byte[] feature,int personId,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return converterFeatureBean.fromRight(service.addFeatureMulti(feature,
                personId,
                GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                deviceId));
    }
    /** 
     * generic version of {@link #addFeature(byte[],int,Map<ByteBuffer, FaceBean>,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 2 GENERIC
    public FeatureBean addFeatureGeneric(Object feature,int personId,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return converterFeatureBean.fromRight(service.addFeatureMulti(GenericUtils.toBytes(feature),
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
     * @see {@link #_addImage(ByteBuffer, DeviceBean, List, List)}
     */
    // 3 SERIVCE PORT : addImage
    public ImageBean addImage(byte[] imageData,int deviceId,FaceBean faceBean,int personId){
        return converterImageBean.fromRight(service.addImage(imageData,
                deviceId,
                converterFaceBean.toRight(faceBean),
                personId));
    }
    /** 
     * generic version of {@link #addImage(byte[],int,FaceBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 3 GENERIC
    public ImageBean addImageGeneric(Object imageData,int deviceId,FaceBean faceBean,int personId){
        return converterImageBean.fromRight(service.addImage(GenericUtils.toBytes(imageData),
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
    // 5 SERIVCE PORT : addLogList
    public void addLog(List<LogBean> beans){
        service.addLogList(converterLogBean.toRight(beans));
    }

    // 6 SERIVCE PORT : countLogLightWhere
    public int countLogLightWhere(String where){
        return service.countLogLightWhere(where);
    }

    // 7 SERIVCE PORT : countLogWhere
    public int countLogWhere(String where){
        return service.countLogWhere(where);
    }
    /**
     * 删除 personId 关联的所有特征(feature)记录
     * @param personId
     * @param deleteImage 是否删除关联的 image记录
     * @return 
     * @see #deleteFeature(String, boolean)
     */
    // 8 SERIVCE PORT : deleteAllFeaturesByPersonId
    public int deleteAllFeaturesByPersonId(int personId,boolean deleteImage){
        return service.deleteAllFeaturesByPersonId(personId,
                deleteImage);
    }
    /**
     * 删除featureMd5指定的特征记录及关联的face记录
     * @param featureMd5
     * @param deleteImage 是否删除关联的 image记录
     * @return 
     */
    // 9 SERIVCE PORT : deleteFeature
    public List<String> deleteFeature(String featureMd5,boolean deleteImage){
        return service.deleteFeature(featureMd5,
                deleteImage);
    }
    /**
     * 删除imageMd5指定图像及其缩略图
     * @param imageMd5
     * @return 
     */
    // 10 SERIVCE PORT : deleteImage
    public int deleteImage(String imageMd5){
        return service.deleteImage(imageMd5);
    }
    /**
     * 删除personId指定的人员(person)记录及关联的所有记录
     * @param personId
     * @return 
     */
    // 11 SERIVCE PORT : deletePerson
    public int deletePerson(int personId){
        return service.deletePerson(personId);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNum 证件号码
     * @return 返回删除的 person 记录数量
     * @see {@link #deletePerson(int)}
     */
    // 12 SERIVCE PORT : deletePersonByPapersNum
    public int deletePersonByPapersNum(String papersNum){
        return service.deletePersonByPapersNum(papersNum);
    }
    /**
     * 删除personIdList指定的人员(person)记录及关联的所有记录
     * @param personIdList 人员id列表
     * @return 返回删除的 person 记录数量
     */
    // 13 SERIVCE PORT : deletePersons
    public int deletePersons(List<Integer> personIdList){
        return service.deletePersons(personIdList);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNumlist 证件号码列表
     * @return 返回删除的 person 记录数量
     */
    // 14 SERIVCE PORT : deletePersonsByPapersNum
    public int deletePersonsByPapersNum(List<String> papersNumlist){
        return service.deletePersonsByPapersNum(papersNumlist);
    }
    /**
     * 设置 personId 指定的人员为禁止状态
     * @param personId
     * @see #setPersonExpiryDate(int, long)
     */
    // 15 SERIVCE PORT : disablePerson
    public void disablePerson(int personId){
        service.disablePerson(personId);
    }
    /**
     * 设置 personIdList 指定的人员为禁止状态
     * @param personIdList 人员id列表
     */
    // 16 SERIVCE PORT : disablePersonList
    public void disablePerson(List<Integer> personIdList){
        service.disablePersonList(personIdList);
    }
    /**
     * 判断id指定的设备记录是否存在
     * @param id
     * @return 
     */
    // 17 SERIVCE PORT : existsDevice
    public boolean existsDevice(int id){
        return service.existsDevice(id);
    }
    /**
     * 判断md5指定的特征记录是否存在
     * @param md5
     * @return 
     */
    // 18 SERIVCE PORT : existsFeature
    public boolean existsFeature(String md5){
        return service.existsFeature(md5);
    }
    /**
     * 判断md5指定的图像记录是否存在
     * @param md5
     * @return 
     */
    // 19 SERIVCE PORT : existsImage
    public boolean existsImage(String md5){
        return service.existsImage(md5);
    }
    /**
     * 判断是否存在personId指定的人员记录
     * @param persionId
     * @return 
     */
    // 20 SERIVCE PORT : existsPerson
    public boolean existsPerson(int persionId){
        return service.existsPerson(persionId);
    }

    // 21 SERIVCE PORT : getDevice
    public DeviceBean getDevice(int deviceId){
        return converterDeviceBean.fromRight(service.getDevice(deviceId));
    }

    // 22 SERIVCE PORT : getDeviceList
    public List<DeviceBean> getDevice(List<Integer> deviceId){
        return converterDeviceBean.fromRight(service.getDeviceList(deviceId));
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5
     * @return 如果数据库中没有对应的数据则返回null
     */
    // 23 SERIVCE PORT : getFeature
    public FeatureBean getFeature(String md5){
        return converterFeatureBean.fromRight(service.getFeature(md5));
    }
    /**
     * 返回 persionId 关联的所有人脸特征记录
     * @param personId fl_person.id
     * @return 返回 fl_feature.md5  列表
     */
    // 24 SERIVCE PORT : getFeatureBeansByPersonId
    public List<String> getFeatureBeansByPersonId(int personId){
        return service.getFeatureBeansByPersonId(personId);
    }
    /**
     * 根据MD5校验码返回人脸特征数据
     * @param md5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     */
    // 25 SERIVCE PORT : getFeatureBytes
    public byte[] getFeatureBytes(String md5){
        return service.getFeatureBytes(md5);
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5 md5列表
     * @return {@link FeatureBean}列表
     */
    // 26 SERIVCE PORT : getFeatureList
    public List<FeatureBean> getFeature(List<String> md5){
        return converterFeatureBean.fromRight(service.getFeatureList(md5));
    }
    /**
     * 根据图像的MD5校验码返回图像记录
     * @param imageMD5
     * @return {@link ImageBean} ,如果没有对应记录则返回null
     */
    // 27 SERIVCE PORT : getImage
    public ImageBean getImage(String imageMD5){
        return converterImageBean.fromRight(service.getImage(imageMD5));
    }
    /**
     * 根据图像的MD5校验码返回图像数据
     * @param imageMD5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     * @see {@link #getBinary(String)}
     */
    // 28 SERIVCE PORT : getImageBytes
    public byte[] getImageBytes(String imageMD5){
        return service.getImageBytes(imageMD5);
    }
    /**
     * 返回featureMd5的人脸特征记录关联的所有图像记录id(MD5)
     * @param featureMd5 人脸特征id(MD5)
     * @return 
     */
    // 29 SERIVCE PORT : getImagesAssociatedByFeature
    public List<String> getImagesAssociatedByFeature(String featureMd5){
        return service.getImagesAssociatedByFeature(featureMd5);
    }
    /**
     * 返回 persionId 关联的所有日志记录
     * @param personId fl_person.id
     * @return 
     */
    // 30 SERIVCE PORT : getLogBeansByPersonId
    public List<LogBean> getLogBeansByPersonId(int personId){
        return converterLogBean.fromRight(service.getLogBeansByPersonId(personId));
    }
    /**
     * 返回personId指定的人员记录
     * @param personId
     * @return 
     */
    // 31 SERIVCE PORT : getPerson
    public PersonBean getPerson(int personId){
        return converterPersonBean.fromRight(service.getPerson(personId));
    }
    /**
     * 根据证件号码返回人员记录
     * @param papersNum
     * @return 
     */
    // 32 SERIVCE PORT : getPersonByPapersNum
    public PersonBean getPersonByPapersNum(String papersNum){
        return converterPersonBean.fromRight(service.getPersonByPapersNum(papersNum));
    }
    /**
     * 返回 list 指定的人员记录
     * @param idList 人员id列表
     * @return 
     */
    // 33 SERIVCE PORT : getPersons
    public List<PersonBean> getPersons(List<Integer> idList){
        return converterPersonBean.fromRight(service.getPersons(idList));
    }
    /**
     * 判断 personId 指定的人员记录是否过期
     * @param personId
     * @return 
     */
    // 34 SERIVCE PORT : isDisable
    public boolean isDisable(int personId){
        return service.isDisable(personId);
    }
    /**
     * 返回所有人员记录
     * @return 
     */
    // 35 SERIVCE PORT : loadAllPerson
    public List<Integer> loadAllPerson(){
        return service.loadAllPerson();
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_feature.update_time 字段大于指定时间戳( timestamp )的所有fl_feature记录
     * @param timestamp
     * @return 返回 fl_feature.md5 列表
     */
    // 36 SERIVCE PORT : loadFeatureMd5ByUpdate
    public List<String> loadFeatureMd5ByUpdate(long timestamp){
        return service.loadFeatureMd5ByUpdate(timestamp);
    }

    // 37 SERIVCE PORT : loadLogByWhere
    public List<LogBean> loadLogByWhere(String where,int startRow,int numRows){
        return converterLogBean.fromRight(service.loadLogByWhere(where,
                startRow,
                numRows));
    }

    // 38 SERIVCE PORT : loadLogLightByWhere
    public List<LogLightBean> loadLogLightByWhere(String where,int startRow,int numRows){
        return converterLogLightBean.fromRight(service.loadLogLightByWhere(where,
                startRow,
                numRows));
    }
    /**
     * 返回 where 指定的所有人员记录
     * @param where SQL条件语句
     * @return 返回 fl_person.id 列表
     */
    // 39 SERIVCE PORT : loadPersonByWhere
    public List<Integer> loadPersonByWhere(String where){
        return service.loadPersonByWhere(where);
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_person.update_time 字段大于指定时间戳( timestamp )的所有fl_person记录
     * @param timestamp
     * @return 返回fl_person.id 列表
     */
    // 40 SERIVCE PORT : loadPersonIdByUpdate
    public List<Integer> loadPersonIdByUpdate(long timestamp){
        return service.loadPersonIdByUpdate(timestamp);
    }
    /**
     * (主动更新机制实现)<br>
     * 返回fl_person.update_time字段大于指定时间戳( timestamp )的所有fl_person记录<br>
     * 同时包含fl_feature更新记录引用的fl_person记录
     * @param timestamp
     * @return 返回fl_person.id 列表
     */
    // 41 SERIVCE PORT : loadUpdatePersons
    public List<Integer> loadUpdatePersons(long timestamp){
        return service.loadUpdatePersons(timestamp);
    }
    /**
     * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
     * @param personId 人员记录id
     * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
     * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
     */
    // 42 SERIVCE PORT : replaceFeature
    public void replaceFeature(int personId,String featureMd5,boolean deleteOldFeatureImage){
        service.replaceFeature(personId,
                featureMd5,
                deleteOldFeatureImage);
    }

    // 43 SERIVCE PORT : saveDevice
    public DeviceBean saveDevice(DeviceBean deviceBean){
        return converterDeviceBean.fromRight(service.saveDevice(converterDeviceBean.toRight(deviceBean)));
    }
    /**
     * 保存人员(person)记录
     * @param bean
     * @return 
     */
    // 44 SERIVCE PORT : savePerson
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
    // 45 SERIVCE PORT : savePersonFull
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,byte[] featureImage,FaceBean featureFaceBean,int deviceId){
        return converterPersonBean.fromRight(service.savePersonFull(converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                featureImage,
                converterFaceBean.toRight(featureFaceBean),
                deviceId));
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],byte[],byte[],FaceBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 45 GENERIC
    public PersonBean savePersonGeneric(PersonBean bean,Object idPhoto,Object feature,Object featureImage,FaceBean featureFaceBean,int deviceId){
        return converterPersonBean.fromRight(service.savePersonFull(converterPersonBean.toRight(bean),
                GenericUtils.toBytes(idPhoto),
                GenericUtils.toBytes(feature),
                GenericUtils.toBytes(featureImage),
                converterFaceBean.toRight(featureFaceBean),
                deviceId));
    }
    /**
     * 保存人员(person)记录
     * @param beans
     */
    // 46 SERIVCE PORT : savePersonList
    public void savePerson(List<PersonBean> beans){
        service.savePersonList(converterPersonBean.toRight(beans));
    }
    /**
     * 保存人员信息记录(包含标准照)
     * @param persons
     * @return 
     */
    // 47 SERIVCE PORT : savePersonsWithPhoto
    public int savePerson(Map<ByteBuffer, PersonBean> persons){
        return service.savePersonsWithPhoto(GenericUtils.toBytesKey(converterPersonBean.toRightValue(persons)));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像对象,可为null
     * @return 
     */
    // 48 SERIVCE PORT : savePersonWithPhoto
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto){
        return converterPersonBean.fromRight(service.savePersonWithPhoto(converterPersonBean.toRight(bean),
                idPhoto));
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[])}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 48 GENERIC
    public PersonBean savePersonGeneric(PersonBean bean,Object idPhoto){
        return converterPersonBean.fromRight(service.savePersonWithPhoto(converterPersonBean.toRight(bean),
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
    // 49 SERIVCE PORT : savePersonWithPhotoAndFeature
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,FeatureBean featureBean,int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeature(converterPersonBean.toRight(bean),
                idPhoto,
                converterFeatureBean.toRight(featureBean),
                deviceId));
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],FeatureBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 49 GENERIC
    public PersonBean savePersonGeneric(PersonBean bean,Object idPhoto,FeatureBean featureBean,int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeature(converterPersonBean.toRight(bean),
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
    // 50 SERIVCE PORT : savePersonWithPhotoAndFeatureMultiFaces
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,List<FaceBean> faceBeans){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiFaces(converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                converterFaceBean.toRight(faceBeans)));
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],byte[],List<FaceBean>)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 50 GENERIC
    public PersonBean savePersonGeneric(PersonBean bean,Object idPhoto,Object feature,List<FaceBean> faceBeans){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiFaces(converterPersonBean.toRight(bean),
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
    // 51 SERIVCE PORT : savePersonWithPhotoAndFeatureMultiImage
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiImage(converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                deviceId));
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],byte[],Map<ByteBuffer, FaceBean>,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 51 GENERIC
    public PersonBean savePersonGeneric(PersonBean bean,Object idPhoto,Object feature,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiImage(converterPersonBean.toRight(bean),
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
    // 52 SERIVCE PORT : savePersonWithPhotoAndFeatureSaved
    public PersonBean savePerson(PersonBean bean,String idPhotoMd5,String featureMd5){
        return converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureSaved(converterPersonBean.toRight(bean),
                idPhotoMd5,
                featureMd5));
    }
    /**
     * 修改 personId 指定的人员记录的有效期
     * @param personId
     * @param expiryDate 失效日期
     */
    // 53 SERIVCE PORT : setPersonExpiryDate
    public void setPersonExpiryDate(int personId,long expiryDate){
        service.setPersonExpiryDate(personId,
                expiryDate);
    }
    /**
     * 修改 personIdList 指定的人员记录的有效期
     * @param personIdList 人员id列表
     * @param expiryDate 失效日期
     */
    // 54 SERIVCE PORT : setPersonExpiryDateList
    public void setPersonExpiryDate(List<Integer> personIdList,long expiryDate){
        service.setPersonExpiryDateList(personIdList,
                expiryDate);
    }
}
