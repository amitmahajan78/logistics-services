package com.logistics.service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class EventReceiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = "logistics-events")
    public void receive(String payload) {
        log.info("received payload='{}'", payload);
        latch.countDown();
    }
}
