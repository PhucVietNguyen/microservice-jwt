package com.example.ext.service.kafka;

import com.example.ext.service.model.dto.RoomDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class TestListenerKafka {

//  @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

  //    @Value("${cron-job.topic}")
  //    private String topic;

  //    @Value("${spring.kafka.group-id}")
  //    private String groupId;

//  @KafkaListener(topics = "ext_test", groupId = "ext")
//  public void testConsumer(@Payload RoomDto dto)
//      throws ClassNotFoundException, JsonProcessingException {
//    log.info("json string is {}", dto.toString());
//  }
}
