package com.example.rpt.service;

import com.example.rpt.service.kafka.ext.ExtKafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ExtKafkaProperties.class})
public class RptApplication {

  public static void main(String[] args) {
    SpringApplication.run(RptApplication.class, args);
  }
}
