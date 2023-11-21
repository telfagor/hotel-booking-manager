package com.bolun.hotel.dto;

import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.entity.enums.Gender;

public record UserFilter(String firstName,
                         String lastName,
                         String email,
                         Role role,
                         Gender gender,
                         Integer limit,
                         Integer offset) {
}
