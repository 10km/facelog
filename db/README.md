#说明

本文件夹 sql2java 用于生成数据库访问代码(ORM)

gen-mysql.properties sql2java生成ORM代码的配置文件

gen-mysql.sh gen-mysql.bat 用于生成ORM代码的简单脚本,使用示例如下：

	gen-mysql.bat

sql/create_table.sql MySQL数据库建表脚本

gen-sql.xml Maven脚本执行 create_table.sql

使用示例如下

	mvn -f gen-sql.xml sql:execute -Ddb.url=jdbc:mysql://localhost:3306/test
