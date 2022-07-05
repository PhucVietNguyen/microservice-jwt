package com.example.as.service.service.impl;

import com.example.as.service.enums.error.AsErrorCode;
import com.example.as.service.model.entity.UserAuthenticationHistoryEntity;
import com.example.as.service.model.entity.UserEntity;
import com.example.as.service.model.enums.EUserAction;
import com.example.as.service.model.request.LoginRequest;
import com.example.as.service.model.request.LogoutRequest;
import com.example.as.service.repository.UserAuthenticationHistoryRepository;
import com.example.as.service.repository.UserRepository;
import com.example.as.service.service.UserAuthenticationHistoryService;
import com.example.common.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserAuthenticationHistoryServiceImpl implements UserAuthenticationHistoryService {

  @Autowired private UserAuthenticationHistoryRepository userAuthenticationHistoryRepository;

  @Autowired private UserRepository userRepository;

  @Override
  public void saveAuthenticationHistory(
      LogoutRequest logoutRequest, LoginRequest loginRequest, Boolean flag, String username) {

    UserAuthenticationHistoryEntity userAuthenticationHistory =
        new UserAuthenticationHistoryEntity();
    Optional<UserEntity> userOpt = userRepository.findByUsername(username);
    if (!userOpt.isPresent()) {
      throw BusinessException.builder()
          .errorCode(AsErrorCode.USER_IS_NOT_EXIST.getServiceErrorCode())
          .errorMsg(AsErrorCode.USER_IS_NOT_EXIST.getDesc())
          .build();
    }
    userAuthenticationHistory.setUser(userOpt.get());
    userAuthenticationHistory.setActionDatetime(Instant.now());
    userAuthenticationHistory.setDeviceId(
        Objects.nonNull(logoutRequest) ? logoutRequest.getDeviceId() : null);
    userAuthenticationHistory.setDeviceType(
        Objects.nonNull(logoutRequest) ? logoutRequest.getDeviceType() : null);
    userAuthenticationHistory.setSuccess(true);

    if (flag) {
      userAuthenticationHistory.setLoginType("EMAIL"); // Todo set login type(email, phone, social)
      userAuthenticationHistory.setUserAction(EUserAction.LOGIN);
    } else {
      userAuthenticationHistory.setUserAction(EUserAction.LOGOUT);
    }

    userAuthenticationHistoryRepository.save(userAuthenticationHistory);
  }
}
