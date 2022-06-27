package com.example.ext.service.service;

import com.example.ext.service.model.dto.RoomDto;
import com.example.ext.service.model.request.RoomRequest;

public interface RoomService {

  int createRoom(RoomRequest request);

  RoomRequest findRoomById(Long id);

  RoomDto getRoomDtoById(Long id);
}
