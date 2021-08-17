package com.davinomjr.taxcalculator.core.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name = "exempt")
    private boolean taxesExempt;

    private boolean imported;

    public Product(){}
    public Product(String name, boolean taxesExempt, boolean imported) {
        this.name = name;
        this.taxesExempt = taxesExempt;
        this.imported = imported;
    }

    public String getName() {
        return name;
    }

    public boolean isImported() {
        return imported;
    }

    public boolean isTaxesExempt() { return taxesExempt; }
}
