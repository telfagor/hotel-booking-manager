package com.bolun.hotel.dao.impl;

import lombok.SneakyThrows;
import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.Apartment;
import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.entity.enums.ApartmentType;
import com.bolun.hotel.entity.enums.ApartmentStatus;
import com.bolun.hotel.exception.DaoException;
import com.bolun.hotel.connection.ConnectionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApartmentDaoImpl implements ApartmentDao {

    private static final ApartmentDao INSTANCE = new ApartmentDaoImpl();

    private static final String STATUS = "status";
    private static final String TYPE = "apartment_type";

    private static final String FIND_ALL_SQL = """
            SELECT ap.id,
                   number_of_rooms,
                   number_of_seats,
                   price_per_hour,
                   photo,
                   ap_status AS status,
                   ap_type AS apartment_type
            FROM apartment ap JOIN apartment_status aps
            ON ap.apartment_status_id = aps.id
            JOIN apartment_type apt
            ON ap.apartment_type_id = apt.id;
            """;

    @Override
    public Apartment save(Apartment entity) {
        return null;
    }

    @Override
    public Boolean update(Apartment entity) {
        return null;
    }

    @Override
    public Optional<Apartment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Apartment> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Apartment> apartments = new ArrayList<>();
            while (resultSet.next()) {
                apartments.add(buildApartment(resultSet));
            }
            return apartments;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @SneakyThrows
    private Apartment buildApartment(ResultSet resultSet) {
        return Apartment.builder()
                .id(resultSet.getObject("id", Long.class))
                .numberOfRooms(resultSet.getObject("number_of_rooms", Integer.class))
                .numberOfSeats(resultSet.getObject("number_of_seats", Integer.class))
                .pricePerHour(resultSet.getObject("price_per_hour", BigDecimal.class))
                .photo(resultSet.getObject("photo", String.class))
                .status(ApartmentStatus.valueOf(resultSet.getObject("status", String.class)))
                .type(ApartmentType.valueOf(resultSet.getObject("apartment_type", String.class)))
                .build();
    }

    public static ApartmentDao getInstance() {
        return INSTANCE;
    }
}
