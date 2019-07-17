#!/bin/bash
sh_folder=$(dirname $(readlink -f $0))
pushd $sh_folder
java $FACELOG_OPTS $FACELOG_DEBUG_OPTS -jar ${project.build.finalName}-standalone.jar $*
popd