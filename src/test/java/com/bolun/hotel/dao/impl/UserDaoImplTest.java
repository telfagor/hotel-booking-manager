package com.bolun.hotel.dao.impl;

import com.bolun.hotel.dao.UserDao;
import com.bolun.hotel.entity.User;
import com.bolun.hotel.entity.UserDetail;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.ArrayList;
import java.time.Month;
import java.time.LocalDate;

import static com.bolun.hotel.entity.enums.Gender.MALE;
import static com.bolun.hotel.entity.enums.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoImplTest {

    private final UserDao userDao = UserDaoImpl.getInstance();
    private final List<User> users = new ArrayList<>();


    @Test
    void save() {
        User user = createUser();
        User actualResult = userDao.save(user);
        //users.add(user);
        assertNotNull(actualResult.getId());
    }

    @AfterEach
    void clearDatabase() {
        for (User user : users) {
            userDao.delete(user.getId());
        }
    }

    private User createUser() {
        return User.builder()
                .firstName("test")
                .lastName("test")
                .email("test")
                .password("test")
                .role(ADMIN)
                .gender(MALE)
                .build();
    }

    private UserDetail createUserDetail() {
        return UserDetail.builder()
                .contactNumber("+37367123444")
                .birthdate(LocalDate.of(14, Month.APRIL, 3))
                .build();
    }
}
