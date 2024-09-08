package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.service.dto.AccountDto;
import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.request.RegisterAccountRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;
import com.slowv.youtuberef.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/_api/v1/auth")
public interface AuthController {

    @PostMapping("/login")
    Response<LoginResponse> login(@Valid @RequestBody LoginRequest request);

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Response<AccountDto> register(@Valid RegisterAccountRequest request);
}
