package com.davinomjr.taxcalculator.application.services;

import com.davinomjr.taxcalculator.application.interfaces.IOrderService;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.infrastructure.repositories.IOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.*;

@Service
public class OrderService implements IOrderService {

    private IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Override
    public void saveOrUpdate(Order order){
        orderRepository.save(order);
    }


}
