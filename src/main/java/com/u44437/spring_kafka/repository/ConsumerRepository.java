package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.client.ArbitraryClient;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Repository;

@Repository
public class ConsumerRepository {
  private final ArbitraryClient arbitraryClient;

  public ConsumerRepository(ArbitraryClient arbitraryClient) {
    this.arbitraryClient = arbitraryClient;
  }

  @KafkaListener(topics = Constants.TOPIC_FIRST)
  public void consumeMessage(ConsumerRecord<String, String> record) {
    System.out.printf("Got something: %d-%s \n", record.offset(), record.value());
    HttpStatus status = arbitraryClient.sendRequest("/", record.value());
    if(status == HttpStatus.INTERNAL_SERVER_ERROR){
      System.out.println("Error");
    }else
      System.out.println("Success");
  }
}
