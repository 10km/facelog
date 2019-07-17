#!/bin/bash
sh_folder=$(dirname $(readlink -f $0))
FACELOG_DEBUG_OPTS=-Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=n
$sh_folder/start_facelog_server.sh $*