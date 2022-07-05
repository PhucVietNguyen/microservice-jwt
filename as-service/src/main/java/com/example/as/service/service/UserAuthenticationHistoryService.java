package com.example.as.service.service;

import com.example.as.service.model.request.LoginRequest;
import com.example.as.service.model.request.LogoutRequest;

public interface UserAuthenticationHistoryService {

  void saveAuthenticationHistory(
      LogoutRequest logoutRequest, LoginRequest loginRequest, Boolean flag, String username);
}
