package com.example.luunax.congestion.calculator;

import java.util.Date;

public interface TollFeeProvider {
    int getTollFee(Date date, Vehicle vehicle);
}
