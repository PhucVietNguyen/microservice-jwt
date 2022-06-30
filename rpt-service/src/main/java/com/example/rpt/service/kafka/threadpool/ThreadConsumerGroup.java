package com.example.rpt.service.kafka.threadpool;

import com.example.rpt.service.kafka.ext.ExtKafkaProperties;
import com.example.rpt.service.kafka.ext.ProcessMessageKafkaFromExt;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
@Log4j2
@RequiredArgsConstructor
public class ThreadConsumerGroup {

  private ProcessMessageKafkaFromExt consumer;

  private Properties consumerExtConfigs;

  private final ExtKafkaProperties extKafkaProperties;

  private final ApplicationContext applicationContext;

  private ExecutorService executor;

  @PostConstruct
  public void AddThreadConsumerGroup() {
    consumerExtConfigs = applicationContext.getBean("consumerExtConfigs", Properties.class);
    consumer = new ProcessMessageKafkaFromExt();
    consumer.createKafkaConfig(
        consumerExtConfigs, extKafkaProperties.getTopicProperties().getName());
  }

  public void execute() {
    executor =
        new ThreadPoolExecutor(
            this.getNumberOfConsumers(),
            this.getNumberOfConsumers() * 2,
            0L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1000),
            new ThreadPoolExecutor.CallerRunsPolicy());
    while (true) {
      executor.execute(consumer);
    }
  }

  public int getNumberOfConsumers() {
    return this.extKafkaProperties.getThreadProperties().getNumOfConsumer();
  }

  public void shutdown() throws InterruptedException {
    if (consumer != null) {
      consumer.shutdown();
    }
    if (executor != null) {
      executor.shutdown();
    }
    try {
      if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
        System.out.println(
            "Timed out waiting for consumer threads to shut down, exiting uncleanly");
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted during shutdown, exiting uncleanly");
    }
  }
}
