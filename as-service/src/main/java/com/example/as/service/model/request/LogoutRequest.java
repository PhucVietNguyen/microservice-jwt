package com.example.as.service.model.request;

import com.example.as.service.model.enums.EUserAction;
import lombok.Data;

@Data
public class LogoutRequest {

  private String deviceId;

  private String deviceType;

  private String token;

  private String ipAddress;

  private EUserAction userAction;
}
