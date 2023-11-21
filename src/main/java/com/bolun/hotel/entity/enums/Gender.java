package com.bolun.hotel.entity.enums;

public enum Gender {

    MALE("1"),
    FEMALE("2");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
