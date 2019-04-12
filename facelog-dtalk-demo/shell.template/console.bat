pushd %~dp0
java -jar facelog-dtalk-demo-${project.version}-console-standalone.jar %*
popd