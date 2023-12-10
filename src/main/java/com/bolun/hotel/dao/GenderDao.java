package com.bolun.hotel.dao;

import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.connection.ConnectionManager;
import com.bolun.hotel.exception.DaoException;

import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class GenderDao {

    private static final String GENDER_TYPE = "gender_type";
    private static final String ERROR_MESSAGE = "The error occurred in the findAll method!";
    private static final GenderDao INSTANCE = new GenderDao();


    private static final String FIND_ALL_SQL = """
            SELECT gender_type
            FROM gender;
            """;


    public List<Gender> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Gender> genders = new ArrayList<>();
            while (resultSet.next()) {
                genders.add(Gender.valueOf(resultSet.getString(GENDER_TYPE)));
            }

            return genders;
        } catch (SQLException ex) {
            throw new DaoException(ERROR_MESSAGE, ex);
        }
    }

    public static GenderDao getInstance() {
        return INSTANCE;
    }
}
