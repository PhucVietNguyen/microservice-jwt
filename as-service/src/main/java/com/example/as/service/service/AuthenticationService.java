package com.example.as.service.service;

import com.example.as.service.model.dto.ResetPwDTO;
import com.example.as.service.model.request.LogoutRequest;

public interface AuthenticationService {

  boolean logout(LogoutRequest logoutRequest);

  ResetPwDTO resetPassword(ResetPwDTO resetPwDTO);

//  String sendForgotPasswordEmail(String email, Locale locale);
//
//  CheckResetPwCodeVO isResetPwCodeValid(CheckResetPwCodeVO checkResetPwCodeVO);
}
