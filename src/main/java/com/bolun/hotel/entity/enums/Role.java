package com.bolun.hotel.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Role {

    USER(1),
    ADMIN(2);

    private final Integer value;

    Role(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static Optional<Role> find(String name) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(name))
                .findFirst();
    }
}
