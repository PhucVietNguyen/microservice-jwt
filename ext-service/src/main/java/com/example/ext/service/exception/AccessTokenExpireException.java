package com.example.ext.service.exception;

import com.example.ext.service.enums.error.CommonCoreErrorCode;

public class AccessTokenExpireException extends AbstractTokenException {

  public AccessTokenExpireException(String token) {
    super(token, CommonCoreErrorCode.TOKEN_EXPIRED);
  }
}
