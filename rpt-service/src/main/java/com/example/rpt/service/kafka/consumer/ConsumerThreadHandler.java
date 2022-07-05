package com.example.rpt.service.kafka.consumer;

import com.example.common.core.enums.exception.CommonCoreErrorCode;
import com.example.common.core.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Log4j2
public class ConsumerThreadHandler<K, T> implements Runnable {

  private ConsumerRecord<K, T> record;

  public ConsumerThreadHandler(ConsumerRecord<K, T> record) {
    this.record = record;
  }

  @Override
  public void run() {
    try {
      if (this.record != null) {
        log.info("Record is not null");
        log.info(
            "value = "
                + this.record.value()
                + ", key = "
                + this.record.key()
                + ", partition = "
                + this.record.partition());
        log.info("ThreadId = " + Thread.currentThread().getId());
      }
    } catch (Exception e) {
      log.info("consumer is not working");
      throw new BusinessException(
          CommonCoreErrorCode.UNKNOWN_ERROR.getServiceErrorCode(),
          CommonCoreErrorCode.UNKNOWN_ERROR.getDesc());
    }
  }
}
