package com.example.as.service.model.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Audited
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

  @Column(name = "created_by", updatable = false, nullable = true)
  @CreatedBy
  protected String createdBy;

  @Column(name = "created_date", updatable = false, nullable = false)
  @CreatedDate
  protected Instant createdDate;

  @Column(name = "modified_by")
  @LastModifiedBy
  protected String modifiedBy;

  @Column(name = "modified_date")
  @LastModifiedDate
  protected Instant modifiedDate;

  @Column(name = "is_deleted")
  @ColumnDefault("false")
  protected Boolean isDelete;
}
