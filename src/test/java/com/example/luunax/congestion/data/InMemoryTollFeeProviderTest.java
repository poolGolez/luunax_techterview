package com.example.luunax.congestion.data;

import com.example.luunax.congestion.calculator.Car;
import com.example.luunax.congestion.calculator.Motorbike;
import com.example.luunax.congestion.calculator.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTollFeeProviderTest {

    private InMemoryTollFeeProvider calculator;

    @BeforeEach
    void setUp() {
        calculator = new InMemoryTollFeeProvider();
    }

    @ParameterizedTest
    @MethodSource("getTollFeeArgsForTaxableTime")
    void testGetTollFee_taxableTimes(Date date, int expectedFee) {
        int actualFee = calculator.getTollFee(date, new Car());
        assertEquals(expectedFee, actualFee);
    }

    @ParameterizedTest
    @MethodSource("getTollFeeArgsForVehicles")
    void testGetTollFee_differentVehicles(Vehicle vehicle, int expectedFee) {
        Date date = new Date(2013, 1, 3, 6, 30);
        int actualFee = calculator.getTollFee(date, vehicle);
        assertEquals(expectedFee, actualFee);
    }

    @ParameterizedTest
    @MethodSource("getTollFeeArgsForTollFeeDates")
    void testGetTollFee_tollFeeDates(Date date, int expectedFee) {
        int actualFee = calculator.getTollFee(date, new Car());
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
            Arguments.of(new Date(2013, 1, 3, 9, 0), 8),
            Arguments.of(new Date(2013, 1, 3, 14, 0), 8),
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

    private static Stream<Arguments> getTollFeeArgsForVehicles() {
        return Stream.of(
            Arguments.of(new Car(), 13),
            Arguments.of(new Motorbike(), 13),
            Arguments.of((Vehicle) () -> "Motorcycle", 0),
            Arguments.of((Vehicle) () -> "Tractor", 0),
            Arguments.of((Vehicle) () -> "Emergency", 0),
            Arguments.of((Vehicle) () -> "Diplomat", 0),
            Arguments.of((Vehicle) () -> "Foreign", 0),
            Arguments.of((Vehicle) () -> "Military", 0)
        );
    }


    private static Stream<Arguments> getTollFeeArgsForTollFeeDates() {
        return Stream.of(
            // Saturday
            Arguments.of(new Date(2013, 0, 4, 6, 0), 0),
            // Sunday
            Arguments.of(new Date(2013, 0, 5, 6, 0), 0),

            // Holidays
            Arguments.of(new Date(2013, 0, 1, 6, 0), 0),

            Arguments.of(new Date(2013, 2, 28, 6, 0), 0),
            Arguments.of(new Date(2013, 2, 29, 6, 0), 0),

            Arguments.of(new Date(2013, 3, 1, 6, 0), 0),
            Arguments.of(new Date(2013, 3, 30, 6, 0), 0),

            Arguments.of(new Date(2013, 4, 1, 6, 0), 0),
            Arguments.of(new Date(2013, 4, 8, 6, 0), 0),
            Arguments.of(new Date(2013, 4, 9, 6, 0), 0),

            Arguments.of(new Date(2013, 5, 5, 6, 0), 0),
            Arguments.of(new Date(2013, 5, 6, 6, 0), 0),
            Arguments.of(new Date(2013, 5, 21, 6, 0), 0),

            Arguments.of(new Date(2013, 6, 1, 6, 0), 0),
            Arguments.of(new Date(2013, 6, 15, 6, 0), 0),
            Arguments.of(new Date(2013, 6, 31, 6, 0), 0),

            Arguments.of(new Date(2013, 10, 1, 6, 0), 0),

            Arguments.of(new Date(2013, 11, 24, 6, 0), 0),
            Arguments.of(new Date(2013, 11, 25, 6, 0), 0),
            Arguments.of(new Date(2013, 11, 26, 6, 0), 0),
            Arguments.of(new Date(2013, 11, 31, 6, 0), 0)
        );
    }

}