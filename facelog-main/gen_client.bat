set OUT_FOLDER=..\facelog-client\src\thrift\java
rmdir  %OUT_FOLDER% /s/q
java -jar lib\swift-generator-cli-0.14.2-standalone.jar ^
	IFaceLog.thrift ^
	-generate_beans ^
	-override_package net.gdface.facelog.client.thrift ^
	-out %OUT_FOLDER%
 	