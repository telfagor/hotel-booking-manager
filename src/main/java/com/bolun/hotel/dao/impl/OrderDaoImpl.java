package com.bolun.hotel.dao.impl;

import com.bolun.hotel.entity.Apartment;
import com.bolun.hotel.entity.User;
import com.bolun.hotel.entity.UserDetail;
import com.bolun.hotel.entity.enums.*;
import lombok.SneakyThrows;
import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.Order;
import com.bolun.hotel.dao.OrderDao;
import com.bolun.hotel.exception.DaoException;
import com.bolun.hotel.connection.ConnectionManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderDaoImpl implements OrderDao {

    private static final OrderDao INSTANCE = new OrderDaoImpl();

    private static final String ID = "id";
    private static final String CHECK_IN = "check_in";
    private static final String CHECK_OUT = "check_out";
    private static final String USER_ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_ROLE = "user_role";
    private static final String GENDER = "gender_type";
    private static final String USER_DETAIL_ID = "user_detail_id";
    private static final String CONTACT_NUMBER = "contact_number";
    private static final String USER_PHOTO = "photo";
    private static final String BIRTHDATE = "birthdate";
    private static final String MONEY = "money";
    private static final String NUMBER_OF_ROOMS = "number_of_rooms";
    private static final String NUMBER_OF_SEATS = "number_of_seats";
    private static final String PRICE_PER_HOUR = "price_per_hour";
    private static final String PHOTO = "photo";
    private static final String STATUS = "ap_status";
    private static final String TYPE = "ap_type";

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

    private static final String FIND_ALL_SQL = """
            SELECT o.id,
                   o.check_in,
                   o.check_out,
                   o.user_id,
                   o.order_status_id,
                   o.apartment_id,
                   os.status,           
                   u.id,
                   u.first_name,
                   u.last_name,
                   u.email,
                   u.user_password,
                   u.role_id,
                   u.gender_id,
                   u.user_detail_id,
                   us.id,
                   us.contact_number,
                   us.photo,
                   us.birthdate,
                   us.money,
                   g.id,
                   g.gender_type,
                   r.id,
                   r.user_role,
                   a.id,
                   a.number_of_rooms,
                   a.number_of_seats,
                   a.price_per_hour,
                   a.photo,
                   ap_status AS status,
                   ap_type AS apartment_type,
                   aps.id,
                   aps.ap_status,
                   ap.id,
                   ap.ap_type
            FROM "order" o JOIN order_status os
            ON o.order_status_id = os.id JOIN apartment a
            ON o.apartment_id = a.id JOIN apartment_status aps
            ON a.apartment_status_id = aps.id JOIN apartment_type ap
            ON a.apartment_type_id = ap.id JOIN "user" u
            ON o.user_id = u.id JOIN gender g
            ON u.gender_id = g.id JOIN "role" r
            ON u.role_id = r.id LEFT JOIN user_detail us
            ON u.user_detail_id = us.id
            """;

    private static final String FIND_BY_ID = FIND_ALL_SQL + " WHERE o.id = ?";
    private static final String FIND_ORDERS_BY_APARTMENT_ID = FIND_ALL_SQL + " WHERE a.id = ?";
            /*SELECT o.id,
                   check_in,
                   check_out,
                   user_id,
                   order_status_id,
                   apartment_id,
                   order_status_id,
                   apartment_id
            FROM "order" o JOIN apartment ap
            ON o.apartment_id = ap.id
            WHERE ap.id = ?*/
                               

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
    public List<Order> findByApartmentId(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ORDERS_BY_APARTMENT_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }

            return orders;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<Order> findAll() {
        try (Connection connection = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Order> orders = new ArrayList<>();
            while (resultSet.next()) {
                orders.add(buildOrder(resultSet));
            }

            return orders;
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
                .user(buildUser(resultSet))
                .apartment(buildApartment(resultSet))
                .status(OrderStatus.valueOf(resultSet.getObject("status", String.class)))
                .build();
    }

    @SneakyThrows
    private User buildUser(ResultSet resultSet) {
        return User.builder()
                .id(resultSet.getObject(USER_ID, Long.class))
                .firstName(resultSet.getObject(FIRST_NAME, String.class))
                .lastName(resultSet.getObject(LAST_NAME, String.class))
                .email(resultSet.getObject(EMAIL, String.class))
                .password(resultSet.getObject(USER_PASSWORD, String.class))
                .role(Role.valueOf(resultSet.getObject(USER_ROLE, String.class)))
                .gender(Gender.valueOf(resultSet.getObject(GENDER, String.class)))
                .userDetail(getUserDetail(resultSet).orElse(null))
                .build();
    }

    @SneakyThrows
    private Optional<UserDetail> getUserDetail(ResultSet resultSet) {
        if (resultSet.getObject(USER_DETAIL_ID, Long.class) != null) {
            return Optional.of(UserDetail.builder()
                    .id(resultSet.getObject(USER_DETAIL_ID, Long.class))
                    .contactNumber(resultSet.getObject(CONTACT_NUMBER, String.class))
                    .photo(resultSet.getObject(USER_PHOTO, String.class))
                    .birthdate(resultSet.getObject(BIRTHDATE, Date.class).toLocalDate())
                    .money(resultSet.getObject(MONEY, Integer.class))
                    .build());
        }

        return Optional.empty();
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

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
