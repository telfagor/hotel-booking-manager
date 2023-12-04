package com.bolun.hotel.dao.impl;

import lombok.SneakyThrows;
import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.User;
import com.bolun.hotel.dto.UserFilter;
import com.bolun.hotel.dao.UserDao;
import com.bolun.hotel.entity.UserDetail;
import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.exception.DaoException;
import com.bolun.hotel.connection.ConnectionManager;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;
import static java.util.stream.Collectors.joining;

@NoArgsConstructor(access = PRIVATE)
public class UserDaoImpl implements UserDao {
    private static final UserDao INSTANCE = new UserDaoImpl();

    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String USER_PASSWORD = "user_password";
    private static final String USER_ROLE = "user_role";
    private static final String GENDER = "gender";
    private static final String USER_DETAIL_ID = "user_detail_id";
    private static final String CONTACT_NUMBER = "contact_number";
    private static final String USER_PHOTO = "user_photo";
    private static final String BIRTHDATE = "birthdate";

    private static final String INSERT_SQL = """
            INSERT INTO "user"
            (first_name, last_name, email, user_password, role_id, gender_id) 
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private static final String INSERT_USER_DETAIL_ID = """
            INSERT INTO "user" (user_detail_id)
            VALUES (?)
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
                WHERE id = ?
            """;

    private static final String IS_EMAIL_EXIST = """
            SELECT id
            FROM "user"
            WHERE email = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT u.id AS user_id,
                   u.first_name AS first_name,
                   u.last_name AS last_name,
                   u.email AS email,
                   u.user_password AS user_password,
                   us.id AS user_detail_id,
                   us.contact_number AS contact_number,
                   us.photo AS user_photo,
                   us.birthdate AS birthdate,
                   us.user_money AS money,
                   g.id AS gender_id,
                   g.gender_type AS gender,
                   r.id AS role_id,
                   r.user_role AS user_role
            FROM "user" u
            LEFT JOIN user_detail us
            ON u.user_detail_id = us.id
            JOIN gender g
            ON u.gender_id = g.id
            JOIN "role" r
            ON u.role_id = r.id
            """;

    private static final String FIND_USER_BY_EMAIL_AND_PASSWORD =
            FIND_ALL_SQL + " WHERE email = ? AND user_password = ?";

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE u.id = ?";
    private static final String DELETE_BY_ID = """
            DELETE FROM "user"
            WHERE id = ?;
            """;

    @Override
    public User save(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getRole().getValue());
            preparedStatement.setInt(6, user.getGender().getValue());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong(ID));

            return user;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public void saveUserDetail(Long id) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_DETAIL_ID)) {
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    @Override
    public Boolean update(User user) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setLong(5, user.getRole().getValue());
            preparedStatement.setLong(6, user.getGender().getValue());
            preparedStatement.setObject(7, user.getUserDetail() != null ? user.getUserDetail().getId() : null);
            preparedStatement.setLong(8, user.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }



    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = null;

            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }


    public Boolean isEmailAlreadySaved(String email) {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(IS_EMAIL_EXIST)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
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
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }


    @Override
    public List<User> findAll(UserFilter filter) throws IllegalAccessException {
        List<User> users = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        List<String> whereSQL = new ArrayList<>();
        boolean isFilterNotEmpty = false;

        if (filter.firstName() != null) {
            parameters.add('%' + filter.firstName() + '%');
            whereSQL.add("first_name LIKE ?");
            isFilterNotEmpty = true;
        }

        if (filter.lastName() != null) {
            parameters.add('%' + filter.lastName() + '%');
            whereSQL.add("last_name LIKE ?");
            isFilterNotEmpty = true;
        }

        if (filter.email() != null) {
            parameters.add('%' + filter.email() + '%');
            whereSQL.add("email LIKE ?");
            isFilterNotEmpty = true;
        }

        if (filter.gender() != null) {
            parameters.add('%' + filter.gender().name() + '%');
            whereSQL.add("gender LIKE ?");
            isFilterNotEmpty = true;
        }

        if (filter.role() != null) {
            parameters.add('%' + filter.role().name() + '%');
            whereSQL.add("role LIKE ?");
            isFilterNotEmpty = true;
        }

        parameters.add(filter.limit());
        parameters.add(filter.offset());

        String where = whereSQL.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));

        String sql = FIND_ALL_SQL + (isFilterNotEmpty ? where : "");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            return users;
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
    }

    public List<User> findAll2(UserFilter filter) {
        List<User> users = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();
        List<String> whereSQL = new ArrayList<>();
        boolean isFilterNotEmpty = false;

        try {
            Class<UserFilter> clazz = UserFilter.class;
            for (Field field : clazz.getDeclaredFields()) {
                if (field.get(filter) != null) {
                    parameters.add('%' + field.getName() + '%');
                    whereSQL.add(getColumnName(field.getName()) + " LIKE ?");
                    isFilterNotEmpty = true;
                }
            }
        } catch (IllegalAccessException ex) {
            throw new DaoException("An error occur when are working with reflection!", ex);
        }

        String where = whereSQL.stream()
                .collect(joining(" AND ", " WHERE ", " LIMIT ? OFFSET ?"));

        String sql = FIND_ALL_SQL + (isFilterNotEmpty ? where : "");

        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
        } catch (SQLException ex) {
            throw new DaoException(ex.getMessage(), ex);
        }
        return users;
    }

    private String getColumnName(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        int start = 0;
        int upperLetterPos = 0;
        int nextAfterUpperLetterPos;

        for (int i = 0; i < name.length(); i += nextAfterUpperLetterPos) {
            char letter = name.charAt(i);
            if (Character.isUpperCase(letter)) {
                upperLetterPos = i;
                String untilUpperCase = name.substring(start, upperLetterPos);
                stringBuilder.append(untilUpperCase).append("_");
                stringBuilder.append(Character.toLowerCase(letter));
            }
            start = upperLetterPos + 1;
            nextAfterUpperLetterPos = upperLetterPos + 1;
        }

        return stringBuilder.toString();
    }

    @SneakyThrows
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
                    .money(resultSet.getObject("money", Integer.class))
                    .build());
        }

        return Optional.empty();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
