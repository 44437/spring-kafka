package com.u44437.spring_kafka.conf;

import com.u44437.spring_kafka.repository.ProducerRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
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
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
      ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 5000,// this >= linger.ms(default=0) + request.timout.ms(default=30sec)
      ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000,// 3 is adequate
      ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);// infinite
//      ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class); // the default one is viable for now
  }

  @Bean
  public ProducerRepository getProducerRepository(KafkaTemplate<String, String> kafka) {
    return new ProducerRepository(kafka);
  }
}
