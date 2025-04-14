package ru.otus.hw.gateways;

import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.constants.ChannelConstants;
import ru.otus.hw.models.Caterpillar;

@MessagingGateway(defaultRequestChannel = ChannelConstants.INPUT_CHANNEL)
public interface CaterpillarGateway {

    void sendCaterpillar(Caterpillar caterpillar);

}
