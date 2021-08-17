package com.davinomjr.taxcalculator.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Round {
    public static BigDecimal roundToNearestDotZeroFive(BigDecimal value) {
        BigDecimal increment = BigDecimal.valueOf(0.05);
        RoundingMode roundingMode = RoundingMode.UP;
        return value.divide(increment, 0, roundingMode).multiply(increment);
    }

    public static BigDecimal roundToMoney(BigDecimal value){ return value.setScale(2, RoundingMode.HALF_UP); }
}
