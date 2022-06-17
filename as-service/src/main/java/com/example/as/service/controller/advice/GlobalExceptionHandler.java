package com.example.as.service.controller.advice;

import com.example.as.service.exception.TokenRefreshException;
import com.example.as.service.model.response.ErrorResponse;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
@NoArgsConstructor
public class GlobalExceptionHandler {

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.warn("Business logic exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.badRequest()
        .body(ErrorResponse.builder().errorCode("1010").errorMessage(e.getMessage()).build());
  }

  @ExceptionHandler({TokenRefreshException.class})
  public ResponseEntity<ErrorResponse> handleException(TokenRefreshException e) {
    log.warn("refresh token exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.badRequest()
        .body(ErrorResponse.builder().errorCode("1011").errorMessage(e.getMessage()).build());
  }
}