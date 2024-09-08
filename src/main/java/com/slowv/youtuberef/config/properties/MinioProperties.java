package com.slowv.youtuberef.config.properties;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(
        prefix = "integration.minio",
        ignoreUnknownFields = false
)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MinioProperties {
    String accessKey;
    String secretKey;
    String url;
}
