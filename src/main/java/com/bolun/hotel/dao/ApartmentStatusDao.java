package com.bolun.hotel.dao;

import com.bolun.hotel.connection.ConnectionManager;
import com.bolun.hotel.entity.enums.ApartmentStatus;
import com.bolun.hotel.exception.DaoException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ApartmentStatusDao {

    private static final ApartmentStatusDao INSTANCE = new ApartmentStatusDao();

    private static final String FIND_ALL_SQL = """
            SELECT ap_status
            FROM apartment_status
            """;

    public List<ApartmentStatus> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<ApartmentStatus> apartmentStatusList = new ArrayList<>();
            while (resultSet.next()) {
                apartmentStatusList.add(buildApartment(resultSet));
            }

            return apartmentStatusList;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @SneakyThrows
    private ApartmentStatus buildApartment(ResultSet resultSet) {
        return ApartmentStatus.valueOf(resultSet.getObject("ap_status", String.class));
    }

    public static ApartmentStatusDao getInstance() {
        return  INSTANCE;
    }
}
