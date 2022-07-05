package com.example.ext.service.cronjob.impl;

import com.example.ext.service.cronjob.UnitJob;
import com.example.ext.service.model.dto.RoomDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UnitJobImpl implements UnitJob {

  @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

  @Value("${cron-job.topic}")
  private String topic;

  @Override
  public void pushDataToKafkaTopic(RoomDto roomDto) {
    for(int i = 0; i < 10; i++) {
      kafkaTemplate.send(topic, roomDto);
    }
  }
}
