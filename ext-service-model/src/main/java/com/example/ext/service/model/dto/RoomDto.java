package com.example.ext.service.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class RoomDto implements Serializable {

  private Long id;

  private String className;

  private String location;

  private List<StudentDto> students;
}
