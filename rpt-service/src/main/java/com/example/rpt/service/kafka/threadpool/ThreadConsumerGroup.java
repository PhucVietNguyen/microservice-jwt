package com.example.rpt.service.kafka.threadpool;

import com.example.rpt.service.kafka.ext.ExtKafkaProperties;
import com.example.rpt.service.kafka.ext.ProcessMessageKafkaFromExt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@Log4j2
@RequiredArgsConstructor
public class ThreadConsumerGroup {

  private List<ProcessMessageKafkaFromExt> consumers;

  private Properties consumerExtConfigs;

  private final ExtKafkaProperties extKafkaProperties;

  private final ApplicationContext applicationContext;

  @PostConstruct
  public void AddThreadConsumerGroup() {
    consumerExtConfigs = applicationContext.getBean("consumerExtConfigs", Properties.class);
    consumers = new ArrayList<>();
    for (int i = 0; i < this.getNumberOfConsumers(); i++) {
      ProcessMessageKafkaFromExt ncThread = new ProcessMessageKafkaFromExt();
      ncThread.createKafkaConfig(
          consumerExtConfigs, extKafkaProperties.getTopicProperties().getName());
      consumers.add(ncThread);
    }
  }

  public void execute() {
    for (ProcessMessageKafkaFromExt ncThread : consumers) {
      Thread t = new Thread(ncThread);
      t.start();
    }
  }

  public int getNumberOfConsumers() {
    return this.extKafkaProperties.getThreadProperties().getNumOfConsumer();
  }
}
