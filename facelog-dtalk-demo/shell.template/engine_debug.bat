pushd %~dp0
java -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n -jar ${project.build.finalName}-engine-standalone.jar %*
popd