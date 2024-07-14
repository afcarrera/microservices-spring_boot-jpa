FROM openjdk:17-jdk-alpine
RUN apk update && apk upgrade
EXPOSE 9000
ARG JAR_FILE=build/libs/customer-services-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]