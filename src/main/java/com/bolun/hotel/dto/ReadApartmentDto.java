package com.bolun.hotel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;


@Data
@Value
@Builder
@AllArgsConstructor
public class ReadApartmentDto {
    Long id;
    Integer numberOfRooms;
    Integer numberOfSeats;
    BigDecimal pricePerHour;
    String photo;
    String status;
    String type;

    public String toString() {
        return  "id " + id +
                " number of rooms " + numberOfRooms +
                " number of seats " + numberOfSeats +
                " price per hour " + pricePerHour +
                " status " + status +
                " type " + type;
    }
}
