package com.davinomjr.taxcalculator.core.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    private String id;

    private String name;

    private double value;
}
