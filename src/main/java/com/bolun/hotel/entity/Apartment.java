package com.bolun.hotel.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.bolun.hotel.entity.enums.ApartmentType;
import com.bolun.hotel.entity.enums.ApartmentStatus;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Apartment {

    private Long id;
    private Integer numberOfRooms;
    private Integer numberOfSeats;
    private BigDecimal pricePerHour;
    private String photo;
    private ApartmentStatus status;
    private ApartmentType type;
}
