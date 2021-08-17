package com.davinomjr.taxcalculator.core;

public class Round {
    public static double roundToNearestDotZeroFive(double value) { return Math.ceil(value * 20.0) / 20.0; }
    public static double roundToMoney(double value){ return Math.floor(value * 100.0) / 100.0; }
}
