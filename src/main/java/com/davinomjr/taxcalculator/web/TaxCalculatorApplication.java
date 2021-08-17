package com.davinomjr.taxcalculator.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.davinomjr"})
@EnableJpaRepositories(basePackages="com.davinomjr.taxcalculator.infrastructure")
@EntityScan("com.davinomjr.taxcalculator.core.entities")
public class TaxCalculatorApplication {
	public static void main(String[] args) {
		SpringApplication.run(TaxCalculatorApplication.class, args);
	}
}
