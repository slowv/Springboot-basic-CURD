package com.slowv.youtuberef.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.slowv.youtuberef.common.utils.JwtUtil;
import com.slowv.youtuberef.config.SecurityProperties;
import com.slowv.youtuberef.entity.AccountEntity;
import com.slowv.youtuberef.entity.RoleEntity;
import com.slowv.youtuberef.repository.AccountRepository;
import com.slowv.youtuberef.web.rest.error.MessageCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter extends OncePerRequestFilter {
    SecurityProperties securityProperties;
    AccountRepository accountRepository;

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/_api/v1/auth/login".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            final var authentication = getAuthentication(request, response);
            if (!ObjectUtils.isEmpty(authentication)) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            } else {
                responseFailCredential(response, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final var authorization = request.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authentication = null;

        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            final var token = authorization.substring(7);
            try {
                JwtUtil.validateJwtToken(token, securityProperties.getJwtSecret());
            } catch (Exception ex) {
                responseFailCredential(response, HttpStatus.UNAUTHORIZED);
                return null;
            }

            final var uuid = JwtUtil.getUserUuidFromJwtToken(token, securityProperties.getJwtSecret());

            final var account = this.accountRepository.findByUuid(uuid)
                    .orElseThrow(EntityNotFoundException::new);

            authentication = new UsernamePasswordAuthenticationToken(account, null, buildAuthorities(account));

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }

        return authentication;
    }

    private void responseFailCredential(HttpServletResponse response, HttpStatus status) throws IOException {
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        new ObjectMapper()
                .writeValue(response.getOutputStream(), new MessageCode(String.valueOf(status.value()), status.getReasonPhrase()));
        response.flushBuffer();
    }

    private List<? extends GrantedAuthority> buildAuthorities(AccountEntity user) {
        return user.getRoles().stream()
                .map(RoleEntity::getName)
                .map(Enum::name)
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }
}
