package com.carrera.bank.account.controller;


import com.carrera.bank.account.common.ValidationGroups;
import com.carrera.bank.account.dto.impl.TransactionDTO;
import com.carrera.bank.account.service.ITransactionService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public TransactionDTO create(@Validated(ValidationGroups.Create.class) @RequestBody TransactionDTO transactionDTO) {
        return transactionService.create(transactionDTO);
    }

    @GetMapping
    public Page<TransactionDTO> findAll(@RequestParam(defaultValue = "0") @Min(0) int page,
                                    @RequestParam(defaultValue = "10") @Min(0) @Max(100) int size) {
        return transactionService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{transactionId}")
    public TransactionDTO findById(@PathVariable("transactionId") String id) {
        return transactionService.findById(id);
    }

    @DeleteMapping("/{transactionId}")
    public void delete(@PathVariable("transactionId") String id) {
        transactionService.delete(id);
    }
}