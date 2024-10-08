package com.slowv.youtuberef.config;

import com.slowv.youtuberef.config.properties.SecurityProperties;
import com.slowv.youtuberef.security.SecurityProblemSupport;
import com.slowv.youtuberef.security.jwt.JWTConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    final JWTConfigurer jwtConfigurer;
    final SecurityProblemSupport problemSupport;
    final SecurityProperties securityProperties;
    final UserDetailsService userDetailsService;

    public static final List<String> PUBLIC_APIS = List.of(
            "/_api/v1/auth/login",
            "/_api/v1/auth/register",
            "/yubutu/ws/**"
    );

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(apiPublic(mvc)).permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .rememberMe(
                        httpSecurityRememberMeConfigurer ->
                                httpSecurityRememberMeConfigurer.rememberMeParameter("remember-me")
                                        .tokenValiditySeconds((int) securityProperties.getRememberMeExpiration())
                                        .userDetailsService(userDetailsService)
                )
                .exceptionHandling(
                        httpSecurityExceptionHandlingConfigurer ->
                                httpSecurityExceptionHandlingConfigurer
                                        .accessDeniedHandler(problemSupport)
                                        .authenticationEntryPoint(problemSupport)
                )
                .headers(
                        headersConfigurer ->
                                headersConfigurer
                                        .referrerPolicy(
                                                referrer ->
                                                        referrer.policy(
                                                                ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN
                                                        )
                                        )
                                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                                        .httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable)
                )
                .apply(jwtConfigurer);
        return http.build();
    }

    public RequestMatcher[] apiPublic(MvcRequestMatcher.Builder mvc) {
        return PUBLIC_APIS.stream()
                .map(mvc::pattern)
                .toArray(RequestMatcher[]::new);
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspect) {
        return new MvcRequestMatcher.Builder(introspect);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Collections.singletonList("Content-Disposition"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
