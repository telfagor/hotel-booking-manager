package com.bolun.hotel.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
