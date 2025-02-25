package com.example.luunax.congestion.calculator.controller;

import com.example.luunax.congestion.calculator.domain.Car;
import com.example.luunax.congestion.calculator.domain.CongestionTaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    public int calculateTax() throws ParseException {
        Car vehicle = new Car();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        Date[] dates = new Date[]{
            format.parse("2013-01-14 21:00:00"),
            format.parse("2013-02-07 06:23:27"),
            format.parse("2013-01-15 21:00:00"),
            format.parse("2013-02-07 15:27:00"),
            format.parse("2013-02-08 06:27:00"),
            format.parse("2013-02-08 06:20:27"),
            format.parse("2013-02-08 14:35:00"),
            format.parse("2013-02-08 15:29:00"),
            format.parse("2013-02-08 15:47:00"),
            format.parse("2013-02-08 16:01:00"),
            format.parse("2013-02-08 16:48:00"),
            format.parse("2013-02-08 17:49:00"),
            format.parse("2013-02-08 18:29:00"),
            format.parse("2013-02-08 18:35:00"),
            format.parse("2013-03-26 14:25:00"),
            format.parse("2013-03-28 14:07:27")
        };

        return taxCalculator.getTax(vehicle, dates);
    }
}