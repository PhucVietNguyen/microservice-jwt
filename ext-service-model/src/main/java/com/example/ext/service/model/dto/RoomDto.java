package com.example.ext.service.model.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDto implements Serializable {

  private Long id;

  private String className;

  private String location;

  private List<StudentDto> students;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    RoomDto roomDto = (RoomDto) o;

    return new org.apache.commons.lang3.builder.EqualsBuilder()
        .append(id, roomDto.id)
        .append(className, roomDto.className)
        .append(location, roomDto.location)
        .append(students, roomDto.students)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
        .append(id)
        .append(className)
        .append(location)
        .append(students)
        .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("id", id)
        .append("className", className)
        .append("location", location)
        .append("students", students)
        .toString();
  }
}
