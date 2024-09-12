package com.slowv.youtuberef.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slowv.youtuberef.security.jwt.TokenProvider;
import com.slowv.youtuberef.web.rest.error.MessageCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.slowv.youtuberef.config.SecurityConfiguration.PUBLIC_APIS;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter extends OncePerRequestFilter {
    static String AUTHORIZATION_HEADER = "Authorization";
    static String AUTHORIZATION_TOKEN = "access_token";
    TokenProvider tokenProvider;

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        final var path = request.getRequestURI();
        return PUBLIC_APIS.contains(path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } else {
            responseFailCredential(response, HttpStatus.UNAUTHORIZED);
        }
    }

    private void responseFailCredential(HttpServletResponse response, HttpStatus status) throws IOException {
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        new ObjectMapper()
                .writeValue(response.getOutputStream(), new MessageCode(String.valueOf(status.value()), status.getReasonPhrase()));
        response.flushBuffer();
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        String jwt = request.getParameter(AUTHORIZATION_TOKEN);
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }
}
