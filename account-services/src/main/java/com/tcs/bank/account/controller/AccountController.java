package com.tcs.bank.account.controller;

import com.tcs.bank.account.dto.impl.AccountDTO;
import com.tcs.bank.account.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/v1/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping
    public AccountDTO create(@RequestBody AccountDTO accountDTO) {
        return accountService.create(accountDTO);
    }

    @GetMapping
    public Collection<AccountDTO> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{accountId}")
    public AccountDTO findById(@PathVariable("accountId") String id) {
        return accountService.findById(id);
    }

    @PutMapping("/{accountId}")
    public AccountDTO update(@PathVariable("accountId") String id, @RequestBody AccountDTO accountDTO) {
        accountDTO.setAccountId(id);
        return accountService.update(accountDTO);
    }

    @DeleteMapping("/{accountId}")
    public void delete(@PathVariable("accountId") String id) {
        accountService.delete(id);
    }
}