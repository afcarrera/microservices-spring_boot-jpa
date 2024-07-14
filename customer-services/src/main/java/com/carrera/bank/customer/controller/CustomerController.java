package com.carrera.bank.customer.controller;

import com.carrera.bank.customer.common.ValidationGroups;
import com.carrera.bank.customer.dto.impl.CustomerDTO;
import com.carrera.bank.customer.exception.ResourceNotFoundException;
import com.carrera.bank.customer.service.ICustomerService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collection;

@RestController
@RequestMapping("v1/customers")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public CustomerDTO create(@Validated(ValidationGroups.Create.class) @RequestBody CustomerDTO customerDto){
        return this.iCustomerService.create(customerDto);
    }

    @GetMapping
    public Page<CustomerDTO> findAll(@RequestParam(defaultValue = "0") @Min(0) int page,
                                     @RequestParam(defaultValue = "10") @Min(0) @Max(100) int size){
        return this.iCustomerService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{customerId}")
    public CustomerDTO findById(@PathVariable("customerId") String id){
        CustomerDTO customerDTO = this.iCustomerService.findById(id);
        customerDTO.setPassword(null);
        return customerDTO;
    }

    @PatchMapping("/{customerId}")
    public CustomerDTO update(@PathVariable("customerId") String id,
                              @Validated(ValidationGroups.Update.class) @RequestBody CustomerDTO customerDto)
            throws ResourceNotFoundException {
        customerDto.setPersonId(id);
        customerDto.setStatus(Boolean.TRUE);
        return this.iCustomerService.update(customerDto);
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable("customerId") String id) throws ResourceNotFoundException {
        this.iCustomerService.delete(id);
    }
}
