package com.bolun.hotel.dao;

import com.bolun.hotel.entity.User;
import com.bolun.hotel.dto.UserFilter;

import java.util.List;
import java.util.Optional;

public interface UserDao extends BaseDao<Long, User> {
    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findAll(UserFilter filter) throws IllegalAccessException;

    Boolean isEmailAlreadySaved(String email);

    void saveUserDetail(Long id);
}
