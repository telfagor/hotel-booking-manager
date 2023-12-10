package com.bolun.hotel.validator;

import lombok.Getter;

import java.util.List;
import java.util.ArrayList;

public class ValidationResult {

    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
