package com.example.common.core.exception;

import com.example.common.core.enums.exception.ErrorCode;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessException extends RuntimeException {

  private String errorCode;

  private String errorMsg;

  public BusinessException build(ErrorCode errorCode) {
    this.errorCode = errorCode.getServiceErrorCode();
    this.errorMsg = errorCode.getDesc();
    return this;
  }

}
