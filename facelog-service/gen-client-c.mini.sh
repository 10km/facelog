#!/bin/bash
# 生成 facelog C client代码脚本
sh_folder=$(dirname $(readlink -f $0))
pushd "$sh_folder"
IDL=$sh_folder/IFaceLog.mini.thrift
[ -n "$1" ] && IDL=$1
OUT_FOLDER=$sh_folder/../facelog-client-ext/c.mini
[ -n "$2" ] && OUT_FOLDER=$2
[ -z "$(which thrift)" ] && echo "NOT FOUND thrift" && exit -1 
THRIFT_EXE=thrift
[   -d "$OUT_FOLDER" ] && rm -fr "$OUT_FOLDER/*.c" >nul 2>nul 
[ ! -d "$OUT_FOLDER" ] && mkdir "$OUT_FOLDER"
$THRIFT_EXE --gen c_glib \
	-out "$OUT_FOLDER" \
	"$IDL"
popd
