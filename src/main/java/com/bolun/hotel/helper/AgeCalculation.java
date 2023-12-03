package com.bolun.hotel.helper;

import com.bolun.hotel.exception.InvalidDateException;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.bolun.hotel.helper.LocalDateFormatter.format;
import static com.bolun.hotel.helper.LocalDateFormatter.isValid;

@UtilityClass
public class AgeCalculation {

    private static final int ADULT_AGE = 18;

    public static boolean isAdult(String date) {
        if (!isValid(date)) {
            return false;
        }

        long age = ChronoUnit.DAYS.between(format(date), LocalDate.now());
        return age >= ADULT_AGE;
    }
}
