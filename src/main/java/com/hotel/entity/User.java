package com.hotel.entity;

import com.hotel.entity.enums.Role;
import com.hotel.entity.enums.Gender;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Gender gender;
    private Long userDetailId;
}
