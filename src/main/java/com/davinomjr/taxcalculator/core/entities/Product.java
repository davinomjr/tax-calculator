package com.davinomjr.taxcalculator.core.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    private int id;

    private String name;

    private double value;
}
