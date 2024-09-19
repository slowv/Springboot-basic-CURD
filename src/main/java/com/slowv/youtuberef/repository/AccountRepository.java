package com.slowv.youtuberef.repository;

import com.slowv.youtuberef.entity.AccountEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
    String ACCOUNT_BY_EMAIL_CACHE = "accountByEmail";

    @Cacheable(ACCOUNT_BY_EMAIL_CACHE)
    Optional<AccountEntity> findByUsername(String username);
}
