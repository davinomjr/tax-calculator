package com.davinomjr.taxcalculator.application.handlers;
import com.davinomjr.taxcalculator.core.entities.Product;

public class BasicSalesTaxHandler extends SalesTaxHandler{

    public BasicSalesTaxHandler(SalesTaxHandler handler, double rate) {
        super(handler, rate);
    }

    @Override
    public double calculateTax(Product product, double tax) {
        if(this.rate > 0 && !product.isTaxesExempt()){
            tax += super.rate;
        }

        return super.calculateTax(product, tax);
    }
}
