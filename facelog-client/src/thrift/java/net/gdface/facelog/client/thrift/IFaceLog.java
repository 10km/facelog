package net.gdface.facelog.client.thrift;

import com.facebook.swift.codec.*;
import com.facebook.swift.codec.ThriftField.Requiredness;
import com.facebook.swift.service.*;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.*;
import java.util.*;

@ThriftService("IFaceLog")
public interface IFaceLog
{
    @ThriftService("IFaceLog")
    public interface Async
    {
        @ThriftMethod(value = "addFeature")
        ListenableFuture<FeatureBean> addFeature(
            @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=3, name="faecBeans", requiredness=Requiredness.NONE) final List<FaceBean> faecBeans
        );

        @ThriftMethod(value = "addFeatureMulti")
        ListenableFuture<FeatureBean> addFeatureMulti(
            @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=3, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
            @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "addImage")
        ListenableFuture<ImageBean> addImage(
            @ThriftField(value=1, name="imageData", requiredness=Requiredness.NONE) final byte [] imageData,
            @ThriftField(value=2, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=3, name="faceBean", requiredness=Requiredness.NONE) final FaceBean faceBean,
            @ThriftField(value=4, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "addLog")
        ListenableFuture<Void> addLog(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final LogBean bean
        );

        @ThriftMethod(value = "addLogList")
        ListenableFuture<Void> addLogList(
            @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<LogBean> beans
        );

        @ThriftMethod(value = "addPermit")
        ListenableFuture<Void> addPermit(
            @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
            @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
        );

        @ThriftMethod(value = "countLogLightWhere")
        ListenableFuture<Integer> countLogLightWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "countLogWhere")
        ListenableFuture<Integer> countLogWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "deleteAllFeaturesByPersonId")
        ListenableFuture<Integer> deleteAllFeaturesByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
        );

        @ThriftMethod(value = "deleteDeviceGroup")
        ListenableFuture<Integer> deleteDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "deleteFeature")
        ListenableFuture<List<String>> deleteFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
            @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
        );

        @ThriftMethod(value = "deleteImage")
        ListenableFuture<Integer> deleteImage(
            @ThriftField(value=1, name="imageMd5", requiredness=Requiredness.NONE) final String imageMd5
        );

        @ThriftMethod(value = "deletePerson")
        ListenableFuture<Integer> deletePerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "deletePersonByPapersNum")
        ListenableFuture<Integer> deletePersonByPapersNum(
            @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
        );

        @ThriftMethod(value = "deletePersonGroup")
        ListenableFuture<Integer> deletePersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "deletePersons")
        ListenableFuture<Integer> deletePersons(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
        );

        @ThriftMethod(value = "deletePersonsByPapersNum")
        ListenableFuture<Integer> deletePersonsByPapersNum(
            @ThriftField(value=1, name="papersNumlist", requiredness=Requiredness.NONE) final List<String> papersNumlist
        );

        @ThriftMethod(value = "disablePerson")
        ListenableFuture<Void> disablePerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "disablePersonList")
        ListenableFuture<Void> disablePersonList(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
        );

        @ThriftMethod(value = "existsDevice")
        ListenableFuture<Boolean> existsDevice(
            @ThriftField(value=1, name="id", requiredness=Requiredness.NONE) final int id
        );

        @ThriftMethod(value = "existsFeature")
        ListenableFuture<Boolean> existsFeature(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "existsImage")
        ListenableFuture<Boolean> existsImage(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "existsPerson")
        ListenableFuture<Boolean> existsPerson(
            @ThriftField(value=1, name="persionId", requiredness=Requiredness.NONE) final int persionId
        );

        @ThriftMethod(value = "getDevice")
        ListenableFuture<DeviceBean> getDevice(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "getDeviceGroup")
        ListenableFuture<DeviceGroupBean> getDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "getDeviceGroupList")
        ListenableFuture<List<DeviceGroupBean>> getDeviceGroupList(
            @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
        );

        @ThriftMethod(value = "getDeviceList")
        ListenableFuture<List<DeviceBean>> getDeviceList(
            @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
        );

        @ThriftMethod(value = "getDevicesOfGroup")
        ListenableFuture<List<DeviceBean>> getDevicesOfGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "getFeature")
        ListenableFuture<FeatureBean> getFeature(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "getFeatureBeansByPersonId")
        ListenableFuture<List<String>> getFeatureBeansByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getFeatureBytes")
        ListenableFuture<byte []> getFeatureBytes(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "getFeatureList")
        ListenableFuture<List<FeatureBean>> getFeatureList(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final List<String> md5
        );

        @ThriftMethod(value = "getGroupPermit")
        ListenableFuture<Boolean> getGroupPermit(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "getGroupPermitList")
        ListenableFuture<List<Boolean>> getGroupPermitList(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personGroupIdList", requiredness=Requiredness.NONE) final List<Integer> personGroupIdList
        );

        @ThriftMethod(value = "getImage")
        ListenableFuture<ImageBean> getImage(
            @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
        );

        @ThriftMethod(value = "getImageBytes")
        ListenableFuture<byte []> getImageBytes(
            @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
        );

        @ThriftMethod(value = "getImagesAssociatedByFeature")
        ListenableFuture<List<String>> getImagesAssociatedByFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
        );

        @ThriftMethod(value = "getLogBeansByPersonId")
        ListenableFuture<List<LogBean>> getLogBeansByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getPermit")
        ListenableFuture<Boolean> getPermit(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getPermitList")
        ListenableFuture<List<Boolean>> getPermitList(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
        );

        @ThriftMethod(value = "getPerson")
        ListenableFuture<PersonBean> getPerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getPersonByPapersNum")
        ListenableFuture<PersonBean> getPersonByPapersNum(
            @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
        );

        @ThriftMethod(value = "getPersonGroup")
        ListenableFuture<PersonGroupBean> getPersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "getPersonGroupList")
        ListenableFuture<List<PersonGroupBean>> getPersonGroupList(
            @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
        );

        @ThriftMethod(value = "getPersons")
        ListenableFuture<List<PersonBean>> getPersons(
            @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
        );

        @ThriftMethod(value = "getPersonsOfGroup")
        ListenableFuture<List<PersonBean>> getPersonsOfGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "getSubDeviceGroup")
        ListenableFuture<List<DeviceGroupBean>> getSubDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "getSubPersonGroup")
        ListenableFuture<List<PersonGroupBean>> getSubPersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "isDisable")
        ListenableFuture<Boolean> isDisable(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "loadAllPerson")
        ListenableFuture<List<Integer>> loadAllPerson();

        @ThriftMethod(value = "loadFeatureMd5ByUpdate")
        ListenableFuture<List<String>> loadFeatureMd5ByUpdate(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "loadLogByWhere")
        ListenableFuture<List<LogBean>> loadLogByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadLogLightByWhere")
        ListenableFuture<List<LogLightBean>> loadLogLightByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadPermitByUpdate")
        ListenableFuture<List<PermitBean>> loadPermitByUpdate(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "loadPersonByWhere")
        ListenableFuture<List<Integer>> loadPersonByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "loadPersonIdByUpdate")
        ListenableFuture<List<Integer>> loadPersonIdByUpdate(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "loadUpdatePersons")
        ListenableFuture<List<Integer>> loadUpdatePersons(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "removePermit")
        ListenableFuture<Void> removePermit(
            @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
            @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
        );

        @ThriftMethod(value = "replaceFeature")
        ListenableFuture<Void> replaceFeature(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=2, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
            @ThriftField(value=3, name="deleteOldFeatureImage", requiredness=Requiredness.NONE) final boolean deleteOldFeatureImage
        );

        @ThriftMethod(value = "saveDevice")
        ListenableFuture<DeviceBean> saveDevice(
            @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.NONE) final DeviceBean deviceBean
        );

        @ThriftMethod(value = "saveDeviceGroup")
        ListenableFuture<DeviceGroupBean> saveDeviceGroup(
            @ThriftField(value=1, name="deviceGroupBean", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroupBean
        );

        @ThriftMethod(value = "savePerson")
        ListenableFuture<PersonBean> savePerson(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean
        );

        @ThriftMethod(value = "savePersonFull")
        ListenableFuture<PersonBean> savePersonFull(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=4, name="featureImage", requiredness=Requiredness.NONE) final byte [] featureImage,
            @ThriftField(value=5, name="featureFaceBean", requiredness=Requiredness.NONE) final FaceBean featureFaceBean,
            @ThriftField(value=6, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "savePersonGroup")
        ListenableFuture<PersonGroupBean> savePersonGroup(
            @ThriftField(value=1, name="personGroupBean", requiredness=Requiredness.NONE) final PersonGroupBean personGroupBean
        );

        @ThriftMethod(value = "savePersonList")
        ListenableFuture<Void> savePersonList(
            @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<PersonBean> beans
        );

        @ThriftMethod(value = "savePersonWithPhoto")
        ListenableFuture<PersonBean> savePersonWithPhoto(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeature")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeature(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="featureBean", requiredness=Requiredness.NONE) final FeatureBean featureBean,
            @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiFaces(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=4, name="faceBeans", requiredness=Requiredness.NONE) final List<FaceBean> faceBeans
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiImage(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=4, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
            @ThriftField(value=5, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureSaved(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhotoMd5", requiredness=Requiredness.NONE) final String idPhotoMd5,
            @ThriftField(value=3, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
        );

        @ThriftMethod(value = "savePersonsWithPhoto")
        ListenableFuture<Integer> savePersonsWithPhoto(
            @ThriftField(value=1, name="persons", requiredness=Requiredness.NONE) final Map<byte [], PersonBean> persons
        );

        @ThriftMethod(value = "setPersonExpiryDate")
        ListenableFuture<Void> setPersonExpiryDate(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
        );

        @ThriftMethod(value = "setPersonExpiryDateList")
        ListenableFuture<Void> setPersonExpiryDateList(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList,
            @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
        );
    }
    @ThriftMethod(value = "addFeature")
    FeatureBean addFeature(
        @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=3, name="faecBeans", requiredness=Requiredness.NONE) final List<FaceBean> faecBeans
    );


    @ThriftMethod(value = "addFeatureMulti")
    FeatureBean addFeatureMulti(
        @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=3, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
        @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    );


    @ThriftMethod(value = "addImage")
    ImageBean addImage(
        @ThriftField(value=1, name="imageData", requiredness=Requiredness.NONE) final byte [] imageData,
        @ThriftField(value=2, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=3, name="faceBean", requiredness=Requiredness.NONE) final FaceBean faceBean,
        @ThriftField(value=4, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "addLog")
    void addLog(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final LogBean bean
    );


    @ThriftMethod(value = "addLogList")
    void addLogList(
        @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<LogBean> beans
    );


    @ThriftMethod(value = "addPermit")
    void addPermit(
        @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
        @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
    );


    @ThriftMethod(value = "countLogLightWhere")
    int countLogLightWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    );


    @ThriftMethod(value = "countLogWhere")
    int countLogWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    );


    @ThriftMethod(value = "deleteAllFeaturesByPersonId")
    int deleteAllFeaturesByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
    );


    @ThriftMethod(value = "deleteDeviceGroup")
    int deleteDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    );


    @ThriftMethod(value = "deleteFeature")
    List<String> deleteFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
        @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
    );


    @ThriftMethod(value = "deleteImage")
    int deleteImage(
        @ThriftField(value=1, name="imageMd5", requiredness=Requiredness.NONE) final String imageMd5
    );


    @ThriftMethod(value = "deletePerson")
    int deletePerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "deletePersonByPapersNum")
    int deletePersonByPapersNum(
        @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
    );


    @ThriftMethod(value = "deletePersonGroup")
    int deletePersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    );


    @ThriftMethod(value = "deletePersons")
    int deletePersons(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
    );


    @ThriftMethod(value = "deletePersonsByPapersNum")
    int deletePersonsByPapersNum(
        @ThriftField(value=1, name="papersNumlist", requiredness=Requiredness.NONE) final List<String> papersNumlist
    );


    @ThriftMethod(value = "disablePerson")
    void disablePerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "disablePersonList")
    void disablePersonList(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
    );


    @ThriftMethod(value = "existsDevice")
    boolean existsDevice(
        @ThriftField(value=1, name="id", requiredness=Requiredness.NONE) final int id
    );


    @ThriftMethod(value = "existsFeature")
    boolean existsFeature(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    );


    @ThriftMethod(value = "existsImage")
    boolean existsImage(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    );


    @ThriftMethod(value = "existsPerson")
    boolean existsPerson(
        @ThriftField(value=1, name="persionId", requiredness=Requiredness.NONE) final int persionId
    );


    @ThriftMethod(value = "getDevice")
    DeviceBean getDevice(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    );


    @ThriftMethod(value = "getDeviceGroup")
    DeviceGroupBean getDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    );


    @ThriftMethod(value = "getDeviceGroupList")
    List<DeviceGroupBean> getDeviceGroupList(
        @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
    );


    @ThriftMethod(value = "getDeviceList")
    List<DeviceBean> getDeviceList(
        @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
    );


    @ThriftMethod(value = "getDevicesOfGroup")
    List<DeviceBean> getDevicesOfGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    );


    @ThriftMethod(value = "getFeature")
    FeatureBean getFeature(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    );


    @ThriftMethod(value = "getFeatureBeansByPersonId")
    List<String> getFeatureBeansByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "getFeatureBytes")
    byte [] getFeatureBytes(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    );


    @ThriftMethod(value = "getFeatureList")
    List<FeatureBean> getFeatureList(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final List<String> md5
    );


    @ThriftMethod(value = "getGroupPermit")
    boolean getGroupPermit(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    );


    @ThriftMethod(value = "getGroupPermitList")
    List<Boolean> getGroupPermitList(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personGroupIdList", requiredness=Requiredness.NONE) final List<Integer> personGroupIdList
    );


    @ThriftMethod(value = "getImage")
    ImageBean getImage(
        @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
    );


    @ThriftMethod(value = "getImageBytes")
    byte [] getImageBytes(
        @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
    );


    @ThriftMethod(value = "getImagesAssociatedByFeature")
    List<String> getImagesAssociatedByFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
    );


    @ThriftMethod(value = "getLogBeansByPersonId")
    List<LogBean> getLogBeansByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "getPermit")
    boolean getPermit(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "getPermitList")
    List<Boolean> getPermitList(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
    );


    @ThriftMethod(value = "getPerson")
    PersonBean getPerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "getPersonByPapersNum")
    PersonBean getPersonByPapersNum(
        @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
    );


    @ThriftMethod(value = "getPersonGroup")
    PersonGroupBean getPersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    );


    @ThriftMethod(value = "getPersonGroupList")
    List<PersonGroupBean> getPersonGroupList(
        @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
    );


    @ThriftMethod(value = "getPersons")
    List<PersonBean> getPersons(
        @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
    );


    @ThriftMethod(value = "getPersonsOfGroup")
    List<PersonBean> getPersonsOfGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    );


    @ThriftMethod(value = "getSubDeviceGroup")
    List<DeviceGroupBean> getSubDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    );


    @ThriftMethod(value = "getSubPersonGroup")
    List<PersonGroupBean> getSubPersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    );


    @ThriftMethod(value = "isDisable")
    boolean isDisable(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    );


    @ThriftMethod(value = "loadAllPerson")
    List<Integer> loadAllPerson();


    @ThriftMethod(value = "loadFeatureMd5ByUpdate")
    List<String> loadFeatureMd5ByUpdate(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    );


    @ThriftMethod(value = "loadLogByWhere")
    List<LogBean> loadLogByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    );


    @ThriftMethod(value = "loadLogLightByWhere")
    List<LogLightBean> loadLogLightByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    );


    @ThriftMethod(value = "loadPermitByUpdate")
    List<PermitBean> loadPermitByUpdate(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    );


    @ThriftMethod(value = "loadPersonByWhere")
    List<Integer> loadPersonByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    );


    @ThriftMethod(value = "loadPersonIdByUpdate")
    List<Integer> loadPersonIdByUpdate(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    );


    @ThriftMethod(value = "loadUpdatePersons")
    List<Integer> loadUpdatePersons(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    );


    @ThriftMethod(value = "removePermit")
    void removePermit(
        @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
        @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
    );


    @ThriftMethod(value = "replaceFeature")
    void replaceFeature(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=2, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
        @ThriftField(value=3, name="deleteOldFeatureImage", requiredness=Requiredness.NONE) final boolean deleteOldFeatureImage
    );


    @ThriftMethod(value = "saveDevice")
    DeviceBean saveDevice(
        @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.NONE) final DeviceBean deviceBean
    );


    @ThriftMethod(value = "saveDeviceGroup")
    DeviceGroupBean saveDeviceGroup(
        @ThriftField(value=1, name="deviceGroupBean", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroupBean
    );


    @ThriftMethod(value = "savePerson")
    PersonBean savePerson(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean
    );


    @ThriftMethod(value = "savePersonFull")
    PersonBean savePersonFull(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=4, name="featureImage", requiredness=Requiredness.NONE) final byte [] featureImage,
        @ThriftField(value=5, name="featureFaceBean", requiredness=Requiredness.NONE) final FaceBean featureFaceBean,
        @ThriftField(value=6, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    );


    @ThriftMethod(value = "savePersonGroup")
    PersonGroupBean savePersonGroup(
        @ThriftField(value=1, name="personGroupBean", requiredness=Requiredness.NONE) final PersonGroupBean personGroupBean
    );


    @ThriftMethod(value = "savePersonList")
    void savePersonList(
        @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<PersonBean> beans
    );


    @ThriftMethod(value = "savePersonWithPhoto")
    PersonBean savePersonWithPhoto(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeature")
    PersonBean savePersonWithPhotoAndFeature(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="featureBean", requiredness=Requiredness.NONE) final FeatureBean featureBean,
        @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces")
    PersonBean savePersonWithPhotoAndFeatureMultiFaces(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=4, name="faceBeans", requiredness=Requiredness.NONE) final List<FaceBean> faceBeans
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage")
    PersonBean savePersonWithPhotoAndFeatureMultiImage(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=4, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
        @ThriftField(value=5, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved")
    PersonBean savePersonWithPhotoAndFeatureSaved(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhotoMd5", requiredness=Requiredness.NONE) final String idPhotoMd5,
        @ThriftField(value=3, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
    );


    @ThriftMethod(value = "savePersonsWithPhoto")
    int savePersonsWithPhoto(
        @ThriftField(value=1, name="persons", requiredness=Requiredness.NONE) final Map<byte [], PersonBean> persons
    );


    @ThriftMethod(value = "setPersonExpiryDate")
    void setPersonExpiryDate(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
    );


    @ThriftMethod(value = "setPersonExpiryDateList")
    void setPersonExpiryDateList(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList,
        @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
    );

}