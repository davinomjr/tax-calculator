package com.davinomjr.taxcalculator.application.services;

import com.davinomjr.taxcalculator.application.handlers.BasicSalesTaxHandler;
import com.davinomjr.taxcalculator.application.handlers.ImportedSalesTaxHandler;
import com.davinomjr.taxcalculator.application.handlers.SalesTaxHandler;
import com.davinomjr.taxcalculator.application.interfaces.IOrderService;
import com.davinomjr.taxcalculator.application.interfaces.IProductService;
import com.davinomjr.taxcalculator.application.interfaces.ITaxCalculatorService;
import com.davinomjr.taxcalculator.application.util.TaxRateConstants;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.core.entities.OrderItem;
import com.davinomjr.taxcalculator.core.entities.OrderReceipt;
import com.davinomjr.taxcalculator.core.entities.Product;
import com.davinomjr.taxcalculator.core.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxCalculatorService implements ITaxCalculatorService {

    private final IOrderService orderService;
    private final IProductService productService;

    private List<Product> products;
    private SalesTaxHandler chain;

    public TaxCalculatorService(final IProductService productService,
                                final IOrderService orderService)
    {
        this.orderService = orderService;
        this.productService = productService;
        this.chain = new ImportedSalesTaxHandler(new BasicSalesTaxHandler(null, TaxRateConstants.BASIC_RATE), TaxRateConstants.IMPORTED_RATE);
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

            double finalTax = chain.calculateTax(matchedProduct.get(), 0);
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
