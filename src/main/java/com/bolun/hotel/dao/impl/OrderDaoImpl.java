package com.bolun.hotel.dao.impl;

import com.bolun.hotel.connection.ConnectionManager;
import com.bolun.hotel.dao.OrderDao;
import com.bolun.hotel.entity.Order;
import com.bolun.hotel.exception.DaoException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderDaoImpl implements OrderDao {

    private static final OrderDao INSTANCE = new OrderDaoImpl();

    private static final String ID = "id";
    private static final String CHECK_IN = "check_in";
    private static final String CHECK_OUT = "check_out";

    private static final String INSERT_SQL = """
            INSERT INTO "order" (check_in, check_out, user_id, order_status_id, apartment_id) 
            VALUES (?, ?, ?, ?, ?) 
            """;

    private static final String UPDATE_SQL = """
            UPDATE "order"
            SET check_in = ?,
                check_out = ?,
                user_id = ?,
                order_status_id = ?,
                apartment_id = ?
            WHERE id = ?
            """;

    private static final String FIND_BY_ID = """
            SELECT o.id,
                               check_in,
                               check_out,
                               user_id,
                               order_status_id,
                               apartment_id,
                               order_status_id,
                               apartment_id,
                               u.id,
                               u.first_name,
                               u.last_name,
                               u.email,
                               u.user_password,
                               us.id,
                               us.contact_number,
                               us.photo,
                               g.id,
                               g.gender_type,
                               r.id,
                               r.user_role
                        FROM "order" o JOIN order_status os
                        ON o.order_status_id = os.id JOIN apartment a
                        ON o.apartment_id = a.id JOIN "user" u
                        ON o.user_id = u.id JOIN gender g
                        ON u.gender_id = g.id JOIN "role" r
                        ON u.role_id = r.id LEFT JOIN user_detail us
                        ON u.user_detail_id = us.id
                        WHERE id = 1;
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM "order"
            WHERE id = ?
            """;


    @Override
    public Order save(Order order) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(order.getCheckIn()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getCheckOut()));
            preparedStatement.setLong(3, order.getUser().getId());
            preparedStatement.setLong(4, order.getStatus().getValue());
            preparedStatement.setLong(5, order.getApartment().getId());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            order.setId(resultSet.getLong(ID));

            return order;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean update(Order order) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setTimestamp(1, Timestamp.valueOf(order.getCheckIn()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(order.getCheckOut()));
            preparedStatement.setLong(3, order.getUser().getId());
            preparedStatement.setLong(4, order.getStatus().getValue());
            preparedStatement.setLong(5, order.getApartment().getId());
            preparedStatement.setLong(6, order.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            Order order = null;
            while (resultSet.next()) {
                order = buildOrder(resultSet);
            }

            return Optional.ofNullable(order);
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
    private Order buildOrder(ResultSet resultSet) {
        return Order.builder()
                .id(resultSet.getLong(ID))
                .checkIn(resultSet.getTimestamp(CHECK_IN).toLocalDateTime())
                .checkOut(resultSet.getTimestamp(CHECK_OUT).toLocalDateTime())
                .build();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
