package com.example.common.core.exception;

import com.example.common.core.validation.ErrorMap;

public class ValidationException extends RuntimeException {

  private final ErrorMap errorMap;

  public ValidationException(ErrorMap e) {
    super();
    errorMap = e;
  }

  public ErrorMap getErrorMap() {
    return errorMap;
  }
}
