package com.carrera.bank.account.service.impl;

import com.carrera.bank.account.common.Conflict;
import com.carrera.bank.account.common.Constants;
import com.carrera.bank.account.common.NotFound;
import com.carrera.bank.account.connector.ICatalogConnector;
import com.carrera.bank.account.connector.ICustomerConnector;
import com.carrera.bank.account.dto.external.Catalog;
import com.carrera.bank.account.dto.impl.AccountDTO;
import com.carrera.bank.account.dto.impl.TransactionDTO;
import com.carrera.bank.account.entity.TransactionEntity;
import com.carrera.bank.account.exception.ResourceNotFoundException;
import com.carrera.bank.account.exception.ValidationException;
import com.carrera.bank.account.repository.ITransactionRepository;
import com.carrera.bank.account.service.IAccountService;
import com.carrera.bank.account.service.ICatalogService;
import com.carrera.bank.account.service.ITransactionService;
import com.carrera.bank.account.dto.common.MappingDTO;
import com.carrera.bank.account.dto.external.Customer;

import feign.FeignException;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Transaction service implementation.
 *
 * @author acarrera
 * @version 1.0
 */

@Slf4j
@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private ITransactionRepository iTransactionRepository;

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private ICustomerConnector iCustomerConnector;

    @Autowired
    private ICatalogConnector iCatalogConnector;

    @Autowired
    private ICatalogService iCatalogService;

    /**
     * {@inheritDoc}
     */
    @Retryable(
            retryFor = {CannotAcquireLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public TransactionDTO create(TransactionDTO transactionDTO) {
        this.validateCatalogByTypeAndValue(transactionDTO);
        this.validateAmount(transactionDTO);
        AccountDTO accountDTO = this.iAccountService.findById(transactionDTO.getAccountId());
        this.validateAccountBalance(accountDTO, transactionDTO.getAmount());
        TransactionEntity transactionEntity = (TransactionEntity) MappingDTO.convertToEntity(
                transactionDTO, TransactionEntity.class);
        transactionEntity.setBalance(accountDTO.getInitialBalance().add(transactionDTO.getAmount()));
        transactionEntity.setDate(LocalDateTime.now());
        TransactionDTO newTransactionDTO = new TransactionDTO();
        newTransactionDTO = (TransactionDTO) MappingDTO.convertToDto(this.iTransactionRepository.save(
                transactionEntity), newTransactionDTO);
        updateAccountBalance(accountDTO, newTransactionDTO.getBalance());
        return newTransactionDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionDTO update(TransactionDTO transactionDTO) {
        this.findById(transactionDTO.getTransactionId());
        TransactionEntity transactionEntity = (TransactionEntity) MappingDTO.convertToEntity(
                transactionDTO, TransactionEntity.class);
        TransactionDTO updatedTransactionDTO = new TransactionDTO();
        updatedTransactionDTO = (TransactionDTO) MappingDTO.convertToDto(this.iTransactionRepository.save(
                transactionEntity), updatedTransactionDTO);
        return updatedTransactionDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TransactionDTO> findAll(Pageable pageable) {
        Page<TransactionEntity> allTransactionEntities = this.iTransactionRepository.findAll(pageable);
        return mapTransactionEntityPageToDTO(allTransactionEntities, null, getAccountCatalog());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionDTO findById(String id) {
        TransactionEntity transactionEntity = this.findTransactionEntityById(id);
        if (Objects.isNull(transactionEntity)) {
            throw new ResourceNotFoundException(NotFound.NOT_FOUND_TRANSACTION.toString());
        }
        return this.mapTransactionEntityToDTO(transactionEntity, getAccountCatalog());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        this.iTransactionRepository.delete(this.findTransactionEntityById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<TransactionDTO> findByDateBetween(
            LocalDateTime startDate, LocalDateTime endDate, String customerId, Pageable pageable) {
        Customer customer = this.findCustomerById(customerId);
        Page<TransactionEntity> allTransactionEntities
                = this.iTransactionRepository.getByQueryDate(startDate, endDate, customerId, pageable);
        return mapTransactionEntityPageToDTO(allTransactionEntities, customer, getAccountCatalog());
    }

    private TransactionEntity findTransactionEntityById(String id) {
        return this.iTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                NotFound.NOT_FOUND_TRANSACTION.toString()));
    }

    private void updateAccountBalance(AccountDTO account, BigDecimal balance) {
        account.setInitialBalance(balance);
        this.iAccountService.update(account);
    }

    private void validateAccountBalance(AccountDTO account,  BigDecimal balance) {
        if (balance.add(account.getInitialBalance()).compareTo(BigDecimal.ZERO) < BigDecimal.ZERO.intValue()) {
            throw new ValidationException(Conflict.INVALID_TRANSACTION.toString());
        }
    }

    @Retryable(
            retryFor = {RetryableException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    private void validateCatalogByTypeAndValue(TransactionDTO transactionDTO){

        List<Catalog> catalogs = this.iCatalogConnector.findByTypeAndValue(Catalog.builder()
                .code(transactionDTO.getTransactionValueCode())
                .typeCode(transactionDTO.getTransactionTypeCode()).build());
        if (catalogs.isEmpty() ||
                !transactionDTO.getTransactionTypeCode().equals(Constants.TRANSACTION_CATALOG_TYPE)){
            throw new ValidationException(Conflict.GENERIC_TRANSACTION_CONFLICT.toString());
        }
    }

    private Customer findCustomerById(String customerId){
        try{
            return this.iCustomerConnector.findById(customerId);
        }catch (FeignException ex){
            log.error(ex.getMessage());
            return Customer.builder().customerId(customerId).name(Constants.UNAVAILABLE_CUSTOMER).build();
        }
    }

    private void validateAmount(TransactionDTO transactionDTO){
        switch (transactionDTO.getAmount().compareTo(BigDecimal.ZERO)) {
            case 0 -> {
                throw new ValidationException(Conflict.INVALID_AMOUNT.toString());
            }
            case -1 -> {
                if(transactionDTO.getTransactionValueCode().equals(Constants.DEPOSIT_CATALOG_VALUE)){
                    throw new ValidationException(Conflict.INVALID_AMOUNT.toString());
                }
            }
            case 1 -> {
                if(transactionDTO.getTransactionValueCode().equals(Constants.WITHDRAW_CATALOG_VALUE)){
                    throw new ValidationException(Conflict.INVALID_AMOUNT.toString());
                }
            }
        }
    }

    private Map<String, String> getAccountCatalog() {
        return iCatalogService.getCatalogValueByTypeAndValue(Constants.ACCOUNT_CATALOG_TYPE, null);
    }

    private Page<TransactionDTO> mapTransactionEntityPageToDTO(
            Page<TransactionEntity> transactionEntityPage, Customer customer, Map<String, String> catalogMap) {
        return transactionEntityPage.map(transactionEntity -> {
            TransactionDTO transactionDTO = mapTransactionEntityToDTO(transactionEntity, catalogMap);
            transactionDTO.setCustomer(customer);
            return transactionDTO;
        });
    }

    private TransactionDTO mapTransactionEntityToDTO(
            TransactionEntity transactionEntity, Map<String, String> catalogMap) {
        TransactionDTO transactionDTO =
                (TransactionDTO) MappingDTO.convertToDto(transactionEntity, new TransactionDTO());
        if (Objects.nonNull(transactionDTO.getAccount())) {
            String catalogValue = catalogMap.getOrDefault(
                    transactionDTO.getAccount().getAccountValueCode(), Constants.UNAVAILABLE_CATALOG);
            transactionDTO.getAccount().setAccountTypeValue(catalogValue);
        }
        return transactionDTO;
    }
}