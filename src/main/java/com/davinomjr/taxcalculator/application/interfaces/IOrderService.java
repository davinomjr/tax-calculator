package com.davinomjr.taxcalculator.application.interfaces;
import com.davinomjr.taxcalculator.core.entities.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    List<Order> getAll();
    void saveOrUpdate(Order order);
}
