package com.example.common.core.entities;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Audited
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<T> implements EntityInterface<T> {

  @Field("created_by")
  @Column(name = "created_by", updatable = false, nullable = true)
  @CreatedBy
  protected String createdBy;

  @Field("modified_by")
  @Column(name = "modified_by")
  @LastModifiedBy
  protected String modifiedBy;

  @Field("created_date")
  @Column(name = "created_date", updatable = false, nullable = false)
  @CreatedDate
  protected Instant createdDate;

  @Field("modified_date")
  @Column(name = "modified_date")
  @LastModifiedDate
  protected Instant modifiedDate;

  @Field("is_deleted")
  @Column(name = "is_deleted")
  @ColumnDefault("false")
  @NotNull
  protected boolean deleted;
}
