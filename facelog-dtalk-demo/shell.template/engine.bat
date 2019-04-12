pushd %~dp0
java -jar facelog-dtalk-demo-${project.version}-engine-standalone.jar %*
popd