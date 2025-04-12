package ru.otus.hw.configs;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.integration.dsl.IntegrationFlow;
import ru.otus.hw.models.Butterfly;
import ru.otus.hw.models.Caterpillar;

/**
 * Класс IntegrationFlowConfig создает и настраивает интеграционный поток для преобразования гусениц
 * в бабочек с использованием Spring Integration.
 */
@Configuration
@AllArgsConstructor
public class IntegrationFlowConfig {

    private final ChannelsConfig channelsConfig;

    /**
     * Этот класс создает интеграционный поток, который:
     * Принимает сообщения (объекты Caterpillar) из канала caterpillarInputChannel.
     * Преобразует их в объекты Butterfly с помощью трансформера.
     * Отправляет преобразованные сообщения (объекты Butterfly) в канал butterflyOutputChannel.
     */
    @Bean
    public IntegrationFlow caterpillarToButterflyFlow() {
        return IntegrationFlow.from(channelsConfig.caterpillarInputChannel())
                .transform(transformer())
                .channel(channelsConfig.butterflyOutputChannel())
                .get();
    }

    /**
     * Создает экземпляр GenericTransformer, который отвечает за преобразование гусеницы в бабочку.
     * Внутри лямбда-выражения создается новый объект Butterfly, копируются значения свойств name
     * и color из объекта Caterpillar, и возвращается готовый объект Butterfly
     */
    @Bean
    public GenericTransformer<Caterpillar, Butterfly> transformer() {
        return caterpillar -> {
            Butterfly butterfly = new Butterfly();
            butterfly.setName(caterpillar.getName().toLowerCase());
            butterfly.setWingsColor(caterpillar.getColor().toLowerCase());
            return butterfly;
        };
    }

}
