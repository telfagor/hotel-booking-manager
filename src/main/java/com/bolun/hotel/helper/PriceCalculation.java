package com.bolun.hotel.helper;

import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.dao.UserDao;
import com.bolun.hotel.dao.impl.ApartmentDaoImpl;
import com.bolun.hotel.dao.impl.UserDaoImpl;
import com.bolun.hotel.dto.CreateOrderDto;
import com.bolun.hotel.entity.Apartment;
import com.bolun.hotel.entity.User;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@UtilityClass
public class PriceCalculation {

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDaoImpl.getInstance();

    //Tomcat Catalina face probleme
    public boolean isEnoughMoney(CreateOrderDto createOrderDto) {
        Optional<User> maybeUser = userDao.findById(createOrderDto.userId());
        Optional<Apartment> maybeApartment = apartmentDao.findById(Long.parseLong(createOrderDto.apartmentId()));
        boolean isEnoughMoney = false;

        if (maybeUser.isPresent() && maybeApartment.isPresent()) {
            User user = maybeUser.get();
            Apartment apartment = maybeApartment.get();

            LocalDateTime checkIn = LocalDateTimeFormatter.format(createOrderDto.checkIn().concat(":00"));
            LocalDateTime checkOut = LocalDateTimeFormatter.format(createOrderDto.checkOut().concat(":00"));
            long hours = ChronoUnit.HOURS.between(checkIn, checkOut);
            int userMoney = user.getUserDetail().getMoney();
            BigDecimal pricePerHour = apartment.getPricePerHour();

            isEnoughMoney = hours * pricePerHour.longValue() <= userMoney;
        }

        return isEnoughMoney;
    }
}
