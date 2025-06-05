package ru.otus.hw.services;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import ru.otus.hw.constants.ChannelConstants;
import ru.otus.hw.models.Butterfly;

@Service
public class ButterflyService {

    @ServiceActivator(outputChannel = ChannelConstants.OUTPUT_CHANNEL)
    public void handleButterfly(Butterfly butterfly) {

        if (butterfly == null) {
            System.err.println("Received null butterfly");
            return;
        }

        // Это тоже пока не работает, не понятно почему

        System.out.println("Received butterfly: " + butterfly.getName());
    }

}
