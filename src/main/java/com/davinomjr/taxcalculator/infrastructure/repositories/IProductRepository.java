package com.davinomjr.taxcalculator.infrastructure.repositories;

import com.davinomjr.taxcalculator.core.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends CrudRepository<Product, Integer> {

}
