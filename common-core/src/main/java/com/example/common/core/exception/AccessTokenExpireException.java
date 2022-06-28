package com.example.common.core.exception;

import com.example.common.core.enums.exception.CommonCoreErrorCode;

public class AccessTokenExpireException extends AbstractTokenException {

  public AccessTokenExpireException(String token) {
    super(token, CommonCoreErrorCode.TOKEN_EXPIRED);
  }
}
