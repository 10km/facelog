echo off
cd sql2java
rem ant rebuild -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../src/orm/java
rem ant rebuild -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../src/orm/java -Ddriver-lib=../lib 
rem ant rebuild -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../src/orm/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
mvn antrun:run -Dsql2java-config=../gen-mysql.properties -Dgenerated-src=../src/orm/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
cd ..