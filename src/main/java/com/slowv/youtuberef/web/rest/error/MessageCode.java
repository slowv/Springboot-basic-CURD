package com.slowv.youtuberef.web.rest.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class MessageCode implements Serializable {
    protected String code;
    protected String message;

    public MessageCode() {
    }

    public MessageCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public MessageCode(HttpStatus status) {
        this.code = String.valueOf(status.value());
        this.message = status.getReasonPhrase();
    }
}