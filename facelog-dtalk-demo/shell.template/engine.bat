pushd %~dp0
java -jar target\facelog-dtalk-demo-${project.version}-engine-standalone.jar %*
popd