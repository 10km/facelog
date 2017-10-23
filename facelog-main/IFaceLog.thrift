namespace java.swift net.gdface.facelog
namespace py gdface.thrift
namespace java com.gdface
namespace cpp gdface



struct DeviceBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  string name;
  6:  i32 groupId;
  7:  string version;
  8:  string serialNo;
  9:  string mac;
  10:  i64 createTime;
  11:  i64 updateTime;
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
  12:  DeviceBean referencedByDeviceId;
}

struct PersonBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  i32 id;
  5:  i32 groupId;
  6:  string name;
  7:  i32 sex;
  8:  i64 birthdate;
  9:  i32 papersType;
  10:  string papersNum;
  11:  string imageMd5;
  12:  i64 expiryDate;
  13:  i64 createTime;
  14:  i64 updateTime;
  15:  ImageBean referencedByImageMd5;
}

struct FeatureBean {
  1: required bool _new;
  2: required i64 modified;
  3: required i64 initialized;
  4:  string md5;
  5:  i32 personId;
  6:  binary feature;
  7:  i64 updateTime;
  8:  PersonBean referencedByPersonId;
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
  23:  i64 createTime;
  24:  FeatureBean referencedByFeatureMd5;
  25:  ImageBean referencedByImageMd5;
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
  9:  double similarty;
  10:  i64 verifyTime;
  11:  i64 createTime;
  12:  DeviceBean referencedByDeviceId;
  13:  FaceBean referencedByCompareFace;
  14:  FeatureBean referencedByVerifyFeature;
  15:  PersonBean referencedByPersonId;
}

service IFaceLog {
  FeatureBean addFeature(1:  binary feature, 2:  i32 personId, 3:  list<FaceBean> faecBeans);
  FeatureBean addFeatureMulti(1:  binary feature, 2:  i32 personId, 3:  map<binary, FaceBean> faceInfo, 4:  i32 deviceId);
  ImageBean addImage(1:  binary imageData, 2:  i32 deviceId, 3:  FaceBean faceBean, 4:  i32 personId);
  void addLog(1:  LogBean bean);
  void addLogList(1:  list<LogBean> beans);
  i32 countLogLightWhere(1:  string where);
  i32 countLogWhere(1:  string where);
  i32 deleteAllFeaturesByPersonId(1:  i32 personId, 2:  bool deleteImage);
  list<string> deleteFeature(1:  string featureMd5, 2:  bool deleteImage);
  i32 deleteImage(1:  string imageMd5);
  i32 deletePerson(1:  i32 personId);
  i32 deletePersonByPapersNum(1:  string papersNum);
  i32 deletePersons(1:  list<i32> personIdList);
  i32 deletePersonsByPapersNum(1:  list<string> papersNumlist);
  void disablePerson(1:  i32 personId);
  void disablePersonList(1:  list<i32> personIdList);
  bool existsDevice(1:  i32 id);
  bool existsFeature(1:  string md5);
  bool existsImage(1:  string md5);
  bool existsPerson(1:  i32 persionId);
  DeviceBean getDevice(1:  i32 deviceId);
  list<DeviceBean> getDeviceList(1:  list<i32> deviceId);
  FeatureBean getFeature(1:  string md5);
  list<string> getFeatureBeansByPersonId(1:  i32 personId);
  binary getFeatureBytes(1:  string md5);
  list<FeatureBean> getFeatureList(1:  list<string> md5);
  ImageBean getImage(1:  string imageMD5);
  binary getImageBytes(1:  string imageMD5);
  list<string> getImagesAssociatedByFeature(1:  string featureMd5);
  list<LogBean> getLogBeansByPersonId(1:  i32 personId);
  PersonBean getPerson(1:  i32 personId);
  PersonBean getPersonByPapersNum(1:  string papersNum);
  list<PersonBean> getPersons(1:  list<i32> idList);
  bool isDisable(1:  i32 personId);
  list<i32> loadAllPerson();
  list<string> loadFeatureMd5ByUpdate(1:  i64 timestamp);
  list<LogBean> loadLogByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows);
  list<LogLightBean> loadLogLightByWhere(1:  string where, 2:  i32 startRow, 3:  i32 numRows);
  list<i32> loadPersonByWhere(1:  string where);
  list<i32> loadPersonIdByUpdate(1:  i64 timestamp);
  list<i32> loadUpdatePersons(1:  i64 timestamp);
  void replaceFeature(1:  i32 personId, 2:  string featureMd5, 3:  bool deleteOldFeatureImage);
  DeviceBean saveDevice(1:  DeviceBean deviceBean);
  PersonBean savePerson(1:  PersonBean bean);
  PersonBean savePersonFull(1:  PersonBean bean, 2:  binary idPhoto, 3:  binary feature, 4:  binary featureImage, 5:  FaceBean featureFaceBean, 6:  i32 deviceId);
  void savePersonList(1:  list<PersonBean> beans);
  PersonBean savePersonWithPhoto(1:  PersonBean bean, 2:  binary idPhoto);
  PersonBean savePersonWithPhotoAndFeature(1:  PersonBean bean, 2:  binary idPhoto, 3:  FeatureBean featureBean, 4:  i32 deviceId);
  PersonBean savePersonWithPhotoAndFeatureMultiFaces(1:  PersonBean bean, 2:  binary idPhoto, 3:  binary feature, 4:  list<FaceBean> faceBeans);
  PersonBean savePersonWithPhotoAndFeatureMultiImage(1:  PersonBean bean, 2:  binary idPhoto, 3:  binary feature, 4:  map<binary, FaceBean> faceInfo, 5:  i32 deviceId);
  PersonBean savePersonWithPhotoAndFeatureSaved(1:  PersonBean bean, 2:  string idPhotoMd5, 3:  string featureMd5);
  i32 savePersonsWithPhoto(1:  map<binary, PersonBean> persons);
  void setPersonExpiryDate(1:  i32 personId, 2:  i64 expiryDate);
  void setPersonExpiryDateList(1:  list<i32> personIdList, 2:  i64 expiryDate);
}
