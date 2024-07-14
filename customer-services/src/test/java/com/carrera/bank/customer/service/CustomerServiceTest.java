package com.carrera.bank.customer.service;

import com.carrera.bank.customer.common.Constants;
import com.carrera.bank.customer.common.NotFound;
import com.carrera.bank.customer.connector.ICatalogConnector;
import com.carrera.bank.customer.dto.external.Catalog;
import com.carrera.bank.customer.dto.impl.CustomerDTO;
import com.carrera.bank.customer.entity.CustomerEntity;
import com.carrera.bank.customer.exception.ResourceNotFoundException;
import com.carrera.bank.customer.repository.ICustomerRepository;
import com.carrera.bank.customer.service.impl.CustomerService;
import com.carrera.bank.customer.service.impl.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private ICustomerRepository customerRepository;

    @MockBean
    private ICatalogConnector catalogConnector;

    @MockBean
    private PersonService personService;

    private CustomerDTO customerDTO;

    private CustomerEntity customerEntity;

    private Catalog catalog;

    @BeforeEach
    public void setUp() {

        this.customerDTO = CustomerDTO.builder()
                .personId("1")
                .password("password")
                .identification("identification")
                .name("NAME")
                .genderTypeCode(Constants.GENDER_CATALOG_TYPE)
                .genderValueCode("VALUE")
                .build();

        this.customerEntity = CustomerEntity.builder()
                .personId("1")
                .password("password")
                .identification("identification")
                .name("NAME")
                .genderTypeCode(Constants.GENDER_CATALOG_TYPE)
                .genderValueCode("VALUE")
                .build();

        this.catalog = new Catalog();
        catalog.setCode("CODE");
        catalog.setValue("VALUE");
        catalog.setTypeCode(Constants.GENDER_CATALOG_TYPE);
    }

    @Test
    public void testCreateCustomer() {
        when(this.customerRepository.save(any(CustomerEntity.class)))
                .thenReturn(this.customerEntity);

        when(this.catalogConnector.findByTypeAndValue(any(Catalog.class)))
                .thenReturn(List.of(catalog));

        when(this.personService.findByIdentification(
                this.customerDTO.getIdentification(), null))
                .thenThrow(new ResourceNotFoundException(NotFound.NOT_FOUND_PERSON.toString()));

        CustomerDTO createdCustomer = this.customerService.create(this.customerDTO);

        assertEquals(this.customerDTO.getPersonId(), createdCustomer.getPersonId());
        assertEquals(this.customerDTO.getIdentification(), createdCustomer.getIdentification());

        verify(this.customerRepository, times(1)).save(any(CustomerEntity.class));
    }
}
