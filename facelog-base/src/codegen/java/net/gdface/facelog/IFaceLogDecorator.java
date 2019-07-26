package net.gdface.facelog;
import java.util.List;
import java.util.Map;
import net.gdface.facelog.db.DeviceBean;
import net.gdface.facelog.db.DeviceGroupBean;
import net.gdface.facelog.db.FaceBean;
import net.gdface.facelog.db.FeatureBean;
import net.gdface.facelog.db.ImageBean;
import net.gdface.facelog.db.LogBean;
import net.gdface.facelog.db.PermitBean;
import net.gdface.facelog.db.PersonBean;
import net.gdface.facelog.db.PersonGroupBean;
/**
 * decorator pattern 装饰者模式实现{@link IFaceLog}接口<br>
 * 转发所有{@link IFaceLog}接口方法到{@link #delegate()}指定的实例,<br>
 * unchecked后缀的方法将所有显式申明的异常封装到{@link RuntimeException}抛出<br>
 * 计算机生成代码(generated by automated tools DecoratorGenerator @author guyadong)<br>
 * @author guyadong
 *
 */
public class IFaceLogDecorator implements IFaceLog{
    private final IFaceLog delegate;
    
    public IFaceLogDecorator(IFaceLog delegate) {
        super();
        if(null == delegate){
            throw new NullPointerException("delegate is null");
        }
        this.delegate = delegate;
    }

    /**
     * 返回被装饰的{@link IFaceLog}实例
     * @return
     */
    public IFaceLog delegate() {
        return delegate;
    }    

