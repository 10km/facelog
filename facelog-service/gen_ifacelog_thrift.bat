java -cp lib\idl-generator-cli-1.4-standalone.jar;target\classes;..\db\target\classes;..\simplemq\target\classes ^
    gu.rpc.thrift.swift2thrift.generator.Main ^
    -namespace py gdface.thrift ^
    -namespace java com.gdface -namespace cpp gdface ^
    -out IFaceLog.thrift ^
    -package net.gdface.facelog.service BaseFaceLog ^
    -recursive