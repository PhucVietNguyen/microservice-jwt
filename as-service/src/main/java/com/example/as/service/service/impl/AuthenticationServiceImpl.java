package com.example.as.service.service.impl;

import com.example.as.service.enums.redis.RedisConstant;
import com.example.as.service.service.AuthenticationService;
import com.example.as.service.util.JwtUtils;
import com.example.common.core.enums.exception.CommonCoreErrorCode;
import com.example.common.core.exception.AccessTokenExpireException;
import com.example.common.core.exception.BusinessException;
import com.example.common.core.exception.InvalidAccessTokenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired private JwtUtils jwtUtils;

    @Override
    public void blacklistJwt(String token) {
        if (token == null || isTokenBlacklisted(token)) {
            throw new BusinessException(
                    CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode(),
                    CommonCoreErrorCode.INVALID_TOKEN.getDesc());
        }
        String username = jwtUtils.getUserNameFromJwtToken(token);
        redisTemplate
                .opsForValue()
                .set(
                        RedisConstant.JWT + token,
                        username);
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        if (token == null) {
            throw new BusinessException(
                    CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode(),
                    CommonCoreErrorCode.INVALID_TOKEN.getDesc());
        }
        return redisTemplate.opsForValue().get(RedisConstant.JWT + token) != null;
    }

    @Override
    public String authUser(HttpServletRequest request) {
        log.info("Authentication user with token...");
        String token = jwtUtils.parseJwt(request);
        boolean isJwtValid = false;
        try {
            isJwtValid = jwtUtils.validateJwtToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidAccessTokenException(token);
        }

        if (token == null || !isJwtValid) {
            throw new InvalidAccessTokenException(token);
        }

        if (isTokenBlacklisted(token)) {
            throw new AccessTokenExpireException(token);
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);

        return username;
    }

    @Override
    public String authUser(String jwt) {
        log.info("Authentication user with token...");
        String token = jwtUtils.parseJwt(jwt);
        boolean isJwtValid = false;
        try {
            isJwtValid = jwtUtils.validateJwtToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidAccessTokenException(token);
        }

        if (token == null || !isJwtValid) {
            throw new InvalidAccessTokenException(token);
        }

        if (isTokenBlacklisted(token)) {
            throw new AccessTokenExpireException(token);
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);

        return username;
    }
}
