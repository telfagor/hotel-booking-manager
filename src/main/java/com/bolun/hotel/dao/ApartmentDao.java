package com.bolun.hotel.dao;

import com.bolun.hotel.entity.Apartment;

import java.util.List;

public interface ApartmentDao extends BaseDao<Long, Apartment> {

    List<Apartment> findAll();
}
