#!/bin/bash

set -e

JAVA_OPTS="-Dfile.encoding=UTF-8 -Djava.security.edg=file:/dev/urandom $JAVA_OPTS"

echo "DBHOST" $DBHOST

echo "starting"
exec /wait-for-it.sh $DBHOST:3306 -t 0 -- java $JVM_OPTS $JAVA_OPTS -jar app.jar
#exec java $JVM_OPTS $JAVA_OPTS -jar app.jar