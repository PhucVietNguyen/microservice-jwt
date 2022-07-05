package com.example.common.core.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessException extends RuntimeException {

  private String errorCode;

  private String errorMsg;
}
