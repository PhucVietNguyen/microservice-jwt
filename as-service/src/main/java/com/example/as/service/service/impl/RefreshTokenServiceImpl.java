package com.example.as.service.service.impl;

import com.example.as.service.exception.BusinessException;
import com.example.as.service.exception.TokenRefreshException;
import com.example.as.service.model.entity.RefreshTokenEntity;
import com.example.as.service.repository.RefreshTokenRepository;
import com.example.as.service.repository.UserRepository;
import com.example.as.service.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

  @Value("${secret-key.app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;

  @Autowired private RefreshTokenRepository refreshTokenRepository;

  @Autowired private UserRepository userRepository;

  @Override
  public Optional<RefreshTokenEntity> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Override
  public RefreshTokenEntity createRefreshToken(Long userId, String jwt) {
    RefreshTokenEntity refreshToken =
        RefreshTokenEntity.builder()
            .user(userRepository.findById(userId).get())
            .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
            .token(UUID.randomUUID().toString())
            .accessToken(jwt)
            .build();
    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  @Override
  public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(
          token.getToken(), "Refresh token was expired. Please make a new sign in request");
    }
    return token;
  }

  @Override
  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }

  @Override
  public RefreshTokenEntity updateRefreshToken(RefreshTokenEntity token) {
    return refreshTokenRepository.save(token);
  }
}
