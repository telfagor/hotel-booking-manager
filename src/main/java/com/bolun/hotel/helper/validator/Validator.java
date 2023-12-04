package com.bolun.hotel.helper.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
