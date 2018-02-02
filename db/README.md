# 说明

本文件夹 sql2java 用于生成数据库访问代码(ORM)

# 建表语句
sql/create_table.sql MySQL数据库建表脚本

gen-sql.xml Maven脚本执行 create_table.sql

使用示例如下(需要先启动msqyl数据库):

	mvn -f gen-sql.xml sql:execute -Ddb.url=jdbc:mysql://localhost:3306/test?characterEncoding=utf8
	# 如果不指定 db.url参数，则使用默认本地数据库实例(参见gen-sql.xml中db.url定义)


# 生成数据库层代码

gen-mysql.properties sql2java生成ORM代码的配置文件

gen-mysql.sh gen-mysql.bat 用于生成ORM代码的简单脚本

使用示例如下:

	gen-mysql.bat

# 生成接口层代码

gen-mysql-g.properties sql2java生成接口层代码的配置文件

gen-mysql-g.sh gen-mysql-g.bat 用于生成接口层代码的简单脚本

使用示例如下:

	gen-mysql-g.bat

# docker

Dockerfile 用于创建一个基于mysql:5.6的facelog数据库的docker 镜像的脚本

