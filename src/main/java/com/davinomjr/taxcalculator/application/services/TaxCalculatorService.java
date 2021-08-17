package com.davinomjr.taxcalculator.application.services;

import com.davinomjr.taxcalculator.application.handlers.HandlerConfiguration;
import com.davinomjr.taxcalculator.application.handlers.SalesTaxHandler;
import com.davinomjr.taxcalculator.application.interfaces.IOrderService;
import com.davinomjr.taxcalculator.application.interfaces.IProductService;
import com.davinomjr.taxcalculator.application.interfaces.ITaxCalculatorService;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.core.entities.OrderItem;
import com.davinomjr.taxcalculator.core.entities.OrderReceipt;
import com.davinomjr.taxcalculator.core.entities.Product;
import com.davinomjr.taxcalculator.core.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TaxCalculatorService implements ITaxCalculatorService {

    private final IOrderService orderService;
    private final IProductService productService;
    private final HandlerConfiguration handlerConfiguration;

    private List<Product> products;
    private SalesTaxHandler chain;

    public TaxCalculatorService(final IProductService productService,
                                final IOrderService orderService,
                                final HandlerConfiguration handlerConfiguration)
    {
        this.orderService = orderService;
        this.productService = productService;
        this.handlerConfiguration = handlerConfiguration;
        this.chain =  this.handlerConfiguration.getConfiguration();
    }

    @Override
    public OrderReceipt calculate(Order order) throws ProductNotFoundException {
        products = productService.findAll();
        OrderReceipt receipt = new OrderReceipt();
        for(OrderItem item : order.getItems()) {
            Optional<Product> matchedProduct = matchProductWithItem(item.getName().toLowerCase());
            if (matchedProduct.isEmpty()) {
                throw new ProductNotFoundException("Product not found.");
            }

            BigDecimal finalTax = chain.getTaxRate(matchedProduct.get(), BigDecimal.ZERO);
            receipt.addItem(new OrderItem(item, finalTax));
        }

        orderService.saveOrUpdate(new Order(order.getDate(), receipt.getOrderItems()));
        return receipt;
    }

    private Optional<Product> matchProductWithItem(String itemName) {
        return products.stream()
                       .filter(product -> product.getName().toLowerCase().equals(itemName))
                       .findFirst();
    }
}
