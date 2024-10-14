package com.u44437.spring_kafka.repository;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class StreamsInputRepository {
  @Autowired
  void consumeInputTopic(StreamsBuilder streamsBuilder) {
    KStream<String, String> messageStream = streamsBuilder.stream("input-topic");
    messageStream.foreach((key, value) -> System.out.println("Key: " + key + ", Value: " + value));

    messageStream
            .groupByKey()
            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofSeconds(5)))
            .count(Materialized.with(Serdes.String(), Serdes.Long()))
            .toStream()
            .map((Windowed<String> key, Long value) ->
              new KeyValue<>(key.key(), "Key: " + key.key() + ", Count: " + value + ", Window Start: " + key.window().start() + ", Window End: " + key.window().end()))
            .to("output-topic");
  }
}
