package com.tcs.bank.customer.service.impl;

import com.tcs.bank.customer.common.Conflict;
import com.tcs.bank.customer.common.NotFound;
import com.tcs.bank.customer.dto.common.MappingDTO;
import com.tcs.bank.customer.dto.impl.CustomerDTO;
import com.tcs.bank.customer.entity.CustomerEntity;
import com.tcs.bank.customer.exception.ResourceNotFoundException;
import com.tcs.bank.customer.exception.ValidationException;
import com.tcs.bank.customer.repository.ICustomerRepository;
import com.tcs.bank.customer.service.ICustomerService;
import com.tcs.bank.customer.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Customer service implementation.
 *
 * @author acarrera
 * @version 1.0
 */
@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;
    @Autowired
    private IPersonService iPersonService;

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        if (Boolean.FALSE.equals(this.isUniqueIdentification(customerDTO.getIdentification()))){
            throw new ValidationException(Conflict.GENERIC_PERSON_CONFLICT.toString());
        }
        CustomerEntity customerEntity = (CustomerEntity) MappingDTO.convertToEntity(customerDTO, CustomerEntity.class);
        customerEntity.setStatus(Boolean.TRUE);
        CustomerDTO newCustomerDTO = new CustomerDTO();
        newCustomerDTO = (CustomerDTO) MappingDTO.convertToDto(
                iCustomerRepository.save(customerEntity), newCustomerDTO);
        return newCustomerDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        this.iPersonService.findById(customerDTO.getPersonId());
        this.findById(customerDTO.getCustomerId());
        CustomerEntity customerEntity = (CustomerEntity) MappingDTO.convertToEntity(customerDTO, CustomerEntity.class);
        CustomerDTO updatedCustomerDTO = new CustomerDTO();
        updatedCustomerDTO = (CustomerDTO) MappingDTO.convertToDto(
                iCustomerRepository.save(customerEntity), updatedCustomerDTO);
        return updatedCustomerDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<CustomerDTO> findAll() {
        List<CustomerEntity> allCustomerEntities = iCustomerRepository.findAll();
        return allCustomerEntities.stream()
                .filter(CustomerEntity::isStatus)
                .map(customerEntity -> {
                    CustomerDTO customerDTO = (CustomerDTO) MappingDTO.convertToDto( customerEntity, new CustomerDTO());
                    customerDTO.setPassword(null);
                    return customerDTO;
                })
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO findById(String id) {
        CustomerEntity customerEntity = this.findCustomerEntityById(id);
        if (Objects.isNull(customerEntity) || Boolean.FALSE.equals(customerEntity.isStatus())){
            throw new ResourceNotFoundException(NotFound.NOT_FOUND_CUSTOMER.toString());
        }
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO = (CustomerDTO) MappingDTO.convertToDto(
                customerEntity, customerDTO);
        return customerDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        CustomerDTO customerDTO = this.findById(id);
        customerDTO.setStatus(Boolean.FALSE);
        this.update(customerDTO);
    }

    private CustomerEntity findCustomerEntityById(String id){
        Optional<CustomerEntity> departmentEmployeeOptional = iCustomerRepository.findById(id);
        return departmentEmployeeOptional.orElse(null);
    }

    private boolean isUniqueIdentification(String identification) {
        boolean isUniqueIdentification = Boolean.TRUE;
        try{
            this.iPersonService.findByIdentification(identification);
            isUniqueIdentification = Boolean.FALSE;
        }catch(Exception e){

        }
        return isUniqueIdentification;
    }
}
