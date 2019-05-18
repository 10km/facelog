namespace java.swift net.gdface.facelog.decorator
namespace cpp gdface
namespace java net.gdface.facelog.client.thrift
namespace py gdface.thrift


enum TokenType {
  UNINITIALIZED, DEVICE, PERSON, ROOT
}

enum SecurityExceptionType {
  UNCLASSIFIED, INVALID_MAC, INVALID_SN, OCCUPIED_SN, INVALID_TOKEN, INVALID_DEVICE_ID, INVALID_PERSON_ID, INVALID_PASSWORD, REJECT_APPLY, ACCESS_DENIED
}

enum MQParam {
  REDIS_URI, WEBREDIS_URL, CMD_CHANNEL, LOG_MONITOR_CHANNEL, HB_MONITOR_CHANNEL, HB_INTERVAL, HB_EXPIRE
}

struct FaceBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 id;
  5: optional string imageMd5;
  6: optional i32 faceLeft;
  7: optional i32 faceTop;
  8: optional i32 faceWidth;
  9: optional i32 faceHeight;
  10: optional i32 eyeLeftx;
  11: optional i32 eyeLefty;
  12: optional i32 eyeRightx;
  13: optional i32 eyeRighty;
  14: optional i32 mouthX;
  15: optional i32 mouthY;
  16: optional i32 noseX;
  17: optional i32 noseY;
  18: optional i32 angleYaw;
  19: optional i32 anglePitch;
  20: optional i32 angleRoll;
  21: optional binary extInfo;
  22: optional string featureMd5;
}

exception DuplicateRecordException {
  1: optional string message;
  2: optional string causeClass;
  3: optional string serviceStackTraceMessage;
  4: optional string causeFields;
}

exception ServiceRuntimeException {
  1: optional string message;
  2: optional string causeClass;
  3: optional string serviceStackTraceMessage;
  4: optional string causeFields;
  5: required i32 type;
}

struct FeatureBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional string md5;
  5: optional i32 personId;
  6: optional binary feature;
  7: optional i64 updateTime;
}

struct ImageBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional string md5;
  5: optional string format;
  6: optional i32 width;
  7: optional i32 height;
  8: optional i32 depth;
  9: optional i32 faceNum;
  10: optional string thumbMd5;
  11: optional i32 deviceId;
}

struct LogBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 id;
  5: optional i32 personId;
  6: optional i32 deviceId;
  7: optional string verifyFeature;
  8: optional i32 compareFace;
  9: optional i32 verifyStatus;
  10: optional double similarty;
  11: optional i64 verifyTime;
  12: optional i64 createTime;
}

struct DeviceGroupBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 id;
  5: optional string name;
  6: optional i32 leaf;
  7: optional i32 parent;
  8: optional i32 rootGroup;
  9: optional string remark;
  10: optional binary extBin;
  11: optional string extTxt;
  12: optional i64 createTime;
  13: optional i64 updateTime;
}

struct PersonGroupBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 id;
  5: optional string name;
  6: optional i32 leaf;
  7: optional i32 parent;
  8: optional i32 rootGroup;
  9: optional string remark;
  10: optional binary extBin;
  11: optional string extTxt;
  12: optional i64 createTime;
  13: optional i64 updateTime;
}

struct DeviceBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 id;
  5: optional i32 groupId;
  6: optional string name;
  7: optional string productName;
  8: optional string model;
  9: optional string vendor;
  10: optional string manufacturer;
  11: optional i64 madeDate;
  12: optional string version;
  13: optional string serialNo;
  14: optional string mac;
  15: optional string remark;
  16: optional binary extBin;
  17: optional string extTxt;
  18: optional i64 createTime;
  19: optional i64 updateTime;
}

struct PersonBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 id;
  5: optional i32 groupId;
  6: optional string name;
  7: optional i32 sex;
  8: optional i32 rank;
  9: optional string password;
  10: optional i64 birthdate;
  11: optional string mobilePhone;
  12: optional i32 papersType;
  13: optional string papersNum;
  14: optional string imageMd5;
  15: optional i64 expiryDate;
  16: optional string remark;
  17: optional binary extBin;
  18: optional string extTxt;
  19: optional i64 createTime;
  20: optional i64 updateTime;
}

struct LogLightBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 id;
  5: optional i32 personId;
  6: optional string name;
  7: optional i32 papersType;
  8: optional string papersNum;
  9: optional i64 verifyTime;
}

struct PermitBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4: optional i32 deviceGroupId;
  5: optional i32 personGroupId;
  6: optional string remark;
  7: optional binary extBin;
  8: optional string extTxt;
  9: optional i64 createTime;
}

