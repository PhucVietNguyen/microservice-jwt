package com.example.ext.service.controller;

import com.example.ext.service.model.request.RoomRequest;
import com.example.ext.service.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class ImportController {

  @Autowired private RoomService roomService;

  @PostMapping("/add-room")
  @PreAuthorize("hasRole('USER')")
  public Integer addRoom(@RequestBody RoomRequest request) {
    return roomService.createRoom(request);
  }

  @GetMapping("/get-room/{roomId}")
  @PreAuthorize("hasRole('ADMIN')")
  public RoomRequest getRoomByRoomId(@PathVariable Long roomId) {
    return roomService.findRoomById(roomId);
  }
}
