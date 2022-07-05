package com.example.ext.service.mapper;

import com.example.ext.service.model.dto.RoomDto;
import com.example.ext.service.model.entity.RoomEntity;
import com.example.ext.service.model.request.RoomRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {
  RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

  RoomRequest entityToDto(RoomEntity entity);

  RoomEntity dtoToEntity(RoomRequest request);

  RoomDto entityToDto1(RoomEntity entity);
}
