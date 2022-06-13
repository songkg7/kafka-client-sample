package com.example.kafkasubbatch.job;

import java.util.HashMap;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final KafkaProperties kafkaProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    Job kafkaJob() {
        return jobBuilderFactory.get("kafkaJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(kafkaItemReader())
                .writer(kafkaItemWriter())
                .build();
    }

    @Bean
    KafkaItemReader<String, String> kafkaItemReader() {
        Properties properties = new Properties();
        properties.putAll(kafkaProperties.buildConsumerProperties());

        return new KafkaItemReaderBuilder<String, String>()
                .name("kafkaItemReader")
                .topic("test")
                .partitions(0)
                .partitionOffsets(new HashMap<>())
                .consumerProperties(properties)
                .build();
    }

    @Bean
    KafkaItemWriter<String, String> kafkaItemWriter() {
        return new KafkaItemWriterBuilder<String, String>()
                .kafkaTemplate(kafkaTemplate)
                .itemKeyMapper(source -> source)
                .build();
    }

}
