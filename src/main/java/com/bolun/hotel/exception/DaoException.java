package com.bolun.hotel.exception;

public class DaoException extends RuntimeException {

    public DaoException(String message, Throwable ex) {
        super(message);
    }
}
