FROM openjdk:17-jdk-slim AS base
WORKDIR /app
COPY target/project-aux-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar"]