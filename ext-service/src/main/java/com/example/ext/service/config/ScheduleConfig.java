package com.example.ext.service.config;

import com.example.ext.service.cronjob.UnitJob;
import com.example.ext.service.model.dto.RoomDto;
import com.example.ext.service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleConfig {

  @Autowired private UnitJob unitJob;

  @Autowired private RoomService roomService;

  @Scheduled(fixedDelayString = "${cron-job.rate}")
  public void runJobSchedule() {
    // start test job and kafka
    RoomDto roomDto = roomService.getRoomDtoById(1L);
    unitJob.pushDataToKafkaTopic(roomDto);
  }
}
