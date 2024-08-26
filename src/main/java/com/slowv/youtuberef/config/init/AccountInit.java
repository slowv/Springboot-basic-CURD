package com.slowv.youtuberef.config.init;

import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.entity.RoleEntity;
import com.slowv.youtuberef.entity.enums.ERole;
import com.slowv.youtuberef.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountInit implements CommandLineRunner {
    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.count() > 0) {
            return;
        }

        final var account = AccountEntity.builder()
                .username("quocviet.hn98@gmail.com")
                .passwordHash(this.passwordEncoder.encode("viet1998"))
                .uuid(UUID.randomUUID().toString())
                .roles(List.of(
                        RoleEntity.builder()
                                .name(ERole.ADMIN)
                                .build(),
                        RoleEntity.builder()
                                .name(ERole.USER)
                                .build()
                ))
                .build();
        this.accountRepository.save(account);
    }
}
