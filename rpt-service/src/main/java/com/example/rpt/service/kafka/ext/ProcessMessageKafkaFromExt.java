package com.example.rpt.service.kafka.ext;

import com.example.ext.service.model.dto.RoomDto;
import com.example.rpt.service.kafka.consumer.BaseKafkaConsumer;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Log4j2
public class ProcessMessageKafkaFromExt extends BaseKafkaConsumer<String, RoomDto> {

  @Override
  public void handleMessage(
      List<ConsumerRecord<String, RoomDto>> buffer, TopicPartition partition) {
    buffer.stream()
        .forEach(
            record -> {
              log.info("<---record:" + record.value());
            });
  }
}
