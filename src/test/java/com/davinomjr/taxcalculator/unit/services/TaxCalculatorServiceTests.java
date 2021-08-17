package com.davinomjr.taxcalculator.unit.services;

import com.davinomjr.taxcalculator.application.handlers.BasicSalesTaxHandler;
import com.davinomjr.taxcalculator.application.handlers.ImportedSalesTaxHandler;
import com.davinomjr.taxcalculator.application.interfaces.IOrderService;
import com.davinomjr.taxcalculator.application.interfaces.IProductService;
import com.davinomjr.taxcalculator.application.interfaces.ITaxCalculatorService;
import com.davinomjr.taxcalculator.application.services.TaxCalculatorService;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.core.entities.OrderItem;
import com.davinomjr.taxcalculator.core.entities.OrderReceipt;
import com.davinomjr.taxcalculator.core.entities.Product;
import com.davinomjr.taxcalculator.core.exceptions.ProductNotFoundException;
import com.davinomjr.taxcalculator.web.TaxCalculatorApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.davinomjr.taxcalculator.application.handlers.HandlerConfiguration;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ContextConfiguration(classes = TaxCalculatorApplication.class)
public class TaxCalculatorServiceTests {

    @Mock
    private IProductService productService;

    @Mock
    private IOrderService orderService;

    @Mock
    private HandlerConfiguration handlerConfiguration;

    static final BigDecimal BASIC_RATE = BigDecimal.valueOf(10);
    static final BigDecimal IMPORTED_RATE = BigDecimal.valueOf(5);

    static List<Product> products = Arrays.asList(
                                  new Product("Book", true, false),
                                  new Product("Music CD", false, false),
                                  new Product("Chocolate Bar", true, false),
                                  new Product("Imported Box of Chocolates", true, true),
                                  new Product("Imported Bottle of Perfume", false, true),
                                  new Product("Bottle of Perfume", false, false),
                                  new Product("Packet of Headache Pills", true, false)
                                );

    @Test
    public void calculateTax_givenAnOrder_shouldReturnReceipt() throws ProductNotFoundException {
        Mockito.when(productService.findAll()).thenReturn(products);
        Mockito.when(handlerConfiguration.getConfiguration()).thenReturn(new ImportedSalesTaxHandler(new BasicSalesTaxHandler(null, BASIC_RATE), IMPORTED_RATE));
        Mockito.doNothing().when(orderService).saveOrUpdate(any(Order.class));
        ITaxCalculatorService taxCalculatorService = new TaxCalculatorService(productService, orderService, handlerConfiguration);

        Date now = new Date();
        List<OrderItem> orderItems = Arrays.asList(
                new OrderItem("book", 1, BigDecimal.valueOf(12.49), BigDecimal.ZERO),
                new OrderItem("music CD", 1, BigDecimal.valueOf(14.99), BigDecimal.valueOf(10)),
                new OrderItem("Chocolate Bar", 1, BigDecimal.valueOf(0.85), BigDecimal.ZERO)
        );

        OrderReceipt expectedReceipt = new OrderReceipt(orderItems);
        Order order = new Order(now, orderItems);

        OrderReceipt receipt = taxCalculatorService.calculate(order);

        assertThat(receipt.getOrderItems()
                          .stream().collect(Collectors.toList()))
                          .usingRecursiveComparison()
                          .isEqualTo(expectedReceipt.getOrderItems()
                                                    .stream()
                                                    .collect(Collectors.toList()));
    }

     @Test
    public void calculateTax_givenAnOrderMultipleQuantities_shouldReturnReceipt() throws ProductNotFoundException {
        Mockito.when(productService.findAll()).thenReturn(products);
        Mockito.when(handlerConfiguration.getConfiguration()).thenReturn(new ImportedSalesTaxHandler(new BasicSalesTaxHandler(null, BASIC_RATE), IMPORTED_RATE));
        Mockito.doNothing().when(orderService).saveOrUpdate(any(Order.class));
        ITaxCalculatorService taxCalculatorService = new TaxCalculatorService(productService, orderService, handlerConfiguration);

        Date now = new Date();
        List<OrderItem> orderItems = Arrays.asList(
                  new OrderItem("book", 2, BigDecimal.valueOf(12.49), BigDecimal.ZERO),
                new OrderItem("music CD", 3, BigDecimal.valueOf(14.99), BigDecimal.valueOf(10)),
                new OrderItem("Chocolate Bar", 1, BigDecimal.valueOf(0.85), BigDecimal.ZERO)
        );

        OrderReceipt expectedReceipt = new OrderReceipt(orderItems);
        Order order = new Order(now, orderItems);

        OrderReceipt receipt = taxCalculatorService.calculate(order);

        assertThat(receipt.getOrderItems()
                          .stream().collect(Collectors.toList()))
                          .usingRecursiveComparison()
                          .isEqualTo(expectedReceipt.getOrderItems()
                                                    .stream()
                                                    .collect(Collectors.toList()));
    }

    @Test
    public void calculateTax_givenAnOrderWithAProductNotPreviouslyRegistered_shouldThrowProductNotFoundException() {
        Mockito.when(productService.findAll()).thenReturn(products);
        Mockito.when(handlerConfiguration.getConfiguration()).thenReturn(new ImportedSalesTaxHandler(new BasicSalesTaxHandler(null, BASIC_RATE), IMPORTED_RATE));
        ITaxCalculatorService taxCalculatorService = new TaxCalculatorService(productService, orderService, handlerConfiguration);

        Date now = new Date();
        List<OrderItem> orderItems = Arrays.asList(
                new OrderItem("product not registered", 1, BigDecimal.valueOf(10.0), BigDecimal.valueOf(5)),
                new OrderItem("imported bottle of perfume", 1, BigDecimal.valueOf(47.50), BigDecimal.valueOf(15))
        );

        Order order = new Order(now, orderItems);

         assertThatThrownBy(() -> {
            taxCalculatorService.calculate(order);
        }).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    public void calculateTax_givenAnOrder_shouldPersistOrder() throws ProductNotFoundException {
        Mockito.when(productService.findAll()).thenReturn(products);
        Mockito.when(handlerConfiguration.getConfiguration()).thenReturn(new ImportedSalesTaxHandler(new BasicSalesTaxHandler(null, BASIC_RATE), IMPORTED_RATE));
        ITaxCalculatorService taxCalculatorService = new TaxCalculatorService(productService, orderService, handlerConfiguration);

        Date now = new Date();
        List<OrderItem> orderItems = Arrays.asList(
                new OrderItem("imported box of chocolates", 1, BigDecimal.valueOf(10.0), BigDecimal.valueOf(5)),
                new OrderItem("imported bottle of perfume", 1, BigDecimal.valueOf(47.50), BigDecimal.valueOf(15))
        );

        Order order = new Order(now, orderItems);

        taxCalculatorService.calculate(order);

        verify(orderService).saveOrUpdate(any());
    }
}
