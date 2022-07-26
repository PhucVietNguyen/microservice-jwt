package com.example.as.service.util;

import com.example.as.service.config.RsaKeyProperties;
import com.example.as.service.model.entity.RoleEntity;
import com.example.as.service.model.entity.UserEntity;
import com.example.as.service.model.entity.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtUtils {

  @Value("${secret-key.app.jwtSecret}")
  private String jwtSecret;

  @Value("${secret-key.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Autowired private RsaKeyProperties rsaKeyProperties;

  private static final String AUTHORITIES_KEY = "authorities";

  public String generateJwtToken(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .claim(
            AUTHORITIES_KEY,
            userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.RS256, rsaKeyProperties.getPrivateKey())
        .compact();
  }

  public String generateTokenFromUsername(UserEntity user) {
    return Jwts.builder()
        .setSubject(user.getUsername())
        .claim(
            AUTHORITIES_KEY,
            user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.RS256, rsaKeyProperties.getPrivateKey())
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(rsaKeyProperties.getPublicKey())
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String getUserNameFromHttpRequest(HttpServletRequest request) {
    final String token = parseJwt(request);
    return Jwts.parser()
            .setSigningKey(rsaKeyProperties.getPublicKey())
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
  }

  public Date getExpirationDateFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(rsaKeyProperties.getPublicKey())
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();
  }

  //  return token remaining time in ms
  public Long getTokenRemainingTime(String token) {
    Date date = new Date();
    return getExpirationDateFromToken(token).getTime() - date.getTime();
  }

  public String parseJwt(HttpServletRequest request) {
    final String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  public String parseJwt(String headerAuth) {

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(rsaKeyProperties.getPublicKey()).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
