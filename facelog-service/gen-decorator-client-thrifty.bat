rem java -cp lib\codegen-thrift-1.0.5-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes; ^
rem     net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
rem 		-ic net.gdface.facelog.IFaceLog ^
rem 		-rc net.gdface.facelog.FacelogDefaultImpl ^
rem 		-p net.gdface.facelog.thrift ^
rem 		-o ..\facelog-client-android\src\codegen\java ^
rem 		--thrift-package net.gdface.facelog.client.thrift ^
rem 		-gt CLIENT_THRIFTY

pushd ..\facelog-client-android
mvn com.gitee.l0km:codegen-thrift-maven-plugin:generate
popd
