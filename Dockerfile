# Use a lightweight JDK base image
FROM eclipse-temurin:17-jdk-alpine

# Set a volume for temp files
VOLUME /tmp

# Copy the JAR file built by Maven into the image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Run the Spring Boot app
ENTRYPOINT ["java","-jar","/app.jar"]
