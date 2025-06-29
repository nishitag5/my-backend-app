# Use a lightweight JDK base image
FROM eclipse-temurin:17-jdk-alpine

VOLUME /tmp

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8181

ENTRYPOINT ["java", "-jar", "/app.jar"]
