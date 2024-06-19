package com.tcs.bank.account.service.impl;

import com.tcs.bank.account.common.NotFound;
import com.tcs.bank.account.connector.ICustomerConnector;
import com.tcs.bank.account.dto.impl.AccountDTO;
import com.tcs.bank.account.entity.AccountEntity;
import com.tcs.bank.account.exception.ResourceNotFoundException;
import com.tcs.bank.account.repository.IAccountRepository;
import com.tcs.bank.account.service.IAccountService;
import com.tcs.bank.account.dto.common.MappingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Account service implementation.
 *
 * @author acarrera
 * @version 1.0
 */
@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICustomerConnector iCustomerConnector;

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        this.iCustomerConnector.findById(accountDTO.getCustomerId());
        AccountEntity accountEntity = (AccountEntity) MappingDTO.convertToEntity(accountDTO, AccountEntity.class);
        accountEntity.setStatus(Boolean.TRUE);
        AccountDTO newAccountDTO = new AccountDTO();
        newAccountDTO = (AccountDTO) MappingDTO.convertToDto(accountRepository.save(accountEntity), newAccountDTO);
        return newAccountDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        this.findById(accountDTO.getAccountId());
        AccountEntity accountEntity = (AccountEntity) MappingDTO.convertToEntity(accountDTO, AccountEntity.class);
        AccountDTO updatedAccountDTO = new AccountDTO();
        updatedAccountDTO = (AccountDTO) MappingDTO.convertToDto(accountRepository.save(
                accountEntity), updatedAccountDTO);
        return updatedAccountDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<AccountDTO> findAll() {
        List<AccountEntity> allAccountEntities = accountRepository.findAll();
        return allAccountEntities.stream()
                .filter(AccountEntity::isStatus)
                .map(accountEntity -> (AccountDTO) MappingDTO.convertToDto( accountEntity, new AccountDTO()))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO findById(String id) {
        AccountEntity accountEntity = findAccountEntityById(id);
        if (Objects.isNull(accountEntity) || Boolean.FALSE.equals(accountEntity.isStatus())) {
            throw new ResourceNotFoundException(NotFound.NOT_FOUND_ACCOUNT.toString());
        }
        return (AccountDTO) MappingDTO.convertToDto(accountEntity, new AccountDTO());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        AccountDTO accountDTO = findById(id);
        accountDTO.setStatus(Boolean.FALSE);
        this.update(accountDTO);
    }

    private AccountEntity findAccountEntityById(String id) {
        return accountRepository.findById(id).orElse(null);
    }
}