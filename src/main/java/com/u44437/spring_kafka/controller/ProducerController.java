package com.u44437.spring_kafka.controller;

import com.u44437.spring_kafka.repository.ProducerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {
  private ProducerRepository producerRepository;

  public ProducerController(ProducerRepository producerRepository) {
    this.producerRepository = producerRepository;
  }

  @PostMapping(path = "/messages/{message}")
  public ResponseEntity sendMessage(@PathVariable String message){
    long offset = producerRepository.sendMessage(message);

    if (offset == -1){
      return ResponseEntity.internalServerError().build();
    }

    return ResponseEntity.ok(offset);
  }
}
