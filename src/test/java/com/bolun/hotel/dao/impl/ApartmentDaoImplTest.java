package com.bolun.hotel.dao.impl;

import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.entity.Apartment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ApartmentDaoImplTest {

    private final ApartmentDao apartmentDao = ApartmentDaoImpl.getInstance();

    @Test
    void findAll() {
        List<Apartment> actualResult = apartmentDao.findAll();
        assertThat(actualResult).hasSize(5);
    }
}
