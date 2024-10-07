package com.u44437.spring_kafka.controller;

import com.u44437.spring_kafka.model.MessageReq;
import com.u44437.spring_kafka.repository.ProducerRepository;
import com.u44437.spring_kafka.util.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
  private ProducerRepository producerRepository;

  public ProducerController(ProducerRepository producerRepository) {
    this.producerRepository = producerRepository;
  }

  @PostMapping(path = "/messages")
  public ResponseEntity sendMessage(@RequestBody MessageReq messageReq, @RequestHeader(value = "X-Partition-Key", required = false, defaultValue = "0") int partitionKey){
    switch (partitionKey) {
      case 1 -> partitionKey = Constants.PARTITION_IMPORTANT;
      case 2 -> partitionKey = Constants.PARTITION_UNIMPORTANT;
      default -> partitionKey = Constants.PARTITION_DEFAULT;
    }

    try {
      producerRepository.sendMessage(messageReq, partitionKey);
      return ResponseEntity.ok().build();
    }catch (Exception e){
      return ResponseEntity.internalServerError().body(e);
    }
  }
}
