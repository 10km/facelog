// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: service.client.java.vm
// ______________________________________________________

package net.gdface.facelog.client;
import com.facebook.nifty.client.FramedClientConnector;
import com.facebook.swift.service.ThriftClientManager;
import static com.google.common.net.HostAndPort.fromParts;

import java.nio.ByteBuffer;
import java.util.*;
import java.util.Map.Entry;

/**
 * 定义 FaceLog 服务接口<br>
 * 由于Java语言的限制,导致swift无法从interface中获取参数名信息，所以采用interface定义生成的thrift IDL文件中service中的方法
 * 无法生成正确的参数名称(只能是无意义的arg0,arg1...)<br>
 * 所以这里采用抽象类来定义服务接口,如果抽象类中的方法是抽象的，也无法获取参数名，所以这里所有方法都有一个空的函数体。
 * @author guyadong
 */
public class IFaceLogClient implements Constant{
    private final ThriftClientManager clientManager = new ThriftClientManager();
    private final net.gdface.facelog.client.thrift.IFaceLog service;
    public IFaceLogClient(String host,int port){
        try{
            service = clientManager.createClient(
                    new FramedClientConnector(fromParts(host, port)),
                    net.gdface.facelog.client.thrift.IFaceLog.class).get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
    protected static final byte[] toBytes(ByteBuffer buffer){
        if(null == buffer)return null;
        int pos = buffer.position();
        try{
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            return bytes;
        }finally{
            buffer.position(pos);
        }
    }
    protected static final<V>Map<byte[],V> toBytesKey(java.util.Map<ByteBuffer,V> source){
        if(null == source)return null;
        HashMap<byte[], V> dest = new java.util.HashMap<byte[],V>();
        for(Entry<ByteBuffer, V> entry:source.entrySet()){
            dest.put(toBytes(entry.getKey()), entry.getValue());
        }
        return dest;
    }
    protected static final<K> java.util.Map<K,byte[]> toBytesValue(java.util.Map<K,ByteBuffer> source){
        if(null == source)return null;
        HashMap<K,byte[]> dest = new java.util.HashMap<K,byte[]>();
        for(Entry<K, ByteBuffer> entry:source.entrySet()){
            dest.put(entry.getKey(),toBytes(entry.getValue()));
        }
        return dest;        
    }
    /**
     * 增加一个人脸特征记录，如果记录已经存在则抛出异常
     * @param feature 特征数据
     * @param personId 关联的人员id(fl_person.id),可为null
     * @param faecBeans 生成特征数据的人脸信息对象(可以是多个人脸对象合成一个特征),可为null
     * @return 保存的人脸特征记录{@link FeatureBean}
     */
    public FeatureBean addFeature(byte[] feature,int personId,List<FaceBean> faecBeans){
        return ThriftConverter.converterFeatureBean.fromRight(service.addFeature(feature,
                personId,
                ThriftConverter.converterFaceBean.toRight(faecBeans)));
    }
    /**
     * 增加一个人脸特征记录,特征数据由faceInfo指定的多张图像合成，如果记录已经存在则抛出异常
     * @param feature 特征数据
     * @param personId 关联的人员id(fl_person.id),可为null
     * @param faceInfo 生成特征数据的图像及人脸信息对象(每张图对应一张人脸),可为null
     * @param deviceId 图像来源设备id,可为null
     * @return 保存的人脸特征记录{@link FeatureBean}
     */
    public FeatureBean addFeature(byte[] feature,int personId,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return ThriftConverter.converterFeatureBean.fromRight(service.addFeatureMulti(feature,
                personId,
                toBytesKey(ThriftConverter.converterFaceBean.toRightValue(faceInfo)),
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
    public ImageBean addImage(byte[] imageData,int deviceId,FaceBean faceBean,int personId){
        return ThriftConverter.converterImageBean.fromRight(service.addImage(imageData,
                deviceId,
                ThriftConverter.converterFaceBean.toRight(faceBean),
                personId));
    }
    /**
     * 添加一条验证日志记录
     * @param bean
     */
    public void addLog(LogBean bean){
        service.addLog(ThriftConverter.converterLogBean.toRight(bean));
    }
    /**
     * 添加一组验证日志记录(事务存储)
     * @param beans
     */
    public void addLog(List<LogBean> beans){
        service.addLogList(ThriftConverter.converterLogBean.toRight(beans));
    }

    public int countLogLightWhere(String where){
        return service.countLogLightWhere(where);
    }

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
    public List<String> deleteFeature(String featureMd5,boolean deleteImage){
        return service.deleteFeature(featureMd5,
                deleteImage);
    }
    /**
     * 删除imageMd5指定图像及其缩略图
     * @param imageMd5
     * @return 
     */
    public int deleteImage(String imageMd5){
        return service.deleteImage(imageMd5);
    }
    /**
     * 删除personId指定的人员(person)记录及关联的所有记录
     * @param personId
     * @return 
     */
    public int deletePerson(int personId){
        return service.deletePerson(personId);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNum 证件号码
     * @return 返回删除的 person 记录数量
     * @see {@link #deletePerson(int)}
     */
    public int deletePersonByPapersNum(String papersNum){
        return service.deletePersonByPapersNum(papersNum);
    }
    /**
     * 删除personIdList指定的人员(person)记录及关联的所有记录
     * @param personIdList 人员id列表
     * @return 返回删除的 person 记录数量
     */
    public int deletePersons(List<Integer> personIdList){
        return service.deletePersons(personIdList);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNumlist 证件号码列表
     * @return 返回删除的 person 记录数量
     */
    public int deletePersonsByPapersNum(List<String> papersNumlist){
        return service.deletePersonsByPapersNum(papersNumlist);
    }
    /**
     * 设置 personId 指定的人员为禁止状态
     * @param personId
     * @see #setPersonExpiryDate(int, long)
     */
    public void disablePerson(int personId){
        service.disablePerson(personId);
    }
    /**
     * 设置 personIdList 指定的人员为禁止状态
     * @param personIdList 人员id列表
     */
    public void disablePerson(List<Integer> personIdList){
        service.disablePersonList(personIdList);
    }
    /**
     * 判断id指定的设备记录是否存在
     * @param id
     * @return 
     */
    public boolean existsDevice(int id){
        return service.existsDevice(id);
    }
    /**
     * 判断md5指定的特征记录是否存在
     * @param md5
     * @return 
     */
    public boolean existsFeature(String md5){
        return service.existsFeature(md5);
    }
    /**
     * 判断md5指定的图像记录是否存在
     * @param md5
     * @return 
     */
    public boolean existsImage(String md5){
        return service.existsImage(md5);
    }
    /**
     * 判断是否存在personId指定的人员记录
     * @param persionId
     * @return 
     */
    public boolean existsPerson(int persionId){
        return service.existsPerson(persionId);
    }

    public DeviceBean getDevice(int deviceId){
        return ThriftConverter.converterDeviceBean.fromRight(service.getDevice(deviceId));
    }

    public List<DeviceBean> getDevice(List<Integer> deviceId){
        return ThriftConverter.converterDeviceBean.fromRight(service.getDeviceList(deviceId));
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5
     * @return 如果数据库中没有对应的数据则返回null
     */
    public FeatureBean getFeature(String md5){
        return ThriftConverter.converterFeatureBean.fromRight(service.getFeature(md5));
    }
    /**
     * 返回 persionId 关联的所有人脸特征记录
     * @param personId fl_person.id
     * @return 返回 fl_feature.md5  列表
     */
    public List<String> getFeatureBeansByPersonId(int personId){
        return service.getFeatureBeansByPersonId(personId);
    }
    /**
     * 根据MD5校验码返回人脸特征数据
     * @param md5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     */
    public byte[] getFeatureBytes(String md5){
        return service.getFeatureBytes(md5);
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5 md5列表
     * @return {@link FeatureBean}列表
     */
    public List<FeatureBean> getFeature(List<String> md5){
        return ThriftConverter.converterFeatureBean.fromRight(service.getFeatureList(md5));
    }
    /**
     * 根据图像的MD5校验码返回图像记录
     * @param imageMD5
     * @return {@link ImageBean} ,如果没有对应记录则返回null
     */
    public ImageBean getImage(String imageMD5){
        return ThriftConverter.converterImageBean.fromRight(service.getImage(imageMD5));
    }
    /**
     * 根据图像的MD5校验码返回图像数据
     * @param imageMD5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     * @see {@link #getBinary(String)}
     */
    public byte[] getImageBytes(String imageMD5){
        return service.getImageBytes(imageMD5);
    }
    /**
     * 返回featureMd5的人脸特征记录关联的所有图像记录id(MD5)
     * @param featureMd5 人脸特征id(MD5)
     * @return 
     */
    public List<String> getImagesAssociatedByFeature(String featureMd5){
        return service.getImagesAssociatedByFeature(featureMd5);
    }
    /**
     * 返回 persionId 关联的所有日志记录
     * @param personId fl_person.id
     * @return 
     */
    public List<LogBean> getLogBeansByPersonId(int personId){
        return ThriftConverter.converterLogBean.fromRight(service.getLogBeansByPersonId(personId));
    }
    /**
     * 返回personId指定的人员记录
     * @param personId
     * @return 
     */
    public PersonBean getPerson(int personId){
        return ThriftConverter.converterPersonBean.fromRight(service.getPerson(personId));
    }
    /**
     * 根据证件号码返回人员记录
     * @param papersNum
     * @return 
     */
    public PersonBean getPersonByPapersNum(String papersNum){
        return ThriftConverter.converterPersonBean.fromRight(service.getPersonByPapersNum(papersNum));
    }
    /**
     * 返回 list 指定的人员记录
     * @param idList 人员id列表
     * @return 
     */
    public List<PersonBean> getPersons(List<Integer> idList){
        return ThriftConverter.converterPersonBean.fromRight(service.getPersons(idList));
    }
    /**
     * 判断 personId 指定的人员记录是否过期
     * @param personId
     * @return 
     */
    public boolean isDisable(int personId){
        return service.isDisable(personId);
    }
    /**
     * 返回所有人员记录
     * @return 
     */
    public List<Integer> loadAllPerson(){
        return service.loadAllPerson();
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_feature.update_time 字段大于指定时间戳( timestamp )的所有fl_feature记录
     * @param timestamp
     * @return 返回 fl_feature.md5 列表
     */
    public List<String> loadFeatureMd5ByUpdate(long timestamp){
        return service.loadFeatureMd5ByUpdate(timestamp);
    }

    public List<LogBean> loadLogByWhere(String where,int startRow,int numRows){
        return ThriftConverter.converterLogBean.fromRight(service.loadLogByWhere(where,
                startRow,
                numRows));
    }

    public List<LogLightBean> loadLogLightByWhere(String where,int startRow,int numRows){
        return ThriftConverter.converterLogLightBean.fromRight(service.loadLogLightByWhere(where,
                startRow,
                numRows));
    }
    /**
     * 返回 where 指定的所有人员记录
     * @param where SQL条件语句
     * @return 返回 fl_person.id 列表
     */
    public List<Integer> loadPersonByWhere(String where){
        return service.loadPersonByWhere(where);
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_person.update_time 字段大于指定时间戳( timestamp )的所有fl_person记录
     * @param timestamp
     * @return 返回fl_person.id 列表
     */
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
    public List<Integer> loadUpdatePersons(long timestamp){
        return service.loadUpdatePersons(timestamp);
    }
    /**
     * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
     * @param personId 人员记录id
     * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
     * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
     */
    public void replaceFeature(int personId,String featureMd5,boolean deleteOldFeatureImage){
        service.replaceFeature(personId,
                featureMd5,
                deleteOldFeatureImage);
    }

    public DeviceBean saveDevice(DeviceBean deviceBean){
        return ThriftConverter.converterDeviceBean.fromRight(service.saveDevice(ThriftConverter.converterDeviceBean.toRight(deviceBean)));
    }
    /**
     * 保存人员(person)记录
     * @param bean
     * @return 
     */
    public PersonBean savePerson(PersonBean bean){
        return ThriftConverter.converterPersonBean.fromRight(service.savePerson(ThriftConverter.converterPersonBean.toRight(bean)));
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
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,byte[] featureImage,FaceBean featureFaceBean,int deviceId){
        return ThriftConverter.converterPersonBean.fromRight(service.savePersonFull(ThriftConverter.converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                featureImage,
                ThriftConverter.converterFaceBean.toRight(featureFaceBean),
                deviceId));
    }
    /**
     * 保存人员(person)记录
     * @param beans
     */
    public void savePerson(List<PersonBean> beans){
        service.savePersonList(ThriftConverter.converterPersonBean.toRight(beans));
    }
    /**
     * 保存人员信息记录(包含标准照)
     * @param persons
     * @return 
     */
    public int savePerson(Map<ByteBuffer, PersonBean> persons){
        return service.savePersonsWithPhoto(toBytesKey(ThriftConverter.converterPersonBean.toRightValue(persons)));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像对象,可为null
     * @return 
     */
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto){
        return ThriftConverter.converterPersonBean.fromRight(service.savePersonWithPhoto(ThriftConverter.converterPersonBean.toRight(bean),
                idPhoto));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像,可为null
     * @param featureBean 用于验证的人脸特征数据对象,可为null
     * @param deviceId 标准照图像来源设备id,可为null
     * @return 
     */
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,FeatureBean featureBean,int deviceId){
        return ThriftConverter.converterPersonBean.fromRight(service.savePersonWithPhotoAndFeature(ThriftConverter.converterPersonBean.toRight(bean),
                idPhoto,
                ThriftConverter.converterFeatureBean.toRight(featureBean),
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
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,List<FaceBean> faceBeans){
        return ThriftConverter.converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiFaces(ThriftConverter.converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                ThriftConverter.converterFaceBean.toRight(faceBeans)));
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
    public PersonBean savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return ThriftConverter.converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureMultiImage(ThriftConverter.converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                toBytesKey(ThriftConverter.converterFaceBean.toRightValue(faceInfo)),
                deviceId));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhotoMd5 标准照图像对象,可为null
     * @param featureMd5 用于验证的人脸特征数据对象,可为null
     * @return 
     */
    public PersonBean savePerson(PersonBean bean,String idPhotoMd5,String featureMd5){
        return ThriftConverter.converterPersonBean.fromRight(service.savePersonWithPhotoAndFeatureSaved(ThriftConverter.converterPersonBean.toRight(bean),
                idPhotoMd5,
                featureMd5));
    }
    /**
     * 修改 personId 指定的人员记录的有效期
     * @param personId
     * @param expiryDate 失效日期
     */
    public void setPersonExpiryDate(int personId,long expiryDate){
        service.setPersonExpiryDate(personId,
                expiryDate);
    }
    /**
     * 修改 personIdList 指定的人员记录的有效期
     * @param personIdList 人员id列表
     * @param expiryDate 失效日期
     */
    public void setPersonExpiryDate(List<Integer> personIdList,long expiryDate){
        service.setPersonExpiryDateList(personIdList,
                expiryDate);
    }
}
