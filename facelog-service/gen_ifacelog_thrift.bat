java -cp lib\idl-generator-cli-1.1-standalone.jar;target\classes;..\db\target\classes;..\simplemq\target\classes ^
    com.facebook.swift.generator.swift2thrift.custom.Main ^
    -namespace py gdface.thrift ^
    -namespace java com.gdface -namespace cpp gdface ^
    -out IFaceLog.thrift ^
    -package net.gdface.facelog.service BaseFaceLog ^
    -recursive