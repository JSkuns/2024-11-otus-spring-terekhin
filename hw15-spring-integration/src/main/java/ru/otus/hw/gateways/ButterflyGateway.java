package ru.otus.hw.gateways;

import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.constants.ChannelConstants;
import ru.otus.hw.models.Butterfly;

/**
 * Этот интерфейс используется для создания шлюза (gateway) в Spring Integration,
 * который служит точкой входа для асинхронных сообщений.
 * _
 * Аннотация @MessagingGateway:
 * Указывает, что интерфейс является шлюзом для отправки и получения сообщений в интеграционном потоке.
 * Шлюз позволяет другим частям приложения взаимодействовать с интеграционными потоками без необходимости
 * вручную отправлять и получать сообщения через каналы.
 * _
 * Атрибут defaultReplyChannel:
 * Определяет канал, на который будут отправлены результаты выполнения метода шлюза.
 * В данном случае, это канал ChannelConstants.OUTPUT_CHANNEL.
 * _
 * Метод receiveButterfly():
 * Является методом, доступным для внешнего кода, который хочет получить бабочку (Butterfly) из интеграционного потока.
 * Этот метод фактически является точкой получения сообщений из канала, указанного в defaultReplyChannel.
 */
@MessagingGateway(defaultReplyChannel = ChannelConstants.OUTPUT_CHANNEL)
public interface ButterflyGateway {

    Butterfly receiveButterfly();

}
