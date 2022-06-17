package com.example.as.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRefreshResponse {

  private String accessToken;

  private String refreshToken;

  private String tokenType;
}
