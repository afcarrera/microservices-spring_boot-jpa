package com.carrera.bank.customer.service;

import com.carrera.bank.customer.dto.impl.CustomerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Interface for Customer Service.
 * Defines the operations for managing customers.
 *
 * @author acarrera
 * @version 1.0
 */
public interface ICustomerService {

    /**
     * Creates a new customer in the system.
     *
     * @param customerDTO the customer data transfer object containing the details of the customer to create
     * @return the created customer data transfer object
     */
    CustomerDTO create(CustomerDTO customerDTO);

    /**
     * Updates an existing customer in the system.
     *
     * @param customerDTO the customer data transfer object containing the updated details of the customer
     * @return the updated customer data transfer object
     */
    CustomerDTO update(CustomerDTO customerDTO);

    /**
     * Retrieves all customers from the system.
     *
     * @param pageable Pageable filters
     * @return a collection of customer data transfer objects
     */
    Page<CustomerDTO> findAll(Pageable pageable);

    /**
     * Retrieves a customer by their unique identifier.
     *
     * @param id the unique identifier of the customer to retrieve
     * @return the customer data transfer object if found
     */
    CustomerDTO findById(String id);

    /**
     * Deletes a customer from the system by their unique identifier.
     *
     * @param id the unique identifier of the customer to delete
     */
    void delete(String id);
}
