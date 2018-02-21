#!/bin/bash

set -e



JAVA_OPTS="$JAVA_OPTS -Dpinpoint.agentId="$PP_AGENT_ID" -Dpinpoint.applicationName="$PP_APP_NAME

#JAVA_OPTS="-Dfile.encoding=UTF-8 -Djava.security.edg=file:/dev/urandom $JAVA_OPTS"
echo "========================================"
sleep 4
echo $JAVA_OPTS
sleep 2


exec /wait-for-it.sh $DBHOST:3306 -t 0 -- java $JVM_OPTS $JAVA_OPTS -jar /app.jar
#exec java $JVM_OPTS $JAVA_OPTS -jar app.jar