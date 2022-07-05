package com.example.rpt.service.kafka;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadProperties {
  private Integer numOfConsumerDefault;

  private Integer numOfConsumerMax;
}
