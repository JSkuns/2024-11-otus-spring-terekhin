FROM openjdk:17-jdk-slim AS base
WORKDIR /app
COPY target/rate-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]