set OUT_FOLDER=..\facelog-thriftclient-android\src\thrifty\java
rmdir  %OUT_FOLDER% /s/q
REM Java 8 required
java -jar lib\thrifty-compiler-1.0.0-all.jar ^
	--out=%OUT_FOLDER% ^
	IFaceLog.thrift 

