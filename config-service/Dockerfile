FROM openjdk:17-jdk-alpine
RUN apk update && apk upgrade
EXPOSE 9100
ARG JAR_FILE=build/libs/config-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]