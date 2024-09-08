package com.slowv.youtuberef.service;

import com.slowv.youtuberef.service.dto.AccountDto;
import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.request.RegisterAccountRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;

public interface AuthenticationService {
    LoginResponse login(LoginRequest request);

    AccountDto register(RegisterAccountRequest request);
}
