package com.example.common.core.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Data
@Builder
public class ValidUserOrPasswordException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String errCode;

  private String errMessage;
}
