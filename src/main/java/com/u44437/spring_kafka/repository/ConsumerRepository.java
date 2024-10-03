package com.u44437.spring_kafka.repository;

import org.springframework.kafka.annotation.KafkaListener;

public class ConsumerRepository {
  @KafkaListener(topics = "first")
  public void consumeMessage(String message) {
    System.out.println(message);
  }
}
