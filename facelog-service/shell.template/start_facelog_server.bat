pushd %~dp0
java %FACELOG_OPTS% %FACELOG_DEBUG_OPTS% -jar ${project.build.finalName}-standalone.jar %*
popd