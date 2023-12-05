package com.bolun.hotel.service.impl;

import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.dao.OrderDao;
import com.bolun.hotel.dao.UserDao;
import com.bolun.hotel.dao.impl.ApartmentDaoImpl;
import com.bolun.hotel.dao.impl.OrderDaoImpl;
import com.bolun.hotel.dao.impl.UserDaoImpl;
import com.bolun.hotel.dto.CreateOrderDto;
import com.bolun.hotel.dto.ReadOrderDto;
import com.bolun.hotel.entity.Apartment;
import com.bolun.hotel.entity.Order;
import com.bolun.hotel.entity.User;
import com.bolun.hotel.exception.InvalidDateException;
import com.bolun.hotel.mapper.impl.CreateOrderDtoMapper;
import com.bolun.hotel.mapper.impl.CreateUserDtoMapper;
import com.bolun.hotel.mapper.impl.ReadOrderDtoMapper;
import com.bolun.hotel.mapper.impl.ReadUserDtoMapper;
import com.bolun.hotel.service.OrderService;
import com.bolun.hotel.validator.OrderValidatorImpl;
import com.bolun.hotel.validator.ValidationResult;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.bolun.hotel.entity.enums.ApartmentStatus.*;
import static com.bolun.hotel.entity.enums.OrderStatus.PENDING;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderServiceImpl implements OrderService {

    private static final OrderService INSTANCE = new OrderServiceImpl();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDaoImpl.getInstance();
    private final CreateOrderDtoMapper createMapper = CreateOrderDtoMapper.getInstance();
    private final ReadOrderDtoMapper readMapper = ReadOrderDtoMapper.getInstance();
    private final ReadUserDtoMapper userDtoMapper = ReadUserDtoMapper.getInstance();

    private final OrderValidatorImpl validator = OrderValidatorImpl.getInstance();

    public CreateOrderDto create(CreateOrderDto createOrderDto) {
        ValidationResult validationResult = validator.isValid(createOrderDto);

        if (!validationResult.isValid()) {
            throw new InvalidDateException(validationResult.getErrors());
        }

        Order order = createMapper.mapFrom(createOrderDto);
        User user = userDtoMapper.mapToEntity(createOrderDto.readUserDto());

        //This code creates problems
        /*apartmentDao.findById(Long.parseLong(createOrderDto.apartmentId()))
                .ifPresent(apartment -> {
                    apartment.setStatus(OCCUPIED);
                    order.setApartment(apartment);
                    apartmentDao.update(apartment);
                });*/

        //Sometimes this code work
        Optional<Apartment> maybeApartment = apartmentDao.findById(Long.parseLong(createOrderDto.apartmentId()));
        if (maybeApartment.isPresent()) {
            Apartment apartment = maybeApartment.get();
            apartment.setStatus(OCCUPIED);
            order.setApartment(apartment);
            apartmentDao.update(apartment);
        }

        order.setUser(user);
        order.setStatus(PENDING);


        orderDao.save(order);
        return createOrderDto;
    }

    @Override
    public List<ReadOrderDto> findAll() {
        return orderDao.findAll().stream()
                .map(readMapper::mapFrom)
                .toList();
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
