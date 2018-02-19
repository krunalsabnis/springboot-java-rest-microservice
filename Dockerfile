FROM java:8

VOLUME ./build

EXPOSE 8080

ADD /libs/qualibrate-java-api-0.0.1-SNAPSHOT.jar app.jar
CMD set SPRING_PROFILES_ACTIVE=test
ENTRYPOINT ["java","-jar","app.jar"]