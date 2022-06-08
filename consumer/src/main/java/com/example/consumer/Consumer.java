package com.example.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    @KafkaListener(topics = "test")
    public void receiveTopic(ConsumerRecord<String, String> consumerRecord) {
        log.info("Receiver on topic: {}", consumerRecord);
    }

}
