package com.davinomjr.taxcalculator.application.handlers;

import com.davinomjr.taxcalculator.core.entities.Product;
import java.math.BigDecimal;

public abstract class SalesTaxHandler {
    private SalesTaxHandler handler;

    protected BigDecimal rate;

    public SalesTaxHandler(SalesTaxHandler handler, BigDecimal rate){
        this.handler = handler;
        this.rate = rate;
    }

    public BigDecimal getTaxRate(Product product, BigDecimal tax) {
        if(handler != null){
            return handler.getTaxRate(product, tax);
        }

        return tax;
    }
}
