package com.bolun.hotel.dto;

public record CreateOrderDto(Long userId,
                             String checkIn,
                             String checkOut,
                             String apartmentId) {
}
