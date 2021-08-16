package com.davinomjr.taxcalculator.core.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(ProductOrderCompositeKey.class)
public class ProductOrder {

    @Id
    private String productId;

    @Id
    private String orderId;

    private int quantity;

}
