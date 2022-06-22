package com.example.ext.service.model;

import com.example.ext.service.model.dto.RoomDto;

public interface ExtDubboService {

  RoomDto findRoomById(Long id);
}
