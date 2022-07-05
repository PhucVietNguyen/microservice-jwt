package com.example.as.service.repository;

import com.example.as.service.model.entity.RefreshTokenEntity;
import com.example.as.service.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

  @Override
  Optional<RefreshTokenEntity> findById(Long id);

  Optional<RefreshTokenEntity> findByToken(String token);

  int deleteByUser(UserEntity user);

  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("DELETE FROM RefreshTokenEntity rt WHERE rt.user.username = :username")
  int deleteByUserName(@Param("username") String username);
}
