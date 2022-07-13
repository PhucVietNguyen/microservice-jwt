package com.example.gateway.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GwApplication {

	public static void main(String[] args) {
		SpringApplication.run(GwApplication.class, args);
	}

}
