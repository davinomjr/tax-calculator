package com.davinomjr.taxcalculator.core.entities;

import java.io.Serializable;
import java.util.Objects;

public class ProductOrderCompositeKey implements Serializable {

    private String productId;

    private String orderId;

    public ProductOrderCompositeKey(String productId, String orderId) {
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
        return Objects.equals(productId, compositeKey.productId ) &&
                Objects.equals(orderId, compositeKey.orderId );
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, orderId);
    }
}
