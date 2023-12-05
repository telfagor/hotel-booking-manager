package com.bolun.hotel.mapper.impl;

import com.bolun.hotel.dto.CreateOrderDto;
import com.bolun.hotel.entity.Order;
import com.bolun.hotel.mapper.helper.LocalDateTimeFormatter;
import com.bolun.hotel.mapper.Mapper;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateOrderDtoMapper implements Mapper<CreateOrderDto, Order> {

    private static final CreateOrderDtoMapper INSTANCE = new CreateOrderDtoMapper();

    @Override
    public Order mapFrom(CreateOrderDto object) {
        return Order.builder()
                .checkIn(LocalDateTimeFormatter.format(object.checkIn().concat(":00")))
                .checkOut(LocalDateTimeFormatter.format(object.checkOut().concat(":00")))
                .build();
    }

    public static CreateOrderDtoMapper getInstance() {
        return INSTANCE;
    }
}
