package com.bolun.hotel.helper;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class AgeCalculation {

    private static final int ADULT_AGE = 18;

    public static boolean isAdult(String date) {
        if (!LocalDateFormatter.isValid(date)) {
            return false;
        }

        long age = ChronoUnit.DAYS.between(LocalDateFormatter.format(date), LocalDate.now());
        return age >= ADULT_AGE;
    }


}
