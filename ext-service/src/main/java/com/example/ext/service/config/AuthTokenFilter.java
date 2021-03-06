package com.example.ext.service.config;

import com.example.common.core.exception.AccessTokenExpireException;
import com.example.common.core.exception.InvalidAccessTokenException;
import com.example.ext.service.util.JwtUtils;
import com.example.ext.service.util.RedisUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

  @Autowired private JwtUtils jwtUtils;

  @Autowired private RedisUtils redisUtils;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = parseJwt(request);
    Boolean isJwtValid = jwtUtils.validateJwtToken(jwt);
    if (jwt == null || !isJwtValid) {
      throw new InvalidAccessTokenException(jwt);
    }
    if (redisUtils.isTokenBlacklisted(jwt)) {
      throw new AccessTokenExpireException(jwt);
    }

    String username = jwtUtils.getUserNameFromJwtToken(jwt);
    if (username != null) {
      @SuppressWarnings("unchecked")
      List<String> authorities = jwtUtils.getAuthoritiesFromJwtToken(jwt);

      // 5. Create auth object
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              username,
              null,
              authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

      // 6. Authenticate the user
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7, headerAuth.length());
    }
    return null;
  }
}
