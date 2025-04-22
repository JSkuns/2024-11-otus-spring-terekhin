package ru.otus.hw.resilience;

import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    /**
     * Circuit Breaker: защищает от каскадных сбоев путём временного отключения вызова при высокой частоте ошибок
     */
    @Bean
    public CircuitBreakerConfig circuitBreakerConfig() {
        return CircuitBreakerConfig.custom()
                .slowCallDurationThreshold(Duration.ofMillis(500)) // медленный вызов длится больше 500 мс
                .failureRateThreshold(50) // порог ошибки составляет 50%
                .waitDurationInOpenState(Duration.ofSeconds(10)) // состояние open длится 10 сек
                .build();
    }

    /**
     * Retry: позволяет автоматически повторять неудачные запросы несколько раз
     */
    @Bean
    public TimeLimiterConfig timeLimiterConfig() {
        return TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(300)) // максимальный таймаут — 300 мс
                .cancelRunningFuture(true) // отменять выполнение длительных операций
                .build();
    }

    /**
     * Bulkhead: ограничивает доступные ресурсы, предотвращая перегрузку серверов
     * (например, очередь ожидания для сетевых запросов)
     */
    @Bean
    public ThreadPoolBulkheadConfig threadPoolBulkheadConfig() {
        return ThreadPoolBulkheadConfig.custom()
                .maxThreadPoolSize(10) // максимальное число потоков — 10
                .coreThreadPoolSize(5) // начальное число потоков — 5
                .queueCapacity(100) // размер очереди — 100 элементов
                .build();
    }

    /**
     * Time Limiter: устанавливает строгие ограничения по времени выполнения методов, позволяя предотвратить зависания
     */
    @Bean
    public RetryConfig retryConfig() {
        return RetryConfig.custom()
                .maxAttempts(3) // максимум три попытки повторить вызов
                .waitDuration(Duration.ofMillis(500)) // задержка между попытками 500 мс
                .build();
    }

}

