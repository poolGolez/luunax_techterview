package com.example.luunax.congestion.calculator;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CongestionTaxCalculator {

    private final TollFeeProvider tollFeeProvider;

    public CongestionTaxCalculator(TollFeeProvider tollFeeProvider) {
        this.tollFeeProvider = tollFeeProvider;
    }

    public int getTax(Vehicle vehicle, Date[] dates) {
        Date intervalStart = dates[0];
        int totalFee = 0;

        for (int i = 0; i < dates.length; i++) {
            Date date = dates[i];
            int nextFee = tollFeeProvider.getTollFee(date, vehicle);
            int tempFee = tollFeeProvider.getTollFee(intervalStart, vehicle);

            long diffInMillies = date.getTime() - intervalStart.getTime();
            long minutes = diffInMillies / 1000 / 60;

            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= tempFee;
                if (nextFee >= tempFee) tempFee = nextFee;
                totalFee += tempFee;
            } else {
                totalFee += nextFee;
                intervalStart = date;
            }
        }

        if (totalFee > 60) totalFee = 60;// 60 is cap PER DAY
        return totalFee;
    }

}
