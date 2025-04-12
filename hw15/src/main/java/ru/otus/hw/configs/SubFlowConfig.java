package ru.otus.hw.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.hw.constants.ChannelConstants;
import ru.otus.hw.models.Butterfly;

/**
 * Класс отвечает за создание подпотока (субпотока) в интеграционном потоке (Integration Flow),
 * который выполняет определенную обработку данных.
 */
@Configuration
public class SubFlowConfig {

    /**
     * Создает подпоток с помощью DSL (Domain Specific Language) Spring Integration.
     * Использует метод from() для указания начального канала, откуда будут поступать сообщения.
     * Применяет обработку сообщений с помощью метода handle(), который выполняет некоторую логику над
     * каждым сообщением.
     * Отправляет обработанное сообщение в выходной канал с помощью метода channel().
     * _
     * Внутри метода handle() выполняется некоторая обработка над каждым сообщением.
     * В данном случае к содержимому сообщения добавляется строка " processed", и это изменённое сообщение
     * возвращается для дальнейшего использования.
     */
    @Bean
    public IntegrationFlow subFlow() {
        return IntegrationFlow.from(ChannelConstants.INPUT_CHANNEL)
                .transform(process())

                // Тут что-то должно делаться, но пока не работает

                .channel(ChannelConstants.OUTPUT_CHANNEL)
                .get();
    }

    private GenericTransformer<Butterfly, Butterfly> process() {
        return butterfly -> {
            butterfly.setWingsColor(butterfly.getWingsColor().toUpperCase());
            return butterfly;
        };
    }

}
