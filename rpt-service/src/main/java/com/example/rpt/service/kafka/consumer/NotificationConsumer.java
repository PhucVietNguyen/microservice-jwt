package com.example.rpt.service.kafka.consumer;

import com.example.rpt.service.config.CustomJsonDeserializer;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Log4j2
public class NotificationConsumer<K, T> {

  private KafkaConsumer<K, T> consumer;

  private ExecutorService executor;

  public NotificationConsumer(String brokers, String groupId, String topic) {
    Properties prop = consumerConfigs(brokers, groupId);
    this.consumer = new KafkaConsumer<>(prop);
    this.consumer.subscribe(Arrays.asList(topic));
  }

  /**
   * Creates a {@link ThreadPoolExecutor} with a given number of threads to consume the messages
   * from the broker.
   *
   * @param numberOfThreadsDefault The number of threads will be used to consume the message
   */
  public void execute(int numberOfThreadsDefault, int numOfThreadsMax) {
    executor =
        new ThreadPoolExecutor(
            numberOfThreadsDefault,
            numOfThreadsMax,
            0L,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(100),
            new ThreadPoolExecutor.CallerRunsPolicy());

    while (true) {
      final Duration pollTimeout = Duration.ofMillis(100);
      ConsumerRecords<K, T> records = consumer.poll(pollTimeout);
      for (final ConsumerRecord<K, T> record : records) {
        executor.submit(new ConsumerThreadHandler(record));
      }
    }
  }

  private static Properties consumerConfigs(String brokers, String groupId) {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomJsonDeserializer.class);
    return props;
  }

  public void shutdown() {
    if (consumer != null) {
      consumer.close();
    }
    if (executor != null) {
      executor.shutdown();
    }
    try {
      if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
        log.info("Timed out waiting for consumer threads to shut down, exiting uncleanly");
      }
    } catch (InterruptedException e) {
      log.info("Interrupted during shutdown, exiting uncleanly");
    }
  }
}
