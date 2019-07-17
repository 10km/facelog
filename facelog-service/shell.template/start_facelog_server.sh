#!/bin/bash
sh_folder=$(dirname $(readlink -f $0))
pushd $sh_folder > /dev/null 2>&1
java $FACELOG_OPTS $FACELOG_DEBUG_OPTS -jar ${project.build.finalName}-standalone.jar $*
popd  > /dev/null 2>&1