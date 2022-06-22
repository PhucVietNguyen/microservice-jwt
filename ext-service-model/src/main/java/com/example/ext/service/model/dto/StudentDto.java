package com.example.ext.service.model.dto;

import com.example.ext.service.model.enums.EGender;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StudentDto implements Serializable {

  private Long id;

  private String studentName;

  private Integer age;

  private EGender gender;
}
