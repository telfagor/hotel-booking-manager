package com.bolun.hotel.dto;

import lombok.Data;
import lombok.Value;
import lombok.Builder;
import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.entity.UserDetail;

@Data
@Value
@Builder
public class ReadUserDto {
    Long id;
    String firstName;
    String lastName;
    String email;
    UserDetail userDetail;
    Role role;
    Gender gender;
}


