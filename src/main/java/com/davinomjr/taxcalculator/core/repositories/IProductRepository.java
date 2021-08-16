package com.davinomjr.taxcalculator.core.repositories;
import org.springframework.data.repository.CrudRepository;

public interface IProductRepository<Product, String> extends CrudRepository<Product, String> {

}
