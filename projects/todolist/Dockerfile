# Build stage
FROM ubuntu:latest AS build

# Install necessary tools
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Set the working directory
WORKDIR /app

# Copy the source code and build
COPY . .
RUN mvn clean install

# Final stage
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/todolist-1.0.0.jar app.jar

# Expose the necessary port
EXPOSE 8080

# Define the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]
