package com.bolun.hotel.dao;

import com.bolun.hotel.entity.Order;

import java.util.List;

public interface OrderDao extends BaseDao<Long, Order> {

    List<Order> findByApartmentId(Long id);
}