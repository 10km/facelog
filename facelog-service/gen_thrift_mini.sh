#!/bin/bash
# 生成 facelog 接口定义文件(IDL)
sh_folder=$(dirname $(readlink -f $0))
pushd "$sh_folder"
out=IFaceLog.mini.thrift
[ -n "$1" ] && out=$1
mvn com.gitee.l0km:swift2thrift-maven-plugin:generate \
		-Dthrift.service.decorator.package=net.gdface.facelog.decorator.mini \
		-Dswift2thrift.out=$out
popd
