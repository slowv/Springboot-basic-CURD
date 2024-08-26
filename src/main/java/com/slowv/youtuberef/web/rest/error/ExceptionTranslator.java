package com.slowv.youtuberef.web.rest.error;

import com.slowv.youtuberef.common.constants.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    private ResponseEntity<ErrorResponse> badRequest(ErrorResponse result) {
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> internalServerError(ErrorResponse result) {
        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> notFound(ErrorResponse result) {
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> forbidden(ErrorResponse result) {
        return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put("service", ex.getMessage());
        return internalServerError(
                new ErrorResponse(AppConstant.SERVICE_ERROR.getCode(),
                AppConstant.SERVICE_ERROR.getMessage(), map)
        );
    }
}
