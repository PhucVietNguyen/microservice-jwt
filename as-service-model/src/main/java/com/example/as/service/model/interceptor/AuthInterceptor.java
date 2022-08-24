package com.example.as.service.model.interceptor;

import com.example.as.service.model.AsDubboService;
import com.example.common.core.annotation.IgnoreAuthentication;
import com.example.common.core.constant.RequestHeader;
import com.example.common.core.exception.AccessTokenExpireException;
import com.example.common.core.exception.ErrorResponse;
import com.example.common.core.exception.InvalidAccessTokenException;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@Log4j2
public class AuthInterceptor implements HandlerInterceptor {

  @DubboReference(version = "1.0.0")
  private AsDubboService asDubboService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // Set ignore check auth when need
    if (false) {
      log.warn(
          "user auth interceptor is turned off, default user id 1 is assigned to request "
              + "attribute");
      request.setAttribute(RequestHeader.USER_ID, "1");
      return true;
    }

    log.info("user AuthInterceptor is running, request uri: {}", request.getRequestURI());

    HandlerMethod hm;
    try {
      hm = (HandlerMethod) handler;
    } catch (ClassCastException e) {
      return HandlerInterceptor.super.preHandle(request, response, handler);
    }
    Method method = hm.getMethod();
    log.info("AuthInterceptor: {}", method);
    if (method.isAnnotationPresent(IgnoreAuthentication.class)) {
      log.info("method {} is annotatend with IgnoreAuthentication, so no authentication", method);
      return true;
    }
    if (method.getDeclaringClass().isAnnotationPresent(IgnoreAuthentication.class)) {
      log.info(
          "Class {} is annotatend with IgnoreAuthentication, so no authentication",
          method.getDeclaringClass());
      return true;
    }
    try {
      String userId = asDubboService.authCheck(request.getHeader("Authorization"));
      request.setAttribute(RequestHeader.USER_ID, userId);
    } catch (InvalidAccessTokenException e) {
      throw new InvalidAccessTokenException("token");
    } catch (AccessTokenExpireException e) {
      throw new AccessTokenExpireException("token");
    }
    return true;
  }
}
