pushd %~dp0
java -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n -jar target\facelog-service-2.0.3-SNAPSHOT-standalone.jar
popd