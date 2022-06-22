package com.example.ext.service.model.entity;

import com.example.ext.service.enums.EGender;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentEntity extends BaseEntity implements Serializable {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String studentName;

  @Column private Integer age;

  @Column
  @Enumerated(EnumType.STRING)
  private EGender gender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "room_id")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private RoomEntity room;
}
