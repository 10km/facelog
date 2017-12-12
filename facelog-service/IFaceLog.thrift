namespace java.swift net.gdface.facelog.service
namespace py gdface.thrift
namespace java com.gdface
namespace cpp gdface


enum TokenType {
  UNINITIALIZED, DEVICE, PERSON, ROOT
}

enum SecurityExceptionType {
  UNCLASSIFIED, INVALID_MAC, INVALID_SN, OCCUPIED_SN, INVALID_TOKEN, INVALID_DEVICE_ID, INVALID_PERSON_ID, INVALID_PASSWORD
}

enum MQParam {
  REDIS_URI, CMD_CHANNEL, LOG_MONITOR_CHANNEL, HB_MONITOR_CHANNEL, HB_INTERVAL, HB_EXPIRE
}

struct FaceBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  string imageMd5;
  6:  i32 faceLeft;
  7:  i32 faceTop;
  8:  i32 faceWidth;
  9:  i32 faceHeight;
  10:  i32 eyeLeftx;
  11:  i32 eyeLefty;
  12:  i32 eyeRightx;
  13:  i32 eyeRighty;
  14:  i32 mouthX;
  15:  i32 mouthY;
  16:  i32 noseX;
  17:  i32 noseY;
  18:  i32 angleYaw;
  19:  i32 anglePitch;
  20:  i32 angleRoll;
  21:  binary extInfo;
  22:  string featureMd5;
}

exception ServiceRuntimeException {
  1:  string message;
  2:  string causeClass;
  3:  string serviceStackTraceMessage;
  4:  string causeFields;
  5:  i32 type;
}

exception DuplicateRecordException {
  1:  string message;
  2:  string causeClass;
  3:  string serviceStackTraceMessage;
  4:  string causeFields;
}

struct FeatureBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  string md5;
  5:  i32 personId;
  6:  binary feature;
  7:  i64 updateTime;
}

struct ImageBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  string md5;
  5:  string format;
  6:  i32 width;
  7:  i32 height;
  8:  i32 depth;
  9:  i32 faceNum;
  10:  string thumbMd5;
  11:  i32 deviceId;
}

struct LogBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  i32 personId;
  6:  i32 deviceId;
  7:  string verifyFeature;
  8:  i32 compareFace;
  9:  i32 verifyStatus;
  10:  double similarty;
  11:  i64 verifyTime;
  12:  i64 createTime;
}

struct DeviceGroupBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  string name;
  6:  i32 leaf;
  7:  i32 parent;
  8:  string remark;
  9:  binary extBin;
  10:  string extTxt;
  11:  i64 createTime;
  12:  i64 updateTime;
}

struct PersonGroupBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  string name;
  6:  i32 leaf;
  7:  i32 parent;
  8:  string remark;
  9:  binary extBin;
  10:  string extTxt;
  11:  i64 createTime;
  12:  i64 updateTime;
}

struct DeviceBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  i32 groupId;
  6:  string name;
  7:  string version;
  8:  string serialNo;
  9:  string mac;
  10:  string remark;
  11:  i64 createTime;
  12:  i64 updateTime;
}

struct PersonBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  i32 groupId;
  6:  string name;
  7:  i32 sex;
  8:  i32 rank;
  9:  string password;
  10:  i64 birthdate;
  11:  string mobilePhone;
  12:  i32 papersType;
  13:  string papersNum;
  14:  string imageMd5;
  15:  i64 expiryDate;
  16:  string remark;
  17:  i64 createTime;
  18:  i64 updateTime;
}

struct LogLightBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  i32 personId;
  6:  string name;
  7:  i32 papersType;
  8:  string papersNum;
  9:  i64 verifyTime;
}

struct PermitBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 deviceGroupId;
  5:  i32 personGroupId;
  6:  string remark;
  7:  binary extBin;
  8:  string extTxt;
  9:  i64 createTime;
}

struct Token {
  1:  i32 id;
  2:  TokenType type;
  3:  i64 t1;
  4:  i64 t2;
}

exception ServiceSecurityException {
  1:  string message;
  2:  string causeClass;
  3:  string serviceStackTraceMessage;
  4:  string causeFields;
  5:  SecurityExceptionType type;
  6:  i32 deviceID;
}

