package com.davinomjr.taxcalculator.web.controllers;

import com.davinomjr.taxcalculator.application.interfaces.ITaxCalculatorService;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.core.entities.OrderItem;
import com.davinomjr.taxcalculator.core.entities.OrderReceipt;
import com.davinomjr.taxcalculator.core.exceptions.ProductNotFoundException;
import com.davinomjr.taxcalculator.web.views.OrderItemView;
import com.davinomjr.taxcalculator.web.views.OrderReceiptView;
import com.fasterxml.jackson.databind.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tax")
public class TaxCalculatorController {

    private final ITaxCalculatorService taxCalculatorService;

    public TaxCalculatorController(final ITaxCalculatorService taxCalculatorService){
        this.taxCalculatorService = taxCalculatorService;
    }

    @PostMapping("/calculate")
    public String calculate(@RequestBody OrderItem[] orderItems) {
        try{
            OrderReceipt receipt = taxCalculatorService.calculate(new Order(new Date(), Arrays.asList(orderItems)));
            OrderReceiptView result = new OrderReceiptView(String.format("%.2f", receipt.getTaxes()),
                                                           String.format("%.2f", receipt.getTotal()),
                                                           receipt.getOrderItems()
                                                                   .stream()
                                                                   .map(t -> new OrderItemView(t.getName(), t.getQuantity(), String.format("%.2f", t.getValue())))
                                                                   .collect(Collectors.toList()));

            return new ObjectMapper().writerWithDefaultPrettyPrinter()
                         .writeValueAsString(result);
        }
        catch(ProductNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There was an error processing your request.", ex);
        }
    }
}
