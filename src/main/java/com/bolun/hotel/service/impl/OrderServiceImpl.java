package com.bolun.hotel.service.impl;

import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.dao.OrderDao;
import com.bolun.hotel.dao.UserDao;
import com.bolun.hotel.dao.impl.ApartmentDaoImpl;
import com.bolun.hotel.dao.impl.OrderDaoImpl;
import com.bolun.hotel.dao.impl.UserDaoImpl;
import com.bolun.hotel.dto.CreateOrderDto;
import com.bolun.hotel.entity.Order;
import com.bolun.hotel.exception.InvalidDateException;
import com.bolun.hotel.mapper.impl.CreateOrderDtoMapper;
import com.bolun.hotel.service.OrderService;
import com.bolun.hotel.validator.OrderValidatorImpl;
import com.bolun.hotel.validator.ValidationResult;
import lombok.NoArgsConstructor;

import static com.bolun.hotel.entity.enums.OrderStatus.PENDING;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderServiceImpl implements OrderService {

    private static final OrderService INSTANCE = new OrderServiceImpl();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDaoImpl.getInstance();
    private final CreateOrderDtoMapper mapper = CreateOrderDtoMapper.getInstance();

    private final OrderValidatorImpl validator = OrderValidatorImpl.getInstance();

    public CreateOrderDto create(CreateOrderDto createOrderDto) {
        ValidationResult validationResult = validator.isValid(createOrderDto);

        if (!validationResult.isValid()) {
            throw new InvalidDateException(validationResult.getErrors());
        }

        Order order = mapper.mapFrom(createOrderDto);

        apartmentDao.findById(Long.parseLong(createOrderDto.apartmentId()))
                .ifPresent(order::setApartment);

        userDao.findById(createOrderDto.userId()).ifPresent(order::setUser);
        order.setStatus(PENDING);

        orderDao.save(order);
        return createOrderDto;
    }


    public static OrderService getInstance() {
        return INSTANCE;
    }
}
