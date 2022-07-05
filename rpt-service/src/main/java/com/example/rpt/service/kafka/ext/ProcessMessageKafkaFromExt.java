package com.example.rpt.service.kafka.ext;

import com.example.ext.service.model.dto.RoomDto;
import com.example.rpt.service.kafka.consumer.NotificationConsumer;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ProcessMessageKafkaFromExt extends NotificationConsumer<String, RoomDto> {
    public ProcessMessageKafkaFromExt(String brokers, String groupId, String topic) {
        super(brokers, groupId, topic);
    }
}
