pushd %~dp0
java -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n -jar target\facelog-dtalk-demo-${project.version}-engine-standalone.jar %*
popd