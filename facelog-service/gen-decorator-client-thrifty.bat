rem java -cp lib\codegen-thrift-1.0.5-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes; ^
rem     net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
rem 		-ic net.gdface.facelog.IFaceLog ^
rem 		-rc net.gdface.facelog.FacelogDefaultImpl ^
rem 		-p net.gdface.facelog.thrift ^
rem 		-o ..\facelog-client-android\src\codegen\java ^
rem 		--thrift-package net.gdface.facelog.client.thrift ^
rem 		-gt CLIENT_THRIFTY

@rem 生成基于Microsoft/thrifty的client端接口实现代码
@set sh_folder=%~dp0
@pushd %sh_folder%..\facelog-client-android
mvn com.gitee.l0km:codegen-thrift-maven-plugin:generate
@popd
