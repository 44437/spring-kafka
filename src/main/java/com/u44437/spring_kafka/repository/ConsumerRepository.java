package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.client.ArbitraryClient;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Repository;

@Repository
public class ConsumerRepository {
  private final ArbitraryClient arbitraryClient;
  private final Logger logger = LoggerFactory.getLogger(ConsumerRepository.class);

  public ConsumerRepository(ArbitraryClient arbitraryClient) {
    this.arbitraryClient = arbitraryClient;
  }

  @KafkaListener(topics = Constants.TOPIC_FIRST, groupId = Constants.CONSUMER_GROUP_ID)
  public void consumeMessageFromPartitionIMPORTANT(ConsumerRecord<String, String> record, @Header(KafkaHeaders.OFFSET) Long offset) {
    logger.info("IMPORTANT Got something: {}-{}-{}", offset, record.partition(), record.value());// record.offset() works too
    HttpStatus status = arbitraryClient.sendRequest("/", record.value());
    if(status == HttpStatus.INTERNAL_SERVER_ERROR){
      logger.error("Error");
    }else
      logger.info("Success");
  }

//  @KafkaListener(topicPartitions = @TopicPartition(topic = Constants.TOPIC_FIRST, partitions = {Constants.PARTITION_UNIMPORTANT}), groupId = Constants.CONSUMER_GROUP_ID)
  @KafkaListener(topics = Constants.TOPIC_FIRST, groupId = Constants.CONSUMER_GROUP_ID)
  public void consumeMessageFromPartitionUNIMPORTANT(ConsumerRecord<String, String> record, @Header(KafkaHeaders.OFFSET) Long offset) {
    logger.warn("UNIMPORTANT Got something: {}-{}-{}", offset,record.partition(), record.value());
    HttpStatus status = arbitraryClient.sendRequest("/", record.value());
    if(status == HttpStatus.INTERNAL_SERVER_ERROR){
      logger.error("Error");
    }else
      logger.info("Success");
  }
}
