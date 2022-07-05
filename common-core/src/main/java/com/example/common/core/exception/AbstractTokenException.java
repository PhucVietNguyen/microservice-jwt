package com.example.common.core.exception;

import com.example.common.core.enums.exception.CommonCoreErrorCode;

public abstract class AbstractTokenException extends RuntimeException {
  private final String token;
  private final CommonCoreErrorCode errorCode;

  public AbstractTokenException(String token, CommonCoreErrorCode errorCode) {
    this.token = token;
    this.errorCode = errorCode;
  }

  public String getToken() {
    return this.token;
  }

  public CommonCoreErrorCode getErrorCode() {
    return this.errorCode;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("AbstractTokenException{");
    sb.append("token='").append(this.token).append('\'');
    sb.append(", errorCode=").append(this.errorCode);
    sb.append('}');
    return sb.toString();
  }
}
