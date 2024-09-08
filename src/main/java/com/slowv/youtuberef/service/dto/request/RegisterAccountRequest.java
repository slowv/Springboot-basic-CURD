package com.slowv.youtuberef.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterAccountRequest {
    @NotBlank
    String username;
    @NotBlank
    String password;
    MultipartFile avatar;
}
