package com.carrera.bank.customer.service.impl;

import com.carrera.bank.customer.common.Conflict;
import com.carrera.bank.customer.common.Constants;
import com.carrera.bank.customer.common.NotFound;
import com.carrera.bank.customer.common.ObjectUtils;
import com.carrera.bank.customer.connector.ICatalogConnector;
import com.carrera.bank.customer.dto.common.MappingDTO;
import com.carrera.bank.customer.dto.external.Catalog;
import com.carrera.bank.customer.dto.impl.CustomerDTO;
import com.carrera.bank.customer.entity.CustomerEntity;
import com.carrera.bank.customer.exception.ResourceNotFoundException;
import com.carrera.bank.customer.exception.ValidationException;
import com.carrera.bank.customer.repository.ICustomerRepository;
import com.carrera.bank.customer.service.ICustomerService;
import com.carrera.bank.customer.service.IPersonService;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

/**
 * Customer service implementation.
 *
 * @author acarrera
 * @version 1.0
 */
@Service
@Slf4j
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;
    @Autowired
    private IPersonService iPersonService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICatalogConnector iCatalogConnector;

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO create(CustomerDTO customerDTO) {
        this.validateIdentification(customerDTO, null);
        this.validateCatalogByTypeAndValue(customerDTO);
        customerDTO.setName(customerDTO.getName().toUpperCase());
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
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
        CustomerDTO customerDTOSearch = this.findById(customerDTO.getPersonId());
        String customerId = customerDTOSearch.getCustomerId();
        String previousIdentification = customerDTOSearch.getIdentification();
        try{
            ObjectUtils.copyNonNullProperties(customerDTO, customerDTOSearch);
        }catch (IllegalAccessException exception){
            throw new RuntimeException(exception);
        }
        this.validateIdentification(customerDTOSearch, previousIdentification);
        this.validateCatalogByTypeAndValue(customerDTOSearch);
        CustomerEntity customerEntity = (CustomerEntity) MappingDTO.convertToEntity(
                customerDTOSearch, CustomerEntity.class);
        customerEntity.setCustomerId(customerId);
        CustomerDTO updatedCustomerDTO = new CustomerDTO();
        updatedCustomerDTO = (CustomerDTO) MappingDTO.convertToDto(
                iCustomerRepository.save(customerEntity), updatedCustomerDTO);
        return updatedCustomerDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CustomerDTO> findAll(Pageable pageable) {
        Page<CustomerEntity> allCustomerEntities = iCustomerRepository.findAll(pageable);
        return allCustomerEntities
                .map(customerEntity -> {
                    CustomerDTO customerDTO = (CustomerDTO) MappingDTO.convertToDto( customerEntity, new CustomerDTO());
                    return CustomerDTO.builder()
                            .name(customerDTO.getName())
                            .address(customerDTO.getAddress())
                            .phone(customerDTO.getPhone())
                            .age(customerDTO.getAge())
                            .password(customerDTO.getPassword())
                            .status(customerDTO.isStatus())
                            .personId(customerDTO.getPersonId())
                            .build();
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDTO findById(String id) {
        CustomerEntity customerEntity = this.findCustomerEntityById(id);
        if (Boolean.FALSE.equals(customerEntity.isStatus())){
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
        return this.iCustomerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                NotFound.NOT_FOUND_CUSTOMER.toString()));
    }

    private boolean isUniqueIdentification(String identification, String identificationUpdate) {
        boolean isUniqueIdentification = Boolean.TRUE;
        try{
            this.iPersonService.findByIdentification(identification, identificationUpdate);
            isUniqueIdentification = Boolean.FALSE;
        }catch(Exception e){
            log.info(e.getMessage());
        }
        return isUniqueIdentification;
    }

    private void validateIdentification(CustomerDTO customerDTO, String identification){
        if (Boolean.FALSE.equals(this.isUniqueIdentification(customerDTO.getIdentification(), identification))){
            throw new ValidationException(Conflict.GENERIC_PERSON_CONFLICT.toString());
        }
    }

    private void validateCatalogByTypeAndValue(CustomerDTO customerDTO){
        List<Catalog> catalogs = this.iCatalogConnector.findByTypeAndValue(Catalog.builder()
                .code(customerDTO.getGenderValueCode())
                .typeCode(customerDTO.getGenderTypeCode()).build());
        if (catalogs.size() == BigInteger.ZERO.intValue() ||
                !customerDTO.getGenderTypeCode().equals(Constants.GENDER_CATALOG_TYPE)){
            throw new ValidationException(Conflict.GENERIC_PERSON_CONFLICT.toString());
        }
    }
}
