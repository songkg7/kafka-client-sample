package com.example.kafkastreams.processor;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class WordCountProcessorTest {

    @Spy
    private WordCountProcessor wordCountProcessor;

    @Test
    void buildPipeline() {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        wordCountProcessor.buildPipeline(streamsBuilder);
        Topology topology = streamsBuilder.build();

        try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, new Properties())) {
            TestInputTopic<String, String> inputTopic = topologyTestDriver.createInputTopic("stream-input",
                    new StringSerializer(), new StringSerializer());

            TestOutputTopic<String, Long> outputTopic = topologyTestDriver
                    .createOutputTopic("stream-output", new StringDeserializer(), new LongDeserializer());

            inputTopic.pipeInput("key", "hello world");
            inputTopic.pipeInput("key2", "hello");

            assertThat(outputTopic.readKeyValuesToList()).containsExactly(
                    KeyValue.pair("hello", 1L),
                    KeyValue.pair("world", 1L),
                    KeyValue.pair("hello", 2L)
            );
        }
    }
}