package com.bolun.hotel.dao.impl;

import lombok.SneakyThrows;
import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.Apartment;
import com.bolun.hotel.dao.ApartmentDao;
import com.bolun.hotel.exception.DaoException;
import com.bolun.hotel.entity.enums.ApartmentType;
import com.bolun.hotel.entity.enums.ApartmentStatus;
import com.bolun.hotel.connection.ConnectionManager;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApartmentDaoImpl implements ApartmentDao {

    private static final ApartmentDao INSTANCE = new ApartmentDaoImpl();
    private static final String ID = "id";
    private static final String NUMBER_OF_ROOMS = "number_of_rooms";
    private static final String NUMBER_OF_SEATS = "number_of_seats";
    private static final String PRICE_PER_HOUR = "price_per_hour";
    private static final String PHOTO = "photo";
    private static final String STATUS = "status";
    private static final String TYPE = "apartment_type";

    private static final String INSERT_SQL = """
            INSERT INTO 
            apartment (number_of_rooms, number_of_seats, price_per_hour, photo, apartment_status_id, apartment_type_id)
            VALUES (?, ?, ?, ?, ?, ?) 
            """;

    private static final String UPDATE_SQL = """
            UPDATE apartment
            SET number_of_rooms = ?,
                number_of_seats = ?,
                price_per_hour = ?,
                photo = ?,
                apartment_status_id = ?,
                apartment_type_id = ?
            WHERE id = ?
            """;

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
            ON ap.apartment_type_id = apt.id
            """;

    private static final String FIND_BY_ID = FIND_ALL_SQL + " WHERE ap.id = ?";

    private static final String DELETE_BY_ID = """
            DELETE FROM apartment
            WHERE id = ?
            """;



    @Override
    public Apartment save(Apartment apartment) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, apartment.getNumberOfRooms());
            preparedStatement.setInt(2, apartment.getNumberOfSeats());
            preparedStatement.setBigDecimal(3, apartment.getPricePerHour());
            preparedStatement.setString(4, apartment.getPhoto());
            preparedStatement.setInt(5, apartment.getStatus().getValue());
            preparedStatement.setInt(6, apartment.getType().getValue());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            apartment.setId(resultSet.getLong(ID));

            return apartment;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean update(Apartment apartment) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setInt(1, apartment.getNumberOfRooms());
            preparedStatement.setInt(2, apartment.getNumberOfSeats());
            preparedStatement.setBigDecimal(3, apartment.getPricePerHour());
            preparedStatement.setObject(4, apartment.getPhoto());
            preparedStatement.setInt(5, apartment.getStatus().getValue());
            preparedStatement.setInt(6, apartment.getType().getValue());
            preparedStatement.setLong(7, apartment.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<Apartment> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Apartment apartment = null;
            while (resultSet.next()) {
                apartment = buildApartment(resultSet);
            }

            return Optional.ofNullable(apartment);
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
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
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @SneakyThrows
    private Apartment buildApartment(ResultSet resultSet) {
        return Apartment.builder()
                .id(resultSet.getObject(ID, Long.class))
                .numberOfRooms(resultSet.getObject(NUMBER_OF_ROOMS, Integer.class))
                .numberOfSeats(resultSet.getObject(NUMBER_OF_SEATS, Integer.class))
                .pricePerHour(resultSet.getObject(PRICE_PER_HOUR, BigDecimal.class))
                .photo(resultSet.getObject(PHOTO, String.class))
                .status(ApartmentStatus.valueOf(resultSet.getObject(STATUS, String.class)))
                .type(ApartmentType.valueOf(resultSet.getObject(TYPE, String.class)))
                .build();
    }

    public static ApartmentDao getInstance() {
        return INSTANCE;
    }
}
