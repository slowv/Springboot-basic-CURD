package com.slowv.youtuberef.service.impl;

import com.slowv.youtuberef.common.utils.JwtUtil;
import com.slowv.youtuberef.config.properties.SecurityProperties;
import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.exception.AuthenticationException;
import com.slowv.youtuberef.integration.minio.MinioChannel;
import com.slowv.youtuberef.repository.AccountRepository;
import com.slowv.youtuberef.repository.RoleRepository;
import com.slowv.youtuberef.service.AuthenticationService;
import com.slowv.youtuberef.service.dto.AccountDto;
import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.request.RegisterAccountRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;
import com.slowv.youtuberef.service.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    // Other
    private final PasswordEncoder passwordEncoder;

    // Properties
    private final SecurityProperties securityProperties;

    // Channel
    private final MinioChannel minioChannel;

    // Mapper
    private final AccountMapper accountMapper;

    // Repository
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

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
