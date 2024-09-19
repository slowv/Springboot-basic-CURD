package com.slowv.youtuberef.config;

import com.slowv.youtuberef.config.properties.CacheProperties;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.config.ClusterServersConfig;
import org.redisson.jcache.configuration.RedissonConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.config.Config;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import static com.slowv.youtuberef.repository.AccountRepository.ACCOUNT_BY_EMAIL_CACHE;

@Configuration
@EnableCaching
public class CacheConfiguration {
    @Autowired(required = false)
    private GitProperties gitProperties;
    @Autowired(required = false)
    private BuildProperties buildProperties;

    @Bean
    public javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration(CacheProperties properties) {
        final MutableConfiguration<Object, Object> jcacheConfig = new MutableConfiguration<>();

        final var redis = properties.getRedis();
        final var redisUri = URI.create(redis.getServer()[0]);
        final var config = new Config();
        if (redis.isCluster()) {
            final var clusterServersConfig = config
                    .useClusterServers()
                    .setMasterConnectionPoolSize(redis.getConnectionPoolSize())
                    .setMasterConnectionMinimumIdleSize(redis.getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(redis.getSubscriptionConnectionPoolSize())
                    .setRetryAttempts(3)
                    .addNodeAddress(redis.getServer());
            if (redisUri.getUserInfo() != null) {
                clusterServersConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        } else {
            final var singleServerConfig = config
                    .useSingleServer()
                    .setConnectionPoolSize(redis.getConnectionPoolSize())
                    .setConnectionMinimumIdleSize(redis.getConnectionMinimumIdleSize())
                    .setSubscriptionConnectionPoolSize(redis.getSubscriptionConnectionPoolSize())
                    .setRetryAttempts(3)
                    .setAddress(redis.getServer()[0]);
            if (redisUri.getUserInfo() != null) {
                singleServerConfig.setPassword(redisUri.getUserInfo().substring(redisUri.getUserInfo().indexOf(':') + 1));
            }
        }

        jcacheConfig.setStatisticsEnabled(true);
        jcacheConfig.setExpiryPolicyFactory(
                CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, redis.getExpiration()))
        );
        return RedissonConfiguration.fromInstance(Redisson.create(config), jcacheConfig);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer(javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration) {
        return cm -> {
            createCache(cm, ACCOUNT_BY_EMAIL_CACHE, jcacheConfiguration);
        };
    }

    private void createCache(
            javax.cache.CacheManager cm,
            String cacheName,
            javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration
    ) {
        final var cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
