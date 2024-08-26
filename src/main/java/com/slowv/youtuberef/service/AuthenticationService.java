package com.slowv.youtuberef.service;

import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);
}
