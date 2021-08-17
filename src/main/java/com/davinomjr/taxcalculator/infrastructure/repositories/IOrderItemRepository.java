package com.davinomjr.taxcalculator.infrastructure.repositories;

import com.davinomjr.taxcalculator.core.entities.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderItemRepository extends CrudRepository<OrderItem, Integer> {

}
