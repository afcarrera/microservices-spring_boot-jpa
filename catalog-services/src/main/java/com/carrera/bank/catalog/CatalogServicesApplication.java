package com.carrera.bank.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableR2dbcRepositories
@EnableDiscoveryClient
@SpringBootApplication
public class CatalogServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogServicesApplication.class, args);
	}

}
