package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.model.MessageReq;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

@Repository
public class ProducerRepository {
  private final KafkaOperations<String, String> kafkaOperations;

  public ProducerRepository(KafkaOperations<String, String> kafkaOperations) {
    this.kafkaOperations = kafkaOperations;
  }

  @Async
  public void sendMessage(MessageReq messageReq, int partitionKey) {
    try {
      kafkaOperations.send(
              new ProducerRecord<>(
                      Constants.TOPIC_FIRST,
                      partitionKey,
                      Constants.KEY_ORDERS,
                      messageReq.toJSONString()));
    } catch (Exception e) {
      e.printStackTrace();
    }
}}
