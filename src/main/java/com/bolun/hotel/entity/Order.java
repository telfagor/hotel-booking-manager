package com.bolun.hotel.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.bolun.hotel.entity.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private User user;
    private OrderStatus status;
    private Apartment apartment;
}
