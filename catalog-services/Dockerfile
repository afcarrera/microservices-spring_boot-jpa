FROM openjdk:17-jdk-alpine
RUN apk update && apk upgrade
EXPOSE 9002
ARG JAR_FILE=build/libs/catalog-services-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]