package com.example.as.service.model.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

  private String errorCode;

  private String errorMessage;
}
