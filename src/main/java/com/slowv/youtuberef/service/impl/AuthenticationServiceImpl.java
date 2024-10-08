package com.slowv.youtuberef.service.impl;

import com.slowv.youtuberef.config.properties.SecurityProperties;
import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.integration.minio.MinioChannel;
import com.slowv.youtuberef.repository.AccountRepository;
import com.slowv.youtuberef.repository.RoleRepository;
import com.slowv.youtuberef.security.jwt.TokenProvider;
import com.slowv.youtuberef.service.AuthenticationService;
import com.slowv.youtuberef.service.dto.AccountDto;
import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.request.RegisterAccountRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;
import com.slowv.youtuberef.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    // Other
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // Channel
    private final MinioChannel minioChannel;

    // Mapper
    private final AccountMapper accountMapper;

    // Repository
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    @Override
    public LoginResponse login(LoginRequest request) {
        final var authenticationToken = new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        );
        final var authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponse(tokenProvider.createToken(authentication, request.rememberMe()));
    }

    @Override
    public AccountDto register(RegisterAccountRequest request) {
        final var account = new AccountEntity();
        account.setUsername(request.getUsername());
        account.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        account.setUuid(UUID.randomUUID().toString());
        account.setRoles(roleRepository.findAll());
        account.setAvatar(minioChannel.upload(request.getAvatar()));
        return accountMapper.toDto(accountRepository.save(account));
    }
}
