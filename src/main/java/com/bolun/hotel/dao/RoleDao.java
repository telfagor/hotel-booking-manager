package com.bolun.hotel.dao;

import com.bolun.hotel.connection.ConnectionManager;
import com.bolun.hotel.entity.enums.Role;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RoleDao {

    private static final RoleDao INSTANCE = new RoleDao();

    private static final String INSERT_SQL = """
            INSERT INTO "role" (user_role)
            VALUES (?)
            """;

    @SneakyThrows
    public Role save(Role role) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {
             preparedStatement.setString(1, role.name());
             preparedStatement.executeUpdate();
             return role;
        }
    }

    public void update(Role entity) {

    }

    public Optional<Role> findByRole(Role role) {
        return Optional.empty();
    }

    public boolean deleteByRole(Role role) {
        return false;
    }

    public static RoleDao getInstance() {
        return INSTANCE;

    }
}
