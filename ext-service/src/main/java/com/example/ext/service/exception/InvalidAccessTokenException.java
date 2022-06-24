package com.example.ext.service.exception;

import com.example.ext.service.enums.error.CommonCoreErrorCode;

public class InvalidAccessTokenException extends AbstractTokenException {
  public InvalidAccessTokenException(String accessToken) {
    super(accessToken, CommonCoreErrorCode.INVALID_TOKEN);
  }
}
