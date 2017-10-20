// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: service.client.async.java.vm
// ______________________________________________________
package net.gdface.facelog.client;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import static com.google.common.base.Preconditions.checkNotNull;
import java.nio.ByteBuffer;
import java.util.*;
/**
 * remote implementation of the service IFaceLog(asynchronous implementation)<br>
 * all method comments be copied from {@code net.gdface.facelog.FaceLogDefinition.java}<br>
 * <b>NOTE:</b>methods with 'Generic' suffix support generic type argument for {@code byte[]}.<br>
 * @author guyadong
 */
class IFaceLogClientAsync implements Constant{
    
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
    private IBeanConverter<LogLightBean,net.gdface.facelog.client.thrift.LogLightBean> converterLogLightBean = ThriftConverter.converterLogLightBean;    private final net.gdface.facelog.client.thrift.IFaceLog.Async service;
    /**
     * constructor 
     * @param service a instance of net.gdface.facelog.client.thrift.IFaceLog.Async created by Swift, must not be null
     */
    IFaceLogClientAsync(net.gdface.facelog.client.thrift.IFaceLog.Async service){
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
    public ListenableFuture<FeatureBean> addFeature(byte[] feature,int personId,List<FaceBean> faecBeans){
        return Futures.transform(
                service.addFeature(feature,
                personId,
                converterFaceBean.toRight(faecBeans)), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.FeatureBean,FeatureBean>(){
                    @Override
                    public FeatureBean apply(net.gdface.facelog.client.thrift.FeatureBean input) {
                        return converterFeatureBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #addFeature(byte[],int,List<FaceBean>)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 1 GENERIC
    public ListenableFuture<FeatureBean> addFeatureGeneric(Object feature,int personId,List<FaceBean> faecBeans){
        return Futures.transform(
                service.addFeature(GenericUtils.toBytes(feature),
                personId,
                converterFaceBean.toRight(faecBeans)), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.FeatureBean,FeatureBean>(){
                    @Override
                    public FeatureBean apply(net.gdface.facelog.client.thrift.FeatureBean input) {
                        return converterFeatureBean.fromRight(input);
                    }
                });
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
    public ListenableFuture<FeatureBean> addFeature(byte[] feature,int personId,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return Futures.transform(
                service.addFeatureMulti(feature,
                personId,
                GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.FeatureBean,FeatureBean>(){
                    @Override
                    public FeatureBean apply(net.gdface.facelog.client.thrift.FeatureBean input) {
                        return converterFeatureBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #addFeature(byte[],int,Map<ByteBuffer, FaceBean>,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 2 GENERIC
    public ListenableFuture<FeatureBean> addFeatureGeneric(Object feature,int personId,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return Futures.transform(
                service.addFeatureMulti(GenericUtils.toBytes(feature),
                personId,
                GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.FeatureBean,FeatureBean>(){
                    @Override
                    public FeatureBean apply(net.gdface.facelog.client.thrift.FeatureBean input) {
                        return converterFeatureBean.fromRight(input);
                    }
                });
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
    public ListenableFuture<ImageBean> addImage(byte[] imageData,int deviceId,FaceBean faceBean,int personId){
        return Futures.transform(
                service.addImage(imageData,
                deviceId,
                converterFaceBean.toRight(faceBean),
                personId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.ImageBean,ImageBean>(){
                    @Override
                    public ImageBean apply(net.gdface.facelog.client.thrift.ImageBean input) {
                        return converterImageBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #addImage(byte[],int,FaceBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 3 GENERIC
    public ListenableFuture<ImageBean> addImageGeneric(Object imageData,int deviceId,FaceBean faceBean,int personId){
        return Futures.transform(
                service.addImage(GenericUtils.toBytes(imageData),
                deviceId,
                converterFaceBean.toRight(faceBean),
                personId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.ImageBean,ImageBean>(){
                    @Override
                    public ImageBean apply(net.gdface.facelog.client.thrift.ImageBean input) {
                        return converterImageBean.fromRight(input);
                    }
                });
    }
    /**
     * 添加一条验证日志记录
     * @param bean
     */
    // 4 SERIVCE PORT : addLog
    public ListenableFuture<Void> addLog(LogBean bean){
        return service.addLog(converterLogBean.toRight(bean));
    }
    /**
     * 添加一组验证日志记录(事务存储)
     * @param beans
     */
    // 5 SERIVCE PORT : addLogList
    public ListenableFuture<Void> addLog(List<LogBean> beans){
        return service.addLogList(converterLogBean.toRight(beans));
    }

    // 6 SERIVCE PORT : countLogLightWhere
    public ListenableFuture<Integer> countLogLightWhere(String where){
        return service.countLogLightWhere(where);
    }

    // 7 SERIVCE PORT : countLogWhere
    public ListenableFuture<Integer> countLogWhere(String where){
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
    public ListenableFuture<Integer> deleteAllFeaturesByPersonId(int personId,boolean deleteImage){
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
    public ListenableFuture<List<String>> deleteFeature(String featureMd5,boolean deleteImage){
        return service.deleteFeature(featureMd5,
                deleteImage);
    }
    /**
     * 删除imageMd5指定图像及其缩略图
     * @param imageMd5
     * @return 
     */
    // 10 SERIVCE PORT : deleteImage
    public ListenableFuture<Integer> deleteImage(String imageMd5){
        return service.deleteImage(imageMd5);
    }
    /**
     * 删除personId指定的人员(person)记录及关联的所有记录
     * @param personId
     * @return 
     */
    // 11 SERIVCE PORT : deletePerson
    public ListenableFuture<Integer> deletePerson(int personId){
        return service.deletePerson(personId);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNum 证件号码
     * @return 返回删除的 person 记录数量
     * @see {@link #deletePerson(int)}
     */
    // 12 SERIVCE PORT : deletePersonByPapersNum
    public ListenableFuture<Integer> deletePersonByPapersNum(String papersNum){
        return service.deletePersonByPapersNum(papersNum);
    }
    /**
     * 删除personIdList指定的人员(person)记录及关联的所有记录
     * @param personIdList 人员id列表
     * @return 返回删除的 person 记录数量
     */
    // 13 SERIVCE PORT : deletePersons
    public ListenableFuture<Integer> deletePersons(List<Integer> personIdList){
        return service.deletePersons(personIdList);
    }
    /**
     * 删除papersNum指定的人员(person)记录及关联的所有记录
     * @param papersNumlist 证件号码列表
     * @return 返回删除的 person 记录数量
     */
    // 14 SERIVCE PORT : deletePersonsByPapersNum
    public ListenableFuture<Integer> deletePersonsByPapersNum(List<String> papersNumlist){
        return service.deletePersonsByPapersNum(papersNumlist);
    }
    /**
     * 设置 personId 指定的人员为禁止状态
     * @param personId
     * @see #setPersonExpiryDate(int, long)
     */
    // 15 SERIVCE PORT : disablePerson
    public ListenableFuture<Void> disablePerson(int personId){
        return service.disablePerson(personId);
    }
    /**
     * 设置 personIdList 指定的人员为禁止状态
     * @param personIdList 人员id列表
     */
    // 16 SERIVCE PORT : disablePersonList
    public ListenableFuture<Void> disablePerson(List<Integer> personIdList){
        return service.disablePersonList(personIdList);
    }
    /**
     * 判断id指定的设备记录是否存在
     * @param id
     * @return 
     */
    // 17 SERIVCE PORT : existsDevice
    public ListenableFuture<Boolean> existsDevice(int id){
        return service.existsDevice(id);
    }
    /**
     * 判断md5指定的特征记录是否存在
     * @param md5
     * @return 
     */
    // 18 SERIVCE PORT : existsFeature
    public ListenableFuture<Boolean> existsFeature(String md5){
        return service.existsFeature(md5);
    }
    /**
     * 判断md5指定的图像记录是否存在
     * @param md5
     * @return 
     */
    // 19 SERIVCE PORT : existsImage
    public ListenableFuture<Boolean> existsImage(String md5){
        return service.existsImage(md5);
    }
    /**
     * 判断是否存在personId指定的人员记录
     * @param persionId
     * @return 
     */
    // 20 SERIVCE PORT : existsPerson
    public ListenableFuture<Boolean> existsPerson(int persionId){
        return service.existsPerson(persionId);
    }

    // 21 SERIVCE PORT : getDevice
    public ListenableFuture<DeviceBean> getDevice(int deviceId){
        return Futures.transform(
                service.getDevice(deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.DeviceBean,DeviceBean>(){
                    @Override
                    public DeviceBean apply(net.gdface.facelog.client.thrift.DeviceBean input) {
                        return converterDeviceBean.fromRight(input);
                    }
                });
    }

    // 22 SERIVCE PORT : getDeviceList
    public ListenableFuture<List<DeviceBean>> getDevice(List<Integer> deviceId){
        return Futures.transform(
                service.getDeviceList(deviceId), 
                new com.google.common.base.Function<List<net.gdface.facelog.client.thrift.DeviceBean>,List<DeviceBean>>(){
                    @Override
                    public List<DeviceBean> apply(List<net.gdface.facelog.client.thrift.DeviceBean> input) {
                        return converterDeviceBean.fromRight(input);
                    }
                });
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5
     * @return 如果数据库中没有对应的数据则返回null
     */
    // 23 SERIVCE PORT : getFeature
    public ListenableFuture<FeatureBean> getFeature(String md5){
        return Futures.transform(
                service.getFeature(md5), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.FeatureBean,FeatureBean>(){
                    @Override
                    public FeatureBean apply(net.gdface.facelog.client.thrift.FeatureBean input) {
                        return converterFeatureBean.fromRight(input);
                    }
                });
    }
    /**
     * 返回 persionId 关联的所有人脸特征记录
     * @param personId fl_person.id
     * @return 返回 fl_feature.md5  列表
     */
    // 24 SERIVCE PORT : getFeatureBeansByPersonId
    public ListenableFuture<List<String>> getFeatureBeansByPersonId(int personId){
        return service.getFeatureBeansByPersonId(personId);
    }
    /**
     * 根据MD5校验码返回人脸特征数据
     * @param md5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     */
    // 25 SERIVCE PORT : getFeatureBytes
    public ListenableFuture<byte[]> getFeatureBytes(String md5){
        return service.getFeatureBytes(md5);
    }
    /**
     * 根据MD5校验码返回人脸特征数据记录
     * @param md5 md5列表
     * @return {@link FeatureBean}列表
     */
    // 26 SERIVCE PORT : getFeatureList
    public ListenableFuture<List<FeatureBean>> getFeature(List<String> md5){
        return Futures.transform(
                service.getFeatureList(md5), 
                new com.google.common.base.Function<List<net.gdface.facelog.client.thrift.FeatureBean>,List<FeatureBean>>(){
                    @Override
                    public List<FeatureBean> apply(List<net.gdface.facelog.client.thrift.FeatureBean> input) {
                        return converterFeatureBean.fromRight(input);
                    }
                });
    }
    /**
     * 根据图像的MD5校验码返回图像记录
     * @param imageMD5
     * @return {@link ImageBean} ,如果没有对应记录则返回null
     */
    // 27 SERIVCE PORT : getImage
    public ListenableFuture<ImageBean> getImage(String imageMD5){
        return Futures.transform(
                service.getImage(imageMD5), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.ImageBean,ImageBean>(){
                    @Override
                    public ImageBean apply(net.gdface.facelog.client.thrift.ImageBean input) {
                        return converterImageBean.fromRight(input);
                    }
                });
    }
    /**
     * 根据图像的MD5校验码返回图像数据
     * @param imageMD5
     * @return 二进制数据字节数组,如果数据库中没有对应的数据则返回null
     * @see {@link #getBinary(String)}
     */
    // 28 SERIVCE PORT : getImageBytes
    public ListenableFuture<byte[]> getImageBytes(String imageMD5){
        return service.getImageBytes(imageMD5);
    }
    /**
     * 返回featureMd5的人脸特征记录关联的所有图像记录id(MD5)
     * @param featureMd5 人脸特征id(MD5)
     * @return 
     */
    // 29 SERIVCE PORT : getImagesAssociatedByFeature
    public ListenableFuture<List<String>> getImagesAssociatedByFeature(String featureMd5){
        return service.getImagesAssociatedByFeature(featureMd5);
    }
    /**
     * 返回 persionId 关联的所有日志记录
     * @param personId fl_person.id
     * @return 
     */
    // 30 SERIVCE PORT : getLogBeansByPersonId
    public ListenableFuture<List<LogBean>> getLogBeansByPersonId(int personId){
        return Futures.transform(
                service.getLogBeansByPersonId(personId), 
                new com.google.common.base.Function<List<net.gdface.facelog.client.thrift.LogBean>,List<LogBean>>(){
                    @Override
                    public List<LogBean> apply(List<net.gdface.facelog.client.thrift.LogBean> input) {
                        return converterLogBean.fromRight(input);
                    }
                });
    }
    /**
     * 返回personId指定的人员记录
     * @param personId
     * @return 
     */
    // 31 SERIVCE PORT : getPerson
    public ListenableFuture<PersonBean> getPerson(int personId){
        return Futures.transform(
                service.getPerson(personId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /**
     * 根据证件号码返回人员记录
     * @param papersNum
     * @return 
     */
    // 32 SERIVCE PORT : getPersonByPapersNum
    public ListenableFuture<PersonBean> getPersonByPapersNum(String papersNum){
        return Futures.transform(
                service.getPersonByPapersNum(papersNum), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /**
     * 返回 list 指定的人员记录
     * @param idList 人员id列表
     * @return 
     */
    // 33 SERIVCE PORT : getPersons
    public ListenableFuture<List<PersonBean>> getPersons(List<Integer> idList){
        return Futures.transform(
                service.getPersons(idList), 
                new com.google.common.base.Function<List<net.gdface.facelog.client.thrift.PersonBean>,List<PersonBean>>(){
                    @Override
                    public List<PersonBean> apply(List<net.gdface.facelog.client.thrift.PersonBean> input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /**
     * 判断 personId 指定的人员记录是否过期
     * @param personId
     * @return 
     */
    // 34 SERIVCE PORT : isDisable
    public ListenableFuture<Boolean> isDisable(int personId){
        return service.isDisable(personId);
    }
    /**
     * 返回所有人员记录
     * @return 
     */
    // 35 SERIVCE PORT : loadAllPerson
    public ListenableFuture<List<Integer>> loadAllPerson(){
        return service.loadAllPerson();
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_feature.update_time 字段大于指定时间戳( timestamp )的所有fl_feature记录
     * @param timestamp
     * @return 返回 fl_feature.md5 列表
     */
    // 36 SERIVCE PORT : loadFeatureMd5ByUpdate
    public ListenableFuture<List<String>> loadFeatureMd5ByUpdate(long timestamp){
        return service.loadFeatureMd5ByUpdate(timestamp);
    }

    // 37 SERIVCE PORT : loadLogByWhere
    public ListenableFuture<List<LogBean>> loadLogByWhere(String where,int startRow,int numRows){
        return Futures.transform(
                service.loadLogByWhere(where,
                startRow,
                numRows), 
                new com.google.common.base.Function<List<net.gdface.facelog.client.thrift.LogBean>,List<LogBean>>(){
                    @Override
                    public List<LogBean> apply(List<net.gdface.facelog.client.thrift.LogBean> input) {
                        return converterLogBean.fromRight(input);
                    }
                });
    }

    // 38 SERIVCE PORT : loadLogLightByWhere
    public ListenableFuture<List<LogLightBean>> loadLogLightByWhere(String where,int startRow,int numRows){
        return Futures.transform(
                service.loadLogLightByWhere(where,
                startRow,
                numRows), 
                new com.google.common.base.Function<List<net.gdface.facelog.client.thrift.LogLightBean>,List<LogLightBean>>(){
                    @Override
                    public List<LogLightBean> apply(List<net.gdface.facelog.client.thrift.LogLightBean> input) {
                        return converterLogLightBean.fromRight(input);
                    }
                });
    }
    /**
     * 返回 where 指定的所有人员记录
     * @param where SQL条件语句
     * @return 返回 fl_person.id 列表
     */
    // 39 SERIVCE PORT : loadPersonByWhere
    public ListenableFuture<List<Integer>> loadPersonByWhere(String where){
        return service.loadPersonByWhere(where);
    }
    /**
     * (主动更新机制实现)<br>
     * 返回 fl_person.update_time 字段大于指定时间戳( timestamp )的所有fl_person记录
     * @param timestamp
     * @return 返回fl_person.id 列表
     */
    // 40 SERIVCE PORT : loadPersonIdByUpdate
    public ListenableFuture<List<Integer>> loadPersonIdByUpdate(long timestamp){
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
    public ListenableFuture<List<Integer>> loadUpdatePersons(long timestamp){
        return service.loadUpdatePersons(timestamp);
    }
    /**
     * 替换personId指定的人员记录的人脸特征数据,同时删除原特征数据记录(fl_feature)及关联的fl_face表记录
     * @param personId 人员记录id
     * @param featureMd5 人脸特征数据记录id (已经保存在数据库中)
     * @param deleteOldFeatureImage 是否删除原特征数据记录间接关联的原始图像记录(fl_image)
     */
    // 42 SERIVCE PORT : replaceFeature
    public ListenableFuture<Void> replaceFeature(int personId,String featureMd5,boolean deleteOldFeatureImage){
        return service.replaceFeature(personId,
                featureMd5,
                deleteOldFeatureImage);
    }

    // 43 SERIVCE PORT : saveDevice
    public ListenableFuture<DeviceBean> saveDevice(DeviceBean deviceBean){
        return Futures.transform(
                service.saveDevice(converterDeviceBean.toRight(deviceBean)), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.DeviceBean,DeviceBean>(){
                    @Override
                    public DeviceBean apply(net.gdface.facelog.client.thrift.DeviceBean input) {
                        return converterDeviceBean.fromRight(input);
                    }
                });
    }
    /**
     * 保存人员(person)记录
     * @param bean
     * @return 
     */
    // 44 SERIVCE PORT : savePerson
    public ListenableFuture<PersonBean> savePerson(PersonBean bean){
        return Futures.transform(
                service.savePerson(converterPersonBean.toRight(bean)), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
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
    public ListenableFuture<PersonBean> savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,byte[] featureImage,FaceBean featureFaceBean,int deviceId){
        return Futures.transform(
                service.savePersonFull(converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                featureImage,
                converterFaceBean.toRight(featureFaceBean),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],byte[],byte[],FaceBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 45 GENERIC
    public ListenableFuture<PersonBean> savePersonGeneric(PersonBean bean,Object idPhoto,Object feature,Object featureImage,FaceBean featureFaceBean,int deviceId){
        return Futures.transform(
                service.savePersonFull(converterPersonBean.toRight(bean),
                GenericUtils.toBytes(idPhoto),
                GenericUtils.toBytes(feature),
                GenericUtils.toBytes(featureImage),
                converterFaceBean.toRight(featureFaceBean),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /**
     * 保存人员(person)记录
     * @param beans
     */
    // 46 SERIVCE PORT : savePersonList
    public ListenableFuture<Void> savePerson(List<PersonBean> beans){
        return service.savePersonList(converterPersonBean.toRight(beans));
    }
    /**
     * 保存人员信息记录(包含标准照)
     * @param persons
     * @return 
     */
    // 47 SERIVCE PORT : savePersonsWithPhoto
    public ListenableFuture<Integer> savePerson(Map<ByteBuffer, PersonBean> persons){
        return service.savePersonsWithPhoto(GenericUtils.toBytesKey(converterPersonBean.toRightValue(persons)));
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhoto 标准照图像对象,可为null
     * @return 
     */
    // 48 SERIVCE PORT : savePersonWithPhoto
    public ListenableFuture<PersonBean> savePerson(PersonBean bean,byte[] idPhoto){
        return Futures.transform(
                service.savePersonWithPhoto(converterPersonBean.toRight(bean),
                idPhoto), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[])}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 48 GENERIC
    public ListenableFuture<PersonBean> savePersonGeneric(PersonBean bean,Object idPhoto){
        return Futures.transform(
                service.savePersonWithPhoto(converterPersonBean.toRight(bean),
                GenericUtils.toBytes(idPhoto)), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
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
    public ListenableFuture<PersonBean> savePerson(PersonBean bean,byte[] idPhoto,FeatureBean featureBean,int deviceId){
        return Futures.transform(
                service.savePersonWithPhotoAndFeature(converterPersonBean.toRight(bean),
                idPhoto,
                converterFeatureBean.toRight(featureBean),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],FeatureBean,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 49 GENERIC
    public ListenableFuture<PersonBean> savePersonGeneric(PersonBean bean,Object idPhoto,FeatureBean featureBean,int deviceId){
        return Futures.transform(
                service.savePersonWithPhotoAndFeature(converterPersonBean.toRight(bean),
                GenericUtils.toBytes(idPhoto),
                converterFeatureBean.toRight(featureBean),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
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
    public ListenableFuture<PersonBean> savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,List<FaceBean> faceBeans){
        return Futures.transform(
                service.savePersonWithPhotoAndFeatureMultiFaces(converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                converterFaceBean.toRight(faceBeans)), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],byte[],List<FaceBean>)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 50 GENERIC
    public ListenableFuture<PersonBean> savePersonGeneric(PersonBean bean,Object idPhoto,Object feature,List<FaceBean> faceBeans){
        return Futures.transform(
                service.savePersonWithPhotoAndFeatureMultiFaces(converterPersonBean.toRight(bean),
                GenericUtils.toBytes(idPhoto),
                GenericUtils.toBytes(feature),
                converterFaceBean.toRight(faceBeans)), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
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
    public ListenableFuture<PersonBean> savePerson(PersonBean bean,byte[] idPhoto,byte[] feature,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return Futures.transform(
                service.savePersonWithPhotoAndFeatureMultiImage(converterPersonBean.toRight(bean),
                idPhoto,
                feature,
                GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /** 
     * generic version of {@link #savePerson(PersonBean,byte[],byte[],Map<ByteBuffer, FaceBean>,int)}<br>
     * {@code Object} type instead of all argument with {@code byte[]} type,which can read binary data,
     * such as {@code InputStream,URL,URI,File,ByteBuffer},supported type depend on {@link GenericUtils#toBytes(Object)} <br>
     * @see {@link GenericUtils#toBytes(Object)}
     */
    // 51 GENERIC
    public ListenableFuture<PersonBean> savePersonGeneric(PersonBean bean,Object idPhoto,Object feature,Map<ByteBuffer, FaceBean> faceInfo,int deviceId){
        return Futures.transform(
                service.savePersonWithPhotoAndFeatureMultiImage(converterPersonBean.toRight(bean),
                GenericUtils.toBytes(idPhoto),
                GenericUtils.toBytes(feature),
                GenericUtils.toBytesKey(converterFaceBean.toRightValue(faceInfo)),
                deviceId), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /**
     * 保存人员信息记录
     * @param bean
     * @param idPhotoMd5 标准照图像对象,可为null
     * @param featureMd5 用于验证的人脸特征数据对象,可为null
     * @return 
     */
    // 52 SERIVCE PORT : savePersonWithPhotoAndFeatureSaved
    public ListenableFuture<PersonBean> savePerson(PersonBean bean,String idPhotoMd5,String featureMd5){
        return Futures.transform(
                service.savePersonWithPhotoAndFeatureSaved(converterPersonBean.toRight(bean),
                idPhotoMd5,
                featureMd5), 
                new com.google.common.base.Function<net.gdface.facelog.client.thrift.PersonBean,PersonBean>(){
                    @Override
                    public PersonBean apply(net.gdface.facelog.client.thrift.PersonBean input) {
                        return converterPersonBean.fromRight(input);
                    }
                });
    }
    /**
     * 修改 personId 指定的人员记录的有效期
     * @param personId
     * @param expiryDate 失效日期
     */
    // 53 SERIVCE PORT : setPersonExpiryDate
    public ListenableFuture<Void> setPersonExpiryDate(int personId,long expiryDate){
        return service.setPersonExpiryDate(personId,
                expiryDate);
    }
    /**
     * 修改 personIdList 指定的人员记录的有效期
     * @param personIdList 人员id列表
     * @param expiryDate 失效日期
     */
    // 54 SERIVCE PORT : setPersonExpiryDateList
    public ListenableFuture<Void> setPersonExpiryDate(List<Integer> personIdList,long expiryDate){
        return service.setPersonExpiryDateList(personIdList,
                expiryDate);
    }
}
