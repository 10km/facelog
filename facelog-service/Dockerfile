# docker image of facelog service
# version 1.0.1
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
COPY docker-entrypoint.sh /usr/bin
RUN chmod +x /usr/bin/docker-entrypoint.sh
#USER facelog
RUN  mkdir -p /usr/local/facelog /var/lib/facelog/log ~/.facelog 
WORKDIR /usr/local/facelog
EXPOSE $SERVICE_PORT $FACELOG_DEBUG_PORT
VOLUME /var/lib/facelog
CMD set -x; \
		if [ $FACELOG_DEBUG ] ; then \
			debug="-Xrunjdwp:transport=dt_socket,server=y,address=$FACELOG_DEBUG_PORT,suspend=n"; \
		else \
			debug=''; \
		fi ; \
		java $debug -jar /usr/local/facelog/$SERVICE_JAR