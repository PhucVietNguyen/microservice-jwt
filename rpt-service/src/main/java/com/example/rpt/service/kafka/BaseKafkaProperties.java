package com.example.rpt.service.kafka;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseKafkaProperties {
  private String bootstrapServers;
  private String zookeeperHosts;
  private TopicProperties topicProperties;
  private ConsumerProperties consumerProperties;
  private ThreadProperties threadProperties;
}
