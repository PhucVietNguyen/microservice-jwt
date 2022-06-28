package com.example.as.service.util;

import com.example.as.service.enums.redis.RedisConstant;
import com.example.as.service.model.entity.RefreshTokenEntity;
import com.example.common.core.enums.exception.CommonCoreErrorCode;
import com.example.common.core.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RedisUtils {

  @Autowired private RedisTemplate<String, Object> redisTemplate;

  @Autowired private JwtUtils jwtUtils;

  public boolean isTokenBlacklisted(String token) {
    if (token == null) {
      throw new BusinessException(
          CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode(),
          CommonCoreErrorCode.INVALID_TOKEN.getDesc());
    }
    return redisTemplate.opsForValue().get(RedisConstant.JWT + token) != null;
  }

  public RefreshTokenEntity blacklistJwt(RefreshTokenEntity refreshToken) {
    if (refreshToken == null || isTokenBlacklisted(refreshToken.getAccessToken())) {
      throw new BusinessException(
          CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode(),
          CommonCoreErrorCode.INVALID_TOKEN.getDesc());
    }
    redisTemplate
        .opsForValue()
        .set(
            RedisConstant.JWT + refreshToken.getAccessToken(),
            refreshToken.getUser().getUsername());
    return refreshToken;
  }

  public void blacklistJwt(String token, String username) {
    if (token == null || isTokenBlacklisted(token)) {
      throw new BusinessException(
          CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode(),
          CommonCoreErrorCode.INVALID_TOKEN.getDesc());
    }
    redisTemplate.opsForValue().set(RedisConstant.JWT + token, username);
  }
}
