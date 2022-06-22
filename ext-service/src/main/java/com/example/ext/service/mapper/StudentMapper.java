package com.example.ext.service.mapper;

import com.example.ext.service.model.dto.StudentDto;
import com.example.ext.service.model.entity.StudentEntity;
import com.example.ext.service.model.request.StudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StudentMapper {
  StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

  StudentRequest entityToDto(StudentEntity entity);

  StudentEntity dtoToEntity(StudentRequest request);

  List<StudentEntity> listDtoToEntity(List<StudentRequest> requests);

  List<StudentRequest> listEntityToDto(List<StudentEntity> entities);

  List<StudentDto> listEntityToDto1(List<StudentEntity> entities);
}
