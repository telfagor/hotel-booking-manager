package com.bolun.hotel.entity.enums;

public enum OrderStatus {

    PENDING(1),
    APPROVED(2),
    REJECTED(3);

    private final Integer value;

    OrderStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
