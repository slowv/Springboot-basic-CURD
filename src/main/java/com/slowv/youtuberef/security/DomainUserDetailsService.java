package com.slowv.youtuberef.security;

import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.entity.RoleEntity;
import com.slowv.youtuberef.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.debug("Authenticating {}", username);
        if (new EmailValidator().isValid(username, null)) {
            return accountRepository
                    .findByUsername(username)
                    .map(this::createSpringSecurityUser)
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " was not found in the database"));
        }
        final var lowercaseLogin = username.toLowerCase(Locale.ENGLISH);
        return accountRepository
                .findByUsername(lowercaseLogin)
                .map(this::createSpringSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database"));
    }

    private User createSpringSecurityUser(AccountEntity account) {

        final var grantedAuthorities = account
                .getRoles()
                .stream()
                .map(RoleEntity::getName)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(account.getUsername(), account.getPasswordHash(), grantedAuthorities);
    }
}
