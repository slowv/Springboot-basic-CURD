package com.slowv.youtuberef.common.constants;

import com.slowv.youtuberef.web.rest.error.MessageCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConstant {
    public static final MessageCode SERVICE_ERROR = new MessageCode(HttpStatus.INTERNAL_SERVER_ERROR);
    public static final MessageCode BAD_REQUEST = new MessageCode(HttpStatus.BAD_REQUEST);
    public static final MessageCode FORBIDDEN = new MessageCode(HttpStatus.FORBIDDEN);
    public static final MessageCode UNAUTHORIZED = new MessageCode(HttpStatus.UNAUTHORIZED);

    public static final String SYSTEM = "system";
}
