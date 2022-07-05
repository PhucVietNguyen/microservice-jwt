package com.example.rpt.service.kafka.ext;

import com.example.rpt.service.kafka.BaseKafkaProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "kafka-ext", ignoreUnknownFields = true)
public class ExtKafkaProperties extends BaseKafkaProperties {}
