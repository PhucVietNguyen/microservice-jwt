package com.example.ext.service.model.dto;

import com.example.ext.service.model.enums.EGender;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto implements Serializable {

  private Long id;

  private String studentName;

  private Integer age;

  private EGender gender;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    StudentDto that = (StudentDto) o;

    return new EqualsBuilder()
        .append(id, that.id)
        .append(studentName, that.studentName)
        .append(age, that.age)
        .append(gender, that.gender)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(id)
        .append(studentName)
        .append(age)
        .append(gender)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("studentName", studentName)
        .append("age", age)
        .append("gender", gender)
        .toString();
  }
}
