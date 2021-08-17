package com.davinomjr.taxcalculator.application.handlers;
import com.davinomjr.taxcalculator.core.entities.Product;

import java.math.BigDecimal;

public class ImportedSalesTaxHandler extends SalesTaxHandler {

    public ImportedSalesTaxHandler(SalesTaxHandler handler, BigDecimal rate) {
        super(handler, rate);
    }

      @Override
      public BigDecimal getTaxRate(Product product, BigDecimal tax) {
        if(this.rate != BigDecimal.ZERO && product.isImported()) {
            tax = super.rate.add(tax);
        }

        return super.getTaxRate(product, tax);
    }
}
