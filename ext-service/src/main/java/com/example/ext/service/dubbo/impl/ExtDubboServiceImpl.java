package com.example.ext.service.dubbo.impl;

import com.example.ext.service.model.ExtDubboService;
import com.example.ext.service.model.dto.RoomDto;
import com.example.ext.service.service.RoomService;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@DubboService(version = "1.0.0")
@Service
public class ExtDubboServiceImpl implements ExtDubboService {

  @Autowired private RoomService roomService;

  @Override
  public RoomDto findRoomById(Long id) {
    log.info("dubbo call get data from ext service...");
    return roomService.getRoomDtoById(id);
  }
}
