package com.davinomjr.taxcalculator.core.entities;

import com.davinomjr.taxcalculator.core.util.Round;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "OrderItems")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private String name;

    private int quantity;

    private BigDecimal value;

    @JsonIgnore
    private BigDecimal tax;

    public OrderItem(){}
    public OrderItem(OrderItem item, BigDecimal tax) {
        this.name = item.name;
        this.quantity = item.quantity;
        this.value = item.value;
        this.tax = tax;
    }

    public OrderItem (String name, int quantity, BigDecimal value, BigDecimal tax){
        this.name = name;
        this.quantity = quantity;
        this.value = value;
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getValue(){
        if(!this.tax.equals(BigDecimal.ZERO)){
            return Round.roundToNearestDotZeroFive(this.value.add(this.tax.multiply(this.value).divide(BigDecimal.valueOf(100))));
        }

        return this.value;
    }

    @JsonIgnore
    public BigDecimal getValueMinusTaxes(){
        return getValue().subtract(this.value);
    }
}
