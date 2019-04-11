echo off
set sh_folder=%~dp0
pushd %sh_folder%sql2java
rem ant rebuild -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../src/orm/java
rem ant rebuild -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../src/orm/java -Ddriver-lib=../lib 
rem ant rebuild -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../src/orm/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
call mvn antrun:run -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../facelog-db-orm/src/main/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
popd