    @Override
    public FeatureBean addFeature (byte[] feature,String featureVersion,Integer personId,boolean asIdPhotoIfAbsent,byte[] featurePhoto,FaceBean faceBean,Token token) throws DuplicateRecordException{
        return delegate().addFeature(feature,featureVersion,personId,asIdPhotoIfAbsent,featurePhoto,faceBean,token);
    }

    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.String,java.lang.Integer,boolean,byte[],FaceBean,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param feature
     * @param featureVersion
     * @param personId
     * @param asIdPhotoIfAbsent
     * @param featurePhoto
     * @param faceBean
     * @param token
     * @return FeatureBean
     */
    public FeatureBean addFeatureUnchecked (byte[] feature,String featureVersion,Integer personId,boolean asIdPhotoIfAbsent,byte[] featurePhoto,FaceBean faceBean,Token token) {
        try{
            return delegate().addFeature(feature,featureVersion,personId,asIdPhotoIfAbsent,featurePhoto,faceBean,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public FeatureBean addFeature (byte[] feature,String featureVersion,Integer personId,List<byte[]> photos,List<FaceBean> faces,Token token) throws DuplicateRecordException{
        return delegate().addFeature(feature,featureVersion,personId,photos,faces,token);
    }

    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.String,java.lang.Integer,List,List,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param feature
     * @param featureVersion
     * @param personId
     * @param photos
     * @param faces
     * @param token
     * @return FeatureBean
     */
    public FeatureBean addFeatureUnchecked (byte[] feature,String featureVersion,Integer personId,List<byte[]> photos,List<FaceBean> faces,Token token) {
        try{
            return delegate().addFeature(feature,featureVersion,personId,photos,faces,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public FeatureBean addFeature (byte[] feature,String featureVersion,Integer personId,List<FaceBean> faecBeans,Token token) throws DuplicateRecordException{
        return delegate().addFeature(feature,featureVersion,personId,faecBeans,token);
    }

    /**
     * {@link IFaceLog#addFeature(byte[],java.lang.String,java.lang.Integer,List,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param feature
     * @param featureVersion
     * @param personId
     * @param faecBeans
     * @param token
     * @return FeatureBean
     */
    public FeatureBean addFeatureUnchecked (byte[] feature,String featureVersion,Integer personId,List<FaceBean> faecBeans,Token token) {
        try{
            return delegate().addFeature(feature,featureVersion,personId,faecBeans,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageBean addImage (byte[] imageData,Integer deviceId,FaceBean faceBean,Integer personId,Token token) throws DuplicateRecordException{
        return delegate().addImage(imageData,deviceId,faceBean,personId,token);
    }

    /**
     * {@link IFaceLog#addImage(byte[],java.lang.Integer,FaceBean,java.lang.Integer,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param imageData
     * @param deviceId
     * @param faceBean
     * @param personId
     * @param token
     * @return ImageBean
     */
    public ImageBean addImageUnchecked (byte[] imageData,Integer deviceId,FaceBean faceBean,Integer personId,Token token) {
        try{
            return delegate().addImage(imageData,deviceId,faceBean,personId,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addLog (LogBean logBean,Token token) throws DuplicateRecordException{
         delegate().addLog(logBean,token);
    }

    /**
     * {@link IFaceLog#addLog(LogBean,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param logBean
     * @param token
     * @return void
     */
    public void addLogUnchecked (LogBean logBean,Token token) {
        try{
             delegate().addLog(logBean,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addLog (LogBean logBean,FaceBean faceBean,byte[] featureImage,Token token) throws DuplicateRecordException{
         delegate().addLog(logBean,faceBean,featureImage,token);
    }

    /**
     * {@link IFaceLog#addLog(LogBean,FaceBean,byte[],Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param logBean
     * @param faceBean
     * @param featureImage
     * @param token
     * @return void
     */
    public void addLogUnchecked (LogBean logBean,FaceBean faceBean,byte[] featureImage,Token token) {
        try{
             delegate().addLog(logBean,faceBean,featureImage,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addLogs (List<LogBean> logBeans,List<FaceBean> faceBeans,List<byte[]> featureImages,Token token) throws DuplicateRecordException{
         delegate().addLogs(logBeans,faceBeans,featureImages,token);
    }

    /**
     * {@link IFaceLog#addLogs(List,List,List,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param logBeans
     * @param faceBeans
     * @param featureImages
     * @param token
     * @return void
     */
    public void addLogsUnchecked (List<LogBean> logBeans,List<FaceBean> faceBeans,List<byte[]> featureImages,Token token) {
        try{
             delegate().addLogs(logBeans,faceBeans,featureImages,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addLogs (List<LogBean> beans,Token token) throws DuplicateRecordException{
         delegate().addLogs(beans,token);
    }

    /**
     * {@link IFaceLog#addLogs(List,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param beans
     * @param token
     * @return void
     */
    public void addLogsUnchecked (List<LogBean> beans,Token token) {
        try{
             delegate().addLogs(beans,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeviceBean addNullDevice (Integer groupId,String name,String mac,String serialNo,String remark,Token token) throws DuplicateRecordException{
        return delegate().addNullDevice(groupId,name,mac,serialNo,remark,token);
    }

    /**
     * {@link IFaceLog#addNullDevice(java.lang.Integer,java.lang.String,java.lang.String,java.lang.String,java.lang.String,Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param groupId
     * @param name
     * @param mac
     * @param serialNo
     * @param remark
     * @param token
     * @return DeviceBean
     */
    public DeviceBean addNullDeviceUnchecked (Integer groupId,String name,String mac,String serialNo,String remark,Token token) {
        try{
            return delegate().addNullDevice(groupId,name,mac,serialNo,remark,token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public String applyAckChannel (int duration,Token token) {
        return delegate().applyAckChannel(duration,token);
    }

    @Override
    public String applyAckChannel (Token token) {
        return delegate().applyAckChannel(token);
    }

    @Override
    public int applyCmdSn (Token token) {
        return delegate().applyCmdSn(token);
    }

    @Override
    public Token applyPersonToken (int personId,String password,boolean isMd5) throws ServiceSecurityException{
        return delegate().applyPersonToken(personId,password,isMd5);
    }

    /**
     * {@link IFaceLog#applyPersonToken(int,java.lang.String,boolean)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param personId
     * @param password
     * @param isMd5
     * @return Token
     */
    public Token applyPersonTokenUnchecked (int personId,String password,boolean isMd5) {
        try{
            return delegate().applyPersonToken(personId,password,isMd5);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Token applyRootToken (String password,boolean isMd5) throws ServiceSecurityException{
        return delegate().applyRootToken(password,isMd5);
    }

    /**
     * {@link IFaceLog#applyRootToken(java.lang.String,boolean)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param password
     * @param isMd5
     * @return Token
     */
    public Token applyRootTokenUnchecked (String password,boolean isMd5) {
        try{
            return delegate().applyRootToken(password,isMd5);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Token applyUserToken (int userid,String password,boolean isMd5) throws ServiceSecurityException{
        return delegate().applyUserToken(userid,password,isMd5);
    }

    /**
     * {@link IFaceLog#applyUserToken(int,java.lang.String,boolean)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param userid
     * @param password
     * @param isMd5
     * @return Token
     */
    public Token applyUserTokenUnchecked (int userid,String password,boolean isMd5) {
        try{
            return delegate().applyUserToken(userid,password,isMd5);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void bindBorder (Integer personGroupId,Integer deviceGroupId,Token token) {
         delegate().bindBorder(personGroupId,deviceGroupId,token);
    }

    @Override
    public List<Integer> childListForDeviceGroup (int deviceGroupId) {
        return delegate().childListForDeviceGroup(deviceGroupId);
    }

    @Override
    public List<Integer> childListForPersonGroup (int personGroupId) {
        return delegate().childListForPersonGroup(personGroupId);
    }

    @Override
    public int countDeviceByWhere (String where) {
        return delegate().countDeviceByWhere(where);
    }

    @Override
    public int countDeviceGroupByWhere (String where) {
        return delegate().countDeviceGroupByWhere(where);
    }

    @Override
    public int countLogByWhere (String where) {
        return delegate().countLogByWhere(where);
    }

    @Override
    public int countLogLightByVerifyTime (String timestamp) {
        return delegate().countLogLightByVerifyTime(timestamp);
    }

    @Override
    public int countLogLightByVerifyTime (long timestamp) {
        return delegate().countLogLightByVerifyTime(timestamp);
    }

    @Override
    public int countLogLightByWhere (String where) {
        return delegate().countLogLightByWhere(where);
    }

    @Override
    public int countPersonByWhere (String where) {
        return delegate().countPersonByWhere(where);
    }

    @Override
    public int countPersonGroupByWhere (String where) {
        return delegate().countPersonGroupByWhere(where);
    }

    @Override
    public int deleteAllFeaturesByPersonId (int personId,boolean deleteImage,Token token) {
        return delegate().deleteAllFeaturesByPersonId(personId,deleteImage,token);
    }

    @Override
    public int deleteDeviceGroup (int deviceGroupId,Token token) {
        return delegate().deleteDeviceGroup(deviceGroupId,token);
    }

    @Override
    public List<String> deleteFeature (String featureMd5,boolean deleteImage,Token token) {
        return delegate().deleteFeature(featureMd5,deleteImage,token);
    }

    @Override
    public int deleteGroupPermitOnDeviceGroup (int deviceGroupId,Token token) {
        return delegate().deleteGroupPermitOnDeviceGroup(deviceGroupId,token);
    }

    @Override
    public int deleteImage (String imageMd5,Token token) {
        return delegate().deleteImage(imageMd5,token);
    }

    @Override
    public int deletePermit (int deviceGroupId,int personGroupId,Token token) {
        return delegate().deletePermit(deviceGroupId,personGroupId,token);
    }

    @Override
    public int deletePerson (int personId,Token token) {
        return delegate().deletePerson(personId,token);
    }

    @Override
    public int deletePersonByPapersNum (String papersNum,Token token) {
        return delegate().deletePersonByPapersNum(papersNum,token);
    }

    @Override
    public int deletePersonGroup (int personGroupId,Token token) {
        return delegate().deletePersonGroup(personGroupId,token);
    }

    @Override
    public int deletePersonGroupPermit (int personGroupId,Token token) {
        return delegate().deletePersonGroupPermit(personGroupId,token);
    }

    @Override
    public int deletePersons (List<Integer> personIdList,Token token) {
        return delegate().deletePersons(personIdList,token);
    }

    @Override
    public int deletePersonsByPapersNum (List<String> papersNumlist,Token token) {
        return delegate().deletePersonsByPapersNum(papersNumlist,token);
    }

    @Override
    public void disablePerson (int personId,Integer moveToGroupId,boolean deletePhoto,boolean deleteFeature,boolean deleteLog,Token token) {
         delegate().disablePerson(personId,moveToGroupId,deletePhoto,deleteFeature,deleteLog,token);
    }

    @Override
    public void disablePerson (List<Integer> personIdList,Token token) {
         delegate().disablePerson(personIdList,token);
    }

    @Override
    public boolean existsDevice (int id) {
        return delegate().existsDevice(id);
    }

    @Override
    public boolean existsFeature (String md5) {
        return delegate().existsFeature(md5);
    }

    @Override
    public boolean existsImage (String md5) {
        return delegate().existsImage(md5);
    }

    @Override
    public boolean existsPerson (int persionId) {
        return delegate().existsPerson(persionId);
    }

    @Override
    public DeviceBean getDevice (int deviceId) {
        return delegate().getDevice(deviceId);
    }

    @Override
    public DeviceBean getDeviceByMac (String mac) {
        return delegate().getDeviceByMac(mac);
    }

    @Override
    public DeviceGroupBean getDeviceGroup (int deviceGroupId) {
        return delegate().getDeviceGroup(deviceGroupId);
    }

    @Override
    public List<DeviceGroupBean> getDeviceGroups (List<Integer> groupIdList) {
        return delegate().getDeviceGroups(groupIdList);
    }

    @Override
    public List<Integer> getDeviceGroupsBelongs (int deviceId) {
        return delegate().getDeviceGroupsBelongs(deviceId);
    }

    @Override
    public List<Integer> getDeviceGroupsPermit (int personGroupId) {
        return delegate().getDeviceGroupsPermit(personGroupId);
    }

    @Override
    public List<Integer> getDeviceGroupsPermittedBy (int personGroupId) {
        return delegate().getDeviceGroupsPermittedBy(personGroupId);
    }

    @Override
    public Integer getDeviceIdOfFeature (String featureMd5) {
        return delegate().getDeviceIdOfFeature(featureMd5);
    }

    @Override
    public List<DeviceBean> getDevices (List<Integer> idList) {
        return delegate().getDevices(idList);
    }

    @Override
    public List<Integer> getDevicesOfGroup (int deviceGroupId) {
        return delegate().getDevicesOfGroup(deviceGroupId);
    }

    @Override
    public FaceBean getFace (int faceId) {
        return delegate().getFace(faceId);
    }

    @Override
    public FeatureBean getFeature (String md5) {
        return delegate().getFeature(md5);
    }

    @Override
    public byte[] getFeatureBytes (String md5) {
        return delegate().getFeatureBytes(md5);
    }

    @Override
    public List<FeatureBean> getFeatures (List<String> md5) {
        return delegate().getFeatures(md5);
    }

    @Override
    public List<String> getFeaturesByPersonIdAndSdkVersion (int personId,String sdkVersion) {
        return delegate().getFeaturesByPersonIdAndSdkVersion(personId,sdkVersion);
    }

    @Override
    public List<String> getFeaturesOfPerson (int personId) {
        return delegate().getFeaturesOfPerson(personId);
    }

    @Override
    public List<String> getFeaturesPermittedOnDevice (int deviceId,boolean ignoreSchedule,String sdkVersion,List<String> excludeFeatureIds,Long timestamp) {
        return delegate().getFeaturesPermittedOnDevice(deviceId,ignoreSchedule,sdkVersion,excludeFeatureIds,timestamp);
    }

    @Override
    public PermitBean getGroupPermit (int deviceId,int personGroupId) {
        return delegate().getGroupPermit(deviceId,personGroupId);
    }

    @Override
    public PermitBean getGroupPermitOnDeviceGroup (int deviceGroupId,int personGroupId) {
        return delegate().getGroupPermitOnDeviceGroup(deviceGroupId,personGroupId);
    }

    @Override
    public List<PermitBean> getGroupPermits (int deviceId,List<Integer> personGroupIdList) {
        return delegate().getGroupPermits(deviceId,personGroupIdList);
    }

    @Override
    public ImageBean getImage (String imageMD5) {
        return delegate().getImage(imageMD5);
    }

    @Override
    public byte[] getImageBytes (String imageMD5) {
        return delegate().getImageBytes(imageMD5);
    }

    @Override
    public List<String> getImagesAssociatedByFeature (String featureMd5) {
        return delegate().getImagesAssociatedByFeature(featureMd5);
    }

    @Override
    public List<LogBean> getLogBeansByPersonId (int personId) {
        return delegate().getLogBeansByPersonId(personId);
    }

    @Override
    public PersonBean getPerson (int personId) {
        return delegate().getPerson(personId);
    }

    @Override
    public PersonBean getPersonByMobilePhone (String mobilePhone) {
        return delegate().getPersonByMobilePhone(mobilePhone);
    }

    @Override
    public PersonBean getPersonByPapersNum (String papersNum) {
        return delegate().getPersonByPapersNum(papersNum);
    }

    @Override
    public PersonGroupBean getPersonGroup (int personGroupId) {
        return delegate().getPersonGroup(personGroupId);
    }

    @Override
    public List<PersonGroupBean> getPersonGroups (List<Integer> groupIdList) {
        return delegate().getPersonGroups(groupIdList);
    }

    @Override
    public List<Integer> getPersonGroupsBelongs (int personId) {
        return delegate().getPersonGroupsBelongs(personId);
    }

    @Override
    public List<Integer> getPersonGroupsPermittedBy (int deviceGroupId) {
        return delegate().getPersonGroupsPermittedBy(deviceGroupId);
    }

    @Override
    public PermitBean getPersonPermit (int deviceId,int personId) {
        return delegate().getPersonPermit(deviceId,personId);
    }

    @Override
    public List<PermitBean> getPersonPermits (int deviceId,List<Integer> personIdList) {
        return delegate().getPersonPermits(deviceId,personIdList);
    }

    @Override
    public List<PersonBean> getPersons (List<Integer> idList) {
        return delegate().getPersons(idList);
    }

    @Override
    public List<Integer> getPersonsOfGroup (int personGroupId) {
        return delegate().getPersonsOfGroup(personGroupId);
    }

    @Override
    public List<Integer> getPersonsPermittedOnDevice (int deviceId,boolean ignoreSchedule,List<Integer> excludePersonIds,Long timestamp) {
        return delegate().getPersonsPermittedOnDevice(deviceId,ignoreSchedule,excludePersonIds,timestamp);
    }

    @Override
    public Map<String, String> getProperties (String prefix,Token token) {
        return delegate().getProperties(prefix,token);
    }

    @Override
    public String getProperty (String key,Token token) {
        return delegate().getProperty(key,token);
    }

    @Override
    public Map<net.gdface.facelog.MQParam, String> getRedisParameters (Token token) {
        return delegate().getRedisParameters(token);
    }

    @Override
    public Map<String, String> getServiceConfig (Token token) {
        return delegate().getServiceConfig(token);
    }

    @Override
    public List<Integer> getSubDeviceGroup (int deviceGroupId) {
        return delegate().getSubDeviceGroup(deviceGroupId);
    }

    @Override
    public List<Integer> getSubPersonGroup (int personGroupId) {
        return delegate().getSubPersonGroup(personGroupId);
    }

    @Override
    public boolean isDisable (int personId) {
        return delegate().isDisable(personId);
    }

    @Override
    public boolean isLocal () {
        return delegate().isLocal();
    }

    @Override
    public boolean isValidAckChannel (String ackChannel) {
        return delegate().isValidAckChannel(ackChannel);
    }

    @Override
    public boolean isValidCmdSn (int cmdSn) {
        return delegate().isValidCmdSn(cmdSn);
    }

    @Override
    public boolean isValidDeviceToken (Token token) {
        return delegate().isValidDeviceToken(token);
    }

    @Override
    public boolean isValidPassword (String userId,String password,boolean isMd5) {
        return delegate().isValidPassword(userId,password,isMd5);
    }

    @Override
    public boolean isValidPersonToken (Token token) {
        return delegate().isValidPersonToken(token);
    }

    @Override
    public boolean isValidRootToken (Token token) {
        return delegate().isValidRootToken(token);
    }

    @Override
    public boolean isValidToken (Token token) {
        return delegate().isValidToken(token);
    }

    @Override
    public boolean isValidUserToken (Token token) {
        return delegate().isValidUserToken(token);
    }

    @Override
    public List<Integer> listOfParentForDeviceGroup (int deviceGroupId) {
        return delegate().listOfParentForDeviceGroup(deviceGroupId);
    }

    @Override
    public List<Integer> listOfParentForPersonGroup (int personGroupId) {
        return delegate().listOfParentForPersonGroup(personGroupId);
    }

    @Override
    public List<Integer> loadAllPerson () {
        return delegate().loadAllPerson();
    }

    @Override
    public List<DeviceBean> loadDeviceByWhere (String where,int startRow,int numRows) {
        return delegate().loadDeviceByWhere(where,startRow,numRows);
    }

    @Override
    public List<Integer> loadDeviceGroupByWhere (String where,int startRow,int numRows) {
        return delegate().loadDeviceGroupByWhere(where,startRow,numRows);
    }

    @Override
    public List<Integer> loadDeviceGroupIdByWhere (String where) {
        return delegate().loadDeviceGroupIdByWhere(where);
    }

    @Override
    public List<Integer> loadDeviceIdByWhere (String where) {
        return delegate().loadDeviceIdByWhere(where);
    }

    @Override
    public List<Integer> loadDistinctIntegerColumn (String table,String column,String where) {
        return delegate().loadDistinctIntegerColumn(table,column,where);
    }

    @Override
    public List<String> loadDistinctStringColumn (String table,String column,String where) {
        return delegate().loadDistinctStringColumn(table,column,where);
    }

    @Override
    public List<String> loadFeatureMd5ByUpdate (String timestamp) {
        return delegate().loadFeatureMd5ByUpdate(timestamp);
    }

    @Override
    public List<String> loadFeatureMd5ByUpdate (long timestamp) {
        return delegate().loadFeatureMd5ByUpdate(timestamp);
    }

    @Override
    public List<LogBean> loadLogByWhere (String where,int startRow,int numRows) {
        return delegate().loadLogByWhere(where,startRow,numRows);
    }

    @Override
    public List<net.gdface.facelog.db.LogLightBean> loadLogLightByVerifyTime (String timestamp,int startRow,int numRows) {
        return delegate().loadLogLightByVerifyTime(timestamp,startRow,numRows);
    }

    @Override
    public List<net.gdface.facelog.db.LogLightBean> loadLogLightByVerifyTime (long timestamp,int startRow,int numRows) {
        return delegate().loadLogLightByVerifyTime(timestamp,startRow,numRows);
    }

    @Override
    public List<net.gdface.facelog.db.LogLightBean> loadLogLightByWhere (String where,int startRow,int numRows) {
        return delegate().loadLogLightByWhere(where,startRow,numRows);
    }

    @Override
    public List<PermitBean> loadPermitByUpdate (String timestamp) {
        return delegate().loadPermitByUpdate(timestamp);
    }

    @Override
    public List<PermitBean> loadPermitByUpdate (long timestamp) {
        return delegate().loadPermitByUpdate(timestamp);
    }

    @Override
    public List<PersonBean> loadPersonByWhere (String where,int startRow,int numRows) {
        return delegate().loadPersonByWhere(where,startRow,numRows);
    }

    @Override
    public List<Integer> loadPersonGroupByWhere (String where,int startRow,int numRows) {
        return delegate().loadPersonGroupByWhere(where,startRow,numRows);
    }

    @Override
    public List<Integer> loadPersonGroupIdByWhere (String where) {
        return delegate().loadPersonGroupIdByWhere(where);
    }

    @Override
    public List<Integer> loadPersonIdByUpdateTime (String timestamp) {
        return delegate().loadPersonIdByUpdateTime(timestamp);
    }

    @Override
    public List<Integer> loadPersonIdByUpdateTime (long timestamp) {
        return delegate().loadPersonIdByUpdateTime(timestamp);
    }

    @Override
    public List<Integer> loadPersonIdByWhere (String where) {
        return delegate().loadPersonIdByWhere(where);
    }

    @Override
    public List<Integer> loadUpdatedPersons (String timestamp) {
        return delegate().loadUpdatedPersons(timestamp);
    }

    @Override
    public List<Integer> loadUpdatedPersons (long timestamp) {
        return delegate().loadUpdatedPersons(timestamp);
    }

    @Override
    public void offline (Token token) throws ServiceSecurityException{
         delegate().offline(token);
    }

    /**
     * {@link IFaceLog#offline(Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param token
     * @return void
     */
    public void offlineUnchecked (Token token) {
        try{
             delegate().offline(token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Token online (DeviceBean device) throws ServiceSecurityException{
        return delegate().online(device);
    }

    /**
     * {@link IFaceLog#online(DeviceBean)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param device
     * @return Token
     */
    public Token onlineUnchecked (DeviceBean device) {
        try{
            return delegate().online(device);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeviceBean registerDevice (DeviceBean newDevice) throws ServiceSecurityException{
        return delegate().registerDevice(newDevice);
    }

    /**
     * {@link IFaceLog#registerDevice(DeviceBean)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param newDevice
     * @return DeviceBean
     */
    public DeviceBean registerDeviceUnchecked (DeviceBean newDevice) {
        try{
            return delegate().registerDevice(newDevice);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void releasePersonToken (Token token) throws ServiceSecurityException{
         delegate().releasePersonToken(token);
    }

    /**
     * {@link IFaceLog#releasePersonToken(Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param token
     * @return void
     */
    public void releasePersonTokenUnchecked (Token token) {
        try{
             delegate().releasePersonToken(token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void releaseRootToken (Token token) throws ServiceSecurityException{
         delegate().releaseRootToken(token);
    }

    /**
     * {@link IFaceLog#releaseRootToken(Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param token
     * @return void
     */
    public void releaseRootTokenUnchecked (Token token) {
        try{
             delegate().releaseRootToken(token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void releaseUserToken (Token token) throws ServiceSecurityException{
         delegate().releaseUserToken(token);
    }

    /**
     * {@link IFaceLog#releaseUserToken(Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param token
     * @return void
     */
    public void releaseUserTokenUnchecked (Token token) {
        try{
             delegate().releaseUserToken(token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void replaceFeature (Integer personId,String featureMd5,boolean deleteOldFeatureImage,Token token) {
         delegate().replaceFeature(personId,featureMd5,deleteOldFeatureImage,token);
    }

    @Override
    public Integer rootGroupOfDevice (Integer deviceId) {
        return delegate().rootGroupOfDevice(deviceId);
    }

    @Override
    public Integer rootGroupOfPerson (Integer personId) {
        return delegate().rootGroupOfPerson(personId);
    }

    @Override
    public String runCmd (List<Integer> target,boolean group,String cmdpath,String jsonArgs,String ackChannel,Token token) {
        return delegate().runCmd(target,group,cmdpath,jsonArgs,ackChannel,token);
    }

    @Override
    public Integer runTask (String taskQueue,String cmdpath,String jsonArgs,String ackChannel,Token token) {
        return delegate().runTask(taskQueue,cmdpath,jsonArgs,ackChannel,token);
    }

    @Override
    public DeviceBean saveDevice (DeviceBean deviceBean,Token token) {
        return delegate().saveDevice(deviceBean,token);
    }

    @Override
    public DeviceGroupBean saveDeviceGroup (DeviceGroupBean deviceGroupBean,Token token) {
        return delegate().saveDeviceGroup(deviceGroupBean,token);
    }

    @Override
    public PermitBean savePermit (int deviceGroupId,int personGroupId,String schedule,Token token) {
        return delegate().savePermit(deviceGroupId,personGroupId,schedule,token);
    }

    @Override
    public PermitBean savePermit (PermitBean permitBean,Token token) {
        return delegate().savePermit(permitBean,token);
    }

    @Override
    public PersonBean savePerson (PersonBean personBean,byte[] idPhoto,byte[] feature,String featureVersion,byte[] featureImage,FaceBean featureFaceBean,Token token) {
        return delegate().savePerson(personBean,idPhoto,feature,featureVersion,featureImage,featureFaceBean,token);
    }

    @Override
    public PersonBean savePerson (PersonBean personBean,byte[] idPhoto,byte[] feature,String featureVersion,List<byte[]> photos,List<FaceBean> faces,Token token) {
        return delegate().savePerson(personBean,idPhoto,feature,featureVersion,photos,faces,token);
    }

    @Override
    public PersonBean savePerson (PersonBean personBean,byte[] idPhoto,byte[] feature,String featureVersion,List<FaceBean> faceBeans,Token token) {
        return delegate().savePerson(personBean,idPhoto,feature,featureVersion,faceBeans,token);
    }

    @Override
    public PersonBean savePerson (PersonBean personBean,byte[] idPhoto,Token token) {
        return delegate().savePerson(personBean,idPhoto,token);
    }

    @Override
    public PersonBean savePerson (PersonBean personBean,byte[] idPhoto,FeatureBean featureBean,Token token) {
        return delegate().savePerson(personBean,idPhoto,featureBean,token);
    }

    @Override
    public PersonBean savePerson (PersonBean personBean,String idPhotoMd5,String featureMd5,Token token) {
        return delegate().savePerson(personBean,idPhotoMd5,featureMd5,token);
    }

    @Override
    public PersonBean savePerson (PersonBean personBean,Token token) {
        return delegate().savePerson(personBean,token);
    }

    @Override
    public PersonGroupBean savePersonGroup (PersonGroupBean personGroupBean,Token token) {
        return delegate().savePersonGroup(personGroupBean,token);
    }

    @Override
    public int savePersons (List<byte[]> photos,List<PersonBean> persons,Token token) {
        return delegate().savePersons(photos,persons,token);
    }

    @Override
    public void savePersons (List<PersonBean> persons,Token token) {
         delegate().savePersons(persons,token);
    }

    @Override
    public void saveServiceConfig (Token token) {
         delegate().saveServiceConfig(token);
    }

    @Override
    public String sdkTaskQueueOf (String task,String sdkVersion,Token token) {
        return delegate().sdkTaskQueueOf(task,sdkVersion,token);
    }

    @Override
    public void setPersonExpiryDate (int personId,String expiryDate,Token token) {
         delegate().setPersonExpiryDate(personId,expiryDate,token);
    }

    @Override
    public void setPersonExpiryDate (int personId,long expiryDate,Token token) {
         delegate().setPersonExpiryDate(personId,expiryDate,token);
    }

    @Override
    public void setPersonExpiryDate (List<Integer> personIdList,long expiryDate,Token token) {
         delegate().setPersonExpiryDate(personIdList,expiryDate,token);
    }

    @Override
    public void setProperties (Map<String, String> config,Token token) {
         delegate().setProperties(config,token);
    }

    @Override
    public void setProperty (String key,String value,Token token) {
         delegate().setProperty(key,value,token);
    }

    @Override
    public String taskQueueOf (String task,Token token) {
        return delegate().taskQueueOf(task,token);
    }

    @Override
    public void unbindBorder (Integer personGroupId,Integer deviceGroupId,Token token) {
         delegate().unbindBorder(personGroupId,deviceGroupId,token);
    }

    @Override
    public void unregisterDevice (Token token) throws ServiceSecurityException{
         delegate().unregisterDevice(token);
    }

    /**
     * {@link IFaceLog#unregisterDevice(Token)}对应的unchecked方法,
     * 所有显式申明的异常都被封装到{@link RuntimeException}抛出<br>
     * @param token
     * @return void
     */
    public void unregisterDeviceUnchecked (Token token) {
        try{
             delegate().unregisterDevice(token);
        } catch(RuntimeException e){
            throw e;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeviceBean updateDevice (DeviceBean deviceBean,Token token) {
        return delegate().updateDevice(deviceBean,token);
    }

    @Override
    public String version () {
        return delegate().version();
    }

    @Override
    public Map<String, String> versionInfo () {
        return delegate().versionInfo();
    }
}