rem java -cp lib\codegen-thrift-1.0.3-SNAPSHOT-standalone.jar;facelog-base\target\classes;db\facelog-db-base\target\classes;..\faceapi\faceapi-base\target\classes; ^
rem     net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
rem 		-ic net.gdface.facelog.IFaceLog ^
rem 		-rc net.gdface.facelog.FacelogDefaultImpl ^
rem 		-p net.gdface.facelog.decorator ^
rem 		-o facelog-thriftservice\src\service\java ^
rem 		--thrift-package net.gdface.facelog.thrift.client ^
rem 		-gt SERVICE
		
cd facelog-thriftservice
mvn com.gitee.l0km:codegen-thrift-maven-plugin:1.0.3-SNAPSHOT:generate
cd ..