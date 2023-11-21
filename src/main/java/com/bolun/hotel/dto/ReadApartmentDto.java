package com.bolun.hotel.dto;

import java.math.BigDecimal;

public record ReadApartmentDto(Long id,
                               Integer numberOfRooms,
                               Integer numberOfSeats,
                               BigDecimal pricePerHour,
                               String photo,
                               String apartment,
                               String type) {
    @Override
    public String toString() {
        return "Apartment id " +
                id + " number of rooms " +
                numberOfRooms + " number of seats " +
                numberOfSeats + " price per hour " +
                pricePerHour + " photo " +
                photo + " apartment " +
                apartment + " type " +
                type;
    }
}
