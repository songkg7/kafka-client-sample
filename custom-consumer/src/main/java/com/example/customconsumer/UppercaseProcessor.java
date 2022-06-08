package com.example.customconsumer;

import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UppercaseProcessor {

    @KafkaListener(topics = "test")
    public void receiveTopic(ConsumerRecord<String, String> consumerRecord) {
        log.info(consumerRecord.value().toUpperCase(Locale.ROOT));
    }

}
