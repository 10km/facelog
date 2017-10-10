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
  FeatureBean addFeature(1:  binary arg0, 2:  i32 arg1, 3:  list<FaceBean> arg2);
  FeatureBean addFeatureMulti(1:  binary arg0, 2:  i32 arg1, 3:  map<binary, FaceBean> arg2, 4:  i32 arg3);
  ImageBean addImage(1:  binary arg0, 2:  i32 arg1, 3:  FaceBean arg2, 4:  i32 arg3);
  void addLog(1:  LogBean arg0);
  void addLogList(1:  list<LogBean> arg0);
  i32 countLogLightWhere(1:  string arg0);
  i32 countLogWhere(1:  string arg0);
  i32 deleteAllFeaturesByPersonId(1:  i32 arg0, 2:  bool arg1);
  list<string> deleteFeature(1:  string arg0, 2:  bool arg1);
  i32 deleteImage(1:  string arg0);
  i32 deletePerson(1:  i32 arg0);
  i32 deletePersonByPapersNum(1:  string arg0);
  i32 deletePersons(1:  list<i32> arg0);
  i32 deletePersonsByPapersNum(1:  list<string> arg0);
  void disablePerson(1:  i32 arg0);
  void disablePersonList(1:  list<i32> arg0);
  bool existsDevice(1:  i32 arg0);
  bool existsFeature(1:  string arg0);
  bool existsImage(1:  string arg0);
  bool existsPerson(1:  i32 arg0);
  DeviceBean getDevice(1:  i32 arg0);
  list<DeviceBean> getDeviceList(1:  list<i32> arg0);
  FeatureBean getFeature(1:  string arg0);
  list<string> getFeatureBeansByPersonId(1:  i32 arg0);
  binary getFeatureBytes(1:  string arg0);
  list<FeatureBean> getFeatureList(1:  list<string> arg0);
  ImageBean getImage(1:  string arg0);
  binary getImageBytes(1:  string arg0);
  list<string> getImagesAssociatedByFeature(1:  string arg0);
  list<LogBean> getLogBeansByPersonId(1:  i32 arg0);
  PersonBean getPerson(1:  i32 arg0);
  PersonBean getPersonByPapersNum(1:  string arg0);
  list<PersonBean> getPersons(1:  list<i32> arg0);
  bool isDisable(1:  i32 arg0);
  list<i32> loadAllPerson();
  list<string> loadFeatureMd5ByUpdate(1:  i64 arg0);
  list<LogBean> loadLogByWhere(1:  string arg0, 2:  list<i32> arg1, 3:  i32 arg2, 4:  i32 arg3);
  list<LogLightBean> loadLogLightByWhere(1:  string arg0, 2:  i32 arg1, 3:  i32 arg2);
  list<i32> loadPersonByWhere(1:  string arg0);
  list<i32> loadPersonIdByUpdate(1:  i64 arg0);
  list<i32> loadUpdatePersons(1:  i64 arg0);
  void replaceFeature(1:  i32 arg0, 2:  string arg1, 3:  bool arg2);
  DeviceBean saveDevice(1:  DeviceBean arg0);
  PersonBean savePerson(1:  PersonBean arg0);
  PersonBean savePersonFull(1:  PersonBean arg0, 2:  binary arg1, 3:  binary arg2, 4:  binary arg3, 5:  FaceBean arg4, 6:  i32 arg5);
  void savePersonList(1:  list<PersonBean> arg0);
  PersonBean savePersonWithPhoto(1:  PersonBean arg0, 2:  binary arg1);
  PersonBean savePersonWithPhotoAndFeature(1:  PersonBean arg0, 2:  binary arg1, 3:  FeatureBean arg2, 4:  i32 arg3);
  PersonBean savePersonWithPhotoAndFeatureMultiFaces(1:  PersonBean arg0, 2:  binary arg1, 3:  binary arg2, 4:  list<FaceBean> arg3);
  PersonBean savePersonWithPhotoAndFeatureMultiImage(1:  PersonBean arg0, 2:  binary arg1, 3:  binary arg2, 4:  map<binary, FaceBean> arg3, 5:  i32 arg4);
  PersonBean savePersonWithPhotoAndFeatureSaved(1:  PersonBean arg0, 2:  string arg1, 3:  string arg2);
  i32 savePersonsWithPhoto(1:  map<binary, PersonBean> arg0);
  void setPersonExpiryDate(1:  i32 arg0, 2:  i64 arg1);
}
