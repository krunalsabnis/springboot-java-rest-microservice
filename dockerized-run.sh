#!/bin/sh
set -e

# place holder for PinPoint agent or any other OPTS Settings for JVM
JAVA_OPTS=""


exec java $JVM_OPTS $JAVA_OPTS -jar /opt/app/server.jar
