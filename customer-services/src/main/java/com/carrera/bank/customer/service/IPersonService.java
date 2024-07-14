package com.carrera.bank.customer.service;

import com.carrera.bank.customer.dto.impl.PersonDTO;

import java.util.Collection;

/**
 * Interface for Person Service.
 * Defines the operations for managing persons.
 *
 * @author acarrera
 * @version 1.0
 */
public interface IPersonService {

    /**
     * Creates a new person in the system.
     *
     * @param personDTO the person data transfer object containing the details of the person to create
     * @return the created person data transfer object
     */
    PersonDTO create(PersonDTO personDTO);

    /**
     * Updates an existing person in the system.
     *
     * @param personDTO the person data transfer object containing the updated details of the person
     * @return the updated person data transfer object
     */
    PersonDTO update(PersonDTO personDTO);

    /**
     * Retrieves all persons from the system.
     *
     * @return a collection of person data transfer objects
     */
    Collection<PersonDTO> findAll();

    /**
     * Retrieves a person by their unique identifier.
     *
     * @param id the unique identifier of the person to retrieve
     * @return the person data transfer object if found
     */
    PersonDTO findById(String id);

    /**
     * Deletes a person from the system by their unique identifier.
     *
     * @param id the unique identifier of the person to delete
     */
    void delete(String id);

    /**
     * Retrieves a person by their identifier.
     *
     * @param identification the identifier of the person to retrieve
     * @param identificationUpdated the identifier of the person to retrieve
     * @return the person data transfer object if found
     */
    PersonDTO findByIdentification(String identification, String identificationUpdated);
}
