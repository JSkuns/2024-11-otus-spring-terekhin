# Используем официальный образ JDK 17 как базовый
FROM eclipse-temurin:17-jdk-jammy

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем артефакт приложения (например, JAR-файл) в контейнер
COPY target/app-docker-0.0.1-SNAPSHOT.jar /app.jar

# Запускаем приложение при запуске контейнера
CMD ["java", "-jar", "/app.jar"]