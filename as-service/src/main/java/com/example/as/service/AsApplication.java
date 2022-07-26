package com.example.as.service;

//import com.example.as.service.config.interceptor.AuthenticationAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient
@SpringBootApplication
public class AsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AsApplication.class, args);
  }
}


