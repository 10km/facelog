#!/bin/bash
sh_folder=$(dirname $(readlink -f $0))
pushd $sh_folder
java -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n -jar ${project.build.finalName}-standalone.jar $*
popd