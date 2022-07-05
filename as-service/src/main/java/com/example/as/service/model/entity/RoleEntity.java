package com.example.as.service.model.entity;

import com.example.as.service.model.enums.ERole;
import com.example.common.core.entities.AbstractAuditingEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity extends AbstractAuditingEntity<Long> implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;
}
