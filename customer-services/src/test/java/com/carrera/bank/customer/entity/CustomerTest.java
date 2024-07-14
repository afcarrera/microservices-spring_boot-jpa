package com.carrera.bank.customer.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
    @Test
    public void testCustomerBuilder() {
        String expectedPassword = "132456";
        String expectedPersonId = "1";
        String expectedCustomerId = "1";

        CustomerEntity customerEntity = CustomerEntity.builder()
                .customerId(expectedCustomerId)
                .personId(expectedPersonId)
                .password(expectedPassword)
                .status(Boolean.FALSE)
                .build();

        assertEquals(expectedPassword, customerEntity.getPassword());
        assertEquals(expectedPersonId, customerEntity.getPersonId());
        assertEquals(expectedCustomerId, customerEntity.getCustomerId());
        assertEquals(Boolean.FALSE, customerEntity.isStatus());
    }
}
