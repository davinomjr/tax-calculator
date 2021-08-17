package com.davinomjr.taxcalculator.core.entities;

import com.davinomjr.taxcalculator.core.Round;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

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

    private double value;

    private double tax;

    public OrderItem(){}
    public OrderItem(OrderItem item, double tax) {
        this.name = item.name;
        this.quantity = item.quantity;
        this.value = item.value;
        this.tax = tax;
    }

    public OrderItem (String name, int quantity, double value){
        this.name = name;
        this.quantity = quantity;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getValue(){
        if(this.tax > 0){
            return Round.roundToNearestDotZeroFive(this.value + (this.tax * this.value / 100));
        }

        return this.value;
    }

    @JsonIgnore
    public double getValueMinusTaxes(){
        return getValue() - this.value;
    }
}
