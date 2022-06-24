package com.example.ext.service.config;

import com.example.ext.service.enums.error.CommonCoreErrorCode;
import com.example.ext.service.exception.AccessTokenExpireException;
import com.example.ext.service.exception.InvalidAccessTokenException;
import com.example.ext.service.model.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
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
