package com.u44437.spring_kafka.conf;

import com.u44437.spring_kafka.repository.ProducerRepository;
import com.u44437.spring_kafka.util.CustomConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.RoundRobinPartitioner;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class FrontOfficeConfiguration {
  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> pf) {
    return new KafkaTemplate<>(pf);
  }

  @Bean
  public ProducerFactory<String, Object> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerFactoryProperties());
  }

  @Bean
  public Map<String, Object> producerFactoryProperties() {
    return Map.of(
      ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094",
      ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
      ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class,
      ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 5000,// this >= linger.ms(default=0) + request.timout.ms(default=30sec)
      ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000,// 3 is adequate
      ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE,
      ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4",// none, gzip, snappy, zstd
//      ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);// default=true
      ProducerConfig.PARTITIONER_CLASS_CONFIG, RoundRobinPartitioner.class,
      CustomConfig.SCHEMA_REGISTRY_URL, CustomConfig.SCHEMA_REGISTRY_URL_VALUE);
  }

  @Bean
  public ProducerRepository getProducerRepository(KafkaTemplate<String, Object> kafka) {
    return new ProducerRepository(kafka);
  }
}
