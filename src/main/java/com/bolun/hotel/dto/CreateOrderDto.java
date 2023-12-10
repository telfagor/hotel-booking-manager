package com.bolun.hotel.dto;

public record CreateOrderDto(ReadUserDto readUserDto,
                             String checkIn,
                             String checkOut,
                             String apartmentId) {
}
