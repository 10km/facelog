java -cp lib\idl-generator-cli-1.5-standalone.jar;target\classes;..\db\target\classes;..\simplemq\target\classes ^
    com.gitee.l0km.thrift.swift2thrift.generator.Main ^
    -namespace cpp gdface ^
    -namespace java com.gdface ^
    -namespace py gdface.thrift ^
    -out IFaceLog.thrift ^
    -package net.gdface.facelog.service BaseFaceLog ^
    -recursive