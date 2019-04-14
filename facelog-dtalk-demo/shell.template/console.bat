pushd %~dp0
java -jar ${project.build.finalName}-console-standalone.jar %*
popd