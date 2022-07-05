package com.example.rpt.service.kafka.threadpool;

import com.example.rpt.service.kafka.ext.ExtKafkaProperties;
import com.example.rpt.service.kafka.ext.ProcessMessageKafkaFromExt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Log4j2
@RequiredArgsConstructor
public class ThreadPoolKafkaService {

  private final ExtKafkaProperties extKafkaProperties;

  @PostConstruct
  public void createThreadPool() {
    ProcessMessageKafkaFromExt kafkaFromExt =
        new ProcessMessageKafkaFromExt(
            extKafkaProperties.getBootstrapServers(),
            extKafkaProperties.getConsumerProperties().getGroupId(),
            extKafkaProperties.getTopicProperties().getName());
    kafkaFromExt.execute(
        extKafkaProperties.getThreadProperties().getNumOfConsumerDefault(),
        extKafkaProperties.getThreadProperties().getNumOfConsumerMax());
    try {
      Thread.sleep(100000);
    } catch (InterruptedException ie) {

    }
    kafkaFromExt.shutdown();
  }
}
