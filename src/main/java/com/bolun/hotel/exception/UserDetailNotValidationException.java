package com.bolun.hotel.exception;

import lombok.Getter;
import java.util.List;
import com.bolun.hotel.helper.validator.Error;

public class UserDetailNotValidationException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public UserDetailNotValidationException(List<Error> errors) {
        this.errors = errors;
    }
}
