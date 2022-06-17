package com.example.as.service.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JwtResponse {

  private String token;

  private String tokenType;

  private Long id;

  private String username;

  private String email;

  private String refreshToken;

  private List<String> role;
}
