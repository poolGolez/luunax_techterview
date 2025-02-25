package com.example.luunax.congestion.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CongestionTaxCalculatorGetTaxTest {

    private CongestionTaxCalculator calculator;

    @Mock
    private TollFeeProvider tollFeeProvider;

    private static Vehicle DEFAULT_VEHICLE = new Car();
    private static DateFormat DATE_PARSER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @BeforeEach
    void setUp() {
        calculator = new CongestionTaxCalculator(tollFeeProvider);
    }

    @Test
    void getTax_singleDay() throws ParseException {
        Date[] dates = new Date[]{
            DATE_PARSER.parse("2025-06-09 06:30:00")
        };
        when(tollFeeProvider.getTollFee(dates[0], DEFAULT_VEHICLE)).thenReturn(13);

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
        when(tollFeeProvider.getTollFee(dates[0], DEFAULT_VEHICLE)).thenReturn(13);
        when(tollFeeProvider.getTollFee(dates[1], DEFAULT_VEHICLE)).thenReturn(13);
        when(tollFeeProvider.getTollFee(dates[2], DEFAULT_VEHICLE)).thenReturn(8);
        when(tollFeeProvider.getTollFee(dates[3], DEFAULT_VEHICLE)).thenReturn(8);

        int tax = calculator.getTax(DEFAULT_VEHICLE, dates);

        assertEquals(42, tax);
    }

    @Test
    void getTax_within1HourIntervalAnd1Day() throws ParseException {
        Date[] dates = new Date[]{
            DATE_PARSER.parse("2025-01-09 06:29:00"), // 13 (waived)
            DATE_PARSER.parse("2025-01-09 07:29:00") // 18
        };
        when(tollFeeProvider.getTollFee(dates[0], DEFAULT_VEHICLE)).thenReturn(13);
        when(tollFeeProvider.getTollFee(dates[1], DEFAULT_VEHICLE)).thenReturn(18);

        int tax = calculator.getTax(DEFAULT_VEHICLE, dates);

        assertEquals(18, tax);
    }

    @Test
    void getTax_within1HourIntervalAndMultipleDays() throws ParseException {
        Date[] dates = new Date[]{
            DATE_PARSER.parse("2025-01-09 06:29:00"), // 8 (waived)
            DATE_PARSER.parse("2025-01-09 07:29:00"), // 18

            DATE_PARSER.parse("2025-01-15 07:59:00"), // 18
            DATE_PARSER.parse("2025-01-15 08:13:00"), // 13 (waived)
            DATE_PARSER.parse("2025-01-15 08:59:00"), // 8 (waived)

            DATE_PARSER.parse("2025-01-16 08:48:00"), // 8
        };
        when(tollFeeProvider.getTollFee(dates[0], DEFAULT_VEHICLE)).thenReturn(8);
        when(tollFeeProvider.getTollFee(dates[1], DEFAULT_VEHICLE)).thenReturn(18);
        when(tollFeeProvider.getTollFee(dates[2], DEFAULT_VEHICLE)).thenReturn(18);
        when(tollFeeProvider.getTollFee(dates[3], DEFAULT_VEHICLE)).thenReturn(13);
        when(tollFeeProvider.getTollFee(dates[4], DEFAULT_VEHICLE)).thenReturn(8);
        when(tollFeeProvider.getTollFee(dates[5], DEFAULT_VEHICLE)).thenReturn(8);

        int tax = calculator.getTax(DEFAULT_VEHICLE, dates);

        assertEquals(44, tax);
    }
}