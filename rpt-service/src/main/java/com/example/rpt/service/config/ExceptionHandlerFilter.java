package com.example.rpt.service.config;

import com.example.common.core.enums.exception.CommonCoreErrorCode;
import com.example.common.core.exception.AccessTokenExpireException;
import com.example.common.core.exception.ErrorResponse;
import com.example.common.core.exception.InvalidAccessTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {
    try {
      filterChain.doFilter(httpServletRequest, httpServletResponse);
    } catch (InvalidAccessTokenException | AccessTokenExpireException e) {
      e.printStackTrace();
      ErrorResponse errorResponse =
          ErrorResponse.builder()
              .errorCode(e.getErrorCode().getServiceErrorCode())
              .errorMessage(e.getErrorCode().getDesc())
              .build();
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    } catch (AccessDeniedException e) {
      e.printStackTrace();
      ErrorResponse errorResponse =
          ErrorResponse.builder()
              .errorCode(CommonCoreErrorCode.INVALID_TOKEN.getServiceErrorCode())
              .errorMessage(CommonCoreErrorCode.INVALID_TOKEN.getDesc())
              .build();
      httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
      httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    } catch (Exception e) {
      e.printStackTrace();
      ErrorResponse errorResponse =
          ErrorResponse.builder()
              .errorCode(CommonCoreErrorCode.UNKNOWN_ERROR.getServiceErrorCode())
              .errorMessage(CommonCoreErrorCode.UNKNOWN_ERROR.getDesc())
              .build();
      httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
    }
  }

  public String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}
