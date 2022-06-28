package com.example.as.service.model.entity;

import com.example.as.service.model.enums.EUserAction;
import com.example.common.core.entities.AbstractAuditingEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "user_authentication_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAuthenticationHistoryEntity extends AbstractAuditingEntity<Long>
    implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String loginType; // email, phone...

  @Column private String deviceId;

  @Column private String deviceType;

  @Column private Instant actionDatetime;

  @Column private String ipAddress;

  @Column private boolean isSuccess;

  @Column
  @Enumerated(EnumType.STRING)
  private EUserAction userAction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
