rem java -cp lib\codegen-thrift-1.0.5-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes; ^
rem     net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
rem 		-ic net.gdface.facelog.IFaceLog ^
rem 		-rc net.gdface.facelog.FacelogDefaultImpl ^
rem 		-p net.gdface.facelog.decorator ^
rem 		-o src\codegen\java ^
rem 		--thrift-package net.gdface.facelog.thrift.client ^
rem 		-gt SERVICE
		
pushd ..\facelog-service
mvn com.gitee.l0km:codegen-thrift-maven-plugin:generate
popd