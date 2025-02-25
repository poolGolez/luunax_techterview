package com.example.luunax.congestion.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CongestionTaxCalculatorGetTaxTest {

    private CongestionTaxCalculator calculator;
    private static Vehicle DEFAULT_VEHICLE = new Car();
    private static DateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @BeforeEach
    void setUp() {
        calculator = new CongestionTaxCalculator();
    }

    @Test
    void getTax_singleDay() throws ParseException {
        Date[] dates = new Date[]{
            DATE_PARSER.parse("2025-06-09 06:30:00")
        };

        int tax = calculator.getTax(DEFAULT_VEHICLE, dates);

        assertEquals(13, tax);
    }

    @Test
    void getTax_notWithin1HourAndMultipleDays() throws ParseException {
        Date[] dates = new Date[]{
            DATE_PARSER.parse("2025-01-09 06:30:00"), // 13
            DATE_PARSER.parse("2025-01-09 17:00:00"), // 13
            DATE_PARSER.parse("2025-01-15 10:48:00"), // 8
            DATE_PARSER.parse("2025-01-16 20:30:00") // 8
        };

        int tax = calculator.getTax(DEFAULT_VEHICLE, dates);

        assertEquals(34, tax);
    }

    @Test
    void getTax_within1HourIntervalAnd1Day() throws ParseException {
        Date[] dates = new Date[]{
            DATE_PARSER.parse("2025-01-09 06:29:00"), // 13
            DATE_PARSER.parse("2025-01-09 07:29:00") // 18
        };

        int tax = calculator.getTax(DEFAULT_VEHICLE, dates);

        assertEquals(18, tax);
    }
}