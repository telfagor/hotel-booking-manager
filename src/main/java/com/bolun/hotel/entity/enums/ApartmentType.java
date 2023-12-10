package com.bolun.hotel.entity.enums;

public enum ApartmentType {

    NORMAL(1),
    LUX(2);

    private Integer value;

    ApartmentType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
