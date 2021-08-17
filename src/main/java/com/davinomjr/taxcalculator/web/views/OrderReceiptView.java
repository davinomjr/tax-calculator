package com.davinomjr.taxcalculator.web.views;

import com.davinomjr.taxcalculator.web.util.OrderItemViewSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class OrderReceiptView {
    @JsonSerialize(using = OrderItemViewSerializer.class)
    @JsonProperty("Basket")
    private List<OrderItemView> orderItems;

    @JsonProperty("Sales Taxes")
    private String taxes;

    @JsonProperty("Total")
    private String total;

    public OrderReceiptView(String taxes, String total, List<OrderItemView> orderItems){
        this.taxes = taxes;
        this.total = total;
        this.orderItems = orderItems ;
    }
}
