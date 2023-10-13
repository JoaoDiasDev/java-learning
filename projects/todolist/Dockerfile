FROM ubuntu:latest AS build
FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y openjdk-17-jdk

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

EXPOSE 8080

COPY --from=build /target/todolist-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]