package com.bolun.hotel.dao;

import com.bolun.hotel.connection.ConnectionManager;
import com.bolun.hotel.entity.UserDetail;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDetailDao implements Dao<Long, UserDetail> {

    private static final String INSERT_SQL = """
            INSERT INTO user_detail (contact_number, photo)
            VALUES (?, ?)
            """;

    private static final UserDetailDao INSTANCE = new UserDetailDao();

    @SneakyThrows
    @Override
    public UserDetail save(UserDetail userDetail) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, userDetail.getContactNumber());
            preparedStatement.setString(2, userDetail.getPhoto());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            userDetail.setId(resultSet.getLong("id"));

            return userDetail;
        }
    }

    @Override
    public void update(UserDetail entity) {

    }

    @Override
    public Optional<UserDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public static UserDetailDao getInstance() {
        return INSTANCE;
    }
}
