package com.example.as.service.repository;

import com.example.as.service.model.entity.RefreshTokenEntity;
import com.example.as.service.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  @Override
  Optional<RefreshTokenEntity> findById(Long id);

  Optional<RefreshTokenEntity> findByToken(String token);

  int deleteByUser(UserEntity user);
}
