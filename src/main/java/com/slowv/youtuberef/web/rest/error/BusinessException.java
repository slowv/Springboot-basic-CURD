package com.slowv.youtuberef.web.rest.error;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
public class BusinessException extends RuntimeException {
    private final String errorCode;
    private final String message;

    /**
     * Construct a new instance of {@code RestClientException} with the given message and
     * exception.
     *
     * @param msg the message
     */
    public BusinessException(String msg) {
        super(msg);
        this.errorCode = Strings.EMPTY;
        this.message = msg;
    }

    /**
     * Construct a new instance of {@code RestClientException} with the given message and
     * exception.
     *
     * @param errorCode the message
     * @param msg       the message
     * @param ex        the exception
     */
    public BusinessException(String errorCode, String msg, Throwable ex) {
        super(msg, ex);
        this.errorCode = errorCode;
        this.message = msg;
    }

    /**
     * Construct a new instance of {@code RestClientException} with the given message and
     * exception.
     *
     * @param errorCode the message
     * @param msg       the message
     */
    public BusinessException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.message = msg;
    }
}
