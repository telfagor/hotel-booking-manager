package com.bolun.hotel.dao.impl;

import com.bolun.hotel.dao.OrderDao;
import com.bolun.hotel.entity.Order;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderDaoImplTest {

    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    @Test
    void findAll() {
        List<Order> orders = orderDao.findAll();
        System.out.println();
        assertThat(orders).hasSize(1);
    }
}
