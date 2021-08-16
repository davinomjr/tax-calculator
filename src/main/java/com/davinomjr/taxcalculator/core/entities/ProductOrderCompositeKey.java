package com.davinomjr.taxcalculator.core.entities;

import java.io.Serializable;
import java.util.Objects;

public class ProductOrderCompositeKey implements Serializable {

    private int productId;

    private int orderId;

    public ProductOrderCompositeKey(int productId, int orderId) {
        this.productId = productId;
        this.orderId = orderId;
    }

    private ProductOrderCompositeKey() {}

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        ProductOrderCompositeKey compositeKey = (ProductOrderCompositeKey) o;
        return productId == compositeKey.productId && orderId == compositeKey.orderId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, orderId);
    }
}
