package com.bolun.hotel.dto;

import lombok.Builder;
import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.entity.enums.Gender;

@Builder
public record ReadUserDto(Long id,
                          String firstName,
                          String lastName,
                          String email,
                          //String image,
                          Role role,
                          Gender gender) {
}
