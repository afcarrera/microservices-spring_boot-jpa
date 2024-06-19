package com.tcs.bank.account;

import com.tcs.bank.account.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class AccountServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServicesApplication.class, args);
	}

}
