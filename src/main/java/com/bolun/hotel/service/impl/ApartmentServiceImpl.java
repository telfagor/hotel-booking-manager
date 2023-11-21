package com.bolun.hotel.service.impl;

import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.dao.impl.ApartmentDaoImpl;
import com.bolun.hotel.dto.ReadApartmentDto;
import com.bolun.hotel.service.ApartmentService;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApartmentServiceImpl implements ApartmentService {

    private static final ApartmentService INSTANCE = new ApartmentServiceImpl();
    private final ApartmentDao apartmentDao = ApartmentDaoImpl.getInstance();

    @Override
    public List<ReadApartmentDto> findAll() {
        return apartmentDao.findAll().stream()
                .map(apartment -> new ReadApartmentDto(
                                apartment.getId(),
                                apartment.getNumberOfRooms(),
                                apartment.getNumberOfSeats(),
                                apartment.getPricePerHour(),
                                apartment.getPhoto(),
                                apartment.getStatus().name(),
                                apartment.getType().name()
                        ))
                .toList();

    }

    public static ApartmentService getInstance() {
        return INSTANCE;
    }
}
