package com.example.demo.util;

import java.time.LocalDate;

public class DateUtil {
    public static boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }
}