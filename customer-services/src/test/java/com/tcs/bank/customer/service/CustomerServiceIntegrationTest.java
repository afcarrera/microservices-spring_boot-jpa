package com.tcs.bank.customer.service;

import com.tcs.bank.customer.common.NotFound;
import com.tcs.bank.customer.dto.impl.CustomerDTO;
import com.tcs.bank.customer.dto.impl.PersonDTO;
import com.tcs.bank.customer.entity.CustomerEntity;
import com.tcs.bank.customer.entity.PersonEntity;
import com.tcs.bank.customer.exception.ResourceNotFoundException;
import com.tcs.bank.customer.repository.ICustomerRepository;
import com.tcs.bank.customer.service.impl.CustomerService;
import com.tcs.bank.customer.service.impl.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private ICustomerRepository customerRepository;

    @MockBean
    private PersonService personService;

    private CustomerDTO customerDTO;

    private CustomerEntity customerEntity;

    private PersonEntity personEntity;
    private PersonDTO personDTO;

    @BeforeEach
    public void setUp() {
        personDTO = new PersonDTO();
        personDTO.setPersonId("1");

        customerDTO = new CustomerDTO();
        customerDTO.setPersonId("1");
        customerDTO.setPerson(personDTO);

        personEntity = PersonEntity.builder()
                .personId("1")
                .build();

        customerEntity = CustomerEntity.builder()
                .personId("1")
                .person(personEntity)
                .build();
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        when(personService.findById(customerDTO.getPersonId()))
                .thenThrow(new ResourceNotFoundException(NotFound.NOT_FOUND_PERSON.toString()));

        when(personService.create(any(PersonDTO.class)))
                .thenReturn(customerDTO.getPerson());

        when(customerRepository.save(any(CustomerEntity.class)))
                .thenReturn(customerEntity);

        CustomerDTO createdCustomer = customerService.create(customerDTO);

        assertEquals(customerDTO.getPersonId(), createdCustomer.getPersonId());

        verify(personService, times(1)).findById(customerDTO.getPersonId());
        verify(personService, times(1)).create(createdCustomer.getPerson());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }
}
