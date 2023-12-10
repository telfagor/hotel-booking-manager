package com.bolun.hotel.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException(String message, Exception ex) {
        super(message, ex);
    }
}
