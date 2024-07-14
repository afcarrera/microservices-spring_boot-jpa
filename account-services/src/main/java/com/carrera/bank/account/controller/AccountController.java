package com.carrera.bank.account.controller;

import com.carrera.bank.account.common.ValidationGroups;
import com.carrera.bank.account.dto.impl.AccountDTO;
import com.carrera.bank.account.service.IAccountService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping
    @ResponseStatus( HttpStatus.CREATED )
    public AccountDTO create(@Validated(ValidationGroups.Create.class) @RequestBody AccountDTO accountDTO) {
        return accountService.create(accountDTO);
    }

    @GetMapping
    public Page<AccountDTO> findAll(@RequestParam(defaultValue = "0") @Min(0) int page,
                                     @RequestParam(defaultValue = "10") @Min(0) @Max(100) int size) {
        return accountService.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{accountId}")
    public AccountDTO findById(@PathVariable("accountId") String id) {
        return accountService.findById(id);
    }

    @PutMapping("/{accountId}")
    public AccountDTO update(
            @PathVariable("accountId") String id,
            @Validated(ValidationGroups.Update.class)
            @RequestBody AccountDTO accountDTO) {
        accountDTO.setAccountId(id);
        accountDTO.setStatus(Boolean.TRUE);
        return accountService.update(accountDTO);
    }

    @DeleteMapping("/{accountId}")
    public void delete(@PathVariable("accountId") String id) {
        accountService.delete(id);
    }
}