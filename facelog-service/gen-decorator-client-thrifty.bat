java -cp lib\codegen-thrift-1.0.5-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes; ^
    net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
		-ic net.gdface.facelog.IFaceLog ^
		-rc net.gdface.facelog.FacelogDefaultImpl ^
		-p net.gdface.facelog.thrift ^
		-o ..\facelog-client-android\src\codegen\java ^
		--thrift-package net.gdface.facelog.client.thrift ^
		-gt CLIENT_THRIFTY

rem cd facelog-client-android
rem mvn com.gitee.l0km:codegen-thrift-maven-plugin:1.0.5:generate
rem cd ..