package com.u44437.spring_kafka.controller;

import com.u44437.spring_kafka.model.MessageReq;
import com.u44437.spring_kafka.repository.ProducerRepository;
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
    long offset = producerRepository.sendMessage(messageReq, partitionKey);

    if (offset == -1){
      return ResponseEntity.internalServerError().build();
    }

    return ResponseEntity.ok(offset);
  }
}
