package com.example.ext.service.util;

import com.example.common.core.enums.exception.CommonCoreErrorCode;
import com.example.common.core.exception.BusinessException;
import com.example.ext.service.enums.redis.RedisConstant;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class RedisUtils {

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  public boolean isTokenBlacklisted(String token) {
    if (token == null) {
      throw new BusinessException(
          CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode(),
          CommonCoreErrorCode.INVALID_TOKEN.getDesc());
    }
    return redisTemplate.opsForValue().get(RedisConstant.JWT + token) != null;
  }
}
