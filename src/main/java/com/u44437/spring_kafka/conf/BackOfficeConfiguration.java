package com.u44437.spring_kafka.conf;

import com.u44437.spring_kafka.repository.ConsumerRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Map;

@Configuration
@EnableKafka
public class BackOfficeConfiguration {
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaListenerContainerFactory(
          ConsumerFactory<Integer, String> cf) {
    var factory = new ConcurrentKafkaListenerContainerFactory<Integer, String>(); factory.setConsumerFactory(cf);
    return factory;
  }
  @Bean
  public ConsumerFactory<Integer, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfiguration()); }
  @Bean
  public Map<String, Object> consumerConfiguration() {
    return Map.of(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
      ConsumerConfig.GROUP_ID_CONFIG, "test-messages");
  }

  @Bean
  public ConsumerRepository getConsumerRepository(){
    return new ConsumerRepository();
  }
}
