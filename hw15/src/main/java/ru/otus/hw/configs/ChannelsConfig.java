package ru.otus.hw.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;

/**
 * Класс ChannelsConfig создает и регистрирует два канала (caterpillarInputChannel
 * и butterflyOutputChannel) типа QueueChannel, которые могут использоваться
 * для передачи сообщений между различными частями приложения.
 * _
 * Эти каналы могут использоваться в других частях приложения, например, в интеграционных потоках (Integration Flows),
 * где один компонент публикует сообщения в канал, а другой компонент извлекает их для последующей обработки.
 */
@Configuration
public class ChannelsConfig {

    /**
     * QueueChannel — это тип канала, который реализует очередь сообщений (first-in-first-out, FIFO).
     * Сообщения помещаются в очередь и извлекаются в порядке поступления.
     * Это полезно, когда нужно буферизировать сообщения
     * или когда несколько потребителей могут одновременно извлекать сообщения из канала
     */
    @Bean
    public QueueChannel caterpillarInputChannel() {
        return new QueueChannel();
    }

    @Bean
    public QueueChannel butterflyOutputChannel() {
        return new QueueChannel();
    }

}
