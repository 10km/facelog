pushd sql2java
rem ant rebuild -Dsql2java-config=../sql2java.properties -Dgenerated-src=../src/main/java
ant rebuild -Dsql2java-config=../sql2java.properties -Dgenerated-src=../src/main/java -Ddriver-lib=../lib 
rem ant rebuild -Dsql2java-config=../sql2java.properties -Dgenerated-src=../src/main/java -Ddriver-jar=../lib/mysql-connector-java-5.1.43-bin.jar
popd