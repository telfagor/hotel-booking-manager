package com.bolun.hotel.entity.enums;

public enum Role {

    USER("1"),
    ADMIN("2");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
