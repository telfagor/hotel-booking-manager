package com.bolun.hotel.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import com.bolun.hotel.entity.Apartment;
import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.entity.enums.ApartmentType;
import com.bolun.hotel.entity.enums.ApartmentStatus;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApartmentDaoImplTest {

    private final ApartmentDao apartmentDao = ApartmentDaoImpl.getInstance();
    private final List<Apartment> apartments = new ArrayList<>();

    @Test
    @DisplayName("should save apartment")
    void shouldSaveApartment() {
        Apartment apartment = createApartment();

        Apartment actualResult = apartmentDao.save(apartment);
        apartments.add(apartment);

        assertNotNull(actualResult.getId());
    }

    @Test
    @DisplayName("should update apartment")
    void shouldUpdateApartment() {
        Apartment apartment = createApartment();

        Apartment savedApartment = apartmentDao.save(apartment);
        apartments.add(savedApartment);
        savedApartment.setNumberOfRooms(10);
        boolean actualResult = apartmentDao.update(savedApartment);

        assertTrue(actualResult);
    }

    @Test
    @DisplayName("should find an apartment by id")
    void shouldFindApartmentById() {
        Apartment apartment = createApartment();

        Apartment savedApartment = apartmentDao.save(apartment);
        apartments.add(savedApartment);
        Optional<Apartment> actualResult = apartmentDao.findById(savedApartment.getId());

        assertThat(actualResult).contains(savedApartment);
    }

    @Test
    @DisplayName("should find all apartments")
    @Disabled
    void shouldFindAllApartments() {
        Apartment firstApartment = createApartment();
        Apartment secondApartment = createApartment();
        secondApartment.setPhoto("my_photo");

        apartmentDao.save(firstApartment);
        apartmentDao.save(secondApartment);
        apartments.add(firstApartment);
        apartments.add(secondApartment);
        List<Apartment> actualResult = apartmentDao.findAll();

        assertThat(actualResult).hasSize(2);
    }

    @Test
    @DisplayName("should delete by id")
    void shouldDeleteApartmentById() {
        Apartment apartment = createApartment();

        Apartment savedApartment = apartmentDao.save(apartment);
        boolean actualResult = apartmentDao.delete(savedApartment.getId());

        assertTrue(actualResult);
    }



    @AfterEach
    void clearDatabase() {
        for (Apartment apartment : apartments) {
            apartmentDao.delete(apartment.getId());
        }
    }

    private Apartment createApartment() {
        return Apartment.builder()
                .id(1L)
                .numberOfSeats(6)
                .numberOfRooms(3)
                .photo("apartments/apartment_1111.jpg")
                .pricePerHour(BigDecimal.valueOf(35.5))
                .status(ApartmentStatus.AVAILABLE)
                .type(ApartmentType.LUX)
                .build();
    }
}
