package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Repository;

@Repository
public class ProducerRepository {
  private final KafkaOperations<String, String> kafkaOperations;

  public ProducerRepository(KafkaOperations<String, String> kafkaOperations) {
    this.kafkaOperations = kafkaOperations;
  }

  public long sendMessage(String message) {
    try {
      RecordMetadata metadata = kafkaOperations.send(
              new ProducerRecord<>(
                      Constants.TOPIC_FIRST,
                      Constants.KEY_ORDERS,
                      message))
              .get()
              .getRecordMetadata();

      return metadata.offset();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
}}
