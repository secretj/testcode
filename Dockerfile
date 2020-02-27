FROM openjdk:8-jdk-alpine
USER chloe

WORKDIR /home/chloe

ARG JAR_FILE
COPY /target/${JAR_FILE} /home/chloe/app.jar
ENTRYPOINT ["java","-jar","/home/chloe/app.jar"]