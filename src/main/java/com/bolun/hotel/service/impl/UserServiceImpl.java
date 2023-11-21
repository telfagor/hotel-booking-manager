package com.bolun.hotel.service.impl;

import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.User;
import com.bolun.hotel.dao.UserDao;
import com.bolun.hotel.dto.ReadUserDto;
import com.bolun.hotel.dto.CreateUserDto;
import com.bolun.hotel.dao.impl.UserDaoImpl;
import com.bolun.hotel.service.UserService;
import com.bolun.hotel.validator.ValidatorImpl;
import com.bolun.hotel.validator.ValidationResult;
import com.bolun.hotel.mapper.impl.ReadUserDtoMapper;
import com.bolun.hotel.exception.UserNotValidException;
import com.bolun.hotel.mapper.impl.CreateUserDtoMapper;

import java.sql.SQLException;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserServiceImpl implements UserService {

    private static final UserService INSTANCE = new UserServiceImpl();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final CreateUserDtoMapper createUserDtoMapper = CreateUserDtoMapper.getInstance();
    private final ReadUserDtoMapper readUserDtoMapper = ReadUserDtoMapper.getInstance();
    private final ValidatorImpl validator = ValidatorImpl.getInstance();

    @Override
    public Long save(CreateUserDto createUserDto) {
        ValidationResult validationResult = validator.isValid(createUserDto);

        if (!validationResult.isValid()) {
            throw new UserNotValidException(validationResult.getErrors());
        }
        User user = createUserDtoMapper.mapFrom(createUserDto);

        return userDao.save(user).getId();
    }

    @Override
    public Optional<ReadUserDto> findByEmailAndPassword(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(readUserDtoMapper::mapFrom);
    }

    @Override
    public Boolean update(CreateUserDto createUserDto) {
        User user = createUserDtoMapper.mapFrom(createUserDto);
        return userDao.update(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        return userDao.delete(id);
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
