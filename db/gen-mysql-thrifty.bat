echo off
cd sql2java
mvn antrun:run -Dsql2java-config=../gen-mysql-thrifty.properties -Dgenerated-src=../../facelog-client-android/src/sql2java/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar 
cd ..