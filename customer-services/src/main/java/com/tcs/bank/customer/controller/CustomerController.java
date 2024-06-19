package com.tcs.bank.customer.controller;

import com.tcs.bank.customer.dto.impl.CustomerDTO;
import com.tcs.bank.customer.exception.ResourceNotFoundException;
import com.tcs.bank.customer.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("v1/customers")
public class CustomerController {
    @Autowired
    private ICustomerService iCustomerService;

    @PostMapping
    public CustomerDTO create(@RequestBody CustomerDTO customerDto){
        return this.iCustomerService.create(customerDto);
    }

    @GetMapping
    public Collection<CustomerDTO> findAll(){
        return this.iCustomerService.findAll();
    }

    @GetMapping("/{customerId}")
    public CustomerDTO findById(@PathVariable("customerId") String id){
        return this.iCustomerService.findById(id);
    }

    @PutMapping("/{customerId}")
    public CustomerDTO update(@PathVariable("customerId") String id, @RequestBody CustomerDTO customerDto)
            throws ResourceNotFoundException {
        customerDto.setCustomerId(id);
        return this.iCustomerService.update(customerDto);
    }

    @DeleteMapping("/{customerId}")
    public void delete(@PathVariable("customerId") String id) throws ResourceNotFoundException {
        this.iCustomerService.delete(id);
    }
}
