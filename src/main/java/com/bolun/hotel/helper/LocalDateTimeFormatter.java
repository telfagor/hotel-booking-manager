package com.bolun.hotel.helper;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class LocalDateTimeFormatter {

    private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static LocalDateTime format(String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public static boolean isValid(String date) {
        try {
            format(date);
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}
