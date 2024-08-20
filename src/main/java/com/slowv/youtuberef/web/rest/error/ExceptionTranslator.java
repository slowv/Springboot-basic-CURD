package com.slowv.youtuberef.web.rest.error;

import com.slowv.youtuberef.common.AppConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionTranslator implements ResponseErrorHandler {

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        final Map<String, Object> errorResults = new HashMap<>();
        for (FieldError e : ex.getBindingResult().getFieldErrors()) {
            errorResults.put(e.getField(), e.getDefaultMessage());
        }
        final ErrorResponse response = new ErrorResponse(AppConstant.BAD_REQUEST.getCode(),
                "Validation exception", errorResults);
        return badRequest(response);
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

    }
}
