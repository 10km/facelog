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
        @ThriftMethod(value = "addFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1),
                          @ThriftException(type=DuplicateReord.class, id=2)
                      })
        ListenableFuture<FeatureBean> addFeature(
            @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=3, name="faecBeans", requiredness=Requiredness.NONE) final List<FaceBean> faecBeans
        );

        @ThriftMethod(value = "addFeatureMulti",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1),
                          @ThriftException(type=DuplicateReord.class, id=2)
                      })
        ListenableFuture<FeatureBean> addFeatureMulti(
            @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=3, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
            @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "addImage",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1),
                          @ThriftException(type=DuplicateReord.class, id=2)
                      })
        ListenableFuture<ImageBean> addImage(
            @ThriftField(value=1, name="imageData", requiredness=Requiredness.NONE) final byte [] imageData,
            @ThriftField(value=2, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=3, name="faceBean", requiredness=Requiredness.NONE) final FaceBean faceBean,
            @ThriftField(value=4, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "addLog",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1),
                          @ThriftException(type=DuplicateReord.class, id=2)
                      })
        ListenableFuture<Void> addLog(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final LogBean bean
        );

        @ThriftMethod(value = "addLogs",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1),
                          @ThriftException(type=DuplicateReord.class, id=2)
                      })
        ListenableFuture<Void> addLogs(
            @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<LogBean> beans
        );

        @ThriftMethod(value = "addPermit",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> addPermit(
            @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
            @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
        );

        @ThriftMethod(value = "addPermitById",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> addPermitById(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId,
            @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "countDeviceByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> countDeviceByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "countDeviceGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> countDeviceGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "countLogByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> countLogByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "countLogLightByVerifyTime",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> countLogLightByVerifyTime(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "countLogLightByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> countLogLightByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "countPersonByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> countPersonByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "countPersonGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> countPersonGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "deleteAllFeaturesByPersonId",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deleteAllFeaturesByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
        );

        @ThriftMethod(value = "deleteDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deleteDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "deleteFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<String>> deleteFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
            @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
        );

        @ThriftMethod(value = "deleteImage",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deleteImage(
            @ThriftField(value=1, name="imageMd5", requiredness=Requiredness.NONE) final String imageMd5
        );

        @ThriftMethod(value = "deletePermit",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deletePermit(
            @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
            @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
        );

        @ThriftMethod(value = "deletePerson",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deletePerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "deletePersonByPapersNum",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deletePersonByPapersNum(
            @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
        );

        @ThriftMethod(value = "deletePersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deletePersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "deletePersons",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deletePersons(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
        );

        @ThriftMethod(value = "deletePersonsByPapersNum",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> deletePersonsByPapersNum(
            @ThriftField(value=1, name="papersNumlist", requiredness=Requiredness.NONE) final List<String> papersNumlist
        );

        @ThriftMethod(value = "disablePerson",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> disablePerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "disablePersonList",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> disablePersonList(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
        );

        @ThriftMethod(value = "existsDevice",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Boolean> existsDevice(
            @ThriftField(value=1, name="id", requiredness=Requiredness.NONE) final int id
        );

        @ThriftMethod(value = "existsFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Boolean> existsFeature(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "existsImage",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Boolean> existsImage(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "existsPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Boolean> existsPerson(
            @ThriftField(value=1, name="persionId", requiredness=Requiredness.NONE) final int persionId
        );

        @ThriftMethod(value = "getDevice",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<DeviceBean> getDevice(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "getDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<DeviceGroupBean> getDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "getDeviceGroups",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<DeviceGroupBean>> getDeviceGroups(
            @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
        );

        @ThriftMethod(value = "getDeviceIdOfFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> getDeviceIdOfFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
        );

        @ThriftMethod(value = "getDevices",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<DeviceBean>> getDevices(
            @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
        );

        @ThriftMethod(value = "getDevicesOfGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<DeviceBean>> getDevicesOfGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "getFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<FeatureBean> getFeature(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "getFeatureBeansByPersonId",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<String>> getFeatureBeansByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getFeatureBytes",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<byte []> getFeatureBytes(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
        );

        @ThriftMethod(value = "getFeatures",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<FeatureBean>> getFeatures(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final List<String> md5
        );

        @ThriftMethod(value = "getFeaturesOfPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<String>> getFeaturesOfPerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getGroupPermit",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Boolean> getGroupPermit(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "getGroupPermits",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Boolean>> getGroupPermits(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personGroupIdList", requiredness=Requiredness.NONE) final List<Integer> personGroupIdList
        );

        @ThriftMethod(value = "getImage",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<ImageBean> getImage(
            @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
        );

        @ThriftMethod(value = "getImageBytes",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<byte []> getImageBytes(
            @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
        );

        @ThriftMethod(value = "getImagesAssociatedByFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<String>> getImagesAssociatedByFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
        );

        @ThriftMethod(value = "getLogBeansByPersonId",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<LogBean>> getLogBeansByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> getPerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getPersonByPapersNum",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> getPersonByPapersNum(
            @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
        );

        @ThriftMethod(value = "getPersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonGroupBean> getPersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "getPersonGroups",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<PersonGroupBean>> getPersonGroups(
            @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
        );

        @ThriftMethod(value = "getPersonPermit",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Boolean> getPersonPermit(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "getPersonPermits",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Boolean>> getPersonPermits(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
            @ThriftField(value=2, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
        );

        @ThriftMethod(value = "getPersons",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<PersonBean>> getPersons(
            @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
        );

        @ThriftMethod(value = "getPersonsOfGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<PersonBean>> getPersonsOfGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "getSubDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<DeviceGroupBean>> getSubDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
        );

        @ThriftMethod(value = "getSubPersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<PersonGroupBean>> getSubPersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
        );

        @ThriftMethod(value = "isDisable",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Boolean> isDisable(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
        );

        @ThriftMethod(value = "loadAllPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadAllPerson();

        @ThriftMethod(value = "loadDeviceByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<DeviceBean>> loadDeviceByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadDeviceGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<DeviceGroupBean>> loadDeviceGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadDeviceGroupIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadDeviceGroupIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "loadDeviceIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadDeviceIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "loadFeatureMd5ByUpdate",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<String>> loadFeatureMd5ByUpdate(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "loadLogByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<LogBean>> loadLogByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadLogLightByVerifyTime",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<LogLightBean>> loadLogLightByVerifyTime(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadLogLightByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<LogLightBean>> loadLogLightByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadPermitByUpdate",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<PermitBean>> loadPermitByUpdate(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "loadPersonByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<PersonBean>> loadPersonByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadPersonGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<PersonGroupBean>> loadPersonGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
        );

        @ThriftMethod(value = "loadPersonGroupIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadPersonGroupIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "loadPersonIdByUpdateTime",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadPersonIdByUpdateTime(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "loadPersonIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadPersonIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
        );

        @ThriftMethod(value = "loadUpdatedPersons",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadUpdatedPersons(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
        );

        @ThriftMethod(value = "replaceFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> replaceFeature(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=2, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
            @ThriftField(value=3, name="deleteOldFeatureImage", requiredness=Requiredness.NONE) final boolean deleteOldFeatureImage
        );

        @ThriftMethod(value = "saveDevice",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<DeviceBean> saveDevice(
            @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.NONE) final DeviceBean deviceBean
        );

        @ThriftMethod(value = "saveDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<DeviceGroupBean> saveDeviceGroup(
            @ThriftField(value=1, name="deviceGroupBean", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroupBean
        );

        @ThriftMethod(value = "savePerson",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> savePerson(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean
        );

        @ThriftMethod(value = "savePersonFull",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonFull(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=4, name="featureImage", requiredness=Requiredness.NONE) final byte [] featureImage,
            @ThriftField(value=5, name="featureFaceBean", requiredness=Requiredness.NONE) final FaceBean featureFaceBean,
            @ThriftField(value=6, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "savePersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonGroupBean> savePersonGroup(
            @ThriftField(value=1, name="personGroupBean", requiredness=Requiredness.NONE) final PersonGroupBean personGroupBean
        );

        @ThriftMethod(value = "savePersonWithPhoto",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhoto(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeature(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="featureBean", requiredness=Requiredness.NONE) final FeatureBean featureBean,
            @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiFaces(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=4, name="faceBeans", requiredness=Requiredness.NONE) final List<FaceBean> faceBeans
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiImage(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
            @ThriftField(value=4, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
            @ThriftField(value=5, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureSaved(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
            @ThriftField(value=2, name="idPhotoMd5", requiredness=Requiredness.NONE) final String idPhotoMd5,
            @ThriftField(value=3, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
        );

        @ThriftMethod(value = "savePersons",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> savePersons(
            @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<PersonBean> beans
        );

        @ThriftMethod(value = "savePersonsWithPhoto",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Integer> savePersonsWithPhoto(
            @ThriftField(value=1, name="persons", requiredness=Requiredness.NONE) final Map<byte [], PersonBean> persons
        );

        @ThriftMethod(value = "setPersonExpiryDate",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> setPersonExpiryDate(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
            @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
        );

        @ThriftMethod(value = "setPersonExpiryDateList",
                      exception = {
                          @ThriftException(type=ServiceRuntime.class, id=1)
                      })
        ListenableFuture<Void> setPersonExpiryDateList(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList,
            @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
        );
    }
    @ThriftMethod(value = "addFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1),
                      @ThriftException(type=DuplicateReord.class, id=2)
                  })
    FeatureBean addFeature(
        @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=3, name="faecBeans", requiredness=Requiredness.NONE) final List<FaceBean> faecBeans
    ) throws ServiceRuntime, DuplicateReord;

    @ThriftMethod(value = "addFeatureMulti",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1),
                      @ThriftException(type=DuplicateReord.class, id=2)
                  })
    FeatureBean addFeatureMulti(
        @ThriftField(value=1, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=3, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
        @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    ) throws ServiceRuntime, DuplicateReord;

    @ThriftMethod(value = "addImage",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1),
                      @ThriftException(type=DuplicateReord.class, id=2)
                  })
    ImageBean addImage(
        @ThriftField(value=1, name="imageData", requiredness=Requiredness.NONE) final byte [] imageData,
        @ThriftField(value=2, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=3, name="faceBean", requiredness=Requiredness.NONE) final FaceBean faceBean,
        @ThriftField(value=4, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime, DuplicateReord;

    @ThriftMethod(value = "addLog",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1),
                      @ThriftException(type=DuplicateReord.class, id=2)
                  })
    void addLog(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final LogBean bean
    ) throws ServiceRuntime, DuplicateReord;

    @ThriftMethod(value = "addLogs",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1),
                      @ThriftException(type=DuplicateReord.class, id=2)
                  })
    void addLogs(
        @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<LogBean> beans
    ) throws ServiceRuntime, DuplicateReord;

    @ThriftMethod(value = "addPermit",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void addPermit(
        @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
        @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
    ) throws ServiceRuntime;

    @ThriftMethod(value = "addPermitById",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void addPermitById(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId,
        @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "countDeviceByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int countDeviceByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "countDeviceGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int countDeviceGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "countLogByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int countLogByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "countLogLightByVerifyTime",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int countLogLightByVerifyTime(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    ) throws ServiceRuntime;

    @ThriftMethod(value = "countLogLightByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int countLogLightByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "countPersonByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int countPersonByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "countPersonGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int countPersonGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deleteAllFeaturesByPersonId",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deleteAllFeaturesByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deleteDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deleteDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deleteFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<String> deleteFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
        @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.NONE) final boolean deleteImage
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deleteImage",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deleteImage(
        @ThriftField(value=1, name="imageMd5", requiredness=Requiredness.NONE) final String imageMd5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deletePermit",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deletePermit(
        @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroup,
        @ThriftField(value=2, name="personGroup", requiredness=Requiredness.NONE) final PersonGroupBean personGroup
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deletePerson",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deletePerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deletePersonByPapersNum",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deletePersonByPapersNum(
        @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deletePersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deletePersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deletePersons",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deletePersons(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "deletePersonsByPapersNum",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int deletePersonsByPapersNum(
        @ThriftField(value=1, name="papersNumlist", requiredness=Requiredness.NONE) final List<String> papersNumlist
    ) throws ServiceRuntime;

    @ThriftMethod(value = "disablePerson",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void disablePerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "disablePersonList",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void disablePersonList(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "existsDevice",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    boolean existsDevice(
        @ThriftField(value=1, name="id", requiredness=Requiredness.NONE) final int id
    ) throws ServiceRuntime;

    @ThriftMethod(value = "existsFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    boolean existsFeature(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "existsImage",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    boolean existsImage(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "existsPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    boolean existsPerson(
        @ThriftField(value=1, name="persionId", requiredness=Requiredness.NONE) final int persionId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getDevice",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    DeviceBean getDevice(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    DeviceGroupBean getDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getDeviceGroups",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<DeviceGroupBean> getDeviceGroups(
        @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getDeviceIdOfFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int getDeviceIdOfFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getDevices",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<DeviceBean> getDevices(
        @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getDevicesOfGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<DeviceBean> getDevicesOfGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    FeatureBean getFeature(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getFeatureBeansByPersonId",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<String> getFeatureBeansByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getFeatureBytes",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    byte [] getFeatureBytes(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final String md5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getFeatures",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<FeatureBean> getFeatures(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.NONE) final List<String> md5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getFeaturesOfPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<String> getFeaturesOfPerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getGroupPermit",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    boolean getGroupPermit(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getGroupPermits",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Boolean> getGroupPermits(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personGroupIdList", requiredness=Requiredness.NONE) final List<Integer> personGroupIdList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getImage",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    ImageBean getImage(
        @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getImageBytes",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    byte [] getImageBytes(
        @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.NONE) final String imageMD5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getImagesAssociatedByFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<String> getImagesAssociatedByFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getLogBeansByPersonId",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<LogBean> getLogBeansByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean getPerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPersonByPapersNum",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean getPersonByPapersNum(
        @ThriftField(value=1, name="papersNum", requiredness=Requiredness.NONE) final String papersNum
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonGroupBean getPersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPersonGroups",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<PersonGroupBean> getPersonGroups(
        @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.NONE) final List<Integer> groupIdList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPersonPermit",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    boolean getPersonPermit(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPersonPermits",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Boolean> getPersonPermits(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.NONE) final int deviceId,
        @ThriftField(value=2, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPersons",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<PersonBean> getPersons(
        @ThriftField(value=1, name="idList", requiredness=Requiredness.NONE) final List<Integer> idList
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getPersonsOfGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<PersonBean> getPersonsOfGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getSubDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<DeviceGroupBean> getSubDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.NONE) final int deviceGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "getSubPersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<PersonGroupBean> getSubPersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.NONE) final int personGroupId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "isDisable",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    boolean isDisable(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadAllPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Integer> loadAllPerson() throws ServiceRuntime;

    @ThriftMethod(value = "loadDeviceByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<DeviceBean> loadDeviceByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadDeviceGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<DeviceGroupBean> loadDeviceGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadDeviceGroupIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Integer> loadDeviceGroupIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadDeviceIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Integer> loadDeviceIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadFeatureMd5ByUpdate",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<String> loadFeatureMd5ByUpdate(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadLogByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<LogBean> loadLogByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadLogLightByVerifyTime",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<LogLightBean> loadLogLightByVerifyTime(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadLogLightByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<LogLightBean> loadLogLightByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadPermitByUpdate",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<PermitBean> loadPermitByUpdate(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadPersonByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<PersonBean> loadPersonByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadPersonGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<PersonGroupBean> loadPersonGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.NONE) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.NONE) final int numRows
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadPersonGroupIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Integer> loadPersonGroupIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadPersonIdByUpdateTime",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Integer> loadPersonIdByUpdateTime(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadPersonIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Integer> loadPersonIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.NONE) final String where
    ) throws ServiceRuntime;

    @ThriftMethod(value = "loadUpdatedPersons",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    List<Integer> loadUpdatedPersons(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.NONE) final long timestamp
    ) throws ServiceRuntime;

    @ThriftMethod(value = "replaceFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void replaceFeature(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=2, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5,
        @ThriftField(value=3, name="deleteOldFeatureImage", requiredness=Requiredness.NONE) final boolean deleteOldFeatureImage
    ) throws ServiceRuntime;

    @ThriftMethod(value = "saveDevice",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    DeviceBean saveDevice(
        @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.NONE) final DeviceBean deviceBean
    ) throws ServiceRuntime;

    @ThriftMethod(value = "saveDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    DeviceGroupBean saveDeviceGroup(
        @ThriftField(value=1, name="deviceGroupBean", requiredness=Requiredness.NONE) final DeviceGroupBean deviceGroupBean
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePerson",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean savePerson(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonFull",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean savePersonFull(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=4, name="featureImage", requiredness=Requiredness.NONE) final byte [] featureImage,
        @ThriftField(value=5, name="featureFaceBean", requiredness=Requiredness.NONE) final FaceBean featureFaceBean,
        @ThriftField(value=6, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonGroupBean savePersonGroup(
        @ThriftField(value=1, name="personGroupBean", requiredness=Requiredness.NONE) final PersonGroupBean personGroupBean
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonWithPhoto",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean savePersonWithPhoto(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonWithPhotoAndFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeature(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="featureBean", requiredness=Requiredness.NONE) final FeatureBean featureBean,
        @ThriftField(value=4, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeatureMultiFaces(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=4, name="faceBeans", requiredness=Requiredness.NONE) final List<FaceBean> faceBeans
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeatureMultiImage(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.NONE) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.NONE) final byte [] feature,
        @ThriftField(value=4, name="faceInfo", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> faceInfo,
        @ThriftField(value=5, name="deviceId", requiredness=Requiredness.NONE) final int deviceId
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeatureSaved(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.NONE) final PersonBean bean,
        @ThriftField(value=2, name="idPhotoMd5", requiredness=Requiredness.NONE) final String idPhotoMd5,
        @ThriftField(value=3, name="featureMd5", requiredness=Requiredness.NONE) final String featureMd5
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersons",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void savePersons(
        @ThriftField(value=1, name="beans", requiredness=Requiredness.NONE) final List<PersonBean> beans
    ) throws ServiceRuntime;

    @ThriftMethod(value = "savePersonsWithPhoto",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    int savePersonsWithPhoto(
        @ThriftField(value=1, name="persons", requiredness=Requiredness.NONE) final Map<byte [], PersonBean> persons
    ) throws ServiceRuntime;

    @ThriftMethod(value = "setPersonExpiryDate",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void setPersonExpiryDate(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.NONE) final int personId,
        @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
    ) throws ServiceRuntime;

    @ThriftMethod(value = "setPersonExpiryDateList",
                  exception = {
                      @ThriftException(type=ServiceRuntime.class, id=1)
                  })
    void setPersonExpiryDateList(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.NONE) final List<Integer> personIdList,
        @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.NONE) final long expiryDate
    ) throws ServiceRuntime;
}