package com.slowv.youtuberef.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;


@Configuration
@EnableMethodSecurity(securedEnabled = true)  // Kích hoạt @Secured
@EnableWebSocketSecurity
public class WebSocketSecurityConfiguration {


    @Bean
    public AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages.nullDestMatcher().authenticated()
                .simpDestMatchers("/admin/**").hasRole("ADMIN")
                .anyMessage()
                .authenticated();
        return messages.build();
    }
}
