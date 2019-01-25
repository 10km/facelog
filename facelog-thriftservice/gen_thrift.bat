java -cp lib\idl-generator-cli-1.9-standalone.jar;..\facelog-base\target\classes;..\db\facelog-db-base\target\classes;target\classes;..\..\common-java\common-thrift\target\classes;..\..\common-java\common-base\target\classes ^
    com.gitee.l0km.thrift.swift2thrift.generator.Main ^
    -namespace cpp gdface ^
    -namespace java net.gdface.facelog.client.thrift ^
    -namespace py gdface.thrift ^
    -out IFaceLog.thrift ^
    -package net.gdface.facelog.decorator IFaceLogThriftDecorator ^
    -recursive ^
    -objectOptional