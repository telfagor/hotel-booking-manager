package com.bolun.hotel.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}
