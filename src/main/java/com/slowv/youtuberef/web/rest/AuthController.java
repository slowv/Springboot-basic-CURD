package com.slowv.youtuberef.web.rest;

import com.slowv.youtuberef.service.dto.request.LoginRequest;
import com.slowv.youtuberef.service.dto.response.LoginResponse;
import com.slowv.youtuberef.service.dto.response.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/_api/v1/auth")
public interface AuthController {

    @PostMapping("/login")
    Response<LoginResponse> login(@Valid @RequestBody LoginRequest request);
}
