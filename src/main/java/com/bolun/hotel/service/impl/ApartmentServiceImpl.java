package com.bolun.hotel.service.impl;

import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.dao.OrderDao;
import com.bolun.hotel.dao.impl.ApartmentDaoImpl;
import com.bolun.hotel.dao.impl.OrderDaoImpl;
import com.bolun.hotel.dto.ReadApartmentDto;
import com.bolun.hotel.entity.Apartment;
import com.bolun.hotel.entity.Order;
import com.bolun.hotel.service.ApartmentService;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.bolun.hotel.entity.enums.ApartmentStatus.AVAILABLE;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApartmentServiceImpl implements ApartmentService {

    private static final ApartmentService INSTANCE = new ApartmentServiceImpl();
    private final ApartmentDao apartmentDao = ApartmentDaoImpl.getInstance();
    private final OrderDao orderDao = OrderDaoImpl.getInstance();

    @Override
    public List<ReadApartmentDto> findAll() {
        verifyTheApartmentStatus();

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

    private void verifyTheApartmentStatus() {
        List<Apartment> apartments = apartmentDao.findAll().stream()
                .filter(apartment -> "OCCUPIED".equals(apartment.getStatus().name()))
                .toList();

        for (Apartment apartment : apartments) {
            List<Order> orders = orderDao.findByApartmentId(apartment.getId());
            boolean isCheckOutDateTimeBeforeCurrent = true;

            for (Order order : orders) {
                LocalDateTime current = LocalDateTime.now();
                if (order.getCheckOut().isAfter(current) || order.getCheckOut().isEqual(current)) {
                    isCheckOutDateTimeBeforeCurrent = false;
                    break;
                }
            }

            if (isCheckOutDateTimeBeforeCurrent) {
                apartment.setStatus(AVAILABLE);
                apartmentDao.update(apartment);
            }
        }
    }

    public static ApartmentService getInstance() {
        return INSTANCE;
    }
}
