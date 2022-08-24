package com.example.as.service.dubbo;

import com.example.as.service.model.AsDubboService;
import com.example.as.service.util.JwtUtils;
import com.example.as.service.util.RedisUtils;
import com.example.common.core.exception.AccessTokenExpireException;
import com.example.common.core.exception.InvalidAccessTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@DubboService(version = "1.0.0")
@Service
public class AsDubboServiceImpl implements AsDubboService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String authCheck(String token) {
        log.info("authentication account with token: {}", token);
        String tokenAuth = jwtUtils.parseJwt(token);
        boolean isJwtValid = false;
        try {
            isJwtValid = jwtUtils.validateJwtToken(tokenAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidAccessTokenException(tokenAuth);
        }

        if (tokenAuth == null || !isJwtValid) {
            throw new InvalidAccessTokenException(tokenAuth);
        }

        if (redisUtils.isTokenBlacklisted(tokenAuth)) {
            throw new AccessTokenExpireException(tokenAuth);
        }

        String userIdFromToken = jwtUtils.getUserIdFromJwtToken(tokenAuth);

        return userIdFromToken;
    }
}
