pushd %~dp0
java -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n -jar facelog-dtalk-demo-${project.version}-console-standalone.jar %*
popd