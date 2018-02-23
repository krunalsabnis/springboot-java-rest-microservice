FROM java:8u72-jre

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=test

COPY dockerized-run.sh /dockerized-run.sh
CMD gradlew build
COPY build/libs/quali*.jar /opt/app/server.jar
CMD chmod +x /dockerized-run.sh

ENTRYPOINT ["/dockerized-run.sh"]