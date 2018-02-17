FROM java:8

VOLUME .

EXPOSE 8080

ADD /build/libs/qualibrate-java-api-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]