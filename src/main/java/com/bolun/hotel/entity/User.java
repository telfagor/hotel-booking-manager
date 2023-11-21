package com.bolun.hotel.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.bolun.hotel.entity.enums.Role;
import com.bolun.hotel.entity.enums.Gender;

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
    private UserDetail userDetail;
}
