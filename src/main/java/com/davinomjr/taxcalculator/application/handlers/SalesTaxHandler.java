package com.davinomjr.taxcalculator.application.handlers;

import com.davinomjr.taxcalculator.core.entities.OrderItem;
import com.davinomjr.taxcalculator.core.entities.Product;

public abstract class SalesTaxHandler {
    private SalesTaxHandler handler;

    protected double rate;
    public SalesTaxHandler(double rate){
        this.rate = rate;
    }

    public SalesTaxHandler(SalesTaxHandler handler, double rate){
        this.handler = handler;
        this.rate = rate;
    }

    public double calculateTax(Product product, double tax) {
        if(handler != null){
            return handler.calculateTax(product, tax);
        }

        return tax;
    }
}
