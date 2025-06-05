package ru.otus.hw.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean isCustomServiceUp = true;

        if (!isCustomServiceUp) {
            return Health.down()
                    .withDetail("custom-service-status", "SERVICE_DOWN")
                    .withDetail("random-int", new Random().nextInt(10))
                    .build();
        }

        return Health.up()
                .withDetail("custom-service-status", "SERVICE_UP")
                .withDetail("random-int", new Random().nextInt(10))
                .build();
    }

}
