package com.example.kafkasubbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class KafkaSubBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaSubBatchApplication.class, args);
    }

}
