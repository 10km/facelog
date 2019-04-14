pushd %~dp0
java -jar ${project.build.finalName}-standalone.jar %*
popd