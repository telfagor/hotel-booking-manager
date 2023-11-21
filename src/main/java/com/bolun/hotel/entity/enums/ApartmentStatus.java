package com.bolun.hotel.entity.enums;

public enum ApartmentStatus {

    AVAILABLE(1),
    OCCUPIED(2);

    private Integer value;

    ApartmentStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
