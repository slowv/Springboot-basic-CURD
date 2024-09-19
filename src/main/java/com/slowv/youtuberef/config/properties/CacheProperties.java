package com.slowv.youtuberef.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(
        prefix = "cache",
        ignoreUnknownFields = false
)
public class CacheProperties {
    private final Redis redis = new Redis();

    @Data
    public static class Redis {
        private String[] server;
        private int expiration;
        private boolean cluster;
        private int connectionPoolSize;
        private int connectionMinimumIdleSize;
        private int subscriptionConnectionPoolSize;
        private int subscriptionConnectionMinimumIdleSize;

        public Redis() {
            this.server = YubutuDefault.Redis.server;
            this.expiration = YubutuDefault.Redis.expiration;
            this.cluster = YubutuDefault.Redis.cluster;
            this.connectionPoolSize = YubutuDefault.Redis.connectionPoolSize;
            this.connectionMinimumIdleSize = YubutuDefault.Redis.connectionMinimumIdleSize;
            this.subscriptionConnectionPoolSize = YubutuDefault.Redis.subscriptionConnectionPoolSize;
            this.subscriptionConnectionMinimumIdleSize = YubutuDefault.Redis.subscriptionConnectionMinimumIdleSize;
        }
    }
}
