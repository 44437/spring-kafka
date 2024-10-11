package com.u44437.spring_kafka.repository;

import com.u44437.spring_kafka.model.MessageReq;
import com.u44437.spring_kafka.util.Constants;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Repository
@Transactional
public class ProducerRepository {
  private final KafkaOperations<String, String> kafkaOperations;
  private final Producer kafkaProducer;

  public ProducerRepository(KafkaOperations<String, String> kafkaOperations, Producer kafkaProducer) {
    this.kafkaOperations = kafkaOperations;
    this.kafkaProducer = kafkaProducer;
  }

  public long sendMessage(MessageReq messageReq, int partitionKey) {
    try {
      kafkaProducer.beginTransaction();

      RecordMetadata metadata = (RecordMetadata) kafkaProducer.send(
              new ProducerRecord<>(
                      Constants.TOPIC_FIRST,
                      partitionKey,
                      Constants.KEY_ORDERS,
                      messageReq.toJSONString())).get();

      kafkaProducer.commitTransaction();

      return metadata.offset();
    } catch (Exception e) {
      e.printStackTrace();
      kafkaProducer.abortTransaction();

      return -1;
    }
  }

  public long sendMessageViaKafkaOperations(MessageReq messageReq, int partitionKey){
    return kafkaOperations.executeInTransaction(operations -> {
      try {
        RecordMetadata metadata = operations.send(new ProducerRecord<>(
                Constants.TOPIC_FIRST, partitionKey, Constants.KEY_ORDERS, messageReq.toJSONString()))
                  .get()
                  .getRecordMetadata();

        return metadata.offset();
      } catch (InterruptedException e) {
        return -1L;
      } catch (ExecutionException e) {
        return -1L;
      }
    });
  }
}
