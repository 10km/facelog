echo off
cd sql2java
mvn antrun:run -Dsql2java-config=../gen-mysql-g.properties -Dgenerated-src=../src/general/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar -Dnative-src=../src/orm/java
cd ..