package com.example.as.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AsApplication.class, args);
  }
}
