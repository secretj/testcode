FROM openjdk:8-jdk-alpine
RUN adduser -D -s /bin/sh chloe
USER chloe

WORKDIR /opt/chloe

COPY target/*.jar /opt/chloe/app.jar
ENTRYPOINT ["java","-jar","/opt/chloe/app.jar"]