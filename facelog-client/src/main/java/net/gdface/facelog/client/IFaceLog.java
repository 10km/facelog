package net.gdface.facelog.client;

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
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final byte [] arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final List<FaceBean> arg2
        );

        @ThriftMethod(value = "addFeatureMulti")
        ListenableFuture<FeatureBean> addFeatureMulti(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final byte [] arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> arg2,
            @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
        );

        @ThriftMethod(value = "addImage")
        ListenableFuture<ImageBean> addImage(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final byte [] arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final FaceBean arg2,
            @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
        );

        @ThriftMethod(value = "addLog")
        ListenableFuture<Void> addLog(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final LogBean arg0
        );

        @ThriftMethod(value = "addLogList")
        ListenableFuture<Void> addLogList(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<LogBean> arg0
        );

        @ThriftMethod(value = "countLogLightWhere")
        ListenableFuture<Integer> countLogLightWhere(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "countLogWhere")
        ListenableFuture<Integer> countLogWhere(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "deleteAllFeaturesByPersonId")
        ListenableFuture<Integer> deleteAllFeaturesByPersonId(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final boolean arg1
        );

        @ThriftMethod(value = "deleteFeature")
        ListenableFuture<List<String>> deleteFeature(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final boolean arg1
        );

        @ThriftMethod(value = "deleteImage")
        ListenableFuture<Integer> deleteImage(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "deletePerson")
        ListenableFuture<Integer> deletePerson(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "deletePersonByPapersNum")
        ListenableFuture<Integer> deletePersonByPapersNum(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "deletePersons")
        ListenableFuture<Integer> deletePersons(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
        );

        @ThriftMethod(value = "deletePersonsByPapersNum")
        ListenableFuture<Integer> deletePersonsByPapersNum(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<String> arg0
        );

        @ThriftMethod(value = "disablePerson")
        ListenableFuture<Void> disablePerson(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "disablePersonList")
        ListenableFuture<Void> disablePersonList(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
        );

        @ThriftMethod(value = "existsDevice")
        ListenableFuture<Boolean> existsDevice(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "existsFeature")
        ListenableFuture<Boolean> existsFeature(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "existsImage")
        ListenableFuture<Boolean> existsImage(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "existsPerson")
        ListenableFuture<Boolean> existsPerson(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "getDevice")
        ListenableFuture<DeviceBean> getDevice(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "getDeviceList")
        ListenableFuture<List<DeviceBean>> getDeviceList(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
        );

        @ThriftMethod(value = "getFeature")
        ListenableFuture<FeatureBean> getFeature(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "getFeatureBeansByPersonId")
        ListenableFuture<List<String>> getFeatureBeansByPersonId(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "getFeatureBytes")
        ListenableFuture<byte []> getFeatureBytes(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "getFeatureList")
        ListenableFuture<List<FeatureBean>> getFeatureList(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<String> arg0
        );

        @ThriftMethod(value = "getImage")
        ListenableFuture<ImageBean> getImage(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "getImageBytes")
        ListenableFuture<byte []> getImageBytes(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "getImagesAssociatedByFeature")
        ListenableFuture<List<String>> getImagesAssociatedByFeature(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "getLogBeansByPersonId")
        ListenableFuture<List<LogBean>> getLogBeansByPersonId(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "getPerson")
        ListenableFuture<PersonBean> getPerson(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "getPersonByPapersNum")
        ListenableFuture<PersonBean> getPersonByPapersNum(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "getPersons")
        ListenableFuture<List<PersonBean>> getPersons(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
        );

        @ThriftMethod(value = "isDisable")
        ListenableFuture<Boolean> isDisable(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
        );

        @ThriftMethod(value = "loadAllPerson")
        ListenableFuture<List<Integer>> loadAllPerson();

        @ThriftMethod(value = "loadFeatureMd5ByUpdate")
        ListenableFuture<List<String>> loadFeatureMd5ByUpdate(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final long arg0
        );

        @ThriftMethod(value = "loadLogByWhere")
        ListenableFuture<List<LogBean>> loadLogByWhere(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final List<Integer> arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final int arg2,
            @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
        );

        @ThriftMethod(value = "loadLogLightByWhere")
        ListenableFuture<List<LogLightBean>> loadLogLightByWhere(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final int arg2
        );

        @ThriftMethod(value = "loadPersonByWhere")
        ListenableFuture<List<Integer>> loadPersonByWhere(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
        );

        @ThriftMethod(value = "loadPersonIdByUpdate")
        ListenableFuture<List<Integer>> loadPersonIdByUpdate(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final long arg0
        );

        @ThriftMethod(value = "loadUpdatePersons")
        ListenableFuture<List<Integer>> loadUpdatePersons(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final long arg0
        );

        @ThriftMethod(value = "replaceFeature")
        ListenableFuture<Void> replaceFeature(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final String arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final boolean arg2
        );

        @ThriftMethod(value = "saveDevice")
        ListenableFuture<DeviceBean> saveDevice(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final DeviceBean arg0
        );

        @ThriftMethod(value = "savePerson")
        ListenableFuture<PersonBean> savePerson(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0
        );

        @ThriftMethod(value = "savePersonFull")
        ListenableFuture<PersonBean> savePersonFull(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final byte [] arg2,
            @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final byte [] arg3,
            @ThriftField(value=5, name="arg4", requiredness=Requiredness.NONE) final FaceBean arg4,
            @ThriftField(value=6, name="arg5", requiredness=Requiredness.NONE) final int arg5
        );

        @ThriftMethod(value = "savePersonList")
        ListenableFuture<Void> savePersonList(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<PersonBean> arg0
        );

        @ThriftMethod(value = "savePersonWithPhoto")
        ListenableFuture<PersonBean> savePersonWithPhoto(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeature")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeature(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final FeatureBean arg2,
            @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiFaces(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final byte [] arg2,
            @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final List<FaceBean> arg3
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureMultiImage(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final byte [] arg2,
            @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> arg3,
            @ThriftField(value=5, name="arg4", requiredness=Requiredness.NONE) final int arg4
        );

        @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved")
        ListenableFuture<PersonBean> savePersonWithPhotoAndFeatureSaved(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final String arg1,
            @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final String arg2
        );

        @ThriftMethod(value = "savePersonsWithPhoto")
        ListenableFuture<Integer> savePersonsWithPhoto(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final Map<byte [], PersonBean> arg0
        );

        @ThriftMethod(value = "setPersonExpiryDate")
        ListenableFuture<Void> setPersonExpiryDate(
            @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0,
            @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final long arg1
        );
    }
    @ThriftMethod(value = "addFeature")
    FeatureBean addFeature(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final byte [] arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final List<FaceBean> arg2
    );


    @ThriftMethod(value = "addFeatureMulti")
    FeatureBean addFeatureMulti(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final byte [] arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> arg2,
        @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
    );


    @ThriftMethod(value = "addImage")
    ImageBean addImage(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final byte [] arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final FaceBean arg2,
        @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
    );


    @ThriftMethod(value = "addLog")
    void addLog(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final LogBean arg0
    );


    @ThriftMethod(value = "addLogList")
    void addLogList(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<LogBean> arg0
    );


    @ThriftMethod(value = "countLogLightWhere")
    int countLogLightWhere(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "countLogWhere")
    int countLogWhere(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "deleteAllFeaturesByPersonId")
    int deleteAllFeaturesByPersonId(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final boolean arg1
    );


    @ThriftMethod(value = "deleteFeature")
    List<String> deleteFeature(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final boolean arg1
    );


    @ThriftMethod(value = "deleteImage")
    int deleteImage(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "deletePerson")
    int deletePerson(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "deletePersonByPapersNum")
    int deletePersonByPapersNum(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "deletePersons")
    int deletePersons(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
    );


    @ThriftMethod(value = "deletePersonsByPapersNum")
    int deletePersonsByPapersNum(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<String> arg0
    );


    @ThriftMethod(value = "disablePerson")
    void disablePerson(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "disablePersonList")
    void disablePersonList(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
    );


    @ThriftMethod(value = "existsDevice")
    boolean existsDevice(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "existsFeature")
    boolean existsFeature(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "existsImage")
    boolean existsImage(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "existsPerson")
    boolean existsPerson(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "getDevice")
    DeviceBean getDevice(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "getDeviceList")
    List<DeviceBean> getDeviceList(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
    );


    @ThriftMethod(value = "getFeature")
    FeatureBean getFeature(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "getFeatureBeansByPersonId")
    List<String> getFeatureBeansByPersonId(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "getFeatureBytes")
    byte [] getFeatureBytes(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "getFeatureList")
    List<FeatureBean> getFeatureList(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<String> arg0
    );


    @ThriftMethod(value = "getImage")
    ImageBean getImage(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "getImageBytes")
    byte [] getImageBytes(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "getImagesAssociatedByFeature")
    List<String> getImagesAssociatedByFeature(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "getLogBeansByPersonId")
    List<LogBean> getLogBeansByPersonId(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "getPerson")
    PersonBean getPerson(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "getPersonByPapersNum")
    PersonBean getPersonByPapersNum(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "getPersons")
    List<PersonBean> getPersons(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<Integer> arg0
    );


    @ThriftMethod(value = "isDisable")
    boolean isDisable(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0
    );


    @ThriftMethod(value = "loadAllPerson")
    List<Integer> loadAllPerson();


    @ThriftMethod(value = "loadFeatureMd5ByUpdate")
    List<String> loadFeatureMd5ByUpdate(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final long arg0
    );


    @ThriftMethod(value = "loadLogByWhere")
    List<LogBean> loadLogByWhere(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final List<Integer> arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final int arg2,
        @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
    );


    @ThriftMethod(value = "loadLogLightByWhere")
    List<LogLightBean> loadLogLightByWhere(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final int arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final int arg2
    );


    @ThriftMethod(value = "loadPersonByWhere")
    List<Integer> loadPersonByWhere(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final String arg0
    );


    @ThriftMethod(value = "loadPersonIdByUpdate")
    List<Integer> loadPersonIdByUpdate(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final long arg0
    );


    @ThriftMethod(value = "loadUpdatePersons")
    List<Integer> loadUpdatePersons(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final long arg0
    );


    @ThriftMethod(value = "replaceFeature")
    void replaceFeature(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final String arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final boolean arg2
    );


    @ThriftMethod(value = "saveDevice")
    DeviceBean saveDevice(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final DeviceBean arg0
    );


    @ThriftMethod(value = "savePerson")
    PersonBean savePerson(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0
    );


    @ThriftMethod(value = "savePersonFull")
    PersonBean savePersonFull(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final byte [] arg2,
        @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final byte [] arg3,
        @ThriftField(value=5, name="arg4", requiredness=Requiredness.NONE) final FaceBean arg4,
        @ThriftField(value=6, name="arg5", requiredness=Requiredness.NONE) final int arg5
    );


    @ThriftMethod(value = "savePersonList")
    void savePersonList(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final List<PersonBean> arg0
    );


    @ThriftMethod(value = "savePersonWithPhoto")
    PersonBean savePersonWithPhoto(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeature")
    PersonBean savePersonWithPhotoAndFeature(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final FeatureBean arg2,
        @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final int arg3
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiFaces")
    PersonBean savePersonWithPhotoAndFeatureMultiFaces(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final byte [] arg2,
        @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final List<FaceBean> arg3
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeatureMultiImage")
    PersonBean savePersonWithPhotoAndFeatureMultiImage(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final byte [] arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final byte [] arg2,
        @ThriftField(value=4, name="arg3", requiredness=Requiredness.NONE) final Map<byte [], FaceBean> arg3,
        @ThriftField(value=5, name="arg4", requiredness=Requiredness.NONE) final int arg4
    );


    @ThriftMethod(value = "savePersonWithPhotoAndFeatureSaved")
    PersonBean savePersonWithPhotoAndFeatureSaved(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final PersonBean arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final String arg1,
        @ThriftField(value=3, name="arg2", requiredness=Requiredness.NONE) final String arg2
    );


    @ThriftMethod(value = "savePersonsWithPhoto")
    int savePersonsWithPhoto(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final Map<byte [], PersonBean> arg0
    );


    @ThriftMethod(value = "setPersonExpiryDate")
    void setPersonExpiryDate(
        @ThriftField(value=1, name="arg0", requiredness=Requiredness.NONE) final int arg0,
        @ThriftField(value=2, name="arg1", requiredness=Requiredness.NONE) final long arg1
    );

}