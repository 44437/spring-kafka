package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.model.MessageReq;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;

@Repository
public class ConsumerRepository {
  @KafkaListener(topics = Constants.TOPIC_FIRST)
  public void consumeMessage(ConsumerRecord<String, MessageReq> record) {
    System.out.printf("%d-%s \n", record.offset(), record.value().main);
  }
}
