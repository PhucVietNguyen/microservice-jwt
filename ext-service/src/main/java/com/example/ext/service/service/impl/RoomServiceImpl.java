package com.example.ext.service.service.impl;

import com.example.common.core.exception.BusinessException;
import com.example.ext.service.enums.error.ExtErrorCode;
import com.example.ext.service.mapper.RoomMapper;
import com.example.ext.service.mapper.StudentMapper;
import com.example.ext.service.model.dto.RoomDto;
import com.example.ext.service.model.dto.StudentDto;
import com.example.ext.service.model.entity.RoomEntity;
import com.example.ext.service.model.entity.StudentEntity;
import com.example.ext.service.model.request.RoomRequest;
import com.example.ext.service.model.request.StudentRequest;
import com.example.ext.service.repository.RoomRepository;
import com.example.ext.service.repository.StudentRepository;
import com.example.ext.service.service.RoomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class RoomServiceImpl implements RoomService {

  @Autowired private RoomRepository roomRepository;

  @Autowired private StudentRepository studentRepository;

  private RoomMapper roomMapper = RoomMapper.INSTANCE;

  private StudentMapper studentMapper = StudentMapper.INSTANCE;

  @Override
  @Transactional
  public int createRoom(RoomRequest request) {
    if (request == null) {
      throw new BusinessException(
          ExtErrorCode.INVALID_REQUEST_CREATE_ROOM.getServiceErrorCode(),
          ExtErrorCode.INVALID_REQUEST_CREATE_ROOM.getDesc());
    }
    RoomEntity room = roomMapper.dtoToEntity(request);
    RoomEntity room1 = roomRepository.save(room);
    if (request.getStudents() != null && !request.getStudents().isEmpty()) {
      List<StudentEntity> students = studentMapper.listDtoToEntity(request.getStudents());
      students.forEach(
          student -> {
            student.setRoom(room1);
          });
      studentRepository.saveAll(students);
    }
    return 1;
  }

  @Override
  public RoomRequest findRoomById(Long id) {
    if (id == null) {
      throw new BusinessException(
          ExtErrorCode.INVALID_REQUEST_GET_ROOM_ID.getServiceErrorCode(),
          ExtErrorCode.INVALID_REQUEST_GET_ROOM_ID.getDesc());
    }
    Optional<RoomEntity> roomOpt = roomRepository.findById(id);
    if (roomOpt == null || roomOpt.isEmpty()) {
      throw new BusinessException(
          ExtErrorCode.ROOM_IS_NOT_EXIST.getServiceErrorCode(),
          ExtErrorCode.ROOM_IS_NOT_EXIST.getDesc());
    }
    RoomRequest request = roomMapper.entityToDto(roomOpt.get());
    List<StudentEntity> entities = studentRepository.findAllByRoomId(id);
    List<StudentRequest> requestList = studentMapper.listEntityToDto(entities);
    request.setStudents(requestList);
    return request;
  }

  @Override
  public RoomDto getRoomDtoById(Long id) {
    if (id == null) {
      throw new BusinessException(
          ExtErrorCode.INVALID_REQUEST_GET_ROOM_ID.getServiceErrorCode(),
          ExtErrorCode.INVALID_REQUEST_GET_ROOM_ID.getDesc());
    }
    Optional<RoomEntity> roomOpt = roomRepository.findById(id);
    if (roomOpt == null || roomOpt.isEmpty()) {
      throw new BusinessException(
          ExtErrorCode.ROOM_IS_NOT_EXIST.getServiceErrorCode(),
          ExtErrorCode.ROOM_IS_NOT_EXIST.getDesc());
    }
    RoomDto dto = roomMapper.entityToDto1(roomOpt.get());
    List<StudentEntity> entities = studentRepository.findAllByRoomId(id);
    List<StudentDto> requestDto = studentMapper.listEntityToDto1(entities);
    dto.setStudents(requestDto);
    return dto;
  }
}
