package com.example.as.service.config.filter;

import com.example.as.service.model.constant.AuthConstant;
import com.example.as.service.model.entity.security.UserDetailsImpl;
import com.example.as.service.service.AuthenticationService;
import com.example.as.service.service.UserService;
import com.example.as.service.util.JwtUtils;
import com.example.common.core.exception.AccessTokenExpireException;
import com.example.common.core.exception.InvalidAccessTokenException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtUtils.parseJwt(request);
        Boolean isJwtValid = jwtUtils.validateJwtToken(jwt);
        if (jwt == null || !isJwtValid) {
            throw new InvalidAccessTokenException(jwt);
        }
        if (authenticationService.isTokenBlacklisted(jwt)) {
            throw new AccessTokenExpireException(jwt);
        }

        String username = jwtUtils.getUserNameFromJwtToken(jwt);
        if (username != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) userService.loadUserByUsername(username);
            userDetails.setJwt(jwt);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Stream.of(AuthConstant.AUTH_WHITELIST, AuthConstant.DOCUMENT_WHITELIST)
                .flatMap(Stream::of)
                .anyMatch(x -> new AntPathMatcher().match(x, request.getServletPath()));
    }
}
