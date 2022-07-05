package com.example.ext.service.cronjob;

import com.example.ext.service.model.dto.RoomDto;

public interface UnitJob {

  void pushDataToKafkaTopic(RoomDto roomDto);
}
