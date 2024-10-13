package com.u44437.spring_kafka.conf;

import com.u44437.spring_kafka.client.ArbitraryClient;
import com.u44437.spring_kafka.repository.ConsumerRepository;
import com.u44437.spring_kafka.util.CustomConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import okhttp3.OkHttpClient;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class BackOfficeConfiguration {
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> kafkaListenerContainerFactory(
          ConsumerFactory<String, Object> cf) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, Object>();
    factory.setConsumerFactory(cf);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, Object> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfiguration()); }

  @Bean
  public Map<String, Object> consumerConfiguration() {
    return Map.of(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094,localhost:8094",
      ConsumerConfig.CLIENT_DNS_LOOKUP_CONFIG, "resolve_canonical_bootstrap_servers_only",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
      JsonDeserializer.TRUSTED_PACKAGES, "*",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class,
      ConsumerConfig.GROUP_ID_CONFIG, "test-messages",
      ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 5000,// default=3000
      ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest",
      CustomConfig.SPECIFIC_AVRO_READER, CustomConfig.SPECIFIC_AVRO_READER_VALUE,
      CustomConfig.SCHEMA_REGISTRY_URL, CustomConfig.SCHEMA_REGISTRY_URL_VALUE);
  }

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient();
  }

  @Bean
  public ArbitraryClient arbitraryClient() {
    return new ArbitraryClient(okHttpClient());
  }

  @Bean
  public ConsumerRepository getConsumerRepository(){
    return new ConsumerRepository(arbitraryClient());
  }
}
