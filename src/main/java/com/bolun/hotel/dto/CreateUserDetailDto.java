package com.bolun.hotel.dto;

import jakarta.servlet.http.Part;

public record CreateUserDetailDto (Long userId,
                                   String contactNumber,
                                   Part photo,
                                   String birthdate,
                                   String money) {
}
