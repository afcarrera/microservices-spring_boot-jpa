package com.carrera.bank.account.service.impl;

import com.carrera.bank.account.common.Conflict;
import com.carrera.bank.account.common.Constants;
import com.carrera.bank.account.common.NotFound;
import com.carrera.bank.account.connector.ICatalogConnector;
import com.carrera.bank.account.connector.ICustomerConnector;
import com.carrera.bank.account.dto.external.Catalog;
import com.carrera.bank.account.dto.external.Customer;
import com.carrera.bank.account.dto.impl.AccountDTO;
import com.carrera.bank.account.dto.impl.TransactionDTO;
import com.carrera.bank.account.entity.AccountEntity;
import com.carrera.bank.account.entity.TransactionEntity;
import com.carrera.bank.account.exception.ResourceNotFoundException;
import com.carrera.bank.account.exception.ValidationException;
import com.carrera.bank.account.repository.IAccountRepository;
import com.carrera.bank.account.service.IAccountService;
import com.carrera.bank.account.dto.common.MappingDTO;
import com.carrera.bank.account.service.ICatalogService;
import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Account service implementation.
 *
 * @author acarrera
 * @version 1.0
 */
@Slf4j
@Service
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ICustomerConnector iCustomerConnector;

    @Autowired
    private ICatalogConnector iCatalogConnector;

    @Autowired
    private ICatalogService iCatalogService;

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        accountDTO.setStatus(Boolean.TRUE);
        return save(accountDTO, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO update(AccountDTO accountDTO) {
        this.findById(accountDTO.getAccountId());
        return save(accountDTO, accountDTO.getAccountNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<AccountDTO> findAll(Pageable pageable) {
        Page<AccountEntity> allAccountEntities = this.accountRepository.findAllActiveItems(pageable);
        return this.mapAccountEntityPageToDTO(allAccountEntities, this.getAccountCatalog());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO findById(String id) {
        AccountEntity accountEntity = this.findAccountEntityById(id);
        if (Boolean.FALSE.equals(accountEntity.isStatus())) {
            throw new ResourceNotFoundException(NotFound.NOT_FOUND_ACCOUNT.toString());
        }
        return this.mapAccountEntityToDTO(accountEntity, this.getAccountCatalog());
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

    private AccountDTO save(AccountDTO accountDTO, String accountNumberUpdate){
        this.validateUniqueIdentification(accountDTO.getAccountNumber(), accountNumberUpdate);
        this.validateCatalogByTypeAndValue(accountDTO.getAccountTypeCode(), accountDTO.getAccountValueCode());
        this.validateCustomerById(accountDTO.getCustomerId());
        AccountEntity accountEntity = (AccountEntity) MappingDTO.convertToEntity(accountDTO, AccountEntity.class);
        AccountDTO newAccountDTO = new AccountDTO();
        newAccountDTO = (AccountDTO) MappingDTO.convertToDto(this.accountRepository.save(
                accountEntity), newAccountDTO);
        return newAccountDTO;
    }

    private AccountEntity findAccountEntityById(String id) {
        return accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                NotFound.NOT_FOUND_ACCOUNT.toString()));
    }

    private void validateUniqueIdentification(String accountNumber, String accountNumberUpdate) {
        Optional<AccountEntity> existingAccount = this.accountRepository.findByAccountNumber(
                accountNumber, accountNumberUpdate);
        if (existingAccount.isPresent()) {
            throw new ValidationException(Conflict.GENERIC_ACCOUNT_CONFLICT.toString());
        }
    }

    @Retryable(
            retryFor = {RetryableException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    private void validateCatalogByTypeAndValue(String accountTypeCode, String accountValueCode){
        List<Catalog> catalogs = this.iCatalogConnector.findByTypeAndValue(Catalog.builder()
                .code(accountValueCode)
                .typeCode(accountTypeCode).build());
        if (catalogs.isEmpty() || !accountTypeCode.equals(Constants.ACCOUNT_CATALOG_TYPE)){
            throw new ValidationException(Conflict.GENERIC_ACCOUNT_CONFLICT.toString());
        }
    }

    @Retryable(
            retryFor = {RetryableException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    private void validateCustomerById(String customerId) {
        this.iCustomerConnector.findById(customerId);
    }
    private Map<String, String> getAccountCatalog() {
        return iCatalogService.getCatalogValueByTypeAndValue(Constants.ACCOUNT_CATALOG_TYPE, null);
    }

    private Page<AccountDTO> mapAccountEntityPageToDTO(
            Page<AccountEntity> accountEntityPage, Map<String, String> catalogMap) {
        return accountEntityPage.map(accountEntity -> mapAccountEntityToDTO(accountEntity, catalogMap));
    }

    private AccountDTO mapAccountEntityToDTO(
            AccountEntity accountEntity, Map<String, String> catalogMap) {
        AccountDTO accountDTO = (AccountDTO) MappingDTO.convertToDto(accountEntity, new AccountDTO());
        String catalogValue = Objects.nonNull(catalogMap.get(accountDTO.getAccountValueCode()))?
                catalogMap.get(accountDTO.getAccountValueCode()):Constants.UNAVAILABLE_CATALOG;
        accountDTO.setAccountTypeValue(catalogValue);
        return accountDTO;
    }

}