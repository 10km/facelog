#!/bin/bash
cd sql2java
mvn antrun:run -Dsql2java-config=../gen-mysql-g.properties -Dgenerated-src=../facelog-db-base/src/main/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar -Dnative-src=../src/orm/java
cd ..