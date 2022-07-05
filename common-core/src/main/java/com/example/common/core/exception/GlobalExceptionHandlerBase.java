package com.example.common.core.exception;

import com.example.common.core.enums.exception.CommonCoreErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@Log4j2
public class GlobalExceptionHandlerBase {

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<ErrorResponse> handleException(BusinessException e) {
    log.warn("Business logic exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ErrorResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getErrorMsg())
                .build());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    log.warn("Unknown exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return new ResponseEntity<>(
        ErrorResponse.builder()
            .errorCode(CommonCoreErrorCode.UNKNOWN_ERROR.getServiceErrorCode())
            .errorMessage(CommonCoreErrorCode.UNKNOWN_ERROR.getDesc())
            .build(),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({TokenRefreshException.class})
  public ResponseEntity<ErrorResponse> handleException(TokenRefreshException e) {
    log.warn("refresh token exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ErrorResponse.builder()
                .errorCode(CommonCoreErrorCode.REFRESH_TOKEN_EXPIRED.getServiceErrorCode())
                .errorMessage(e.getMessage())
                .build());
  }

  @ExceptionHandler({ValidUserOrPasswordException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(ValidUserOrPasswordException e) {
    log.warn("Authentication exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            ErrorResponse.builder()
                .errorCode(e.getErrCode())
                .errorMessage(e.getErrMessage())
                .build());
  }

  @ExceptionHandler({AccessTokenExpireException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(AccessTokenExpireException e) {
    log.warn("Authentication exception: {}, stack trace:", e.getMessage());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            (new ErrorResponse())
                .builder()
                .errorCode(e.getErrorCode().getServiceErrorCode())
                .errorMessage(e.getErrorCode().getDesc())
                .build());
  }

  @ExceptionHandler({InvalidAccessTokenException.class})
  public ResponseEntity<ErrorResponse> handleAuthException(InvalidAccessTokenException e) {
    log.warn("Invalid access token: {}", e.getErrorCode());
    e.printStackTrace();
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(
            (new ErrorResponse())
                .builder()
                .errorCode(e.getErrorCode().getServiceErrorCode())
                .errorMessage(e.getErrorCode().getDesc())
                .build());
  }

  @ExceptionHandler(value = ValidationException.class)
  public ResponseEntity<Map<String, String>> handleException(ValidationException e) {
    log.warn("validation exception: {}, stack trace:", e.toString());
    e.printStackTrace();
    Map<String, String> errorMap = e.getErrorMap().toMap();

    return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
  }
}
