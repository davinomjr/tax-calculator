package com.davinomjr.taxcalculator.application.interfaces;

import com.davinomjr.taxcalculator.core.entities.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    List<Product> findAll();
}
