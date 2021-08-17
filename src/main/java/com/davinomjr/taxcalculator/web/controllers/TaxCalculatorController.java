package com.davinomjr.taxcalculator.web.controllers;

import com.davinomjr.taxcalculator.application.interfaces.IOrderService;
import com.davinomjr.taxcalculator.application.interfaces.ITaxCalculatorService;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.core.entities.OrderItem;
import com.davinomjr.taxcalculator.core.entities.OrderReceipt;
import com.davinomjr.taxcalculator.core.exceptions.ProductNotFoundException;
import com.davinomjr.taxcalculator.web.views.OrderItemView;
import com.davinomjr.taxcalculator.web.views.OrderReceiptView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tax")
public class TaxCalculatorController {

    private final ITaxCalculatorService taxCalculatorService;
    private final IOrderService orderService;

    public TaxCalculatorController(final ITaxCalculatorService taxCalculatorService,
                                   final IOrderService orderService){
        this.taxCalculatorService = taxCalculatorService;
        this.orderService = orderService;
    }

    @PostMapping("/calculate")
    public String calculate(@RequestBody JsonNode json) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            List<OrderItem> orderItems = Arrays.asList(mapper.readValue(json.get("items").toString(), OrderItem[].class));
            OrderReceipt receipt = taxCalculatorService.calculate(new Order(new Date(), orderItems));
            OrderReceiptView result = new OrderReceiptView(String.format("%.2f", receipt.getTaxes()),
                                                           String.format("%.2f", receipt.getTotal()),
                                                           receipt.getOrderItems()
                                                                   .stream()
                                                                   .map(t -> new OrderItemView(t.getName(), t.getQuantity(), String.format("%.2f", t.getValue())))
                                                                   .collect(Collectors.toList()));

            return mapper.writerWithDefaultPrettyPrinter()
                         .writeValueAsString(result);
        }
        catch(ProductNotFoundException ex){
            return ex.getMessage();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return "There was an error processing your request";
        }
    }
}
