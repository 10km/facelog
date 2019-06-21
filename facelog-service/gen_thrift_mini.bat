rem java -cp lib\idl-generator-cli-1.9-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes;target\classes;..\..\common-java\common-thrift\target\classes;..\..\common-java\common-base\target\classes ^
rem     com.gitee.l0km.thrift.swift2thrift.generator.Main ^
rem     -namespace cpp gdface ^
rem     -namespace java net.gdface.facelog.client.thrift ^
rem     -namespace py gdface.thrift ^
rem     -out IFaceLog.thrift ^
rem     -package net.gdface.facelog.decorator IFaceLogThriftDecorator ^
rem     -recursive ^
rem     -objectOptional
@set sh_folder=%~dp0
@pushd "%sh_folder%"
@set out=IFaceLog.mini.thrift
@if NOT "%1"=="" ( set out=%1 )
call mvn com.gitee.l0km:swift2thrift-maven-plugin:generate ^
		-Dthrift.service.decorator.package=net.gdface.facelog.decorator.mini ^
		-Dswift2thrift.out=%out%
@popd