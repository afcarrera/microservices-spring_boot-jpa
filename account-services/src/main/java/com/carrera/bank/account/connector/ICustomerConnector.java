package com.carrera.bank.account.connector;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.carrera.bank.account.dto.external.Customer;

/**
 * Interface for Customer Connector Service.
 *
 * @author acarrera
 * @version 1.0
 */
@FeignClient(name = "customer-services")
public interface ICustomerConnector {
    @GetMapping("customer-services/api/v1/customers/{customerId}")
    Customer findById(@PathVariable("customerId") String id);
}
