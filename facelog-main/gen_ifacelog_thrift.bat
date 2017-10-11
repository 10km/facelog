java -cp lib\swift2thrift-generator-cli-0.14.2-standalone.jar;target\classes;..\db\target\classes ^
    com.facebook.swift.generator.swift2thrift.Main ^
    -namespace py gdface.thrift ^
    -namespace java com.gdface -namespace cpp gdface ^
    -out IFaceLog.thrift ^
    -package net.gdface.facelog FaceLogDefinition ^
    -recursive