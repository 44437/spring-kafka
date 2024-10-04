package com.u44437.spring_kafka.conf;

import com.u44437.spring_kafka.repository.ProducerRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
@EnableKafka
public class FrontOfficeConfiguration {
  @Bean
  public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf) {
    return new KafkaTemplate<>(pf);
  }

  @Bean
  public ProducerFactory<String, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerFactoryProperties());
  }

  @Bean
  public Map<String, Object> producerFactoryProperties() {
    return Map.of(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094",
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
  }

  @Bean
  public ProducerRepository getProducerRepository(KafkaTemplate<String, String> kafka) {
    return new ProducerRepository(kafka);
  }
}
