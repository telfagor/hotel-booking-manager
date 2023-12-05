package com.bolun.hotel.mapper.impl;

import com.bolun.hotel.dto.ReadOrderDto;
import com.bolun.hotel.entity.Order;
import com.bolun.hotel.mapper.Mapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReadOrderDtoMapper implements Mapper<Order, ReadOrderDto> {

    private static final ReadOrderDtoMapper INSTANCE = new ReadOrderDtoMapper();

    @Override
    public ReadOrderDto mapFrom(Order object) {
        return ReadOrderDto.builder()
                .orderId(object.getId())
                .checkIn(object.getCheckIn())
                .checkOut(object.getCheckOut())
                .firstName(object.getUser().getFirstName())
                .lastName(object.getUser().getLastName())
                .email(object.getUser().getEmail())
                .gender(object.getUser().getGender())
                .status(object.getStatus())
                .build();
    }

    public static ReadOrderDtoMapper getInstance() {
        return INSTANCE;
    }
}
