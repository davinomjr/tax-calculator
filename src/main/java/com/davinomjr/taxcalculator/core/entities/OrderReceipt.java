package com.davinomjr.taxcalculator.core.entities;

import com.davinomjr.taxcalculator.core.Round;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderReceipt implements Serializable {

    private List<OrderItem> orderItems;
    private double taxes;
    private double total;

    public OrderReceipt(){
        orderItems = new ArrayList<>();
    }

    public void addItem(OrderItem item){
        this.orderItems.add(item);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTaxes(){
        return Round.roundToMoney(orderItems.stream()
                                            .map(OrderItem::getValueMinusTaxes)
                                            .reduce(0.0, Double::sum));
    }

    public double getTotal(){
        return Round.roundToMoney(orderItems.stream()
                                            .map(t -> t.getValue() * t.getQuantity())
                                            .reduce(0.0, Double::sum));
    }
}
