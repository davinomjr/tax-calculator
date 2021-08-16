package com.davinomjr.taxcalculator.core.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "ProductOrders")
@IdClass(ProductOrderCompositeKey.class)
public class ProductOrder {

    @Id
    private int productId;

    @Id
    private int orderId;

    private int quantity;

}
