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
        return ReadUserDto.builder()
                .id(object.getId())
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .email(object.getEmail())
                .userDetail(object.getUserDetail())
                .role(object.getRole())
                .gender(object.getGender())
                .build();
    }

    public User mapToEntity(ReadUserDto readUserDto) {
        return User.builder()
                .id(readUserDto.getId())
                .firstName(readUserDto.getFirstName())
                .lastName(readUserDto.getLastName())
                .email(readUserDto.getEmail())
                .role(readUserDto.getRole())
                .gender(readUserDto.getGender())
                .build();
    }

    public static ReadUserDtoMapper getInstance() {
        return INSTANCE;
    }
}

