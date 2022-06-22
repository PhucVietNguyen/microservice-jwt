package com.example.ext.service.model.request;

import com.example.ext.service.enums.EGender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentRequest {

  private Long id;

  private String studentName;

  private Integer age;

  private EGender gender;
}
