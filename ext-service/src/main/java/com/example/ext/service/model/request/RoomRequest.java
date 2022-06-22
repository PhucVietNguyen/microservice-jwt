package com.example.ext.service.model.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoomRequest {

  private Long id;

  private String className;

  private String location;

  private List<StudentRequest> students;
}
