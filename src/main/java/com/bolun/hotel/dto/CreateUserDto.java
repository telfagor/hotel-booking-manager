package com.bolun.hotel.dto;

import lombok.Builder;

@Builder
public record CreateUserDto(String firstName,
                            String lastName,
                            String email,
                            String password,
                            String role,
                            String gender) {
}
