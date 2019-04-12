pushd %~dp0
java -jar target\facelog-dtalk-demo-${project.version}-console-standalone.jar %*
popd