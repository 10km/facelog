// ______________________________________________________
// Generated by sql2java - https://github.com/10km/sql2java-2-6-7 (custom branch) 
// modified by guyadong from
// sql2java original version https://sourceforge.net/projects/sql2java/ 
// jdbc driver used at code generation time: com.mysql.jdbc.Driver
// template: service.java.vm
// ______________________________________________________
// gu.rpc.thrift.SwiftServiceParser 
// classloader sun.misc.Launcher$AppClassLoader
// classloader.parent sun.misc.Launcher$ExtClassLoader

/*
com.facebook.swift.service.metadata.ThriftServiceMetadata@acf395d3
IFaceLog
0 name: addFeature 
	param 0: feature class java.nio.ByteBuffer
	param 1: personId class java.lang.Integer
	param 2: faecBeans java.util.List<net.gdface.facelog.db.FaceBean>
1 name: addFeatureMulti original name: addFeature 
	param 0: feature class java.nio.ByteBuffer
	param 1: personId class java.lang.Integer
	param 2: faceInfo java.util.Map<java.nio.ByteBuffer, net.gdface.facelog.db.FaceBean>
	param 3: deviceId class java.lang.Integer
2 name: addImage 
	param 0: imageData class java.nio.ByteBuffer
	param 1: deviceId class java.lang.Integer
	param 2: faceBean class net.gdface.facelog.db.FaceBean
	param 3: personId class java.lang.Integer
3 name: addLog 
	param 0: bean class net.gdface.facelog.db.LogBean
4 name: addLogList original name: addLog 
	param 0: beans java.util.List<net.gdface.facelog.db.LogBean>
5 name: countLogLightWhere 
	param 0: where class java.lang.String
6 name: countLogWhere 
	param 0: where class java.lang.String
7 name: deleteAllFeaturesByPersonId 
	param 0: personId int
	param 1: deleteImage boolean
8 name: deleteFeature 
	param 0: featureMd5 class java.lang.String
	param 1: deleteImage boolean
9 name: deleteImage 
	param 0: imageMd5 class java.lang.String
10 name: deletePerson 
	param 0: personId int
11 name: deletePersonByPapersNum 
	param 0: papersNum class java.lang.String
12 name: deletePersons 
	param 0: personIdList java.util.List<java.lang.Integer>
13 name: deletePersonsByPapersNum 
	param 0: papersNumlist java.util.List<java.lang.String>
14 name: disablePerson 
	param 0: personId int
15 name: disablePersonList original name: disablePerson 
	param 0: personIdList java.util.List<java.lang.Integer>
16 name: existsDevice 
	param 0: id int
17 name: existsFeature 
	param 0: md5 class java.lang.String
18 name: existsImage 
	param 0: md5 class java.lang.String
19 name: existsPerson 
	param 0: persionId int
20 name: getDevice 
	param 0: deviceId class java.lang.Integer
21 name: getDeviceList original name: getDevice 
	param 0: deviceId java.util.List<java.lang.Integer>
22 name: getFeature 
	param 0: md5 class java.lang.String
23 name: getFeatureBeansByPersonId 
	param 0: personId int
24 name: getFeatureBytes 
	param 0: md5 class java.lang.String
25 name: getFeatureList original name: getFeature 
	param 0: md5 java.util.List<java.lang.String>
26 name: getImage 
	param 0: imageMD5 class java.lang.String
27 name: getImageBytes 
	param 0: imageMD5 class java.lang.String
28 name: getImagesAssociatedByFeature 
	param 0: featureMd5 class java.lang.String
29 name: getLogBeansByPersonId 
	param 0: personId int
30 name: getPerson 
	param 0: personId int
31 name: getPersonByPapersNum 
	param 0: papersNum class java.lang.String
32 name: getPersons 
	param 0: idList java.util.List<java.lang.Integer>
33 name: isDisable 
	param 0: personId int
34 name: loadAllPerson 
35 name: loadFeatureMd5ByUpdate 
	param 0: timestamp long
36 name: loadLogByWhere 
	param 0: where class java.lang.String
	param 1: startRow int
	param 2: numRows int
37 name: loadLogLightByWhere 
	param 0: where class java.lang.String
	param 1: startRow int
	param 2: numRows int
38 name: loadPersonByWhere 
	param 0: where class java.lang.String
39 name: loadPersonIdByUpdate 
	param 0: timestamp long
40 name: loadUpdatePersons 
	param 0: timestamp long
41 name: replaceFeature 
	param 0: personId class java.lang.Integer
	param 1: featureMd5 class java.lang.String
	param 2: deleteOldFeatureImage boolean
42 name: saveDevice 
	param 0: deviceBean class net.gdface.facelog.db.DeviceBean
43 name: savePerson 
	param 0: bean class net.gdface.facelog.db.PersonBean
44 name: savePersonFull original name: savePerson 
	param 0: bean class net.gdface.facelog.db.PersonBean
	param 1: idPhoto class java.nio.ByteBuffer
	param 2: feature class java.nio.ByteBuffer
	param 3: featureImage class java.nio.ByteBuffer
	param 4: featureFaceBean class net.gdface.facelog.db.FaceBean
	param 5: deviceId class java.lang.Integer
45 name: savePersonList original name: savePerson 
	param 0: beans java.util.List<net.gdface.facelog.db.PersonBean>
46 name: savePersonWithPhoto original name: savePerson 
	param 0: bean class net.gdface.facelog.db.PersonBean
	param 1: idPhoto class java.nio.ByteBuffer
47 name: savePersonWithPhotoAndFeature original name: savePerson 
	param 0: bean class net.gdface.facelog.db.PersonBean
	param 1: idPhoto class java.nio.ByteBuffer
	param 2: featureBean class net.gdface.facelog.db.FeatureBean
	param 3: deviceId class java.lang.Integer
48 name: savePersonWithPhotoAndFeatureMultiFaces original name: savePerson 
	param 0: bean class net.gdface.facelog.db.PersonBean
	param 1: idPhoto class java.nio.ByteBuffer
	param 2: feature class java.nio.ByteBuffer
	param 3: faceBeans java.util.List<net.gdface.facelog.db.FaceBean>
49 name: savePersonWithPhotoAndFeatureMultiImage original name: savePerson 
	param 0: bean class net.gdface.facelog.db.PersonBean
	param 1: idPhoto class java.nio.ByteBuffer
	param 2: feature class java.nio.ByteBuffer
	param 3: faceInfo java.util.Map<java.nio.ByteBuffer, net.gdface.facelog.db.FaceBean>
	param 4: deviceId class java.lang.Integer
50 name: savePersonWithPhotoAndFeatureSaved original name: savePerson 
	param 0: bean class net.gdface.facelog.db.PersonBean
	param 1: idPhotoMd5 class java.lang.String
	param 2: featureMd5 class java.lang.String
51 name: savePersonsWithPhoto original name: savePerson 
	param 0: persons java.util.Map<java.nio.ByteBuffer, net.gdface.facelog.db.PersonBean>
52 name: setPersonExpiryDate 
	param 0: personId int
	param 1: expiryDate long
53 name: setPersonExpiryDateList original name: setPersonExpiryDate 
	param 0: personIdList java.util.List<java.lang.Integer>
	param 1: expiryDate long

*/

package net.gdface.facelog.client;

public class Service implements Constant{

}
