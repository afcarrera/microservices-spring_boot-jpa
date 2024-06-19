package com.tcs.bank.customer;

import com.tcs.bank.customer.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class CustomerServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServicesApplication.class, args);
	}

}
