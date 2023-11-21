package com.bolun.hotel.mapper.impl;

import lombok.NoArgsConstructor;
import com.bolun.hotel.entity.User;
import com.bolun.hotel.mapper.Mapper;
import com.bolun.hotel.dto.ReadUserDto;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ReadUserDtoMapper implements Mapper<User, ReadUserDto> {

    private static final ReadUserDtoMapper INSTANCE = new ReadUserDtoMapper();

    @Override
    public ReadUserDto mapFrom(User object) {
        return new ReadUserDto(
                object.getId(),
                object.getFirstName(),
                object.getLastName(),
                object.getEmail(),
                //object.getUserDetail().getPhoto(),
                object.getRole(),
                object.getGender()
        );
    }

    public static ReadUserDtoMapper getInstance() {
        return INSTANCE;
    }
}
