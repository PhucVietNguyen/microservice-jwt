package com.example.ext.service.util;

import com.example.ext.service.config.RsaKeyProperties;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class JwtUtils {

  @Value("${secret-key.app.jwtSecret}")
  private String jwtSecret;

  @Autowired private RsaKeyProperties rsaKeyProperties;

  private static final String AUTHORITIES_KEY = "authorities";

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser()
        .setSigningKey(rsaKeyProperties.getPublicKey())
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public List<String> getAuthoritiesFromJwtToken(String token) {
    return (List<String>)
        Jwts.parser()
            .setSigningKey(rsaKeyProperties.getPublicKey())
            .parseClaimsJws(token)
            .getBody()
            .get(AUTHORITIES_KEY);
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
