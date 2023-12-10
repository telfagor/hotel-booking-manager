package com.bolun.hotel.service;

import com.bolun.hotel.dto.ReadOrderDto;
import com.bolun.hotel.dto.CreateOrderDto;

import java.util.List;

public interface OrderService {
    CreateOrderDto create(CreateOrderDto createOrderDto);

    List<ReadOrderDto> findAll();

    List<ReadOrderDto> findUserOrdersById(Long id);
}
