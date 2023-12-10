package com.bolun.hotel.dao.impl;

import lombok.NoArgsConstructor;
import com.bolun.hotel.dao.UserDetailDao;
import com.bolun.hotel.entity.UserDetail;
import com.bolun.hotel.exception.DaoException;
import com.bolun.hotel.connection.ConnectionManager;

import java.sql.*;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDetailDaoImpl implements UserDetailDao {
    private static final UserDetailDao INSTANCE = new UserDetailDaoImpl();

    private static final String INSERT_SQL = """
            INSERT INTO user_detail (id, contact_number, photo, birthdate, user_money)
            VALUES (?, ?, ?, ?, ?)
            """;

    private static final String UPDATE_SQL = """
            UPDATE user_detail
            SET contact_number = ?,
                photo = ?,
                birthdate = ?
            FROM user_detail
            WHERE id = ?;
            """;

    private static final String FIND_BY_ID_SQL = """
            SELECT id,
                   contact_number,
                   photo,
                   birthdate,
                   user_money
            FROM user_detail
            WHERE id = ?
            """;

    private static final String DELETE_BY_ID_SQL = """
            DELETE FROM user_detail
            WHERE id = ?
            """;

    @Override
    public UserDetail save(UserDetail userDetail) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, userDetail.getId());
            preparedStatement.setString(2, userDetail.getContactNumber());
            preparedStatement.setObject(3, userDetail.getPhoto());
            preparedStatement.setDate(4, Date.valueOf(userDetail.getBirthdate()));
            preparedStatement.setInt(5, userDetail.getMoney());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            userDetail.setId(resultSet.getLong("id"));

            return userDetail;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean update(UserDetail userDetail) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setLong(1, userDetail.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<UserDetail> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserDetail userDetail = null;

            while (resultSet.next()) {
                userDetail = buildUserDetail(resultSet);
            }

            return Optional.ofNullable(userDetail);
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    private UserDetail buildUserDetail(ResultSet resultSet) throws SQLException {
        return UserDetail.builder()
                .id(resultSet.getObject("id", Long.class))
                .contactNumber(resultSet.getObject("contact_number", String.class))
                .photo(resultSet.getObject("photo", String.class))
                .birthdate(resultSet.getObject("birthdate", Date.class).toLocalDate())
                .money(resultSet.getObject("user_money", Integer.class))
                .build();
    }

    @Override
    public Boolean delete(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public static UserDetailDao getInstance() {
        return INSTANCE;
    }
}
