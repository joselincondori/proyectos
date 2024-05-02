## Build stage
#FROM gradle:4.7.0-jdk17-alpine AS build
#RUN gradle build --no-daemon

#RUN mkdir /app
#COPY --from=build /target/stack-0.0.1-SNAPSHOT.jar stack.jar
#EXPOSE 8083
#ENTRYPOINT ["java", "-jar", "stack.jar"]


FROM gradle:7.4.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/stack.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/stack.jar"]
