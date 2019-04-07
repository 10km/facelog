pushd %~dp0
java -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n -jar target\facelog-dtalk-demo-2.0.7-SNAPSHOT-console-standalone.jar %*
popd