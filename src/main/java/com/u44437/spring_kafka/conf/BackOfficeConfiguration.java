package com.u44437.spring_kafka.conf;

import com.u44437.spring_kafka.client.ArbitraryClient;
import com.u44437.spring_kafka.repository.ConsumerRepository;
import okhttp3.OkHttpClient;
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
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
public class BackOfficeConfiguration {
  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(
          ConsumerFactory<String, String> cf) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
    factory.setConsumerFactory(cf);
    return factory;
  }

  @Bean
  public ConsumerFactory<String, String> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfiguration()); }

  @Bean
  public Map<String, Object> consumerConfiguration() {
    return Map.of(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
      JsonDeserializer.TRUSTED_PACKAGES, "*",
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
      ConsumerConfig.GROUP_ID_CONFIG, "test-messages");
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
