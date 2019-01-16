set OUT_FOLDER=J:\facelog\facelog-thriftclient\src\thrift\java
rmdir  %OUT_FOLDER% /s/q
java -jar lib\swift-generator-cli-0.14.2-standalone.jar ^
	IFaceLog.thrift ^
	-generate_beans ^
 	-override_package net.gdface.facelog.thrift.client ^
	-out %OUT_FOLDER% 
	
