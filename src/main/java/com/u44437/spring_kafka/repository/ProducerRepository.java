package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.model.MessageReq;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Repository;

@Repository
public class ProducerRepository {
  private final KafkaOperations<String, Object> kafkaOperations;

  public ProducerRepository(KafkaOperations<String, Object> kafkaOperations) {
    this.kafkaOperations = kafkaOperations;
  }

  public long sendMessage(MessageReq messageReq, int partitionKey) {
    try {
      RecordMetadata metadata = kafkaOperations.send(
              new ProducerRecord<>(
                      Constants.TOPIC_FIRST,
//                      partitionKey, // uncommented, in order to distribute the messages with the round-robin algorithm
                      Constants.KEY_ORDERS,
                      messageReq))
              .get()
              .getRecordMetadata();

      return metadata.offset();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return -1;
}}
