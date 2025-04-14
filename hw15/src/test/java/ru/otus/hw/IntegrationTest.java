package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.hw.gateways.ButterflyGateway;
import ru.otus.hw.gateways.CaterpillarGateway;
import ru.otus.hw.models.Butterfly;
import ru.otus.hw.models.Caterpillar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IntegrationTest {

    @Autowired
    private CaterpillarGateway caterpillarGateway;

    @Autowired
    private ButterflyGateway butterflyGateway;

    @Test
    public void testCaterpillarToButterflyTransformation() {
        Caterpillar caterpillar = new Caterpillar("Alice", "Green");

        caterpillarGateway.sendCaterpillar(caterpillar);

        Butterfly butterfly = butterflyGateway.receiveButterfly();

        assertEquals("alice", butterfly.getName());
        assertEquals("green", butterfly.getWingsColor());
    }

}
