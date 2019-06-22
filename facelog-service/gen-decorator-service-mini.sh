#!/bin/bash
# ���ɽӿڵĻ���facebook/swift��service��ʵ�ִ���(decorator)
# ����config�����ļ������ɵĴ�����м���
sh_folder=$(dirname $(readlink -f $0))
pushd $sh_folder
#OUT_FOLDER=src/codegen/java
config=$sh_folder/thrift.service.config.mini.properties
[ -n "$1" ] && config = $1 
mvn com.gitee.l0km:codegen-thrift-maven-plugin:generate ^
		-Dthrift.service.decorator.config=%config% ^
		-Dthrift.service.decorator.package=net.gdface.facelog.decorator.mini 
popd