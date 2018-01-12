FROM java:7
MAINTAINER guyadong(10km0811@sohu.com)
#RUN groupadd -r facelog && useradd -r -d /home/facelog -g facelog facelog
LABEL version="1.0.0" description="facelog docker"
ENV FACELOG_VERSION=1.0.4-SNAPSHOT \
		FACELOG_DEBUG=false \
		FACELOG_DEBUG_PORT=8000 \
		SERVICE_PORT=26411 \
    REDIS_HOST=localhost \
    REDIS_PORT=6379 \
    REDIS_PASSWORD="" \
    DATABASE_HOST=localhost \
    DATABASE_PORT=3306 \
    DATABASE_SCHEMA=test \
    DATABASE_USER=root \
    DATABASE_PASSWORD="" 
ENV SERVICE_JAR facelog-service-${FACELOG_VERSION}-standalone.jar
COPY ./target/$SERVICE_JAR /usr/local/facelog/$SERVICE_JAR
#USER facelog
RUN  mkdir -p /usr/local/facelog /var/lib/facelog/log ~/.facelog 
WORKDIR /usr/local/facelog
EXPOSE $SERVICE_PORT $FACELOG_DEBUG_PORT
VOLUME /var/lib/facelog
CMD set -x; {\
		echo "redis.host=$REDIS_HOST" ;\
		echo "redis.port=$REDIS_PORT" ;\
		echo "redis.password=$REDIS_PASSWORD" ;\
		echo "database.jdbc.host=$DATABASE_HOST" ;\
		echo "database.jdbc.port=$DATABASE_PORT" ;\
		echo "database.jdbc.schema=$DATABASE_SCHEMA" ;\
		echo "database.jdbc.user=$DATABASE_USER" ;\
		echo "database.jdbc.password=$DATABASE_PASSWORD" ;\
		echo "syslog.location=/var/lib/facelog/log/facelog.log" ;\
		} > ~/.facelog/config.properties \
		; if $FACELOG_DEBUG ; then \
			debug="-Xrunjdwp:transport=dt_socket,server=y,address=$FACELOG_DEBUG_PORT,suspend=n"; \
		else \
			debug=''; \
		fi ; \
		java $debug -jar /usr/local/facelog/$SERVICE_JAR