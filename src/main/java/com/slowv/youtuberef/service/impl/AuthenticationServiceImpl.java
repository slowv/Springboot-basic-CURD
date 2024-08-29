package com.slowv.youtuberef.service.impl;

import com.slowv.youtuberef.common.utils.JwtUtil;
import com.slowv.youtuberef.config.SecurityProperties;
import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.exception.AuthenticationException;
import com.slowv.youtuberef.repository.AccountRepository;
import com.slowv.youtuberef.service.AuthenticationService;
import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;

    @Override
    public LoginResponse login(LoginRequest request) {
        final var account = accountRepository.findByUsername(request.username())
                .orElseThrow(() -> new AuthenticationException("Invalid username or password"));

        if (ObjectUtils.isEmpty(request.password()) || !passwordEncoder.matches(request.password(), account.getPasswordHash())) {
            throw new AuthenticationException("Invalid username or password");
        }

        final var token = JwtUtil.generateJwtToken(account, securityProperties.getJwtSecret(), securityProperties.getJwtExpiration());
        return new LoginResponse(token);
    }
}
