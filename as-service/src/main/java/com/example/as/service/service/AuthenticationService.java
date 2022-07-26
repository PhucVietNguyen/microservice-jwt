package com.example.as.service.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    void blacklistJwt(String token);

    boolean isTokenBlacklisted(String token);

    String authUser(HttpServletRequest request);

    String authUser(String jwt);
}
