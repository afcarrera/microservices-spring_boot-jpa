package com.tcs.bank.account.connector;


import com.tcs.bank.account.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerHystrixFallbackFactory implements ICustomerConnector{

    @Override
    public Customer findById(String id) {
        return Customer.builder().build();
    }
}