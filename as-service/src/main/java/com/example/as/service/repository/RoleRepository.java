package com.example.as.service.repository;

import com.example.as.service.model.entity.RoleEntity;
import com.example.as.service.model.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByName(ERole name);
}
