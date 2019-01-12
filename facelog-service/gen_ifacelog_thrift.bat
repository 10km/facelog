  java -cp lib\idl-generator-cli-1.8-standalone.jar;target\classes;..\db\target\classes;..\simplemq\target\classes ^
     com.gitee.l0km.thrift.swift2thrift.generator.Main ^
     -namespace cpp gdface ^
     -namespace java net.gdface.facelog.client.thrift ^
     -namespace py gdface.thrift ^
     -out IFaceLog.thrift ^
     -package net.gdface.facelog.service BaseFaceLog ^
     -recursive ^
     -objectOptional
rem mvn com.gitee.l0km:swift2thrift-maven-plugin:1.8:generate