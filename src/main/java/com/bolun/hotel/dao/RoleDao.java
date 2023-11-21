package com.bolun.hotel.dao;

import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.exception.DaoException;
import com.bolun.hotel.connection.ConnectionManager;

import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RoleDao {

    private static final String USER_ROLE = "user_role";
    private static final String ERROR_MESSAGE = "Error occurred in the findAll method!";
    private static final RoleDao INSTANCE = new RoleDao();

    private static final String FIND_ALL = """
            SELECT user_role
            FROM "role";
            """;

    public List<Role> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Role> roles = new ArrayList<>();
            while (resultSet.next()) {
                roles.add(Role.valueOf(resultSet.getString(USER_ROLE)));
            }

            return roles;
        } catch (SQLException ex) {
            throw new DaoException(ERROR_MESSAGE, ex);
        }
    }

    public static RoleDao getInstance() {
        return INSTANCE;
    }
}
