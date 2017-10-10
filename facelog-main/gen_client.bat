java -jar lib\swift-generator-cli-0.14.2-standalone.jar ^
	IFaceLog.thrift ^
	-generate_beans ^
	-override_package net.gdface.facelog.client ^
	-out ../facelog-client/src/main/java
 	