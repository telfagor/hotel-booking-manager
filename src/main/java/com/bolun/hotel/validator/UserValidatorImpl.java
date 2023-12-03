package com.bolun.hotel.validator;

import com.bolun.hotel.dto.CreateUserDto;
import com.bolun.hotel.entity.enums.Gender;
import com.bolun.hotel.entity.enums.Role;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserValidatorImpl implements Validator<CreateUserDto> {

    private static final UserValidatorImpl INSTANCE = new UserValidatorImpl();

    @Override
    public ValidationResult isValid(CreateUserDto createUserDto) {
        ValidationResult validationResult = new ValidationResult();

        if (createUserDto.firstName() == null || createUserDto.firstName().isBlank()) {
            validationResult.add(Error.of("invalid.first_name", "invalid first name"));
        }

        if (createUserDto.lastName() == null || createUserDto.lastName().isBlank()) {
            validationResult.add(Error.of("invalid.last_name", "invalid last name"));
        }

        if (createUserDto.email() == null || createUserDto.email().isBlank()) {
            validationResult.add(Error.of("invalid.email", "invalid email"));
        }

        if (createUserDto.password() == null || createUserDto.password().isBlank()) {
            validationResult.add(Error.of("invalid.password", "invalid password"));
        }

        if (Gender.find(createUserDto.gender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "invalid gender"));
        }

        if (Role.find(createUserDto.role()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "invalid gender"));
        }

        return validationResult;
    }

    public static UserValidatorImpl getInstance() {
        return INSTANCE;
    }
}
