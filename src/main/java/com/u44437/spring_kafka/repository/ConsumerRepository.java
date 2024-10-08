package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.client.ArbitraryClient;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Arrays;

@Repository
public class ConsumerRepository {
  private final ArbitraryClient arbitraryClient;
  private final KafkaConsumer kafkaConsumer;

  public ConsumerRepository(ArbitraryClient arbitraryClient, KafkaConsumer kafkaConsumer) {
    this.arbitraryClient = arbitraryClient;
    this.kafkaConsumer = kafkaConsumer;
  }

  public void consumeMessage() {
    kafkaConsumer.subscribe(Arrays.asList(Constants.TOPIC_FIRST));

    try {
      while (true) {
        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(500));

        for (ConsumerRecord<String, String> record : records) {
          System.out.printf("Offset: %d, Key: %s", record.offset(), record.key());
        }

//        kafkaConsumer.commitSync(); // If you do not commit, you'll see the same messages again when the program starts from scratch
      }
    } finally {
      kafkaConsumer.close();
    }
  }
}
