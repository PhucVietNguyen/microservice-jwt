package com.example.rpt.service.kafka.threadpool;

import com.example.rpt.service.kafka.ext.ExtKafkaProperties;
import com.example.rpt.service.kafka.ext.ProcessMessageKafkaFromExt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Service
@Log4j2
@RequiredArgsConstructor
public class ThreadPoolKafkaService {

  private final ThreadConsumerGroup threadConsumerGroup;

  @PostConstruct
  public void createThreadPool() {
    threadConsumerGroup.execute();
    try {
      Thread.sleep(1000);
      threadConsumerGroup.shutdown();
    } catch (InterruptedException ie) {
    }
  }
}
