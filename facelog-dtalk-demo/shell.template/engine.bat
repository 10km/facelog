pushd %~dp0
java -jar ${project.build.finalName}-engine-standalone.jar %*
popd