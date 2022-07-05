package com.example.rpt.service.config;

import org.springframework.kafka.support.serializer.JsonDeserializer;

public class CustomJsonDeserializer extends JsonDeserializer {

  public CustomJsonDeserializer() {
    super();
    this.addTrustedPackages("*");
  }
}
