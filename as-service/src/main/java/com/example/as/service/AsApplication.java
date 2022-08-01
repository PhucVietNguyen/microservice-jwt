package com.example.as.service;

import com.example.as.service.config.AppProperties;
import com.example.as.service.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties({AppProperties.class, RsaKeyProperties.class})
public class AsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AsApplication.class, args);
  }
}
