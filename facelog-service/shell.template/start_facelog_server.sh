#!/bin/bash
sh_folder=$(dirname $(readlink -f $0))
pushd $sh_folder
java -jar ${project.build.finalName}-standalone.jar $*
popd