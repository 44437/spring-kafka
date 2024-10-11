package com.u44437.spring_kafka.conf;

import com.u44437.spring_kafka.repository.ProducerRepository;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.transaction.KafkaTransactionManager;

import java.util.Map;

@Configuration
public class FrontOfficeConfiguration {
  @Bean
  public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> pf) {
    return new KafkaTemplate<>(pf);
  }

  @Bean
  public KafkaTransactionManager kafkaTransactionManager(ProducerFactory<String, String> pf) {
    return new KafkaTransactionManager(pf);
  }

  @Bean
  public ProducerFactory<String, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerFactoryProperties());
  }

  @Bean
  public Producer kafkaProducer(ProducerFactory<String, String> producerFactory) {
    return producerFactory.createProducer();
  }

  @Bean
  public Map<String, Object> producerFactoryProperties() {
    return Map.of(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094",
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
      ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 5000,// this >= linger.ms(default=0) + request.timout.ms(default=30sec)
      ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000,// 3 is adequate
      ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE,
      ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4",// none, gzip, snappy, zstd
//      ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);// default=true
//      ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class); // the default one is viable for now
      ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction-messages");
  }

  @Bean
  public ProducerRepository getProducerRepository(KafkaTemplate<String, String> kafka, Producer kafkaProducer) {
    return new ProducerRepository(kafka, kafkaProducer);
  }
}