struct Token {
  1: required i32 id;
  2: required i32 t1;
  3: required i32 t2;
  4: required i32 t3;
  5: required i32 t4;
  6: optional TokenType type;
}

exception ServiceSecurityException {
  1: optional string message;
  2: optional string causeClass;
  3: optional string serviceStackTraceMessage;
  4: optional string causeFields;
  5: optional i32 deviceID;
  6: optional SecurityExceptionType type;
}

service IFaceLog {
  FeatureBean addFeature(1: optional binary feature, 2: optional i32 personId, 3: optional list<FaceBean> faecBeans, 4: optional Token token) throws (1: DuplicateRecordException ex1, 2: ServiceRuntimeException ex2);
  FeatureBean addFeatureMulti(1: optional binary feature, 2: optional i32 personId, 3: optional map<binary, FaceBean> faceInfo, 4: optional i32 deviceId, 5: optional Token token) throws (1: DuplicateRecordException ex1, 2: ServiceRuntimeException ex2);
  FeatureBean addFeatureWithImage(1: optional binary feature, 2: optional i32 personId, 3: required bool asIdPhotoIfAbsent, 4: optional binary featurePhoto, 5: optional FaceBean faceBean, 6: optional i32 deviceId, 7: optional Token token) throws (1: DuplicateRecordException ex1, 2: ServiceRuntimeException ex2);
  ImageBean addImage(1: optional binary imageData, 2: optional i32 deviceId, 3: optional FaceBean faceBean, 4: optional i32 personId, 5: optional Token token) throws (1: DuplicateRecordException ex1, 2: ServiceRuntimeException ex2);
  void addLog(1: optional LogBean bean, 2: optional Token token) throws (1: DuplicateRecordException ex1, 2: ServiceRuntimeException ex2);
  void addLogs(1: optional list<LogBean> beans, 2: optional Token token) throws (1: DuplicateRecordException ex1, 2: ServiceRuntimeException ex2);
  void addPermit(1: optional DeviceGroupBean deviceGroup, 2: optional PersonGroupBean personGroup, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  void addPermitById(1: required i32 deviceGroupId, 2: required i32 personGroupId, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  string applyAckChannel(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  string applyAckChannelWithDuration(1: optional Token token, 2: required i64 duration) throws (1: ServiceRuntimeException ex1);
  i64 applyCmdSn(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  Token applyPersonToken(1: required i32 personId, 2: optional string password, 3: required bool isMd5) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  Token applyRootToken(1: optional string password, 2: required bool isMd5) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  Token applyUserToken(1: required i32 userid, 2: optional string password, 3: required bool isMd5) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  void bindBorder(1: optional i32 personGroupId, 2: optional i32 deviceGroupId, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  list<i32> childListForDeviceGroup(1: required i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> childListForPersonGroup(1: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  i32 countDeviceByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  i32 countDeviceGroupByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  i32 countLogByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  i32 countLogLightByVerifyTime(1: required i64 timestamp) throws (1: ServiceRuntimeException ex1);
  i32 countLogLightByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  i32 countPersonByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  i32 countPersonGroupByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  i32 deleteAllFeaturesByPersonId(1: required i32 personId, 2: required bool deleteImage, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deleteDeviceGroup(1: required i32 deviceGroupId, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  list<string> deleteFeature(1: optional string featureMd5, 2: required bool deleteImage, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deleteGroupPermitOnDeviceGroup(1: required i32 deviceGroupId, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deleteImage(1: optional string imageMd5, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePermit(1: optional DeviceGroupBean deviceGroup, 2: optional PersonGroupBean personGroup, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePermitById(1: required i32 deviceGroupId, 2: required i32 personGroupId, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePerson(1: required i32 personId, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersonByPapersNum(1: optional string papersNum, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersonGroup(1: required i32 personGroupId, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersonGroupPermit(1: required i32 personGroupId, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersons(1: optional list<i32> personIdList, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 deletePersonsByPapersNum(1: optional list<string> papersNumlist, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  void disablePerson(1: required i32 personId, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  void disablePersonList(1: optional list<i32> personIdList, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  bool existsDevice(1: required i32 id) throws (1: ServiceRuntimeException ex1);
  bool existsFeature(1: optional string md5) throws (1: ServiceRuntimeException ex1);
  bool existsImage(1: optional string md5) throws (1: ServiceRuntimeException ex1);
  bool existsPerson(1: required i32 persionId) throws (1: ServiceRuntimeException ex1);
  DeviceBean getDevice(1: required i32 deviceId) throws (1: ServiceRuntimeException ex1);
  DeviceGroupBean getDeviceGroup(1: required i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  list<DeviceGroupBean> getDeviceGroups(1: optional list<i32> groupIdList) throws (1: ServiceRuntimeException ex1);
  list<i32> getDeviceGroupsBelongs(1: required i32 deviceId) throws (1: ServiceRuntimeException ex1);
  list<i32> getDeviceGroupsPermit(1: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> getDeviceGroupsPermittedBy(1: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  i32 getDeviceIdOfFeature(1: optional string featureMd5) throws (1: ServiceRuntimeException ex1);
  list<DeviceBean> getDevices(1: optional list<i32> idList) throws (1: ServiceRuntimeException ex1);
  list<i32> getDevicesOfGroup(1: required i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  FeatureBean getFeature(1: optional string md5) throws (1: ServiceRuntimeException ex1);
  list<string> getFeatureBeansByPersonId(1: required i32 personId) throws (1: ServiceRuntimeException ex1);
  binary getFeatureBytes(1: optional string md5) throws (1: ServiceRuntimeException ex1);
  list<FeatureBean> getFeatures(1: optional list<string> md5) throws (1: ServiceRuntimeException ex1);
  list<string> getFeaturesOfPerson(1: required i32 personId) throws (1: ServiceRuntimeException ex1);
  bool getGroupPermit(1: required i32 deviceId, 2: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  bool getGroupPermitOnDeviceGroup(1: required i32 deviceGroupId, 2: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  list<bool> getGroupPermits(1: required i32 deviceId, 2: optional list<i32> personGroupIdList) throws (1: ServiceRuntimeException ex1);
  ImageBean getImage(1: optional string imageMD5) throws (1: ServiceRuntimeException ex1);
  binary getImageBytes(1: optional string imageMD5) throws (1: ServiceRuntimeException ex1);
  list<string> getImagesAssociatedByFeature(1: optional string featureMd5) throws (1: ServiceRuntimeException ex1);
  list<LogBean> getLogBeansByPersonId(1: required i32 personId) throws (1: ServiceRuntimeException ex1);
  PersonBean getPerson(1: required i32 personId) throws (1: ServiceRuntimeException ex1);
  PersonBean getPersonByPapersNum(1: optional string papersNum) throws (1: ServiceRuntimeException ex1);
  PersonGroupBean getPersonGroup(1: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  list<PersonGroupBean> getPersonGroups(1: optional list<i32> groupIdList) throws (1: ServiceRuntimeException ex1);
  list<i32> getPersonGroupsBelongs(1: required i32 personId) throws (1: ServiceRuntimeException ex1);
  list<i32> getPersonGroupsPermittedBy(1: required i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  bool getPersonPermit(1: required i32 deviceId, 2: required i32 personId) throws (1: ServiceRuntimeException ex1);
  list<bool> getPersonPermits(1: required i32 deviceId, 2: optional list<i32> personIdList) throws (1: ServiceRuntimeException ex1);
  list<PersonBean> getPersons(1: optional list<i32> idList) throws (1: ServiceRuntimeException ex1);
  list<i32> getPersonsOfGroup(1: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  string getProperty(1: optional string key, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  map<MQParam, string> getRedisParameters(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  map<string, string> getServiceConfig(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  list<i32> getSubDeviceGroup(1: required i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> getSubPersonGroup(1: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  bool isDisable(1: required i32 personId) throws (1: ServiceRuntimeException ex1);
  bool isLocal() throws (1: ServiceRuntimeException ex1);
  bool isValidAckChannel(1: optional string ackChannel) throws (1: ServiceRuntimeException ex1);
  bool isValidCmdSn(1: required i64 cmdSn) throws (1: ServiceRuntimeException ex1);
  bool isValidDeviceToken(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  bool isValidPassword(1: optional string userId, 2: optional string password, 3: required bool isMd5) throws (1: ServiceRuntimeException ex1);
  bool isValidPersonToken(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  bool isValidRootToken(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  bool isValidToken(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  bool isValidUserToken(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  list<i32> listOfParentForDeviceGroup(1: required i32 deviceGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> listOfParentForPersonGroup(1: required i32 personGroupId) throws (1: ServiceRuntimeException ex1);
  list<i32> loadAllPerson() throws (1: ServiceRuntimeException ex1);
  list<DeviceBean> loadDeviceByWhere(1: optional string where, 2: required i32 startRow, 3: required i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadDeviceGroupByWhere(1: optional string where, 2: required i32 startRow, 3: required i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadDeviceGroupIdByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  list<i32> loadDeviceIdByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  list<string> loadFeatureMd5ByUpdate(1: required i64 timestamp) throws (1: ServiceRuntimeException ex1);
  list<LogBean> loadLogByWhere(1: optional string where, 2: required i32 startRow, 3: required i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<LogLightBean> loadLogLightByVerifyTime(1: required i64 timestamp, 2: required i32 startRow, 3: required i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<LogLightBean> loadLogLightByWhere(1: optional string where, 2: required i32 startRow, 3: required i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<PermitBean> loadPermitByUpdate(1: required i64 timestamp) throws (1: ServiceRuntimeException ex1);
  list<PersonBean> loadPersonByWhere(1: optional string where, 2: required i32 startRow, 3: required i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonGroupByWhere(1: optional string where, 2: required i32 startRow, 3: required i32 numRows) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonGroupIdByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonIdByUpdateTime(1: required i64 timestamp) throws (1: ServiceRuntimeException ex1);
  list<i32> loadPersonIdByWhere(1: optional string where) throws (1: ServiceRuntimeException ex1);
  list<i32> loadUpdatedPersons(1: required i64 timestamp) throws (1: ServiceRuntimeException ex1);
  void offline(1: optional Token token) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  Token online(1: optional DeviceBean device) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  DeviceBean registerDevice(1: optional DeviceBean newDevice) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  void releasePersonToken(1: optional Token token) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  void releaseRootToken(1: optional Token token) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  void releaseUserToken(1: optional Token token) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  void replaceFeature(1: optional i32 personId, 2: optional string featureMd5, 3: required bool deleteOldFeatureImage, 4: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 rootGroupOfDevice(1: optional i32 deviceId) throws (1: ServiceRuntimeException ex1);
  i32 rootGroupOfPerson(1: optional i32 personId) throws (1: ServiceRuntimeException ex1);
  DeviceBean saveDevice(1: optional DeviceBean deviceBean, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  DeviceGroupBean saveDeviceGroup(1: optional DeviceGroupBean deviceGroupBean, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePerson(1: optional PersonBean personBean, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonFull(1: optional PersonBean personBean, 2: optional binary idPhoto, 3: optional binary feature, 4: optional binary featureImage, 5: optional FaceBean featureFaceBean, 6: optional i32 deviceId, 7: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonGroupBean savePersonGroup(1: optional PersonGroupBean personGroupBean, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhoto(1: optional PersonBean personBean, 2: optional binary idPhoto, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeature(1: optional PersonBean personBean, 2: optional binary idPhoto, 3: optional FeatureBean featureBean, 4: optional i32 deviceId, 5: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeatureMultiFaces(1: optional PersonBean personBean, 2: optional binary idPhoto, 3: optional binary feature, 4: optional list<FaceBean> faceBeans, 5: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeatureMultiImage(1: optional PersonBean personBean, 2: optional binary idPhoto, 3: optional binary feature, 4: optional map<binary, FaceBean> faceInfo, 5: optional i32 deviceId, 6: optional Token token) throws (1: ServiceRuntimeException ex1);
  PersonBean savePersonWithPhotoAndFeatureSaved(1: optional PersonBean personBean, 2: optional string idPhotoMd5, 3: optional string featureMd5, 4: optional Token token) throws (1: ServiceRuntimeException ex1);
  void savePersons(1: optional list<PersonBean> persons, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  i32 savePersonsWithPhoto(1: optional map<binary, PersonBean> persons, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  void saveServiceConfig(1: optional Token token) throws (1: ServiceRuntimeException ex1);
  void setPersonExpiryDate(1: required i32 personId, 2: required i64 expiryDate, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  void setPersonExpiryDateList(1: optional list<i32> personIdList, 2: required i64 expiryDate, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  void setProperties(1: optional map<string, string> config, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  void setProperty(1: optional string key, 2: optional string value, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  string taskQueueOf(1: optional string task, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  string taskRegister(1: optional string task, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  void unbindBorder(1: optional i32 personGroupId, 2: optional i32 deviceGroupId, 3: optional Token token) throws (1: ServiceRuntimeException ex1);
  void unregisterDevice(1: required i32 deviceId, 2: optional Token token) throws (1: ServiceSecurityException ex1, 2: ServiceRuntimeException ex2);
  DeviceBean updateDevice(1: optional DeviceBean deviceBean, 2: optional Token token) throws (1: ServiceRuntimeException ex1);
  string version() throws (1: ServiceRuntimeException ex1);
  map<string, string> versionInfo() throws (1: ServiceRuntimeException ex1);
}
