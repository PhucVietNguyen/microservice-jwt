package com.example.as.service.controller;

import com.example.as.service.exception.ResourceNotFoundException;
import com.example.as.service.model.entity.UserEntity;
import com.example.as.service.model.entity.security.UserDetailsImpl;
import com.example.as.service.repository.UserRepository;
import com.example.as.service.secutiry.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @Autowired private UserRepository userRepository;

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public UserEntity getCurrentUser(@CurrentUser UserDetailsImpl userDetails) {
    return userRepository
        .findById(userDetails.getId())
        .orElseThrow(() -> new ResourceNotFoundException("User", "id", userDetails.getId()));
  }
}
