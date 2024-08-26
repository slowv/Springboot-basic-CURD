package com.slowv.youtuberef.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message) {
        super(message);
    }
}
