package com.bolun.hotel.service;

import com.bolun.hotel.dto.CreateUserDto;
import com.bolun.hotel.dto.ReadUserDto;
import com.bolun.hotel.entity.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserService {

    Long save(CreateUserDto createUserDto);

    void saveUserDetail(Long id);

    Optional<ReadUserDto> findByEmailAndPassword(String email, String password);

    Boolean update(CreateUserDto createUserDto);

    Optional<User> findById(Long id);

    Boolean deleteById(Long id);
}
