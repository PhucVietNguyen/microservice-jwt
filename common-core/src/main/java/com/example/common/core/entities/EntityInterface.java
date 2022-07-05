package com.example.common.core.entities;

import java.io.Serializable;

public interface EntityInterface<T> extends Serializable {

  /**
   * @return entity identity
   */
  T getId();
}
