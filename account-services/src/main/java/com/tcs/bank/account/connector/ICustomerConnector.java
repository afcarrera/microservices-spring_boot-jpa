package com.tcs.bank.account.connector;

import com.tcs.bank.account.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-services", fallback = CustomerHystrixFallbackFactory.class)
public interface ICustomerConnector {
    @GetMapping("api/v1/customers/{customerId}")
    Customer findById(@PathVariable("customerId") String id);
}
