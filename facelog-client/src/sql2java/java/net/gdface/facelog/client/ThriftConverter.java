// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: thriftconverter.java.vm
// ______________________________________________________

package net.gdface.facelog.client;

public class ThriftConverter implements Constant{
    /** {@link IBeanConverter} implemention for convert between {@link DeviceBean} and thrift beans {@link net.gdface.facelog.client.thrift.DeviceBean} */
    public static final IBeanConverter<DeviceBean,net.gdface.facelog.client.thrift.DeviceBean> converterDeviceBean
            = new IBeanConverter.AbstractHandle<DeviceBean,net.gdface.facelog.client.thrift.DeviceBean>(){
        @Override
        protected void _fromRight(DeviceBean left, net.gdface.facelog.client.thrift.DeviceBean right) {
            left.resetIsModified();
            long modified = right.getModified();
            if(0L !=  (modified & FL_DEVICE_ID_ID_MASK))
                left.setId(right.getId());
            if(0L !=  (modified & FL_DEVICE_ID_NAME_MASK))
                left.setName(right.getName());
            if(0L !=  (modified & FL_DEVICE_ID_GROUP_ID_MASK))
                left.setGroupId(right.getGroupId());
            if(0L !=  (modified & FL_DEVICE_ID_VERSION_MASK))
                left.setVersion(right.getVersion());
            if(0L !=  (modified & FL_DEVICE_ID_SERIAL_NO_MASK))
                left.setSerialNo(right.getSerialNo());
            if(0L !=  (modified & FL_DEVICE_ID_MAC_MASK))
                left.setMac(right.getMac());
            if(0L !=  (modified & FL_DEVICE_ID_CREATE_TIME_MASK))
                left.setCreateTime(right.getCreateTime());
            if(0L !=  (modified & FL_DEVICE_ID_UPDATE_TIME_MASK))
                left.setUpdateTime(right.getUpdateTime());
            left.setNew(right.isNew());
        }

        @Override
        protected void _toRight(DeviceBean left, net.gdface.facelog.client.thrift.DeviceBean right) {
            if(left.checkIdInitialized() ){
                right.setId(left.getId());
            }
            if(left.checkNameInitialized() ){
                right.setName(left.getName());
            }
            if(left.checkGroupIdInitialized() ){
                right.setGroupId(left.getGroupId());
            }
            if(left.checkVersionInitialized() ){
                right.setVersion(left.getVersion());
            }
            if(left.checkSerialNoInitialized() ){
                right.setSerialNo(left.getSerialNo());
            }
            if(left.checkMacInitialized() ){
                right.setMac(left.getMac());
            }
// IGNORE field fl_device.create_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
/*
            if(left.checkCreateTimeInitialized() ){
                right.setCreateTime(left.getCreateTime().getTime());
            }
*/
// IGNORE field fl_device.update_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
/*
            if(left.checkUpdateTimeInitialized() ){
                right.setUpdateTime(left.getUpdateTime().getTime());
            }
*/
            right.setNew(left.isNew());
            right.setModified(left.getModified());
            right.setInitialized(left.getInitialized());
        }};
    /** {@link IBeanConverter} implemention for convert between {@link FaceBean} and thrift beans {@link net.gdface.facelog.client.thrift.FaceBean} */
    public static final IBeanConverter<FaceBean,net.gdface.facelog.client.thrift.FaceBean> converterFaceBean
            = new IBeanConverter.AbstractHandle<FaceBean,net.gdface.facelog.client.thrift.FaceBean>(){
        @Override
        protected void _fromRight(FaceBean left, net.gdface.facelog.client.thrift.FaceBean right) {
            left.resetIsModified();
            long modified = right.getModified();
            if(0L !=  (modified & FL_FACE_ID_ID_MASK))
                left.setId(right.getId());
            if(0L !=  (modified & FL_FACE_ID_IMAGE_MD5_MASK))
                left.setImageMd5(right.getImageMd5());
            if(0L !=  (modified & FL_FACE_ID_FACE_LEFT_MASK))
                left.setFaceLeft(right.getFaceLeft());
            if(0L !=  (modified & FL_FACE_ID_FACE_TOP_MASK))
                left.setFaceTop(right.getFaceTop());
            if(0L !=  (modified & FL_FACE_ID_FACE_WIDTH_MASK))
                left.setFaceWidth(right.getFaceWidth());
            if(0L !=  (modified & FL_FACE_ID_FACE_HEIGHT_MASK))
                left.setFaceHeight(right.getFaceHeight());
            if(0L !=  (modified & FL_FACE_ID_EYE_LEFTX_MASK))
                left.setEyeLeftx(right.getEyeLeftx());
            if(0L !=  (modified & FL_FACE_ID_EYE_LEFTY_MASK))
                left.setEyeLefty(right.getEyeLefty());
            if(0L !=  (modified & FL_FACE_ID_EYE_RIGHTX_MASK))
                left.setEyeRightx(right.getEyeRightx());
            if(0L !=  (modified & FL_FACE_ID_EYE_RIGHTY_MASK))
                left.setEyeRighty(right.getEyeRighty());
            if(0L !=  (modified & FL_FACE_ID_MOUTH_X_MASK))
                left.setMouthX(right.getMouthX());
            if(0L !=  (modified & FL_FACE_ID_MOUTH_Y_MASK))
                left.setMouthY(right.getMouthY());
            if(0L !=  (modified & FL_FACE_ID_NOSE_X_MASK))
                left.setNoseX(right.getNoseX());
            if(0L !=  (modified & FL_FACE_ID_NOSE_Y_MASK))
                left.setNoseY(right.getNoseY());
            if(0L !=  (modified & FL_FACE_ID_ANGLE_YAW_MASK))
                left.setAngleYaw(right.getAngleYaw());
            if(0L !=  (modified & FL_FACE_ID_ANGLE_PITCH_MASK))
                left.setAnglePitch(right.getAnglePitch());
            if(0L !=  (modified & FL_FACE_ID_ANGLE_ROLL_MASK))
                left.setAngleRoll(right.getAngleRoll());
            if(0L !=  (modified & FL_FACE_ID_EXT_INFO_MASK))
                left.setExtInfo(right.getExtInfo());
            if(0L !=  (modified & FL_FACE_ID_FEATURE_MD5_MASK))
                left.setFeatureMd5(right.getFeatureMd5());
            if(0L !=  (modified & FL_FACE_ID_CREATE_TIME_MASK))
                left.setCreateTime(right.getCreateTime());
            left.setNew(right.isNew());
        }

        @Override
        protected void _toRight(FaceBean left, net.gdface.facelog.client.thrift.FaceBean right) {
            if(left.checkIdInitialized() ){
                right.setId(left.getId());
            }
            if(left.checkImageMd5Initialized() ){
                right.setImageMd5(left.getImageMd5());
            }
            if(left.checkFaceLeftInitialized() ){
                right.setFaceLeft(left.getFaceLeft());
            }
            if(left.checkFaceTopInitialized() ){
                right.setFaceTop(left.getFaceTop());
            }
            if(left.checkFaceWidthInitialized() ){
                right.setFaceWidth(left.getFaceWidth());
            }
            if(left.checkFaceHeightInitialized() ){
                right.setFaceHeight(left.getFaceHeight());
            }
            if(left.checkEyeLeftxInitialized() ){
                right.setEyeLeftx(left.getEyeLeftx());
            }
            if(left.checkEyeLeftyInitialized() ){
                right.setEyeLefty(left.getEyeLefty());
            }
            if(left.checkEyeRightxInitialized() ){
                right.setEyeRightx(left.getEyeRightx());
            }
            if(left.checkEyeRightyInitialized() ){
                right.setEyeRighty(left.getEyeRighty());
            }
            if(left.checkMouthXInitialized() ){
                right.setMouthX(left.getMouthX());
            }
            if(left.checkMouthYInitialized() ){
                right.setMouthY(left.getMouthY());
            }
            if(left.checkNoseXInitialized() ){
                right.setNoseX(left.getNoseX());
            }
            if(left.checkNoseYInitialized() ){
                right.setNoseY(left.getNoseY());
            }
            if(left.checkAngleYawInitialized() ){
                right.setAngleYaw(left.getAngleYaw());
            }
            if(left.checkAnglePitchInitialized() ){
                right.setAnglePitch(left.getAnglePitch());
            }
            if(left.checkAngleRollInitialized() ){
                right.setAngleRoll(left.getAngleRoll());
            }
            if(left.checkExtInfoInitialized() ){
                right.setExtInfo(left.getExtInfo());
            }
            if(left.checkFeatureMd5Initialized() ){
                right.setFeatureMd5(left.getFeatureMd5());
            }
// IGNORE field fl_face.create_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
/*
            if(left.checkCreateTimeInitialized() ){
                right.setCreateTime(left.getCreateTime().getTime());
            }
*/
            right.setNew(left.isNew());
            right.setModified(left.getModified());
            right.setInitialized(left.getInitialized());
        }};
    /** {@link IBeanConverter} implemention for convert between {@link FeatureBean} and thrift beans {@link net.gdface.facelog.client.thrift.FeatureBean} */
    public static final IBeanConverter<FeatureBean,net.gdface.facelog.client.thrift.FeatureBean> converterFeatureBean
            = new IBeanConverter.AbstractHandle<FeatureBean,net.gdface.facelog.client.thrift.FeatureBean>(){
        @Override
        protected void _fromRight(FeatureBean left, net.gdface.facelog.client.thrift.FeatureBean right) {
            left.resetIsModified();
            long modified = right.getModified();
            if(0L !=  (modified & FL_FEATURE_ID_MD5_MASK))
                left.setMd5(right.getMd5());
            if(0L !=  (modified & FL_FEATURE_ID_PERSON_ID_MASK))
                left.setPersonId(right.getPersonId());
            if(0L !=  (modified & FL_FEATURE_ID_FEATURE_MASK))
                left.setFeature(right.getFeature());
            if(0L !=  (modified & FL_FEATURE_ID_UPDATE_TIME_MASK))
                left.setUpdateTime(right.getUpdateTime());
            left.setNew(right.isNew());
        }

        @Override
        protected void _toRight(FeatureBean left, net.gdface.facelog.client.thrift.FeatureBean right) {
            if(left.checkMd5Initialized() ){
                right.setMd5(left.getMd5());
            }
            if(left.checkPersonIdInitialized() ){
                right.setPersonId(left.getPersonId());
            }
            if(left.checkFeatureInitialized() ){
                right.setFeature(left.getFeature());
            }
// IGNORE field fl_feature.update_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
/*
            if(left.checkUpdateTimeInitialized() ){
                right.setUpdateTime(left.getUpdateTime().getTime());
            }
*/
            right.setNew(left.isNew());
            right.setModified(left.getModified());
            right.setInitialized(left.getInitialized());
        }};
    /** {@link IBeanConverter} implemention for convert between {@link ImageBean} and thrift beans {@link net.gdface.facelog.client.thrift.ImageBean} */
    public static final IBeanConverter<ImageBean,net.gdface.facelog.client.thrift.ImageBean> converterImageBean
            = new IBeanConverter.AbstractHandle<ImageBean,net.gdface.facelog.client.thrift.ImageBean>(){
        @Override
        protected void _fromRight(ImageBean left, net.gdface.facelog.client.thrift.ImageBean right) {
            left.resetIsModified();
            long modified = right.getModified();
            if(0L !=  (modified & FL_IMAGE_ID_MD5_MASK))
                left.setMd5(right.getMd5());
            if(0L !=  (modified & FL_IMAGE_ID_FORMAT_MASK))
                left.setFormat(right.getFormat());
            if(0L !=  (modified & FL_IMAGE_ID_WIDTH_MASK))
                left.setWidth(right.getWidth());
            if(0L !=  (modified & FL_IMAGE_ID_HEIGHT_MASK))
                left.setHeight(right.getHeight());
            if(0L !=  (modified & FL_IMAGE_ID_DEPTH_MASK))
                left.setDepth(right.getDepth());
            if(0L !=  (modified & FL_IMAGE_ID_FACE_NUM_MASK))
                left.setFaceNum(right.getFaceNum());
            if(0L !=  (modified & FL_IMAGE_ID_THUMB_MD5_MASK))
                left.setThumbMd5(right.getThumbMd5());
            if(0L !=  (modified & FL_IMAGE_ID_DEVICE_ID_MASK))
                left.setDeviceId(right.getDeviceId());
            left.setNew(right.isNew());
        }

        @Override
        protected void _toRight(ImageBean left, net.gdface.facelog.client.thrift.ImageBean right) {
            if(left.checkMd5Initialized() ){
                right.setMd5(left.getMd5());
            }
            if(left.checkFormatInitialized() ){
                right.setFormat(left.getFormat());
            }
            if(left.checkWidthInitialized() ){
                right.setWidth(left.getWidth());
            }
            if(left.checkHeightInitialized() ){
                right.setHeight(left.getHeight());
            }
            if(left.checkDepthInitialized() ){
                right.setDepth(left.getDepth());
            }
            if(left.checkFaceNumInitialized() ){
                right.setFaceNum(left.getFaceNum());
            }
            if(left.checkThumbMd5Initialized() ){
                right.setThumbMd5(left.getThumbMd5());
            }
            if(left.checkDeviceIdInitialized() ){
                right.setDeviceId(left.getDeviceId());
            }
            right.setNew(left.isNew());
            right.setModified(left.getModified());
            right.setInitialized(left.getInitialized());
        }};
    /** {@link IBeanConverter} implemention for convert between {@link LogBean} and thrift beans {@link net.gdface.facelog.client.thrift.LogBean} */
    public static final IBeanConverter<LogBean,net.gdface.facelog.client.thrift.LogBean> converterLogBean
            = new IBeanConverter.AbstractHandle<LogBean,net.gdface.facelog.client.thrift.LogBean>(){
        @Override
        protected void _fromRight(LogBean left, net.gdface.facelog.client.thrift.LogBean right) {
            left.resetIsModified();
            long modified = right.getModified();
            if(0L !=  (modified & FL_LOG_ID_ID_MASK))
                left.setId(right.getId());
            if(0L !=  (modified & FL_LOG_ID_PERSON_ID_MASK))
                left.setPersonId(right.getPersonId());
            if(0L !=  (modified & FL_LOG_ID_DEVICE_ID_MASK))
                left.setDeviceId(right.getDeviceId());
            if(0L !=  (modified & FL_LOG_ID_VERIFY_FEATURE_MASK))
                left.setVerifyFeature(right.getVerifyFeature());
            if(0L !=  (modified & FL_LOG_ID_COMPARE_FACE_MASK))
                left.setCompareFace(right.getCompareFace());
            if(0L !=  (modified & FL_LOG_ID_SIMILARTY_MASK))
                left.setSimilarty(right.getSimilarty());
            if(0L !=  (modified & FL_LOG_ID_VERIFY_TIME_MASK))
                left.setVerifyTime(right.getVerifyTime());
            if(0L !=  (modified & FL_LOG_ID_CREATE_TIME_MASK))
                left.setCreateTime(right.getCreateTime());
            left.setNew(right.isNew());
        }

        @Override
        protected void _toRight(LogBean left, net.gdface.facelog.client.thrift.LogBean right) {
            if(left.checkIdInitialized() ){
                right.setId(left.getId());
            }
            if(left.checkPersonIdInitialized() ){
                right.setPersonId(left.getPersonId());
            }
            if(left.checkDeviceIdInitialized() ){
                right.setDeviceId(left.getDeviceId());
            }
            if(left.checkVerifyFeatureInitialized() ){
                right.setVerifyFeature(left.getVerifyFeature());
            }
            if(left.checkCompareFaceInitialized() ){
                right.setCompareFace(left.getCompareFace());
            }
            if(left.checkSimilartyInitialized() ){
                right.setSimilarty(left.getSimilarty());
            }
            if(left.checkVerifyTimeInitialized() ){
                right.setVerifyTime(left.getVerifyTime().getTime());
            }
// IGNORE field fl_log.create_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
/*
            if(left.checkCreateTimeInitialized() ){
                right.setCreateTime(left.getCreateTime().getTime());
            }
*/
            right.setNew(left.isNew());
            right.setModified(left.getModified());
            right.setInitialized(left.getInitialized());
        }};
    /** {@link IBeanConverter} implemention for convert between {@link PersonBean} and thrift beans {@link net.gdface.facelog.client.thrift.PersonBean} */
    public static final IBeanConverter<PersonBean,net.gdface.facelog.client.thrift.PersonBean> converterPersonBean
            = new IBeanConverter.AbstractHandle<PersonBean,net.gdface.facelog.client.thrift.PersonBean>(){
        @Override
        protected void _fromRight(PersonBean left, net.gdface.facelog.client.thrift.PersonBean right) {
            left.resetIsModified();
            long modified = right.getModified();
            if(0L !=  (modified & FL_PERSON_ID_ID_MASK))
                left.setId(right.getId());
            if(0L !=  (modified & FL_PERSON_ID_GROUP_ID_MASK))
                left.setGroupId(right.getGroupId());
            if(0L !=  (modified & FL_PERSON_ID_NAME_MASK))
                left.setName(right.getName());
            if(0L !=  (modified & FL_PERSON_ID_SEX_MASK))
                left.setSex(right.getSex());
            if(0L !=  (modified & FL_PERSON_ID_BIRTHDATE_MASK))
                left.setBirthdate(right.getBirthdate());
            if(0L !=  (modified & FL_PERSON_ID_PAPERS_TYPE_MASK))
                left.setPapersType(right.getPapersType());
            if(0L !=  (modified & FL_PERSON_ID_PAPERS_NUM_MASK))
                left.setPapersNum(right.getPapersNum());
            if(0L !=  (modified & FL_PERSON_ID_IMAGE_MD5_MASK))
                left.setImageMd5(right.getImageMd5());
            if(0L !=  (modified & FL_PERSON_ID_EXPIRY_DATE_MASK))
                left.setExpiryDate(right.getExpiryDate());
            if(0L !=  (modified & FL_PERSON_ID_CREATE_TIME_MASK))
                left.setCreateTime(right.getCreateTime());
            if(0L !=  (modified & FL_PERSON_ID_UPDATE_TIME_MASK))
                left.setUpdateTime(right.getUpdateTime());
            left.setNew(right.isNew());
        }

        @Override
        protected void _toRight(PersonBean left, net.gdface.facelog.client.thrift.PersonBean right) {
            if(left.checkIdInitialized() ){
                right.setId(left.getId());
            }
            if(left.checkGroupIdInitialized() ){
                right.setGroupId(left.getGroupId());
            }
            if(left.checkNameInitialized() ){
                right.setName(left.getName());
            }
            if(left.checkSexInitialized() ){
                right.setSex(left.getSex());
            }
            if(left.checkBirthdateInitialized() ){
                right.setBirthdate(left.getBirthdate().getTime());
            }
            if(left.checkPapersTypeInitialized() ){
                right.setPapersType(left.getPapersType());
            }
            if(left.checkPapersNumInitialized() ){
                right.setPapersNum(left.getPapersNum());
            }
            if(left.checkImageMd5Initialized() ){
                right.setImageMd5(left.getImageMd5());
            }
            if(left.checkExpiryDateInitialized() ){
                right.setExpiryDate(left.getExpiryDate().getTime());
            }
// IGNORE field fl_person.create_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
/*
            if(left.checkCreateTimeInitialized() ){
                right.setCreateTime(left.getCreateTime().getTime());
            }
*/
// IGNORE field fl_person.update_time , controlled by 'general.beanconverter.tonative.ignore' in properties file
/*
            if(left.checkUpdateTimeInitialized() ){
                right.setUpdateTime(left.getUpdateTime().getTime());
            }
*/
            right.setNew(left.isNew());
            right.setModified(left.getModified());
            right.setInitialized(left.getInitialized());
        }};
    /** {@link IBeanConverter} implemention for convert between {@link LogLightBean} and thrift beans {@link net.gdface.facelog.client.thrift.LogLightBean} */
    public static final IBeanConverter<LogLightBean,net.gdface.facelog.client.thrift.LogLightBean> converterLogLightBean
            = new IBeanConverter.AbstractHandle<LogLightBean,net.gdface.facelog.client.thrift.LogLightBean>(){
        @Override
        protected void _fromRight(LogLightBean left, net.gdface.facelog.client.thrift.LogLightBean right) {
            left.resetIsModified();
            long modified = right.getModified();
            if(0L !=  (modified & FL_LOG_LIGHT_ID_ID_MASK))
                left.setId(right.getId());
            if(0L !=  (modified & FL_LOG_LIGHT_ID_PERSON_ID_MASK))
                left.setPersonId(right.getPersonId());
            if(0L !=  (modified & FL_LOG_LIGHT_ID_NAME_MASK))
                left.setName(right.getName());
            if(0L !=  (modified & FL_LOG_LIGHT_ID_PAPERS_TYPE_MASK))
                left.setPapersType(right.getPapersType());
            if(0L !=  (modified & FL_LOG_LIGHT_ID_PAPERS_NUM_MASK))
                left.setPapersNum(right.getPapersNum());
            if(0L !=  (modified & FL_LOG_LIGHT_ID_VERIFY_TIME_MASK))
                left.setVerifyTime(right.getVerifyTime());
            left.setNew(right.isNew());
        }

        @Override
        protected void _toRight(LogLightBean left, net.gdface.facelog.client.thrift.LogLightBean right) {
            if(left.checkIdInitialized() ){
                right.setId(left.getId());
            }
            if(left.checkPersonIdInitialized() ){
                right.setPersonId(left.getPersonId());
            }
            if(left.checkNameInitialized() ){
                right.setName(left.getName());
            }
            if(left.checkPapersTypeInitialized() ){
                right.setPapersType(left.getPapersType());
            }
            if(left.checkPapersNumInitialized() ){
                right.setPapersNum(left.getPapersNum());
            }
            if(left.checkVerifyTimeInitialized() ){
                right.setVerifyTime(left.getVerifyTime().getTime());
            }
            right.setNew(left.isNew());
            right.setModified(left.getModified());
            right.setInitialized(left.getInitialized());
        }};
}