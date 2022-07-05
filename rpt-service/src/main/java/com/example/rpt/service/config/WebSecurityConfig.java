package com.example.rpt.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        // make sure we use stateless session; session won't be used to store user's state.
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // handle an authorized attempts
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .and()
        // Add a filter to validate the tokens with every request
        .addFilterAfter(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
        // authorization requests config
        .authorizeRequests()
        // allow all who are accessing "auth" service
        .antMatchers(HttpMethod.POST, "/api/auth/**")
        .permitAll()
        // must be an admin if trying to access admin area (authentication is also required here)
        .antMatchers("/api/admin/**")
        .hasRole("ADMIN")
        .antMatchers("/api/client/**")
        .hasAnyRole("ADMIN", "USER")
        // Any other request must be authenticated
        .anyRequest()
        .authenticated();
    return http.build();
  }
}
