package com.example.as.service.config.interceptor;

import com.example.as.service.model.constant.AuthConstant;
import com.example.as.service.service.AuthenticationService;
import com.example.as.service.util.JwtUtils;
import com.example.common.core.annotation.IgnoreAuthentication;
import com.example.common.core.constant.RequestHeader;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.stream.Stream;

@Component
@Log4j2
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${auth.interceptor.ignoreAuthUser:false}")
    private boolean isIgnoreUserAuth;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("start interceptor pre");
//        if (isIgnoreUserAuth) {
//            log.warn("user auth interceptor is turned off, default user id 1 is assigned to request " +
//                    "attribute");
//            request.setAttribute(RequestHeader.USER_ID, "1");
//            return true;
//        }
//
//        log.info("user AuthInterceptor is running, request uri: {}", request.getRequestURI());
//
//        HandlerMethod hm;
//        try {
//            hm = (HandlerMethod) handler;
//        } catch (ClassCastException e) {
//            return HandlerInterceptor.super.preHandle(request, response, handler);
//        }
//        Method method = hm.getMethod();
//        log.info("AuthInterceptor: {}", method);
//        if (method.isAnnotationPresent(IgnoreAuthentication.class)) {
//            log.info("method {} is annotation with IgnoreAuthentication, so no authentication", method);
//            return true;
//        }
//        if (method.getDeclaringClass().isAnnotationPresent(IgnoreAuthentication.class)) {
//            log.info(
//                    "Class {} is annotation with IgnoreAuthentication, so no authentication",
//                    method.getDeclaringClass());
//            return true;
//        }
//        String username = authenticationService.authUser(request);
//        request.setAttribute(RequestHeader.USER_ID, username);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("start interceptor post");
    }

    public boolean shouldNotFilter(HttpServletRequest request) {
        return Stream.of(AuthConstant.AUTH_WHITELIST)
                .anyMatch(x -> new AntPathMatcher().match(x, request.getServletPath()));
    }
}
