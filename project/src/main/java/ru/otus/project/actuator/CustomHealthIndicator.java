package ru.otus.project.actuator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    private final RestTemplate restTemplate;

    @Override
    public Health health() {
        boolean dbHealthy = checkDatabaseHealth();
        boolean externalServiceHealthy = checkExternalServiceHealth();

        if (dbHealthy && externalServiceHealthy) {
            return Health.up()
                    .withDetail("db-indicator", true)
                    .withDetail("rate-service-indicator", true)
                    .build();
        } else {
            return Health.down()
                    .withDetail("db-indicator", dbHealthy)
                    .withDetail("rate-service-indicator", externalServiceHealthy)
                    .build();
        }
    }

    /**
     * Проверяем подключение к базе данных.
     */
    private boolean checkDatabaseHealth() {
        try (Connection conn = dataSource.getConnection()) {
            return true;
        } catch (SQLException e) {
            log.error("Unable to establish DB connection", e);
            return false;
        }
    }

    /**
     * Проверяем внешний сервис через HTTP-запрос.
     */
    private boolean checkExternalServiceHealth() {
        try {
            ResponseEntity<String> response = restTemplate
                    .getForEntity("http://localhost:8081/ping", String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (RestClientException e) {
            log.error("Failed to communicate with external service", e);
            return false;
        }
    }

}
