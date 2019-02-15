rem java -cp lib\codegen-thrift-1.0.5-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes; ^
rem     net.gdface.codegen.thrift.ThriftServiceDecoratorGenerator ^
rem 		-ic net.gdface.facelog.IFaceLog ^
rem 		-rc net.gdface.facelog.FacelogDefaultImpl ^
rem 		-p net.gdface.facelog.thrift ^
rem 		-o ..\facelog-client\src\codegen\java ^
rem 		--thrift-package net.gdface.facelog.client.thrift ^
rem 		-gt CLIENT

@rem 生成基于facebook/swift的client端接口实现代码
@set sh_folder=%~dp0
@pushd %sh_folder%..\facelog-client
@set OUT_FOLDER=src\codegen\java
@if exist "%OUT_FOLDER%" rmdir  %OUT_FOLDER% /s/q
mvn com.gitee.l0km:codegen-thrift-maven-plugin:generate
@popd