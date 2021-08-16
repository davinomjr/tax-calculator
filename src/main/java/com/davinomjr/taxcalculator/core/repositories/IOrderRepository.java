package com.davinomjr.taxcalculator.core.repositories;
import org.springframework.data.repository.CrudRepository;

public interface IOrderRepository<Order, String> extends CrudRepository<Order, String> {

}
