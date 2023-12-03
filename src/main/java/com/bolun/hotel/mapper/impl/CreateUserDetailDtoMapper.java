package com.bolun.hotel.mapper.impl;

import jakarta.servlet.http.Part;
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
        String photo = object.photo().getSubmittedFileName();

        return UserDetail.builder()
                .id(object.userId())
                .contactNumber(object.contactNumber())
                .birthdate(LocalDateFormatter.format(object.birthdate()))
                .money(Integer.parseInt(object.money()))
                .photo(photo.isBlank() ? photo.strip() : IMAGE_PARENT.concat(photo))
                .build();
    }

    public static CreateUserDetailDtoMapper getInstance() {
        return INSTANCE;
    }
}
