package com.davinomjr.taxcalculator.core.repositories;
import org.springframework.data.repository.CrudRepository;

public interface IProductOrderRepository<ProductOrder, ProductOrderCompositeKey> extends CrudRepository<ProductOrder, ProductOrderCompositeKey> {

}
