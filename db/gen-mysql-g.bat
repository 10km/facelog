echo off
set sh_folder=%~dp0
pushd %sh_folder%sql2java
call mvn antrun:run -Dsql2java-config=../gen-mysql-g.properties -Dgenerated-src=../facelog-db-base/src/main/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
popd