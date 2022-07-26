package com.example.as.service.controller;

import com.example.as.service.model.entity.RefreshTokenEntity;
import com.example.as.service.model.entity.RoleEntity;
import com.example.as.service.model.entity.UserEntity;
import com.example.as.service.model.entity.security.UserDetailsImpl;
import com.example.as.service.model.enums.ERole;
import com.example.as.service.model.request.LoginRequest;
import com.example.as.service.model.request.LogoutRequest;
import com.example.as.service.model.request.SignupRequest;
import com.example.as.service.model.request.TokenRefreshRequest;
import com.example.as.service.model.response.JwtRefreshResponse;
import com.example.as.service.model.response.JwtResponse;
import com.example.as.service.model.response.MessageResponse;
import com.example.as.service.repository.RoleRepository;
import com.example.as.service.repository.UserRepository;
import com.example.as.service.service.AuthenticationService;
import com.example.as.service.service.RefreshTokenService;
import com.example.as.service.service.UserAuthenticationHistoryService;
import com.example.as.service.util.JwtUtils;
import com.example.common.core.enums.exception.CommonCoreErrorCode;
import com.example.common.core.exception.BusinessException;
import com.example.common.core.exception.TokenRefreshException;
import com.example.common.core.exception.ValidUserOrPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private PasswordEncoder encoder;

  @Autowired private JwtUtils jwtUtils;

  @Autowired private RefreshTokenService refreshTokenService;

  @Autowired private AuthenticationService authenticationService;

  @Autowired private UserAuthenticationHistoryService userAuthenticationHistoryService;

  @PostMapping("/sign-in")
  public ResponseEntity<?> signIn(@Valid @RequestBody LoginRequest loginRequest) {
    Authentication authentication = null;
    try {
      authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getUsername(), loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (AuthenticationException ex) {
      throw ValidUserOrPasswordException.builder()
          .errCode(CommonCoreErrorCode.USERNAME_PASSWORD_INVALID.getServiceErrorCode())
          .errMessage(CommonCoreErrorCode.USERNAME_PASSWORD_INVALID.getDesc())
          .build();
    }
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles =
        userDetails.getAuthorities().stream()
            .map(item -> item.getAuthority())
            .collect(Collectors.toList());
    RefreshTokenEntity refreshToken =
        refreshTokenService.createRefreshToken(userDetails.getId(), jwt);
    userAuthenticationHistoryService.saveAuthenticationHistory(
        null, loginRequest, true, userDetails.getUsername());
    return ResponseEntity.ok(
        JwtResponse.builder()
            .token(jwt)
            .id(userDetails.getId())
            .username(userDetails.getUsername())
            .email(userDetails.getEmail())
            .role(roles)
            .tokenType("Bearer")
            .refreshToken(refreshToken.getToken())
            .build());
  }

  @PostMapping("/sign-up")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }
    // Create new user's account
    UserEntity user =
        UserEntity.builder()
            .username(signUpRequest.getUsername())
            .email(signUpRequest.getEmail())
            .password(encoder.encode(signUpRequest.getPassword()))
            .build();
    Set<String> strRoles = signUpRequest.getRoles();
    Set<RoleEntity> roles = new HashSet<>();
    if (strRoles == null) {
      RoleEntity userRole =
          roleRepository
              .findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(
          role -> {
            switch (role) {
              case "admin":
                RoleEntity adminRole =
                    roleRepository
                        .findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
                break;
              case "mod":
                RoleEntity modRole =
                    roleRepository
                        .findByName(ERole.ROLE_MODERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
                break;
              default:
                RoleEntity userRole =
                    roleRepository
                        .findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
          });
    }
    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
    String requestRefreshToken = request.getRefreshToken();
    Optional<RefreshTokenEntity> refreshTokenOpt =
        refreshTokenService.findByToken(requestRefreshToken);
    if (!refreshTokenOpt.isPresent()) {
      throw new TokenRefreshException(requestRefreshToken, "Refresh token is not exist");
    }
    RefreshTokenEntity refreshToken = refreshTokenOpt.get();
    UserDetailsImpl userDetails =
        (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    final String accessToken = userDetails.getJwt();
    authenticationService.blacklistJwt(accessToken);
    refreshTokenService.verifyExpiration(refreshToken);
    String token = jwtUtils.generateTokenFromUsername(refreshToken.getUser());
    return ResponseEntity.ok(new JwtRefreshResponse(token, requestRefreshToken, "Bearer"));
  }

  @PostMapping("/sign-out")
  public ResponseEntity<?> signOut(
      @Valid @RequestBody LogoutRequest logoutRequest, HttpServletRequest servletRequest) {
    String token = jwtUtils.parseJwt(servletRequest);
    if (token == null) {
      throw new BusinessException(
          CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode(),
          CommonCoreErrorCode.INVALID_TOKEN.getDesc());
    }
    logoutRequest.setToken(token);
    String username = jwtUtils.getUserNameFromJwtToken(token);

    if (refreshTokenService.revokeRefreshTokenByUserId(username) == 0) {
      throw BusinessException.builder()
          .errorCode(CommonCoreErrorCode.TOKEN_EXPIRED.getServiceErrorCode())
          .errorMsg(CommonCoreErrorCode.TOKEN_EXPIRED.getDesc())
          .build();
    }
    userAuthenticationHistoryService.saveAuthenticationHistory(
        logoutRequest, null, false, username);

    authenticationService.blacklistJwt(token);
    return ResponseEntity.ok("User successfully logout");
  }
}