service IFaceLog {
  FeatureBean addFeature(1:  binary feature, 2:  i32 personId, 3:  list<FaceBean> faecBeans, 4:  Token token) throws (1: ServiceRuntimeException ex1, 2: DuplicateRecordException ex2);
  FeatureBean addFeatureMulti(1:  binary feature, 2:  i32 personId, 3:  map<binary, FaceBean> faceInfo, 4:  i32 deviceId, 5:  Token token) throws (1: ServiceRuntimeException ex1, 2: DuplicateRecordException ex2);
  ImageBean addImage(1:  binary imageData, 2:  i32 deviceId, 3:  FaceBean faceBean, 4:  i32 personId, 5:  Token token) throws (1: ServiceRuntimeException ex1, 2: DuplicateRecordException ex2);
  void addLog(1:  LogBean bean, 2:  Token token) throws (1: ServiceRuntimeException ex1, 2: DuplicateRecordException ex2);
  void addLogs(1:  list<LogBean> beans, 2:  Token token) throws (1: ServiceRuntimeException ex1, 2: DuplicateRecordException ex2);
  void addPermit(1:  DeviceGroupBean deviceGroup, 2:  PersonGroupBean personGroup, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  void addPermitById(1:  i32 deviceGroupId, 2:  i32 personGroupId, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  string applyAckChannel(1:  Token token) throws (1: ServiceRuntimeException ex1);
  i64 applyCmdSn(1:  Token token) throws (1: ServiceRuntimeException ex1);
  Token applyPersonToken(1:  i32 personId) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  Token applyRootToken(1:  string passwordMD5) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  i32 countDeviceByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  i32 countDeviceGroupByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  i32 countLogByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  i32 countLogLightByVerifyTime(1:  i64 timestamp) throws (1: ServiceRuntimeException ex1);
  i32 countLogLightByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  i32 countPersonByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  i32 countPersonGroupByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  i32 deleteAllFeaturesByPersonId(1:  i32 personId, 2:  bool deleteImage, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deleteDeviceGroup(1:  i32 deviceGroupId, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  list<string> deleteFeature(1:  string featureMd5, 2:  bool deleteImage, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deleteImage(1:  string imageMd5, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePermit(1:  DeviceGroupBean deviceGroup, 2:  PersonGroupBean personGroup, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePerson(1:  i32 personId, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersonByPapersNum(1:  string papersNum, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersonGroup(1:  i32 personGroupId, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersons(1:  list<i32> personIdList, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersonsByPapersNum(1:  list<string> papersNumlist, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  void disablePerson(1:  i32 personId, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  void disablePersonList(1:  list<i32> personIdList, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  bool existsDevice(1:  i32 id) throws (1: ServiceRuntimeException ex1);
  bool existsFeature(1:  string md5) throws (1: ServiceRuntimeException ex1);
  bool existsImage(1:  string md5) throws (1: ServiceRuntimeException ex1);
  bool existsPerson(1:  i32 persionId) throws (1: ServiceRuntimeException ex1);
  DeviceBean getDevice(1:  i32 deviceId) throws (1: ServiceRuntimeException ex1);
  DeviceGroupBean getDeviceGroup(1:  i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  list<DeviceGroupBean> getDeviceGroups(1:  list<i32> groupIdList) throws (1: ServiceRuntimeException ex1);
  list<i32> getDeviceGroupsBelongs(1:  i32 deviceId) throws (1: ServiceRuntimeException ex1);
  i32 getDeviceIdOfFeature(1:  string featureMd5) throws (1: ServiceRuntimeException ex1);
  list<DeviceBean> getDevices(1:  list<i32> idList) throws (1: ServiceRuntimeException ex1);
  list<i32> getDevicesOfGroup(1:  i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  FeatureBean getFeature(1:  string md5) throws (1: ServiceRuntimeException ex1);
  list<string> getFeatureBeansByPersonId(1:  i32 personId) throws (1: ServiceRuntimeException ex1);
  binary getFeatureBytes(1:  string md5) throws (1: ServiceRuntimeException ex1);
  list<FeatureBean> getFeatures(1:  list<string> md5) throws (1: ServiceRuntimeException ex1);
  list<string> getFeaturesOfPerson(1:  i32 personId) throws (1: ServiceRuntimeException ex1);
  bool getGroupPermit(1:  i32 deviceId, 2:  i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  list<bool> getGroupPermits(1:  i32 deviceId, 2:  list<i32> personGroupIdList) throws (1: ServiceRuntimeException ex1);
  ImageBean getImage(1:  string imageMD5) throws (1: ServiceRuntimeException ex1);
  binary getImageBytes(1:  string imageMD5) throws (1: ServiceRuntimeException ex1);
  list<string> getImagesAssociatedByFeature(1:  string featureMd5) throws (1: ServiceRuntimeException ex1);
  list<LogBean> getLogBeansByPersonId(1:  i32 personId) throws (1: ServiceRuntimeException ex1);
  PersonBean getPerson(1:  i32 personId) throws (1: ServiceRuntimeException ex1);
  PersonBean getPersonByPapersNum(1:  string papersNum) throws (1: ServiceRuntimeException ex1);
  PersonGroupBean getPersonGroup(1:  i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  list<PersonGroupBean> getPersonGroups(1:  list<i32> groupIdList) throws (1: ServiceRuntimeException ex1);
  list<i32> getPersonGroupsBelongs(1:  i32 personId) throws (1: ServiceRuntimeException ex1);
  bool getPersonPermit(1:  i32 deviceId, 2:  i32 personId) throws (1: ServiceRuntimeException ex1);
  list<bool> getPersonPermits(1:  i32 deviceId, 2:  list<i32> personIdList) throws (1: ServiceRuntimeException ex1);
  list<PersonBean> getPersons(1:  list<i32> idList) throws (1: ServiceRuntimeException ex1);
  list<i32> getPersonsOfGroup(1:  i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  map<MQParam, string> getRedisParameters(1:  Token token) throws (1: ServiceRuntimeException ex1);
  map<string, string> getServiceConfig(1:  Token token) throws (1: ServiceRuntimeException ex1);
  list<i32> getSubDeviceGroup(1:  i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> getSubPersonGroup(1:  i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  bool isDisable(1:  i32 personId) throws (1: ServiceRuntimeException ex1);
  list<i32> listOfParentForDeviceGroup(1:  i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> listOfParentForPersonGroup(1:  i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> loadAllPerson() throws (1: ServiceRuntimeException ex1);
  list<DeviceBean> loadDeviceByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadDeviceGroupByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadDeviceGroupIdByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  list<i32> loadDeviceIdByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  list<string> loadFeatureMd5ByUpdate(1:  i64 timestamp) throws (1: ServiceRuntimeException ex1);
  list<LogBean> loadLogByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<LogLightBean> loadLogLightByVerifyTime(1:  i64 timestamp, 2:  i32 startRow, 3:  i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<LogLightBean> loadLogLightByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<PermitBean> loadPermitByUpdate(1:  i64 timestamp) throws (1: ServiceRuntimeException ex1);
  list<PersonBean> loadPersonByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonGroupByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonGroupIdByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonIdByUpdateTime(1:  i64 timestamp) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonIdByWhere(1:  string where) throws (1: ServiceRuntimeException ex1);
  list<i32> loadUpdatedPersons(1:  i64 timestamp) throws (1: ServiceRuntimeException ex1);
  void offline(1:  Token token) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  Token online(1:  DeviceBean device) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  DeviceBean registerDevice(1:  DeviceBean newDevice) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  void releasePersonToken(1:  Token token) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  void releaseRootToken(1:  Token token) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  void replaceFeature(1:  i32 personId, 2:  string featureMd5, 3:  bool deleteOldFeatureImage, 4:  Token token) throws (1: ServiceRuntimeException ex1);
  DeviceBean saveDevice(1:  DeviceBean deviceBean, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  DeviceGroupBean saveDeviceGroup(1:  DeviceGroupBean deviceGroupBean, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePerson(1:  PersonBean bean, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonFull(1:  PersonBean bean, 2:  binary idPhoto, 3:  binary feature, 4:  binary featureImage, 5:  FaceBean featureFaceBean, 6:  i32 deviceId, 7:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonGroupBean savePersonGroup(1:  PersonGroupBean personGroupBean, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhoto(1:  PersonBean bean, 2:  binary idPhoto, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeature(1:  PersonBean bean, 2:  binary idPhoto, 3:  FeatureBean featureBean, 4:  i32 deviceId, 5:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeatureMultiFaces(1:  PersonBean bean, 2:  binary idPhoto, 3:  binary feature, 4:  list<FaceBean> faceBeans, 5:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeatureMultiImage(1:  PersonBean bean, 2:  binary idPhoto, 3:  binary feature, 4:  map<binary, FaceBean> faceInfo, 5:  i32 deviceId, 6:  Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeatureSaved(1:  PersonBean bean, 2:  string idPhotoMd5, 3:  string featureMd5, 4:  Token token) throws (1: ServiceRuntimeException ex1);
  void savePersons(1:  list<PersonBean> beans, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  i32 savePersonsWithPhoto(1:  map<binary, PersonBean> persons, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  void saveServiceConfig(1:  Token token) throws (1: ServiceRuntimeException ex1);
  void setPersonExpiryDate(1:  i32 personId, 2:  i64 expiryDate, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  void setPersonExpiryDateList(1:  list<i32> personIdList, 2:  i64 expiryDate, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  void setProperties(1:  map<string, string> config, 2:  Token token) throws (1: ServiceRuntimeException ex1);
  void setProperty(1:  string key, 2:  string value, 3:  Token token) throws (1: ServiceRuntimeException ex1);
  void unregisterDevice(1:  i32 deviceId, 2:  Token token) throws (1: ServiceRuntimeException ex1, 2: ServiceSecurityException ex2);
  DeviceBean updateDevice(1:  DeviceBean deviceBean, 2:  Token token) throws (1: ServiceRuntimeException ex1);
}
