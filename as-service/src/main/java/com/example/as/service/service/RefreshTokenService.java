package com.example.as.service.service;

import com.example.as.service.model.entity.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenService {

  Optional<RefreshTokenEntity> findByToken(String token);

  RefreshTokenEntity createRefreshToken(Long userId, String jwt);

  RefreshTokenEntity verifyExpiration(RefreshTokenEntity token);

  int deleteByUserId(Long userId);

  RefreshTokenEntity updateRefreshToken(RefreshTokenEntity token);

  int revokeRefreshTokenByUserId(String username);
}
