package com.bolun.hotel.dao;

import lombok.SneakyThrows;
import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.connection.ConnectionManager;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class GenderDao {

    private static final GenderDao INSTANCE = new GenderDao();

    private static final String INSERT_SQL = """
            INSERT INTO gender (type)
            VALUES (?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE gender
            SET type = ?
            WHERE type = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT type
            FROM gender;
            """;

    private static final String DELETE_BY_TYPE = """
            DELETE FROM gender
            WHERE type = ?
            """;

    @SneakyThrows
    public Gender save(Gender gender) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
             preparedStatement.setString(1, gender.name());
             preparedStatement.executeUpdate();
             return gender;
        }
    }

    @SneakyThrows
    public void update(Gender gender, Gender newGender) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, newGender.name());
            preparedStatement.setString(2, gender.name());
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    public List<Gender> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Gender> genders = new ArrayList<>();
            while (resultSet.next()) {
                genders.add(Gender.valueOf(resultSet.getObject("type", String.class)));
            }

            return genders;
        }
    }


    @SneakyThrows
    public boolean deleteByGender(Gender gender) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_TYPE)) {
            preparedStatement.setString(1, gender.name());
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static GenderDao getInstance() {
        return INSTANCE;
    }
}
