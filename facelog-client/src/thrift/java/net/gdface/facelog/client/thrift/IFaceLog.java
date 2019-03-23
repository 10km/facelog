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
                          @ThriftException(type=DuplicateRecordException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<FeatureBean> addFeature(
            @ThriftField(value=1, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
            @ThriftField(value=3, name="faecBeans", requiredness=Requiredness.OPTIONAL) final List<FaceBean> faecBeans,
            @ThriftField(value=4, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "addFeatureMulti",
                      exception = {
                          @ThriftException(type=DuplicateRecordException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<FeatureBean> addFeatureMulti(
            @ThriftField(value=1, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
            @ThriftField(value=3, name="faceInfo", requiredness=Requiredness.OPTIONAL) final Map<byte [], FaceBean> faceInfo,
            @ThriftField(value=4, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
            @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "addImage",
                      exception = {
                          @ThriftException(type=DuplicateRecordException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<ImageBean> addImage(
            @ThriftField(value=1, name="imageData", requiredness=Requiredness.OPTIONAL) final byte [] imageData,
            @ThriftField(value=2, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
            @ThriftField(value=3, name="faceBean", requiredness=Requiredness.OPTIONAL) final FaceBean faceBean,
            @ThriftField(value=4, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
            @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "addLog",
                      exception = {
                          @ThriftException(type=DuplicateRecordException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Void> addLog(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final LogBean bean,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "addLogs",
                      exception = {
                          @ThriftException(type=DuplicateRecordException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Void> addLogs(
            @ThriftField(value=1, name="beans", requiredness=Requiredness.OPTIONAL) final List<LogBean> beans,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "addPermit",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> addPermit(
            @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.OPTIONAL) final DeviceGroupBean deviceGroup,
            @ThriftField(value=2, name="personGroup", requiredness=Requiredness.OPTIONAL) final PersonGroupBean personGroup,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "addPermitById",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> addPermitById(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId,
            @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "applyAckChannel",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<String> applyAckChannel(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "applyAckChannelWithDuration",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<String> applyAckChannelWithDuration(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token,
            @ThriftField(value=2, name="duration", requiredness=Requiredness.REQUIRED) final long duration
        );

        @ThriftMethod(value = "applyCmdSn",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Long> applyCmdSn(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "applyPersonToken",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Token> applyPersonToken(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
            @ThriftField(value=2, name="password", requiredness=Requiredness.OPTIONAL) final String password,
            @ThriftField(value=3, name="isMd5", requiredness=Requiredness.REQUIRED) final boolean isMd5
        );

        @ThriftMethod(value = "applyRootToken",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Token> applyRootToken(
            @ThriftField(value=1, name="password", requiredness=Requiredness.OPTIONAL) final String password,
            @ThriftField(value=2, name="isMd5", requiredness=Requiredness.REQUIRED) final boolean isMd5
        );

        @ThriftMethod(value = "countDeviceByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> countDeviceByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "countDeviceGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> countDeviceGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "countLogByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> countLogByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "countLogLightByVerifyTime",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> countLogLightByVerifyTime(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
        );

        @ThriftMethod(value = "countLogLightByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> countLogLightByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "countPersonByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> countPersonByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "countPersonGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> countPersonGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "deleteAllFeaturesByPersonId",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deleteAllFeaturesByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
            @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.REQUIRED) final boolean deleteImage,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deleteDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deleteDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deleteFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<String>> deleteFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5,
            @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.REQUIRED) final boolean deleteImage,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deleteImage",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deleteImage(
            @ThriftField(value=1, name="imageMd5", requiredness=Requiredness.OPTIONAL) final String imageMd5,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deletePermit",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deletePermit(
            @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.OPTIONAL) final DeviceGroupBean deviceGroup,
            @ThriftField(value=2, name="personGroup", requiredness=Requiredness.OPTIONAL) final PersonGroupBean personGroup,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deletePerson",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deletePerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deletePersonByPapersNum",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deletePersonByPapersNum(
            @ThriftField(value=1, name="papersNum", requiredness=Requiredness.OPTIONAL) final String papersNum,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deletePersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deletePersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deletePersons",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deletePersons(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "deletePersonsByPapersNum",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> deletePersonsByPapersNum(
            @ThriftField(value=1, name="papersNumlist", requiredness=Requiredness.OPTIONAL) final List<String> papersNumlist,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "disablePerson",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> disablePerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "disablePersonList",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> disablePersonList(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "existsDevice",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> existsDevice(
            @ThriftField(value=1, name="id", requiredness=Requiredness.REQUIRED) final int id
        );

        @ThriftMethod(value = "existsFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> existsFeature(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
        );

        @ThriftMethod(value = "existsImage",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> existsImage(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
        );

        @ThriftMethod(value = "existsPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> existsPerson(
            @ThriftField(value=1, name="persionId", requiredness=Requiredness.REQUIRED) final int persionId
        );

        @ThriftMethod(value = "getDevice",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<DeviceBean> getDevice(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId
        );

        @ThriftMethod(value = "getDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<DeviceGroupBean> getDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
        );

        @ThriftMethod(value = "getDeviceGroups",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<DeviceGroupBean>> getDeviceGroups(
            @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> groupIdList
        );

        @ThriftMethod(value = "getDeviceGroupsBelongs",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> getDeviceGroupsBelongs(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId
        );

        @ThriftMethod(value = "getDeviceIdOfFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> getDeviceIdOfFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5
        );

        @ThriftMethod(value = "getDevices",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<DeviceBean>> getDevices(
            @ThriftField(value=1, name="idList", requiredness=Requiredness.OPTIONAL) final List<Integer> idList
        );

        @ThriftMethod(value = "getDevicesOfGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> getDevicesOfGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
        );

        @ThriftMethod(value = "getFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<FeatureBean> getFeature(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
        );

        @ThriftMethod(value = "getFeatureBeansByPersonId",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<String>> getFeatureBeansByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
        );

        @ThriftMethod(value = "getFeatureBytes",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<byte []> getFeatureBytes(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
        );

        @ThriftMethod(value = "getFeatures",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<FeatureBean>> getFeatures(
            @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final List<String> md5
        );

        @ThriftMethod(value = "getFeaturesOfPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<String>> getFeaturesOfPerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
        );

        @ThriftMethod(value = "getGroupPermit",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> getGroupPermit(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
            @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
        );

        @ThriftMethod(value = "getGroupPermits",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Boolean>> getGroupPermits(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
            @ThriftField(value=2, name="personGroupIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personGroupIdList
        );

        @ThriftMethod(value = "getImage",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<ImageBean> getImage(
            @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.OPTIONAL) final String imageMD5
        );

        @ThriftMethod(value = "getImageBytes",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<byte []> getImageBytes(
            @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.OPTIONAL) final String imageMD5
        );

        @ThriftMethod(value = "getImagesAssociatedByFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<String>> getImagesAssociatedByFeature(
            @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5
        );

        @ThriftMethod(value = "getLogBeansByPersonId",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<LogBean>> getLogBeansByPersonId(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
        );

        @ThriftMethod(value = "getPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> getPerson(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
        );

        @ThriftMethod(value = "getPersonByPapersNum",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> getPersonByPapersNum(
            @ThriftField(value=1, name="papersNum", requiredness=Requiredness.OPTIONAL) final String papersNum
        );

        @ThriftMethod(value = "getPersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonGroupBean> getPersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
        );

        @ThriftMethod(value = "getPersonGroups",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<PersonGroupBean>> getPersonGroups(
            @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> groupIdList
        );

        @ThriftMethod(value = "getPersonGroupsBelongs",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> getPersonGroupsBelongs(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
        );

        @ThriftMethod(value = "getPersonPermit",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> getPersonPermit(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
            @ThriftField(value=2, name="personId", requiredness=Requiredness.REQUIRED) final int personId
        );

        @ThriftMethod(value = "getPersonPermits",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Boolean>> getPersonPermits(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
            @ThriftField(value=2, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList
        );

        @ThriftMethod(value = "getPersons",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<PersonBean>> getPersons(
            @ThriftField(value=1, name="idList", requiredness=Requiredness.OPTIONAL) final List<Integer> idList
        );

        @ThriftMethod(value = "getPersonsOfGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> getPersonsOfGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
        );

        @ThriftMethod(value = "getProperty",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<String> getProperty(
            @ThriftField(value=1, name="key", requiredness=Requiredness.OPTIONAL) final String key,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "getRedisParameters",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Map<MQParam, String>> getRedisParameters(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "getServiceConfig",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Map<String, String>> getServiceConfig(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "getSubDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> getSubDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
        );

        @ThriftMethod(value = "getSubPersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> getSubPersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
        );

        @ThriftMethod(value = "isDisable",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isDisable(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
        );

        @ThriftMethod(value = "isLocal",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isLocal();

        @ThriftMethod(value = "isValidAckChannel",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isValidAckChannel(
            @ThriftField(value=1, name="ackChannel", requiredness=Requiredness.OPTIONAL) final String ackChannel
        );

        @ThriftMethod(value = "isValidCmdSn",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isValidCmdSn(
            @ThriftField(value=1, name="cmdSn", requiredness=Requiredness.REQUIRED) final long cmdSn
        );

        @ThriftMethod(value = "isValidDeviceToken",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isValidDeviceToken(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "isValidPassword",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isValidPassword(
            @ThriftField(value=1, name="userId", requiredness=Requiredness.OPTIONAL) final String userId,
            @ThriftField(value=2, name="password", requiredness=Requiredness.OPTIONAL) final String password,
            @ThriftField(value=3, name="isMd5", requiredness=Requiredness.REQUIRED) final boolean isMd5
        );

        @ThriftMethod(value = "isValidPersonToken",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isValidPersonToken(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "isValidRootToken",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Boolean> isValidRootToken(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "listOfParentForDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> listOfParentForDeviceGroup(
            @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
        );

        @ThriftMethod(value = "listOfParentForPersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> listOfParentForPersonGroup(
            @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
        );

        @ThriftMethod(value = "loadAllPerson",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadAllPerson();

        @ThriftMethod(value = "loadDeviceByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<DeviceBean>> loadDeviceByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
        );

        @ThriftMethod(value = "loadDeviceGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadDeviceGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
        );

        @ThriftMethod(value = "loadDeviceGroupIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadDeviceGroupIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "loadDeviceIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadDeviceIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "loadFeatureMd5ByUpdate",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<String>> loadFeatureMd5ByUpdate(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
        );

        @ThriftMethod(value = "loadLogByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<LogBean>> loadLogByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
        );

        @ThriftMethod(value = "loadLogLightByVerifyTime",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<LogLightBean>> loadLogLightByVerifyTime(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
        );

        @ThriftMethod(value = "loadLogLightByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<LogLightBean>> loadLogLightByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
        );

        @ThriftMethod(value = "loadPermitByUpdate",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<PermitBean>> loadPermitByUpdate(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
        );

        @ThriftMethod(value = "loadPersonByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<PersonBean>> loadPersonByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
        );

        @ThriftMethod(value = "loadPersonGroupByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadPersonGroupByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
            @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
            @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
        );

        @ThriftMethod(value = "loadPersonGroupIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadPersonGroupIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "loadPersonIdByUpdateTime",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadPersonIdByUpdateTime(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
        );

        @ThriftMethod(value = "loadPersonIdByWhere",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadPersonIdByWhere(
            @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
        );

        @ThriftMethod(value = "loadUpdatedPersons",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<List<Integer>> loadUpdatedPersons(
            @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
        );

        @ThriftMethod(value = "offline",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Void> offline(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "online",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Token> online(
            @ThriftField(value=1, name="device", requiredness=Requiredness.OPTIONAL) final DeviceBean device
        );

        @ThriftMethod(value = "registerDevice",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<DeviceBean> registerDevice(
            @ThriftField(value=1, name="newDevice", requiredness=Requiredness.OPTIONAL) final DeviceBean newDevice
        );

        @ThriftMethod(value = "releasePersonToken",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Void> releasePersonToken(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "releaseRootToken",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Void> releaseRootToken(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "replaceFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> replaceFeature(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
            @ThriftField(value=2, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5,
            @ThriftField(value=3, name="deleteOldFeatureImage", requiredness=Requiredness.REQUIRED) final boolean deleteOldFeatureImage,
            @ThriftField(value=4, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "saveDevice",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<DeviceBean> saveDevice(
            @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.OPTIONAL) final DeviceBean deviceBean,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "saveDeviceGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<DeviceGroupBean> saveDeviceGroup(
            @ThriftField(value=1, name="deviceGroupBean", requiredness=Requiredness.OPTIONAL) final DeviceGroupBean deviceGroupBean,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePerson",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> savePerson(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonFull",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonFull(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
            @ThriftField(value=4, name="featureImage", requiredness=Requiredness.OPTIONAL) final byte [] featureImage,
            @ThriftField(value=5, name="featureFaceBean", requiredness=Requiredness.OPTIONAL) final FaceBean featureFaceBean,
            @ThriftField(value=6, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
            @ThriftField(value=7, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonGroup",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonGroupBean> savePersonGroup(
            @ThriftField(value=1, name="personGroupBean", requiredness=Requiredness.OPTIONAL) final PersonGroupBean personGroupBean,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonWithPhoto",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhoto(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeature",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeature(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
            @ThriftField(value=3, name="featureBean", requiredness=Requiredness.OPTIONAL) final FeatureBean featureBean,
            @ThriftField(value=4, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
            @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiFaces(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
            @ThriftField(value=4, name="faceBeans", requiredness=Requiredness.OPTIONAL) final List<FaceBean> faceBeans,
            @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiImage(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
            @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
            @ThriftField(value=3, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
            @ThriftField(value=4, name="faceInfo", requiredness=Requiredness.OPTIONAL) final Map<byte [], FaceBean> faceInfo,
            @ThriftField(value=5, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
            @ThriftField(value=6, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureSaved(
            @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
            @ThriftField(value=2, name="idPhotoMd5", requiredness=Requiredness.OPTIONAL) final String idPhotoMd5,
            @ThriftField(value=3, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5,
            @ThriftField(value=4, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersons",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> savePersons(
            @ThriftField(value=1, name="beans", requiredness=Requiredness.OPTIONAL) final List<PersonBean> beans,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "savePersonsWithPhoto",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Integer> savePersonsWithPhoto(
            @ThriftField(value=1, name="persons", requiredness=Requiredness.OPTIONAL) final Map<byte [], PersonBean> persons,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "saveServiceConfig",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> saveServiceConfig(
            @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "setPersonExpiryDate",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> setPersonExpiryDate(
            @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
            @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.REQUIRED) final long expiryDate,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "setPersonExpiryDateList",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> setPersonExpiryDateList(
            @ThriftField(value=1, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList,
            @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.REQUIRED) final long expiryDate,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "setProperties",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> setProperties(
            @ThriftField(value=1, name="config", requiredness=Requiredness.OPTIONAL) final Map<String, String> config,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "setProperty",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Void> setProperty(
            @ThriftField(value=1, name="key", requiredness=Requiredness.OPTIONAL) final String key,
            @ThriftField(value=2, name="value", requiredness=Requiredness.OPTIONAL) final String value,
            @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "unregisterDevice",
                      exception = {
                          @ThriftException(type=ServiceSecurityException.class, id=1),
                          @ThriftException(type=ServiceRuntimeException.class, id=2)
                      })
        ListenableFuture<Void> unregisterDevice(
            @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "updateDevice",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<DeviceBean> updateDevice(
            @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.OPTIONAL) final DeviceBean deviceBean,
            @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
        );

        @ThriftMethod(value = "version",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<String> version();

        @ThriftMethod(value = "versionInfo",
                      exception = {
                          @ThriftException(type=ServiceRuntimeException.class, id=1)
                      })
        ListenableFuture<Map<String, String>> versionInfo();
    }
    @ThriftMethod(value = "addFeature",
                  exception = {
                      @ThriftException(type=DuplicateRecordException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    FeatureBean addFeature(
        @ThriftField(value=1, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
        @ThriftField(value=3, name="faecBeans", requiredness=Requiredness.OPTIONAL) final List<FaceBean> faecBeans,
        @ThriftField(value=4, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws DuplicateRecordException, ServiceRuntimeException;

    @ThriftMethod(value = "addFeatureMulti",
                  exception = {
                      @ThriftException(type=DuplicateRecordException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    FeatureBean addFeatureMulti(
        @ThriftField(value=1, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
        @ThriftField(value=3, name="faceInfo", requiredness=Requiredness.OPTIONAL) final Map<byte [], FaceBean> faceInfo,
        @ThriftField(value=4, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
        @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws DuplicateRecordException, ServiceRuntimeException;

    @ThriftMethod(value = "addImage",
                  exception = {
                      @ThriftException(type=DuplicateRecordException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    ImageBean addImage(
        @ThriftField(value=1, name="imageData", requiredness=Requiredness.OPTIONAL) final byte [] imageData,
        @ThriftField(value=2, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
        @ThriftField(value=3, name="faceBean", requiredness=Requiredness.OPTIONAL) final FaceBean faceBean,
        @ThriftField(value=4, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
        @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws DuplicateRecordException, ServiceRuntimeException;

    @ThriftMethod(value = "addLog",
                  exception = {
                      @ThriftException(type=DuplicateRecordException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    void addLog(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final LogBean bean,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws DuplicateRecordException, ServiceRuntimeException;

    @ThriftMethod(value = "addLogs",
                  exception = {
                      @ThriftException(type=DuplicateRecordException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    void addLogs(
        @ThriftField(value=1, name="beans", requiredness=Requiredness.OPTIONAL) final List<LogBean> beans,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws DuplicateRecordException, ServiceRuntimeException;

    @ThriftMethod(value = "addPermit",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void addPermit(
        @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.OPTIONAL) final DeviceGroupBean deviceGroup,
        @ThriftField(value=2, name="personGroup", requiredness=Requiredness.OPTIONAL) final PersonGroupBean personGroup,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "addPermitById",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void addPermitById(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId,
        @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "applyAckChannel",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    String applyAckChannel(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "applyAckChannelWithDuration",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    String applyAckChannelWithDuration(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token,
        @ThriftField(value=2, name="duration", requiredness=Requiredness.REQUIRED) final long duration
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "applyCmdSn",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    long applyCmdSn(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "applyPersonToken",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    Token applyPersonToken(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
        @ThriftField(value=2, name="password", requiredness=Requiredness.OPTIONAL) final String password,
        @ThriftField(value=3, name="isMd5", requiredness=Requiredness.REQUIRED) final boolean isMd5
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "applyRootToken",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    Token applyRootToken(
        @ThriftField(value=1, name="password", requiredness=Requiredness.OPTIONAL) final String password,
        @ThriftField(value=2, name="isMd5", requiredness=Requiredness.REQUIRED) final boolean isMd5
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "countDeviceByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int countDeviceByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "countDeviceGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int countDeviceGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "countLogByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int countLogByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "countLogLightByVerifyTime",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int countLogLightByVerifyTime(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "countLogLightByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int countLogLightByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "countPersonByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int countPersonByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "countPersonGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int countPersonGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deleteAllFeaturesByPersonId",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deleteAllFeaturesByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
        @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.REQUIRED) final boolean deleteImage,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deleteDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deleteDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deleteFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<String> deleteFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5,
        @ThriftField(value=2, name="deleteImage", requiredness=Requiredness.REQUIRED) final boolean deleteImage,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deleteImage",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deleteImage(
        @ThriftField(value=1, name="imageMd5", requiredness=Requiredness.OPTIONAL) final String imageMd5,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deletePermit",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deletePermit(
        @ThriftField(value=1, name="deviceGroup", requiredness=Requiredness.OPTIONAL) final DeviceGroupBean deviceGroup,
        @ThriftField(value=2, name="personGroup", requiredness=Requiredness.OPTIONAL) final PersonGroupBean personGroup,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deletePerson",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deletePerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deletePersonByPapersNum",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deletePersonByPapersNum(
        @ThriftField(value=1, name="papersNum", requiredness=Requiredness.OPTIONAL) final String papersNum,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deletePersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deletePersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deletePersons",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deletePersons(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "deletePersonsByPapersNum",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int deletePersonsByPapersNum(
        @ThriftField(value=1, name="papersNumlist", requiredness=Requiredness.OPTIONAL) final List<String> papersNumlist,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "disablePerson",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void disablePerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "disablePersonList",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void disablePersonList(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "existsDevice",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean existsDevice(
        @ThriftField(value=1, name="id", requiredness=Requiredness.REQUIRED) final int id
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "existsFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean existsFeature(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "existsImage",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean existsImage(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "existsPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean existsPerson(
        @ThriftField(value=1, name="persionId", requiredness=Requiredness.REQUIRED) final int persionId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getDevice",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    DeviceBean getDevice(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    DeviceGroupBean getDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getDeviceGroups",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<DeviceGroupBean> getDeviceGroups(
        @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> groupIdList
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getDeviceGroupsBelongs",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> getDeviceGroupsBelongs(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getDeviceIdOfFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int getDeviceIdOfFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getDevices",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<DeviceBean> getDevices(
        @ThriftField(value=1, name="idList", requiredness=Requiredness.OPTIONAL) final List<Integer> idList
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getDevicesOfGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> getDevicesOfGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    FeatureBean getFeature(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getFeatureBeansByPersonId",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<String> getFeatureBeansByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getFeatureBytes",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    byte [] getFeatureBytes(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final String md5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getFeatures",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<FeatureBean> getFeatures(
        @ThriftField(value=1, name="md5", requiredness=Requiredness.OPTIONAL) final List<String> md5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getFeaturesOfPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<String> getFeaturesOfPerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getGroupPermit",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean getGroupPermit(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
        @ThriftField(value=2, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getGroupPermits",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Boolean> getGroupPermits(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
        @ThriftField(value=2, name="personGroupIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personGroupIdList
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getImage",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    ImageBean getImage(
        @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.OPTIONAL) final String imageMD5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getImageBytes",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    byte [] getImageBytes(
        @ThriftField(value=1, name="imageMD5", requiredness=Requiredness.OPTIONAL) final String imageMD5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getImagesAssociatedByFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<String> getImagesAssociatedByFeature(
        @ThriftField(value=1, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getLogBeansByPersonId",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<LogBean> getLogBeansByPersonId(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean getPerson(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersonByPapersNum",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean getPersonByPapersNum(
        @ThriftField(value=1, name="papersNum", requiredness=Requiredness.OPTIONAL) final String papersNum
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonGroupBean getPersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersonGroups",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<PersonGroupBean> getPersonGroups(
        @ThriftField(value=1, name="groupIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> groupIdList
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersonGroupsBelongs",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> getPersonGroupsBelongs(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersonPermit",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean getPersonPermit(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
        @ThriftField(value=2, name="personId", requiredness=Requiredness.REQUIRED) final int personId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersonPermits",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Boolean> getPersonPermits(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
        @ThriftField(value=2, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersons",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<PersonBean> getPersons(
        @ThriftField(value=1, name="idList", requiredness=Requiredness.OPTIONAL) final List<Integer> idList
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getPersonsOfGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> getPersonsOfGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getProperty",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    String getProperty(
        @ThriftField(value=1, name="key", requiredness=Requiredness.OPTIONAL) final String key,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getRedisParameters",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    Map<MQParam, String> getRedisParameters(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getServiceConfig",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    Map<String, String> getServiceConfig(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getSubDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> getSubDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "getSubPersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> getSubPersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "isDisable",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isDisable(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "isLocal",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isLocal() throws ServiceRuntimeException;

    @ThriftMethod(value = "isValidAckChannel",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isValidAckChannel(
        @ThriftField(value=1, name="ackChannel", requiredness=Requiredness.OPTIONAL) final String ackChannel
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "isValidCmdSn",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isValidCmdSn(
        @ThriftField(value=1, name="cmdSn", requiredness=Requiredness.REQUIRED) final long cmdSn
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "isValidDeviceToken",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isValidDeviceToken(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "isValidPassword",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isValidPassword(
        @ThriftField(value=1, name="userId", requiredness=Requiredness.OPTIONAL) final String userId,
        @ThriftField(value=2, name="password", requiredness=Requiredness.OPTIONAL) final String password,
        @ThriftField(value=3, name="isMd5", requiredness=Requiredness.REQUIRED) final boolean isMd5
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "isValidPersonToken",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isValidPersonToken(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "isValidRootToken",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    boolean isValidRootToken(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "listOfParentForDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> listOfParentForDeviceGroup(
        @ThriftField(value=1, name="deviceGroupId", requiredness=Requiredness.REQUIRED) final int deviceGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "listOfParentForPersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> listOfParentForPersonGroup(
        @ThriftField(value=1, name="personGroupId", requiredness=Requiredness.REQUIRED) final int personGroupId
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadAllPerson",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadAllPerson() throws ServiceRuntimeException;

    @ThriftMethod(value = "loadDeviceByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<DeviceBean> loadDeviceByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadDeviceGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadDeviceGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadDeviceGroupIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadDeviceGroupIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadDeviceIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadDeviceIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadFeatureMd5ByUpdate",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<String> loadFeatureMd5ByUpdate(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadLogByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<LogBean> loadLogByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadLogLightByVerifyTime",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<LogLightBean> loadLogLightByVerifyTime(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadLogLightByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<LogLightBean> loadLogLightByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadPermitByUpdate",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<PermitBean> loadPermitByUpdate(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadPersonByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<PersonBean> loadPersonByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadPersonGroupByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadPersonGroupByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where,
        @ThriftField(value=2, name="startRow", requiredness=Requiredness.REQUIRED) final int startRow,
        @ThriftField(value=3, name="numRows", requiredness=Requiredness.REQUIRED) final int numRows
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadPersonGroupIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadPersonGroupIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadPersonIdByUpdateTime",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadPersonIdByUpdateTime(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadPersonIdByWhere",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadPersonIdByWhere(
        @ThriftField(value=1, name="where", requiredness=Requiredness.OPTIONAL) final String where
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "loadUpdatedPersons",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    List<Integer> loadUpdatedPersons(
        @ThriftField(value=1, name="timestamp", requiredness=Requiredness.REQUIRED) final long timestamp
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "offline",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    void offline(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "online",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    Token online(
        @ThriftField(value=1, name="device", requiredness=Requiredness.OPTIONAL) final DeviceBean device
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "registerDevice",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    DeviceBean registerDevice(
        @ThriftField(value=1, name="newDevice", requiredness=Requiredness.OPTIONAL) final DeviceBean newDevice
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "releasePersonToken",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    void releasePersonToken(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "releaseRootToken",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    void releaseRootToken(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "replaceFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void replaceFeature(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.OPTIONAL) final Integer personId,
        @ThriftField(value=2, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5,
        @ThriftField(value=3, name="deleteOldFeatureImage", requiredness=Requiredness.REQUIRED) final boolean deleteOldFeatureImage,
        @ThriftField(value=4, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "saveDevice",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    DeviceBean saveDevice(
        @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.OPTIONAL) final DeviceBean deviceBean,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "saveDeviceGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    DeviceGroupBean saveDeviceGroup(
        @ThriftField(value=1, name="deviceGroupBean", requiredness=Requiredness.OPTIONAL) final DeviceGroupBean deviceGroupBean,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePerson",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean savePerson(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonFull",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean savePersonFull(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
        @ThriftField(value=4, name="featureImage", requiredness=Requiredness.OPTIONAL) final byte [] featureImage,
        @ThriftField(value=5, name="featureFaceBean", requiredness=Requiredness.OPTIONAL) final FaceBean featureFaceBean,
        @ThriftField(value=6, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
        @ThriftField(value=7, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonGroup",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonGroupBean savePersonGroup(
        @ThriftField(value=1, name="personGroupBean", requiredness=Requiredness.OPTIONAL) final PersonGroupBean personGroupBean,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonWithPhoto",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean savePersonWithPhoto(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonWithPhotoAndFeature",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeature(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
        @ThriftField(value=3, name="featureBean", requiredness=Requiredness.OPTIONAL) final FeatureBean featureBean,
        @ThriftField(value=4, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
        @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeatureMultiFaces(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
        @ThriftField(value=4, name="faceBeans", requiredness=Requiredness.OPTIONAL) final List<FaceBean> faceBeans,
        @ThriftField(value=5, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeatureMultiImage(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
        @ThriftField(value=2, name="idPhoto", requiredness=Requiredness.OPTIONAL) final byte [] idPhoto,
        @ThriftField(value=3, name="feature", requiredness=Requiredness.OPTIONAL) final byte [] feature,
        @ThriftField(value=4, name="faceInfo", requiredness=Requiredness.OPTIONAL) final Map<byte [], FaceBean> faceInfo,
        @ThriftField(value=5, name="deviceId", requiredness=Requiredness.OPTIONAL) final Integer deviceId,
        @ThriftField(value=6, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    PersonBean savePersonWithPhotoAndFeatureSaved(
        @ThriftField(value=1, name="bean", requiredness=Requiredness.OPTIONAL) final PersonBean bean,
        @ThriftField(value=2, name="idPhotoMd5", requiredness=Requiredness.OPTIONAL) final String idPhotoMd5,
        @ThriftField(value=3, name="featureMd5", requiredness=Requiredness.OPTIONAL) final String featureMd5,
        @ThriftField(value=4, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersons",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void savePersons(
        @ThriftField(value=1, name="beans", requiredness=Requiredness.OPTIONAL) final List<PersonBean> beans,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "savePersonsWithPhoto",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    int savePersonsWithPhoto(
        @ThriftField(value=1, name="persons", requiredness=Requiredness.OPTIONAL) final Map<byte [], PersonBean> persons,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "saveServiceConfig",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void saveServiceConfig(
        @ThriftField(value=1, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "setPersonExpiryDate",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void setPersonExpiryDate(
        @ThriftField(value=1, name="personId", requiredness=Requiredness.REQUIRED) final int personId,
        @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.REQUIRED) final long expiryDate,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "setPersonExpiryDateList",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void setPersonExpiryDateList(
        @ThriftField(value=1, name="personIdList", requiredness=Requiredness.OPTIONAL) final List<Integer> personIdList,
        @ThriftField(value=2, name="expiryDate", requiredness=Requiredness.REQUIRED) final long expiryDate,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "setProperties",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void setProperties(
        @ThriftField(value=1, name="config", requiredness=Requiredness.OPTIONAL) final Map<String, String> config,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "setProperty",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    void setProperty(
        @ThriftField(value=1, name="key", requiredness=Requiredness.OPTIONAL) final String key,
        @ThriftField(value=2, name="value", requiredness=Requiredness.OPTIONAL) final String value,
        @ThriftField(value=3, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "unregisterDevice",
                  exception = {
                      @ThriftException(type=ServiceSecurityException.class, id=1),
                      @ThriftException(type=ServiceRuntimeException.class, id=2)
                  })
    void unregisterDevice(
        @ThriftField(value=1, name="deviceId", requiredness=Requiredness.REQUIRED) final int deviceId,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceSecurityException, ServiceRuntimeException;

    @ThriftMethod(value = "updateDevice",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    DeviceBean updateDevice(
        @ThriftField(value=1, name="deviceBean", requiredness=Requiredness.OPTIONAL) final DeviceBean deviceBean,
        @ThriftField(value=2, name="token", requiredness=Requiredness.OPTIONAL) final Token token
    ) throws ServiceRuntimeException;

    @ThriftMethod(value = "version",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    String version() throws ServiceRuntimeException;

    @ThriftMethod(value = "versionInfo",
                  exception = {
                      @ThriftException(type=ServiceRuntimeException.class, id=1)
                  })
    Map<String, String> versionInfo() throws ServiceRuntimeException;
}