rem Java 8 required
@set sh_folder=%~dp0
@pushd %sh_folder%\..\facelog-client-android
@set OUT_FOLDER=src\thrifty\java
@if exist %OUT_FOLDER% rmdir  %OUT_FOLDER% /s/q
mvn com.gitee.l0km:thrifty-compiler-maven-plugin:stub 	
@popd 
