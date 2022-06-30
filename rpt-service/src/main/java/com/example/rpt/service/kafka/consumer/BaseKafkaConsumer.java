package com.example.rpt.service.kafka.consumer;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

@Log4j2
public abstract class BaseKafkaConsumer<K, T> implements Runnable {

  private CountDownLatch countDownLatch;

  private Consumer<K, T> consumer;

  private Properties properties;

  private String topic;

  public void createKafkaConfig(Properties properties, String topic) {
    this.topic = topic;
    this.properties = properties;
    this.countDownLatch = new CountDownLatch(1);
    consumer = new KafkaConsumer<>(properties);
    consumer.subscribe(Collections.singleton(topic));
  }

  public abstract void handleMessage(List<ConsumerRecord<K, T>> buffer, TopicPartition partition);

  @Override
  public void run() {
    final Duration pollTimeout = Duration.ofMillis(100);
    try {
      while (true) {
        final ConsumerRecords<K, T> records = consumer.poll(pollTimeout);
        if (null != records && !records.isEmpty()) {
          log.info("Record is not null");
          for (TopicPartition partition : records.partitions()) {
            log.info(
                "Preparing to consume topic: " + topic + ", partition = " + partition.toString());
            List<ConsumerRecord<K, T>> partitionRecords = records.records(partition);
            if (null != partitionRecords && !partitionRecords.isEmpty()) {
              log.info(
                  "--->prepare to handle message to end while loop, buffer size: "
                      + partitionRecords.size());
              handleMessage(partitionRecords, partition);
              log.info("--->end handle message to end while loop");
            }
          }
          consumer.commitAsync();
          log.info("prepare get offsetAndMetadata committed");
          consumer.committed(records.partitions());
        }
      }
    } catch (WakeupException e) {
      log.info("Consumer poll woke up");
    } finally {
      consumer.close();
      countDownLatch.countDown();
    }
  }

  public void shutdown() throws InterruptedException {
    consumer.wakeup();
    countDownLatch.await();
    log.info("Consumer closed");
  }
}
