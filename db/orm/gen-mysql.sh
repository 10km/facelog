#!/bin/bash
cd sql2java
mvn antrun:run -Dsql2java-config=../sql2java.properties -Dgenerated-src=../src/main/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
cd ..