#!/bin/bash
# 生成 facelog cpp client代码脚本
sh_folder=$(dirname $(readlink -f $0))
pushd "$sh_folder"
IDL=$sh_folder/IFaceLog.mini.thrift
[ -n "$1" ] && IDL=$1
OUT_FOLDER=$sh_folder/../facelog-client-ext/cpp.mini
[ -n "$2" ] && OUT_FOLDER=$2
[ -z "$(which thrift)" ] && echo "NOT FOUND thrift" && exit -1 
THRIFT_EXE=thrift
[   -d "$OUT_FOLDER" ] && rm -fr "$OUT_FOLDER/*.c" >/dev/null 2>/devnull
[ ! -d "$OUT_FOLDER" ] && mkdir "$OUT_FOLDER"
$THRIFT_EXE --gen cpp:no_skeleton,moveable_types,templates \
	-out "$OUT_FOLDER" \
	"$IDL"
popd
