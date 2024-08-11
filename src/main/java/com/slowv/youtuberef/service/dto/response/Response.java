package com.slowv.youtuberef.service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.slowv.youtuberef.web.rest.error.ApiError;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    ApiError status;
    T data;

    public static <T> Response<T> ok(T data) {
        final ApiError status = new ApiError(HttpStatus.OK.value());
        return Response.<T>builder()
                .status(status)
                .data(data)
                .build();
    }

    public static <T> Response<T> created(T data) {
        final ApiError status = new ApiError(HttpStatus.CREATED.value());
        return Response.<T>builder()
                .status(status)
                .data(data)
                .build();
    }

    public static <T> Response<T> fail(String message, int code) {
        ApiError status = new ApiError(message, code);
        return Response.<T>builder().status(status).build();
    }

    public static <T> Response<T> fail(ApiError status) {
        return Response.<T>builder().status(status).build();
    }

    public static <T> Response<T> noContent() {
        ApiError status = new ApiError(HttpStatus.NO_CONTENT.value());
        return Response.<T>builder().status(status).build();
    }
}
