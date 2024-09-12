package com.slowv.youtuberef.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apiguardian.api.API;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.apiguardian.api.API.Status.STABLE;

@API(status = STABLE)
@Component
public class SecurityProblemSupport implements AuthenticationEntryPoint, AccessDeniedHandler {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException) throws IOException, ServletException {
        SecurityUtils.responseFailCredential(response, HttpStatus.UNAUTHORIZED, authException.getLocalizedMessage());
    }

    @Override
    public void handle(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) throws IOException, ServletException {
        SecurityUtils.responseFailCredential(response, HttpStatus.FORBIDDEN, accessDeniedException.getLocalizedMessage());
    }
}
