#!/bin/sh
set -ex
workdir=/usr/local/facelog
config_file = $workdir/config.properties
if [ ! -f $config_file] ; then
# 生成基本配置文件
		cat > $config_file <<EOF 
redis.host=$REDIS_HOST
redis.port=$REDIS_PORT 
redis.password=$REDIS_PASSWORD
database.jdbc.host=$DATABASE_HOST
database.jdbc.port=$DATABASE_PORT
database.jdbc.schema=$DATABASE_SCHEMA
database.jdbc.user=$DATABASE_USER
database.jdbc.password=$DATABASE_PASSWORD
syslog.location=$workdir/log/facelog.log
EOF
		ln -s $config_file ~/.facelog/config.properties
fi

exec "$@"