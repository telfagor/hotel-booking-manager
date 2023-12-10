package com.bolun.hotel.entity;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetail {

    private Long id;
    private String contactNumber;
    private String photo;
    private LocalDate birthdate;
    private Integer money;
}
