package com.davinomjr.taxcalculator.application.handlers;

import com.davinomjr.taxcalculator.application.util.TaxRateConstants;
import org.springframework.stereotype.Component;

@Component
public class HandlerConfiguration {

    public SalesTaxHandler getConfiguration(){
        return new ImportedSalesTaxHandler(new BasicSalesTaxHandler(null, TaxRateConstants.BASIC_RATE), TaxRateConstants.IMPORTED_RATE);
    }
}
