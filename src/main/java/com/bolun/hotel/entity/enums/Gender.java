package com.bolun.hotel.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Gender {

    MALE(1),
    FEMALE(2);

    private final Integer value;

    Gender(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Optional<Gender> find(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(name))
                .findFirst();
    }
}
