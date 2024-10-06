package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.client.ArbitraryClient;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Repository;

@Repository
public class ConsumerRepository {
  private final ArbitraryClient arbitraryClient;

  public ConsumerRepository(ArbitraryClient arbitraryClient) {
    this.arbitraryClient = arbitraryClient;
  }

  @KafkaListener(topics = Constants.TOPIC_FIRST) // topicPartitions doesn't work
  public void consumeMessage(ConsumerRecord<String, String> record, @Header(KafkaHeaders.OFFSET) Long offset) {
    System.out.printf("Got something: %d-%s \n", offset, record.value());// record.offset() works too
    HttpStatus status = arbitraryClient.sendRequest("/", record.value());
    if(status == HttpStatus.INTERNAL_SERVER_ERROR){
      System.out.println("Error");
    }else
      System.out.println("Success");
  }
}
