#!/bin/bash

set -e

#JAVA_OPTS="-Dfile.encoding=UTF-8 -Djava.security.edg=file:/dev/urandom $JAVA_OPTS"
#JAVA_OPTS="-javaagent:/pinpoint-bootstrap-1.7.0.jar -Dpinpoint.agentId=app-in-docker -Dpinpoint.applicationName=qualibrate-api $JAVA_OPTS"


echo "starting"
exec /wait-for-it.sh $DBHOST:3306 -t 0 -- java $JVM_OPTS $JAVA_OPTS -jar app.jar
#exec java $JVM_OPTS $JAVA_OPTS -jar app.jar