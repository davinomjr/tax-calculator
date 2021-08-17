package com.davinomjr.taxcalculator.unit.handlers;

import com.davinomjr.taxcalculator.application.handlers.BasicSalesTaxHandler;
import com.davinomjr.taxcalculator.core.entities.Product;
import com.davinomjr.taxcalculator.web.TaxCalculatorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ContextConfiguration(classes = TaxCalculatorApplication.class)
public class BasicSalesTaxHandlerTests {

    private final int RATE = 10;

    @Test
    public void getTaxRate_productNotExemptAndTaxGivenWithSingleHandler_shouldReturnBasicTax(){
        Product product = new Product("test", false, false);
        BasicSalesTaxHandler handler = new BasicSalesTaxHandler(null, BigDecimal.valueOf(RATE));
        BigDecimal expectedCalculatedTax = BigDecimal.valueOf(RATE);

        BigDecimal calculatedTax = handler.getTaxRate(product, BigDecimal.ZERO);

        assertThat(calculatedTax).isEqualTo(expectedCalculatedTax);
    }

    @Test
    public void getTaxRate_productExemptAndTaxGivenWithSingleHandler_shouldReturnZero(){
        Product product = new Product("test", true, false);
        BasicSalesTaxHandler handler = new BasicSalesTaxHandler(null, BigDecimal.valueOf(RATE));
        BigDecimal expectedCalculatedTax = BigDecimal.valueOf(0);

        BigDecimal calculatedTax = handler.getTaxRate(product, BigDecimal.ZERO);

        assertThat(calculatedTax).isEqualTo(expectedCalculatedTax);
    }

    @Test
    public void getTaxRate_rateZeroGivenWithSingleHandler_shouldReturnZero(){
        Product product = new Product("test", true, false);
        BasicSalesTaxHandler handler = new BasicSalesTaxHandler(null, BigDecimal.ZERO);
        BigDecimal expectedCalculatedTax = BigDecimal.valueOf(0);
        BigDecimal calculatedTax = handler.getTaxRate(product, BigDecimal.ZERO);

        assertThat(calculatedTax).isEqualTo(expectedCalculatedTax);
    }

    @Test
    public void getTaxRate_productAndTaxGivenWithMoreThanOneHandler_shouldReturnZero(){
        Product product = new Product("test", false, false);
        BasicSalesTaxHandler handler = new BasicSalesTaxHandler(new BasicSalesTaxHandler(null, BigDecimal.valueOf(RATE)), BigDecimal.valueOf(RATE));
        BigDecimal expectedCalculatedTax = BigDecimal.valueOf(RATE).multiply(BigDecimal.valueOf(2));

        BigDecimal calculatedTax = handler.getTaxRate(product, BigDecimal.ZERO);

        assertThat(calculatedTax).isEqualTo(expectedCalculatedTax);
    }

    @Test
    public void getTaxRate_productExemptAndTaxGivenWithMoreThanOneHandler_shouldReturnZero(){
        Product product = new Product("test", true, false);
        BasicSalesTaxHandler handler = new BasicSalesTaxHandler(new BasicSalesTaxHandler(null, BigDecimal.valueOf(RATE)), BigDecimal.valueOf(RATE));
        BigDecimal expectedCalculatedTax = BigDecimal.valueOf(0);

        BigDecimal calculatedTax = handler.getTaxRate(product, BigDecimal.ZERO);

        assertThat(calculatedTax).isEqualTo(expectedCalculatedTax);
    }

    @Test
    public void getTaxRate_nullProductGiven_shouldThrowNullPointerException(){
        BasicSalesTaxHandler handler = new BasicSalesTaxHandler(new BasicSalesTaxHandler(null, BigDecimal.valueOf(RATE)), BigDecimal.valueOf(RATE));

       assertThatThrownBy(() -> {
            handler.getTaxRate(null, BigDecimal.ZERO);
        }).isInstanceOf(NullPointerException.class);
    }

}
