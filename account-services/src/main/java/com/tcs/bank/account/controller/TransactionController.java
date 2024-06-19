package com.tcs.bank.account.controller;


import com.tcs.bank.account.dto.impl.TransactionDTO;
import com.tcs.bank.account.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping
    public TransactionDTO create(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.create(transactionDTO);
    }

    @GetMapping
    public Collection<TransactionDTO> findAll() {
        return transactionService.findAll();
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO findById(@PathVariable("transactionId") String id) {
        return transactionService.findById(id);
    }

    @PutMapping("/{transactionId}")
    public TransactionDTO update(@PathVariable("transactionId") String id, @RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setTransactionId(id);
        return transactionService.update(transactionDTO);
    }

    @DeleteMapping("/{transactionId}")
    public void delete(@PathVariable("transactionId") String id) {
        transactionService.delete(id);
    }

    @GetMapping("/by-customer/{customerId}")
    public Collection<TransactionDTO> findByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("customerId") String customerId) {
        return transactionService.findByDateBetween(startDate, endDate, customerId);
    }
}