package com.slowv.youtuberef.security.jwt;

import com.slowv.youtuberef.config.filter.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTConfigurer implements SecurityConfigurer<DefaultSecurityFilterChain, HttpSecurity> {
    private final AuthenticationFilter authenticationFilter;

    @Override
    public void init(final HttpSecurity http) throws Exception {
    }

    @Override
    public void configure(HttpSecurity http) {
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
