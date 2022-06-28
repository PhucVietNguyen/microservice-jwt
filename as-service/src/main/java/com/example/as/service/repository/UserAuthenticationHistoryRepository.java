package com.example.as.service.repository;

import com.example.as.service.model.entity.UserAuthenticationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthenticationHistoryRepository
    extends JpaRepository<UserAuthenticationHistoryEntity, Long> {}
