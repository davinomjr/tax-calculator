package com.davinomjr.taxcalculator.application.services;

import com.davinomjr.taxcalculator.application.interfaces.IProductService;
import com.davinomjr.taxcalculator.core.entities.Product;
import com.davinomjr.taxcalculator.infrastructure.repositories.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService implements IProductService {

    private IProductRepository productRepository;

    public ProductService(IProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
       return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                           .collect(Collectors.toList());
    }
}
