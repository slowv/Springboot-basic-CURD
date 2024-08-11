package com.slowv.youtuberef.web.rest.error;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode()
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiValidationError<T> implements ApiSubError {
    String object;

    String field;

    T rejectedValue;

    String message;

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
