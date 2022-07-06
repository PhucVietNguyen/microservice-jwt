package com.example.as.service.service.impl;

import com.example.as.service.enums.error.AsErrorCode;
import com.example.as.service.model.dto.ResetPwDTO;
import com.example.as.service.model.entity.UserEntity;
import com.example.as.service.model.request.LogoutRequest;
import com.example.as.service.repository.UserRepository;
import com.example.as.service.service.AuthenticationService;
import com.example.as.service.service.RefreshTokenService;
import com.example.as.service.service.UserAuthenticationHistoryService;
import com.example.as.service.util.RedisUtils;
import com.example.common.core.enums.exception.CommonCoreErrorCode;
import com.example.common.core.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired private RefreshTokenService refreshTokenService;

  @Autowired private RedisUtils redisUtils;

  @Autowired private UserAuthenticationHistoryService userAuthenticationHistoryService;

  @Autowired private UserRepository userRepository;

  @Transactional
  @Override
  public boolean logout(LogoutRequest logoutRequest) {
    final String username = logoutRequest.getUsername();
    if (refreshTokenService.revokeRefreshTokenByUserId(username) == 0) {
      throw BusinessException.builder()
          .errorCode(CommonCoreErrorCode.TOKEN_EXPIRED.getServiceErrorCode())
          .errorMsg(CommonCoreErrorCode.TOKEN_EXPIRED.getDesc())
          .build();
    }
    userAuthenticationHistoryService.saveAuthenticationHistory(
        logoutRequest, null, false, username);

    redisUtils.blacklistJwt(logoutRequest.getToken(), username);
    return true;
  }

  @Override
  public ResetPwDTO resetPassword(ResetPwDTO resetPwDTO) {
    if (resetPwDTO == null) {
      throw new BusinessException().build(AsErrorCode.NEW_PW_FORMAT_INCORRECT);
    }
    //    String retrievedToken =
    //            (String) redisTemplate.opsForValue().get(UAsRedisConstant.RESET_PW +
    // resetPwVO.getEmail());
    //    if (retrievedToken == null) {
    //      throw new BusinessException().build(UasErrorCode.INVALID_VERIFICATION_CODE);
    //    }

    if (!(resetPwDTO.getConfirmPassword().equals(resetPwDTO.getPassword()))) {
      throw new BusinessException().build(AsErrorCode.PW_NOT_SAME);
    }
    UserEntity user =
        userRepository
            .findByEmail(resetPwDTO.getEmail())
            .orElseThrow(
                () -> {
                  throw new BusinessException().build(AsErrorCode.USER_NOT_EXIST);
                });
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    user.setPassword(bCryptPasswordEncoder.encode(resetPwDTO.getPassword()));
    userRepository.save(user);
    //    redisTemplate.delete(UAsRedisConstant.RESET_PW + resetPwVO.getEmail());
    resetPwDTO.setSuccess(true);
    return resetPwDTO;
  }

  //  @Override
  //  @Async
  //  public String sendForgotPasswordEmail(String email, Locale locale) {
  //    Optional<UserLoginDetail> optionalUserLoginDetail =
  //            userLoginDetailRepository.findByEmail(email);
  //    if (optionalUserLoginDetail.isEmpty()) {
  //      return null;
  //    }
  //
  //    String resetPwToken = String.format("%04d", new Random().nextInt(10000));
  //    redisTemplate
  //            .opsForValue()
  //            .set(
  //                    UAsRedisConstant.RESET_PW + email, resetPwToken, resetPwExpirationHour,
  // TimeUnit.HOURS);
  //
  //    ForgetPasswordEmailDTO forgetPasswordEmailDTO = new ForgetPasswordEmailDTO();
  //    forgetPasswordEmailDTO.setSubject(ForgetPasswordEmail.getSubject(locale));
  //    forgetPasswordEmailDTO.setTo(email);
  //    forgetPasswordEmailDTO.setVerificationCode(resetPwToken);
  //    ProducerRecord<String, Object> producerRecord =
  //            new ProducerRecord<String, Object>(
  //                    NsKafkaConstant.TO_CLIENT_EMAIL_TOPIC, forgetPasswordEmailDTO);
  //    producerRecord
  //            .headers()
  //            .add(
  //                    NsKafkaConstant.HEADER_CLASS,
  //                    forgetPasswordEmailDTO
  //                            .getClass()
  //                            .getName()
  //                            .getBytes(StandardCharsets.UTF_8));
  //    kafkaTemplate.send(producerRecord);
  //    //    nsDubboService.sendForgetPasswordEmail(forgetPasswordEmailDTO,locale);
  //    return resetPwToken;
  //  }
  //
  //  @Override
  //  public CheckResetPwCodeVO isResetPwCodeValid(CheckResetPwCodeVO checkResetPwCodeVO) {
  //    if (checkResetPwCodeVO.getCode() == null || checkResetPwCodeVO.getEmail() == null) {
  //      checkResetPwCodeVO.setCorrect(false);
  //      return checkResetPwCodeVO;
  //    }
  //    String retrievedCode =
  //            (String)
  //                    redisTemplate
  //                            .opsForValue()
  //                            .get(UAsRedisConstant.RESET_PW + checkResetPwCodeVO.getEmail());
  //    checkResetPwCodeVO.setCorrect(checkResetPwCodeVO.getCode().equals(retrievedCode));
  //    return checkResetPwCodeVO;
  //  }
}
