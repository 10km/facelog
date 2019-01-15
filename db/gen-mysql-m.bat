echo off
cd sql2java
mvn antrun:run -Dsql2java-config=../gen-mysql-m.properties -Dgenerated-src=../facelog-db-manager/src/main/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
cd ..