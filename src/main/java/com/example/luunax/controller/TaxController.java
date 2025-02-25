package com.example.luunax.controller;

import com.example.luunax.congestion.calculator.domain.Car;
import com.example.luunax.congestion.calculator.domain.CongestionTaxCalculator;
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
        Car vehicle = new Car();

        return taxCalculator.getTax(vehicle, params.getSerializedDates());
    }
}

record TaxCalculatorParams(
    String[] dates
) {

    public Date[] getSerializedDates() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date[] result = new Date[dates.length];
        for (int i = 0; i < dates.length; i++) {
            result[i] = format.parse(dates[i]);
        }

        return result;
    }
}