package com.davinomjr.taxcalculator.application.interfaces;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.core.entities.OrderReceipt;
import com.davinomjr.taxcalculator.core.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface ITaxCalculatorService {
    OrderReceipt calculate(Order order) throws ProductNotFoundException;
}
