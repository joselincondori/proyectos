# syntax = docker/dockerfile:1.2
#
# Build stage
#
FROM maven:3.8.4-openjdk-17 AS build
COPY . .
RUN mvn clean
RUN mvn package assembly:single -DskipTests
#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/ejercicio-1.0-SNAPSHOT-jar-with-dependencies.jar ejercicio.jar
# ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-classpath","ejercicio.jar","server.App"]
