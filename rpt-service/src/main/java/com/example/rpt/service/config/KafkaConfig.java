package com.example.rpt.service.config;

import com.example.rpt.service.kafka.BaseKafkaProperties;
import com.example.rpt.service.kafka.ext.ExtKafkaProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  private final ExtKafkaProperties extKafkaProperties;

  @Bean(name = "consumerExtConfigs")
  public Map<String, Object> consumerConfigs() {
    return convertToProperties(extKafkaProperties);
  }

  private Map<String, Object> convertToProperties(BaseKafkaProperties baseKafkaProperties) {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, baseKafkaProperties.getBootstrapServers());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomJsonDeserializer.class);
    props.put(
        ConsumerConfig.GROUP_ID_CONFIG, baseKafkaProperties.getConsumerProperties().getGroupId());
    return props;
  }
}
