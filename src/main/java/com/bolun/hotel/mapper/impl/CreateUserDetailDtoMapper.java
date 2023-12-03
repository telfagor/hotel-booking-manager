package com.bolun.hotel.mapper.impl;

import lombok.NoArgsConstructor;
import com.bolun.hotel.mapper.Mapper;
import com.bolun.hotel.entity.UserDetail;
import com.bolun.hotel.dto.CreateUserDetailDto;
import com.bolun.hotel.helper.LocalDateFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserDetailDtoMapper implements Mapper<CreateUserDetailDto, UserDetail> {

    private static final String IMAGE_PARENT = "users/";
    private static final CreateUserDetailDtoMapper INSTANCE = new CreateUserDetailDtoMapper();

    @Override
    public UserDetail mapFrom(CreateUserDetailDto object) {
        return UserDetail.builder()
                .id(object.userId())
                .contactNumber(object.contactNumber())
                .birthdate(LocalDateFormatter.format(object.birthdate()))
                .money(Integer.parseInt(object.money()))
                .photo(IMAGE_PARENT + object.photo().getSubmittedFileName())
                .build();
    }

    public static CreateUserDetailDtoMapper getInstance() {
        return INSTANCE;
    }
}
