package com.u44437.spring_kafka.repository;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class StreamsInputRepository {
  @Autowired
  void consumeInputTopic(StreamsBuilder streamsBuilder) {
    KStream<String, String> messageStream = streamsBuilder.stream("input-topic");
    messageStream.foreach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));
  }
}
