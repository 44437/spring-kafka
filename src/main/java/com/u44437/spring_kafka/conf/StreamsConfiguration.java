package com.u44437.spring_kafka.conf;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Properties;

@Configuration
public class StreamsConfiguration {

  @Bean
  public Properties properties() {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streaming-app");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    return props;
  }

  @Bean
  @DependsOn("kStream")
  public KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder, Properties properties) {
    KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);

    Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));

    kafkaStreams.start();

    return kafkaStreams;
  }

  @Bean
  public KStream kStream (StreamsBuilder streamsBuilder) {
    KStream<String, String> stream = streamsBuilder.stream("input-topic");
    stream.foreach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

    stream.to("output-topic");

    return stream;
  }

  @Bean
  public StreamsBuilder streamsBuilder() {
    return new StreamsBuilder();
  }
}
