rem set OUT_FOLDER=..\facelog-thriftclient\src\thrift\java
rem rmdir  %OUT_FOLDER% /s/q
rem java -jar lib\swift-generator-cli-0.14.2-standalone.jar ^
rem 	IFaceLog.thrift ^
rem 	-generate_beans ^
rem 	-override_package net.gdface.facelog.client.thrift ^
rem 	-out %OUT_FOLDER% 	
mvn com.facebook.mojo:swift-maven-plugin:0.14.2:generate