FROM java:7
MAINTAINER guyadong
#RUN groupadd -r facelog && useradd -r -d /home/facelog -g facelog facelog
LABEL version="1.0.0" description="facelog docker"
ARG SERVICE_PORT=26411
ARG REDIS_HOST
ARG REDIS_PORT=6379
ARG REDIS_PASSWORD=
ARG DATABASE_HOST
ARG DATABASE_PORT=3306
ARG DATABASE_SCHEMA=test
ARG DATABASE_USER=root
ARG DATABASE_PASSWORD=
RUN mkdir -p /usr/local/facelog /var/lib/facelog/log 
COPY ./target/facelog-service-1.0.4-SNAPSHOT-standalone.jar /usr/local/facelog/facelog-service-1.0.4-SNAPSHOT-standalone.jar
#USER facelog
RUN  mkdir ~/.facelog \
		&& {\
		echo "redis.host=$REDIS_HOST" ;\
		echo "redis.port=$REDIS_PORT" ;\
		echo "redis.password=$REDIS_PASSWORD" ;\
		echo "database.jdbc.host=$DATABASE_HOST" ;\
		echo "database.jdbc.port=$DATABASE_PORT" ;\
		echo "database.jdbc.schema=$DATABASE_SCHEMA" ;\
		echo "database.jdbc.user=$DATABASE_USER" ;\
		echo "database.jdbc.password=$DATABASE_PASSWORD" ;\
		echo "syslog.location=/var/lib/facelog/log/facelog.log" ;\
		} > ~/.facelog/config.properties

WORKDIR /usr/local/facelog
EXPOSE $SERVICE_PORT
ENV FACELOG_VERSION 1.0.4-SNAPSHOT
VOLUME /var/lib/facelog
CMD java -jar /usr/local/facelog/facelog-service-1.0.4-SNAPSHOT-standalone.jar