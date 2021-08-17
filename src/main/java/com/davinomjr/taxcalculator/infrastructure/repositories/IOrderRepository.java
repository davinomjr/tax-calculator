package com.davinomjr.taxcalculator.infrastructure.repositories;
import org.springframework.data.repository.CrudRepository;
import com.davinomjr.taxcalculator.core.entities.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends CrudRepository<Order, Integer> {

}
