package com.bolun.hotel.exception;

import lombok.Getter;

import java.util.List;
import com.bolun.hotel.helper.validator.Error;

public class InvalidDateException extends RuntimeException {

    @Getter
    private final List<Error> errors;

    public InvalidDateException(List<Error> errors) {
        this.errors = errors;
    }
}
