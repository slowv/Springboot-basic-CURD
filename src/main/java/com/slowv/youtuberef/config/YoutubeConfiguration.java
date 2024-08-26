package com.slowv.youtuberef.config;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class YoutubeConfiguration {

    @Value("integration.youtube.application-name")
    private String applicationName;

    @Getter
    @Value("integration.youtube.api-key")
    private String apiKey;

    @Bean
    public YouTube getYouTube() {
        return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), null)
                .setApplicationName(applicationName)
                .setYouTubeRequestInitializer(new YouTubeRequestInitializer(apiKey))
                .build();
    }
}
