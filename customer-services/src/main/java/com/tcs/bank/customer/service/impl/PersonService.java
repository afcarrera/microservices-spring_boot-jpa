package com.tcs.bank.customer.service.impl;

import com.tcs.bank.customer.common.Conflict;
import com.tcs.bank.customer.common.NotFound;
import com.tcs.bank.customer.dto.common.MappingDTO;
import com.tcs.bank.customer.dto.impl.PersonDTO;
import com.tcs.bank.customer.entity.PersonEntity;
import com.tcs.bank.customer.exception.ResourceNotFoundException;
import com.tcs.bank.customer.exception.ValidationException;
import com.tcs.bank.customer.repository.IPersonRepository;
import com.tcs.bank.customer.service.IPersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Person service implementation.
 *
 * @author acarrera
 * @version 1.0
 */
@Service
public class PersonService implements IPersonService {
    @Autowired
    private IPersonRepository iPersonRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO create(PersonDTO personDTO) {
        this.validateUniqueIdentification(personDTO.getIdentification());
        PersonEntity personEntity = (PersonEntity) MappingDTO.convertToEntity(personDTO, PersonEntity.class);
        PersonDTO newPersonDTO = new PersonDTO();
        newPersonDTO = (PersonDTO) MappingDTO.convertToDto(
                iPersonRepository.save(personEntity), newPersonDTO);
        return newPersonDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO update(PersonDTO personDTOToUpdate) {
        PersonDTO personDTO = this.findById(personDTOToUpdate.getPersonId());
        BeanUtils.copyProperties(personDTO, personDTOToUpdate);
        this.validateUniqueIdentification(personDTO.getIdentification());
        PersonEntity personEntity = (PersonEntity) MappingDTO.convertToEntity(personDTO, PersonEntity.class);
        PersonDTO updatedPersonDTO = new PersonDTO();
        updatedPersonDTO = (PersonDTO) MappingDTO.convertToDto(
                iPersonRepository.save(personEntity), updatedPersonDTO);
        return updatedPersonDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<PersonDTO> findAll() {
        List<PersonEntity> allPersonEntities = iPersonRepository.findAll();
        return allPersonEntities.stream()
                .map(customerEntity -> (PersonDTO) MappingDTO.convertToDto( customerEntity, new PersonDTO()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO findById(String id) {
        PersonEntity personEntity = this.findPersonEntityById(id);
        PersonDTO personDTO = new PersonDTO();
        personDTO = (PersonDTO) MappingDTO.convertToDto(
                personEntity, personDTO);
        return personDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        this.iPersonRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonDTO findByIdentification(String identification) {
        PersonEntity personEntity = this.findPersonEntityByIdentification(identification);
        PersonDTO personDTO = new PersonDTO();
        personDTO = (PersonDTO) MappingDTO.convertToDto(
                personEntity, personDTO);
        return personDTO;
    }

    private PersonEntity findPersonEntityById(String id){
        Optional<PersonEntity> personOptional = iPersonRepository.findById(id);
        return personOptional.orElseThrow(() -> new ResourceNotFoundException(NotFound.NOT_FOUND_PERSON.toString()));
    }

    private PersonEntity findPersonEntityByIdentification(String id){
        Optional<PersonEntity> personOptional = iPersonRepository.findByIdentification(id);
        return personOptional.orElseThrow(() -> new ResourceNotFoundException(NotFound.NOT_FOUND_PERSON.toString()));
    }

    private void validateUniqueIdentification(String identification) {
        Optional<PersonEntity> existingPerson = iPersonRepository.findByIdentification(identification);
        if (existingPerson.isPresent()) {
            throw new ValidationException(Conflict.GENERIC_PERSON_CONFLICT.toString());
        }
    }
}
