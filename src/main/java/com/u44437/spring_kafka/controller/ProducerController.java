package com.u44437.spring_kafka.controller;

import com.u44437.spring_kafka.model.MessageReq;
import com.u44437.spring_kafka.repository.ConsumerRepository;
import com.u44437.spring_kafka.repository.ProducerRepository;
import com.u44437.spring_kafka.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProducerController {
  private ProducerRepository producerRepository;
  private ConsumerRepository consumerRepository;

  public ProducerController(ProducerRepository producerRepository, ConsumerRepository consumerRepository) {
    this.producerRepository = producerRepository;
    this.consumerRepository =consumerRepository;
  }

  @PostMapping(path = "/messages")
  public ResponseEntity sendMessage(@RequestBody MessageReq messageReq, @RequestHeader(value = "X-Partition-Key", required = false, defaultValue = "0") int partitionKey){
    switch (partitionKey) {
      case 1 -> partitionKey = Constants.PARTITION_IMPORTANT;
      case 2 -> partitionKey = Constants.PARTITION_UNIMPORTANT;
      default -> partitionKey = Constants.PARTITION_DEFAULT;
    }

    long offset = producerRepository.sendMessage(messageReq, partitionKey);

    if (offset == -1){
      return ResponseEntity.internalServerError().build();
    }

    return ResponseEntity.ok(offset);
  }

  @GetMapping(path = "/messages")
  public ResponseEntity consumeMessage(){
    consumerRepository.consumeMessage();
    return ResponseEntity.ok().build();
  }
}
