package com.davinomjr.taxcalculator.unit.controllers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.davinomjr.taxcalculator.application.services.TaxCalculatorService;
import com.davinomjr.taxcalculator.core.entities.Order;
import com.davinomjr.taxcalculator.core.entities.OrderItem;
import com.davinomjr.taxcalculator.core.entities.OrderReceipt;
import com.davinomjr.taxcalculator.core.exceptions.ProductNotFoundException;
import com.davinomjr.taxcalculator.web.TaxCalculatorApplication;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes= TaxCalculatorApplication.class)
public class TaxCalculatorControllerTests {

    @Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaxCalculatorService service;

	@Test
	public void calculate_whenGivenAValidOrderJson_shouldReturnCorrectResponse() throws Exception {
        List<OrderItem> orderItems = Arrays.asList(
                new OrderItem("book", 1, BigDecimal.valueOf(12.49), BigDecimal.ZERO),
                new OrderItem("music CD", 1, BigDecimal.valueOf(14.99), BigDecimal.valueOf(10)),
                new OrderItem("Chocolate Bar", 1, BigDecimal.valueOf(0.85), BigDecimal.ZERO)
        );

        OrderReceipt expectedReceipt = new OrderReceipt(orderItems);
        when(service.calculate(any(Order.class))).thenReturn(expectedReceipt);

        String input = "[{\"name\":\"book\",\"quantity\":1,\"value\":12.49},{\"name\":\"music CD\",\"quantity\":1,\"value\":14.99},{\"name\":\"Chocolate Bar\",\"quantity\":1,\"value\":0.85}]";
        String expectedOutput = "{\"Basket\":[{\"Item\":{\"Name\":\"book\",\"Quantity\":1,\"Value\":\"12.49\"}},{\"Item\":{\"Name\":\"music CD\",\"Quantity\":1,\"Value\":\"16.50\"}},{\"Item\":{\"Name\":\"Chocolate Bar\",\"Quantity\":1,\"Value\":\"0.85\"}}],\"Sales Taxes\":\"1.51\",\"Total\":\"29.84\"}";

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/tax/calculate")
                                       .content(input)
                                       .contentType(MediaType.APPLICATION_JSON))
                                       .andDo(print())
                                       .andExpect(status().isOk())
                                       .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content.replaceAll("\\s", "")).isEqualTo(expectedOutput.replaceAll("\\s", ""));
	}

	@Test
	public void calculate_whenGivenAInvalidOrderJson_shouldReturnGenericError() throws Exception {
        List<OrderItem> orderItems = Arrays.asList(
                new OrderItem("book", 1, BigDecimal.valueOf(12.49), BigDecimal.ZERO),
                new OrderItem("music CD", 1, BigDecimal.valueOf(14.99), BigDecimal.valueOf(10)),
                new OrderItem("Chocolate Bar", 1, BigDecimal.valueOf(0.85), BigDecimal.ZERO)
        );

        OrderReceipt expectedReceipt = new OrderReceipt(orderItems);
        when(service.calculate(any(Order.class))).thenReturn(expectedReceipt);

        String input = "[{\"nameWRONG\":\"book\",\"quantity\":1,\"value\":12.49},{\"name\":\"music CD\",\"quantity\":1,\"value\":14.99},{\"name\":\"Chocolate Bar\",\"quantity\":1,\"value\":0.85}]";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/tax/calculate")
                                       .content(input)
                                       .contentType(MediaType.APPLICATION_JSON))
                                       .andDo(print())
                                       .andExpect(status().is4xxClientError());
	}

	@Test
	public void calculate_whenGivenAValidOrderWithNonExistentProductJson_shouldReturnErrorProductNotFound() throws Exception {
        String input = "[{\"name\":\"product_that_does_not_exist\",\"quantity\":1,\"value\":12.49},{\"name\":\"music CD\",\"quantity\":1,\"value\":14.99},{\"name\":\"Chocolate Bar\",\"quantity\":1,\"value\":0.85}]";
        final String errorMessage = "Product not found.";
        when(service.calculate(any(Order.class))).thenThrow(new ProductNotFoundException(errorMessage));
        this.mockMvc.perform(MockMvcRequestBuilders.post("/tax/calculate")
                                       .content(input)
                                       .contentType(MediaType.APPLICATION_JSON))
                                       .andDo(print())
                                       .andExpect(status().is4xxClientError())
                                       .andExpect(status().reason(containsString(errorMessage)));
	}

}
