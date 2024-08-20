package com.slowv.youtuberef.web.rest.error;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}