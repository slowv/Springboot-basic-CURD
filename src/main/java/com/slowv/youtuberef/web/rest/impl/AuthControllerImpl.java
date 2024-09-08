package com.slowv.youtuberef.web.rest.impl;

import com.slowv.youtuberef.service.AuthenticationService;
import com.slowv.youtuberef.service.dto.AccountDto;
import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.request.RegisterAccountRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;
import com.slowv.youtuberef.service.dto.response.Response;
import com.slowv.youtuberef.web.rest.AuthController;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authenticationService;

    @Override
    public Response<LoginResponse> login(LoginRequest request) {
        return Response.ok(authenticationService.login(request));
    }

    @Override
    public Response<AccountDto> register(RegisterAccountRequest request) {
        return Response
                .created(authenticationService.register(request));
    }
}
