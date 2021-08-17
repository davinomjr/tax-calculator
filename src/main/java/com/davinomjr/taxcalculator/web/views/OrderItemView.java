package com.davinomjr.taxcalculator.web.views;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItemView {
    @JsonProperty("Name")
    public String name;

    @JsonProperty("Quantity")
    public int quantity;

    @JsonProperty("Value")
    public String value;

    public OrderItemView(String name, int quantity, String value){
        this.name = name;
        this.quantity = quantity;
        this.value = value;
    }
}
