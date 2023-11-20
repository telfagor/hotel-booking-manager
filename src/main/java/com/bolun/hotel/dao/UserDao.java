package com.bolun.hotel.dao;

import lombok.SneakyThrows;
import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.User;
import com.bolun.hotel.entity.UserDetail;
import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.connection.ConnectionManager;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String INSERT_SQL = """
            INSERT INTO "user"
            (first_name, last_name, email, user_password, role_id, gender_id, user_detail_id) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE "user"
            SET first_name = ?,
            last_name = ?,
            email = ?,
            user_password = ?,
            role_id = ?,
            gender_id = ?,
            user_detail_id = ? 
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT u.id AS user_id,
                   u.first_name AS first_name,
                   u.last_name AS last_name,
                   u.email AS email,
                   u.user_password AS user_password,
                   us.id AS user_detail_id,
                   us.contact_number AS contact_number,
                   us.photo AS user_photo,
                   g.id AS gender_id,
                   g.type AS gender,
                   r.id AS role_id,
                   r.user_role AS user_role
            FROM "user" u
            LEFT JOIN user_detail us
            ON u.user_detail_id = us.id
            JOIN gender g
            ON u.gender_id = g.id
            JOIN "role" r
            ON u.role_id = r.id
            WHERE u.id = ?;
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM "user"
            WHERE id = ?;
            """;

    @SneakyThrows
    @Override
    public User save(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setLong(5, user.getRole().ordinal() + 1L);
            preparedStatement.setLong(6, user.getGender().ordinal() + 1L);
            preparedStatement.setObject(7, user.getUserDetail() != null ? user.getUserDetail().getId() : null);
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong("id"));

            return user;
        }
    }

    @SneakyThrows
    @Override
    public void update(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setLong(4, user.getRole().ordinal() + 1L);
            preparedStatement.setLong(5, user.getGender().ordinal() + 1L);
            preparedStatement.setObject(6, user.getUserDetail().getId());
            preparedStatement.executeUpdate();
        }
    }

    @SneakyThrows
    @Override
    public Optional<User> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;

            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        }
    }

    @SneakyThrows
    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    private User buildUser(ResultSet resultSet) {
        return User.builder()
                .id(resultSet.getObject("user_id", Long.class))
                .firstName(resultSet.getObject("first_name", String.class))
                .lastName(resultSet.getObject("last_name", String.class))
                .email(resultSet.getObject("email", String.class))
                .password(resultSet.getObject("user_password", String.class))
                .role(Role.valueOf(resultSet.getObject("user_role", String.class)))
                .gender(Gender.valueOf(resultSet.getObject("gender", String.class)))
                .userDetail(getUserDetail(resultSet).orElse(null))
                .build();
    }

    @SneakyThrows
    private Optional<UserDetail> getUserDetail(ResultSet resultSet) {
        if (resultSet.getObject("user_detail_id", Long.class) != null) {
            return Optional.of(UserDetail.builder()
                    .contactNumber(resultSet.getObject("contact_number", String.class))
                    .photo(resultSet.getObject("user_photo", String.class))
                    .build());
        }

        return Optional.empty();
    }
}
