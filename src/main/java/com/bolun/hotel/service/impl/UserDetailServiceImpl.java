package com.bolun.hotel.service.impl;

import com.bolun.hotel.dao.UserDao;
import com.bolun.hotel.dao.UserDetailDao;
import com.bolun.hotel.dao.impl.UserDaoImpl;
import com.bolun.hotel.dao.impl.UserDetailDaoImpl;
import com.bolun.hotel.dto.CreateUserDetailDto;
import com.bolun.hotel.entity.UserDetail;
import com.bolun.hotel.exception.UserDetailNotValidationException;
import com.bolun.hotel.mapper.impl.CreateUserDetailDtoMapper;
import com.bolun.hotel.service.ImageService;
import com.bolun.hotel.service.UserDetailService;
import com.bolun.hotel.helper.validator.UserDetailValidatorImpl;
import com.bolun.hotel.helper.validator.ValidationResult;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserDetailServiceImpl implements UserDetailService {

    private static final UserDetailService INSTANCE = new UserDetailServiceImpl();
    private final UserDao userDao = UserDaoImpl.getInstance();
    private final UserDetailDao userDetailDao = UserDetailDaoImpl.getInstance();

    private final ImageService imageService = ImageService.getInstance();
    private final CreateUserDetailDtoMapper createUserDetailDtoMapper = CreateUserDetailDtoMapper.getInstance();
    private final UserDetailValidatorImpl userDetailValidatorImpl = UserDetailValidatorImpl.getInstance();

    @SneakyThrows
    @Override
    public CreateUserDetailDto create(CreateUserDetailDto createUserDetailDto) {
        ValidationResult validationResult = userDetailValidatorImpl.isValid(createUserDetailDto);

        if (!validationResult.isValid()) {
            throw new UserDetailNotValidationException(validationResult.getErrors());
        }

        UserDetail userDetail = createUserDetailDtoMapper.mapFrom(createUserDetailDto);
        imageService.upload(userDetail.getPhoto(), createUserDetailDto.photo().getInputStream());
        UserDetail actualUserDetail =  userDetailDao.save(userDetail);

        userDao.findById(userDetail.getId()).ifPresent(user -> {
            user.setUserDetail(actualUserDetail);
            userDao.update(user);
        });

        return createUserDetailDto;
    }



    @Override
    public Optional<UserDetail> findById(Long id) {

        return userDetailDao.findById(id);
    }

    public static UserDetailService getInstance() {
        return INSTANCE;
    }
}
