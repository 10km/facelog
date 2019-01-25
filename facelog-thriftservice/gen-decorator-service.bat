java -cp lib\codegen-thrift-1.0.5-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes; ^
    net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
		-ic net.gdface.facelog.IFaceLog ^
		-rc net.gdface.facelog.FacelogDefaultImpl ^
		-p net.gdface.facelog.decorator ^
		-o src\service\java ^
		--thrift-package net.gdface.facelog.thrift.client ^
		-gt SERVICE
		
rem cd facelog-thriftservice
rem mvn com.gitee.l0km:codegen-thrift-maven-plugin:1.0.5:generate
rem cd ..