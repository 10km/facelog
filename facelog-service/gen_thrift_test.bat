set MY_CLASSES=target\classes;target\test-classes;..\db\target\classes
java -cp lib\swift2thrift-generator-cli-0.14.2-standalone.jar;%MY_CLASSES% ^
    com.facebook.swift.generator.swift2thrift.Main -package net.gdface.facelog ^
    TestService  ^
    -namespace py gdface.thrift -namespace java com.gdface -namespace cpp gdface ^
    -out TestService.thrift ^
    -recursive