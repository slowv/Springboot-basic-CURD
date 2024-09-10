package com.slowv.youtuberef.web.rest.impl;

import com.slowv.youtuberef.service.AuthenticationService;
import com.slowv.youtuberef.service.dto.AccountDto;
import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.request.RegisterAccountRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;
import com.slowv.youtuberef.service.dto.response.Response;
import com.slowv.youtuberef.web.rest.AuthController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<Response<LoginResponse>> login(LoginRequest request) {
        final var loginResponse = authenticationService.login(request);
        final var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token());
        return new ResponseEntity<>(Response.ok(loginResponse), httpHeaders, HttpStatus.OK);
    }

    @Override
    public Response<AccountDto> register(RegisterAccountRequest request) {
        return Response
                .created(authenticationService.register(request));
    }
}
