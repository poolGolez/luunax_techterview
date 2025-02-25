package com.example.luunax.congestion.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CongestionTaxCalculatorTest {

    private CongestionTaxCalculator calculator = new CongestionTaxCalculator();

    @ParameterizedTest
    @MethodSource("getTollFeeArgsForTaxableTime")
    void testGetTollFee_taxableTimes(Date date, int expectedFee) {
        int actualFee = calculator.GetTollFee(date, new Car());
        assertEquals(expectedFee, actualFee);
    }

    private static Stream<Arguments> getTollFeeArgsForTaxableTime() {
        return Stream.of(
            Arguments.of(new Date(2013, 1, 3, 6, 0), 8),
            Arguments.of(new Date(2013, 1, 3, 6, 29), 8),

            Arguments.of(new Date(2013, 1, 3, 6, 30), 13),
            Arguments.of(new Date(2013, 1, 3, 6, 59), 13),

            Arguments.of(new Date(2013, 1, 3, 7, 0), 18),
            Arguments.of(new Date(2013, 1, 3, 7, 59), 18),

            Arguments.of(new Date(2013, 1, 3, 8, 0), 13),
            Arguments.of(new Date(2013, 1, 3, 8, 29), 13),

            Arguments.of(new Date(2013, 1, 3, 8, 30), 8),
            Arguments.of(new Date(2013, 1, 3, 14, 59), 8),

            Arguments.of(new Date(2013, 1, 3, 15, 0), 13),
            Arguments.of(new Date(2013, 1, 3, 15, 29), 13),

            Arguments.of(new Date(2013, 1, 3, 15, 30), 18),
            Arguments.of(new Date(2013, 1, 3, 16, 59), 18),

            Arguments.of(new Date(2013, 1, 3, 17, 0), 13),
            Arguments.of(new Date(2013, 1, 3, 17, 29), 13),

            Arguments.of(new Date(2013, 1, 3, 18, 0), 8),
            Arguments.of(new Date(2013, 1, 3, 18, 29), 8),


            Arguments.of(new Date(2013, 1, 3, 18, 30), 0),
            Arguments.of(new Date(2013, 1, 3, 23, 59), 0),
            Arguments.of(new Date(2013, 1, 3, 0, 0), 0),
            Arguments.of(new Date(2013, 1, 3, 5, 59), 0)


        );
    }


}