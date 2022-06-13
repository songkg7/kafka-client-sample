package com.example.kafkasubbatch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final ProducerFactory<?, ?> producerFactory;

    @Bean
    public KafkaTemplate<?, ?> kafkaTemplate() {
        KafkaTemplate<?, ?> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setDefaultTopic("output");
        return kafkaTemplate;
    }

}
