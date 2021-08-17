package com.davinomjr.taxcalculator.core.entities;

import com.davinomjr.taxcalculator.core.util.Round;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderReceipt implements Serializable {

    private List<OrderItem> orderItems;
    private BigDecimal taxes;
    private BigDecimal total;

    public OrderReceipt(){
        orderItems = new ArrayList<>();
    }

    public OrderReceipt(List<OrderItem> orderItems) { this.orderItems = orderItems; }

    public void addItem(OrderItem item){
        this.orderItems.add(item);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getTaxes(){
        return Round.roundToMoney(orderItems.stream()
                                            .map(OrderItem::getValueMinusTaxes)
                                            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    public BigDecimal getTotal(){
        return Round.roundToMoney(orderItems.stream()
                                            .map(t -> t.getValue().multiply(BigDecimal.valueOf(t.getQuantity())))
                                            .reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
