# Use an official OpenJDK runtime as a parent image
# FROM openjdk:17-jdk-slim
FROM maven:3.9.9-eclipse-temurin-21

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot application JAR file into the container
COPY target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 6161

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
