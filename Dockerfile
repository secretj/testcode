FROM openjdk:8-jdk-alpine
USER chloe

WORKDIR /home/chloe

COPY /target/*.jar /home/chloe/app.jar
ENTRYPOINT ["java","-jar","/home/chloe/app.jar"]