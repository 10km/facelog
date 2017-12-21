rem java -cp lib\idl-generator-cli-1.5-standalone.jar;target\classes;..\db\target\classes;..\simplemq\target\classes ^
rem     com.gitee.l0km.thrift.swift2thrift.generator.Main ^
rem     -namespace cpp gdface ^
rem     -namespace java com.gdface ^
rem     -namespace py gdface.thrift ^
rem     -out IFaceLog.thrift ^
rem     -package net.gdface.facelog.service BaseFaceLog ^
rem     -recursive
mvn com.gitee.l0km:swift2thrift-maven-plugin:1.7:generate