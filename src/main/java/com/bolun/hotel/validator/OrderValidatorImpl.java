package com.bolun.hotel.validator;

import com.bolun.hotel.dto.CreateOrderDto;
import com.bolun.hotel.helper.LocalDateTimeFormatter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderValidatorImpl implements Validator<CreateOrderDto> {

    private static final OrderValidatorImpl INSTANCE = new OrderValidatorImpl();

    @Override
    public ValidationResult isValid(CreateOrderDto createOrderDto) {
        ValidationResult validationResult = new ValidationResult();
        boolean isDateTimeEmpty = false;

        if (createOrderDto.checkIn() == null || createOrderDto.checkIn().isBlank()) {
            validationResult.add(Error.of("invalid.date", "end date is not selected"));
            isDateTimeEmpty = true;
        }

        if (createOrderDto.checkOut() == null || createOrderDto.checkOut().isBlank()) {
            validationResult.add(Error.of("invalid.date", "end date is not selected"));
            isDateTimeEmpty = true;
        }


        if (isDateTimeEmpty) {
            return validationResult;
        }

        if (!LocalDateTimeFormatter.isValid(createOrderDto.checkIn().concat(":00"))) {
            validationResult.add(Error.of("invalid.date", "invalid start to stay"));
        }

        if (!LocalDateTimeFormatter.isValid(createOrderDto.checkOut().concat(":00"))) {
            validationResult.add(Error.of("invalid.date", "invalid end to stay"));
        }

        if (LocalDateTimeFormatter.format(createOrderDto.checkIn().concat(":00"))
                .isAfter(LocalDateTimeFormatter.format(createOrderDto.checkOut().concat(":00")))) {
            validationResult.add(Error.of("invalid.interval", "invalid date time interval"));
        }

        return validationResult;
    }

    public static OrderValidatorImpl getInstance() {
        return INSTANCE;
    }
}
