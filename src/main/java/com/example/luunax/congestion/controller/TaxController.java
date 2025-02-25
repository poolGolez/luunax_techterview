package com.example.luunax.congestion.controller;

import com.example.luunax.congestion.calculator.Car;
import com.example.luunax.congestion.calculator.CongestionTaxCalculator;
import com.example.luunax.congestion.calculator.Motorbike;
import com.example.luunax.congestion.calculator.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@RestController
@RequestMapping("/tax")
public class TaxController {

    @Autowired
    private CongestionTaxCalculator taxCalculator;

    @PostMapping
    public int calculateTax(@RequestBody TaxCalculatorParams params) throws ParseException {
        return taxCalculator.getTax(params.getVehicle(), params.getSerializedDates());
    }
}

record TaxCalculatorParams(
    String vehicleType,
    String[] dates
) {

    public Vehicle getVehicle() {
        return switch (vehicleType) {
            case "Car" -> new Car();
            case "Motorbike" -> new Motorbike();
            default -> null;
        };
    }

    public Date[] getSerializedDates() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date[] result = new Date[dates.length];
        for (int i = 0; i < dates.length; i++) {
            result[i] = format.parse(dates[i]);
        }

        return result;
    }
}