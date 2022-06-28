package com.example.common.core.exception;

import com.example.common.core.enums.exception.CommonCoreErrorCode;

public class InvalidAccessTokenException extends AbstractTokenException {

  public InvalidAccessTokenException(String accessToken) {
    super(accessToken, CommonCoreErrorCode.INVALID_TOKEN);
  }
}
