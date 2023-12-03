package com.bolun.hotel.service;

import com.bolun.hotel.dto.CreateUserDetailDto;
import com.bolun.hotel.entity.UserDetail;

import java.util.Optional;

public interface UserDetailService {

    CreateUserDetailDto create(CreateUserDetailDto userDetail);

    Optional<UserDetail> findById(Long id);
}
