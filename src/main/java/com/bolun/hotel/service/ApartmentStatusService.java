package com.bolun.hotel.service;

import com.bolun.hotel.dao.ApartmentStatusDao;
import com.bolun.hotel.entity.enums.ApartmentStatus;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApartmentStatusService {

    private static final ApartmentStatusService INSTANCE = new ApartmentStatusService();
    private final ApartmentStatusDao apartmentStatusDao = ApartmentStatusDao.getInstance();

    public List<ApartmentStatus> findAll() {
        return apartmentStatusDao.findAll();
    }

    public static ApartmentStatusService getInstance() {
        return INSTANCE;
    }
}
