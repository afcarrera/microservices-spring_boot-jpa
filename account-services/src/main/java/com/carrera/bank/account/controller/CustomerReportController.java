package com.carrera.bank.account.controller;

import com.carrera.bank.account.dto.impl.TransactionDTO;
import com.carrera.bank.account.service.impl.TransactionService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/v1/customers")
public class CustomerReportController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{customerId}/transactions/report")
    public Page<TransactionDTO> findByDateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("customerId") String customerId,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(0) @Max(100) int size) {
        return transactionService.findByDateBetween(startDate, endDate, customerId, PageRequest.of(page, size));
    }
}
