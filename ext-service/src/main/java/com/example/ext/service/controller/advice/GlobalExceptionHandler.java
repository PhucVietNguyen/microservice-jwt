package com.example.ext.service.controller.advice;

import com.example.ext.service.exception.BusinessException;
import com.example.ext.service.model.response.ErrorResponse;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
@NoArgsConstructor
public class GlobalExceptionHandler {

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
    log.warn("Business logic exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.badRequest()
        .body(ErrorResponse.builder().errorCode("1010").errorMessage(e.getMessage()).build());
  }

  @ExceptionHandler({AuthenticationException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException e) {
    log.warn("Authentication exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.badRequest()
        .body(ErrorResponse.builder().errorCode("1011").errorMessage(e.getMessage()).build());
  }
}
