package com.bolun.hotel.dto;

import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.entity.enums.OrderStatus;
import lombok.Value;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Value
@Builder
public class ReadOrderDto {
    Long orderId;
    LocalDateTime checkIn;
    LocalDateTime checkOut;
    String firstName;
    String lastName;
    String email;
    Gender gender;
    OrderStatus status;

    @Override
    public String toString() {
        return "id: \t" + orderId +
                " check in: \t" + checkIn.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                " check out: \t" + checkOut.format(DateTimeFormatter.ofPattern("yyyy-MM-ss HH:mm:ss")) +
                " first name: \t" + firstName +
                " last name: \t" + lastName +
                " email: \t" + email +
                " gender: \t" + gender +
                " status: \t" + status;
    }
}
