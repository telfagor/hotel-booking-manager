package com.bolun.hotel.service;

import com.bolun.hotel.dto.ReadApartmentDto;

import java.util.List;

public interface ApartmentService {

    List<ReadApartmentDto> findAll();
}
