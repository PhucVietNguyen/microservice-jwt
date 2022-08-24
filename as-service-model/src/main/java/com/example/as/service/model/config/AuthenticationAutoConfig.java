package com.example.as.service.model.config;

import com.example.as.service.model.interceptor.AuthInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.example.as.service.model.interceptor")
@Log4j2
public class AuthenticationAutoConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("AuthInterceptor is adding to the project");
        registry.addInterceptor(authInterceptor).addPathPatterns("/*/admin/**");
    }
}
