package com.tcs.bank.account.service.impl;

import com.tcs.bank.account.common.Errors;
import com.tcs.bank.account.common.NotFound;
import com.tcs.bank.account.connector.ICustomerConnector;
import com.tcs.bank.account.dto.impl.AccountDTO;
import com.tcs.bank.account.dto.impl.TransactionDTO;
import com.tcs.bank.account.entity.TransactionEntity;
import com.tcs.bank.account.exception.ResourceNotFoundException;
import com.tcs.bank.account.exception.ValidationException;
import com.tcs.bank.account.model.Customer;
import com.tcs.bank.account.repository.ITransactionRepository;
import com.tcs.bank.account.service.IAccountService;
import com.tcs.bank.account.service.ITransactionService;
import com.tcs.bank.account.dto.common.MappingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Transaction service implementation.
 *
 * @author acarrera
 * @version 1.0
 */
@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private ITransactionRepository iTransactionRepository;

    @Autowired
    private IAccountService iAccountService;

    @Autowired
    private ICustomerConnector iCustomerConnector;

    /**
     * {@inheritDoc}
     */
    @Override
    public TransactionDTO create(TransactionDTO transactionDTO) {
        if (transactionDTO.getAmount().compareTo(BigInteger.ZERO.doubleValue()) == BigInteger.ZERO.intValue()) {
            throw new ValidationException(Errors.INVALID_AMOUNT.toString());
        }
        AccountDTO accountDTO = this.iAccountService.findById(transactionDTO.getAccountId());
        this.validateAccountBalance(accountDTO, transactionDTO.getAmount());
        TransactionEntity transactionEntity = (TransactionEntity) MappingDTO.convertToEntity(
                transactionDTO, TransactionEntity.class);
        TransactionDTO newTransactionDTO = new TransactionDTO();
        transactionEntity.setBalance(accountDTO.getInitialBalance() + transactionDTO.getAmount());
        transactionEntity.setDate(LocalDateTime.now());
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
    public Collection<TransactionDTO> findAll() {
        List<TransactionEntity> allTransactionEntities = this.iTransactionRepository.findAll();
        return allTransactionEntities.stream()
                .map(transactionEntity -> (
                        TransactionDTO) MappingDTO.convertToDto( transactionEntity, new TransactionDTO())
                ).toList();
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
        return (TransactionDTO) MappingDTO.convertToDto(transactionEntity, new TransactionDTO());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String id) {
        TransactionEntity transactionEntity = findTransactionEntityById(id);
        if (Objects.isNull(transactionEntity)) {
            throw new ResourceNotFoundException(NotFound.NOT_FOUND_TRANSACTION.toString());
        }
        this.iTransactionRepository.delete(transactionEntity);
    }

    @Override
    public Collection<TransactionDTO> findByDateBetween(
            LocalDateTime startDate, LocalDateTime endDate, String customerId) {
        Customer customer = this.iCustomerConnector.findById(customerId);
        List<TransactionEntity> allTransactionEntities =
                (List<TransactionEntity>) this.iTransactionRepository.getByQueryDate(startDate, endDate, customerId);
        return allTransactionEntities.stream()
                .sorted(Comparator.comparing(TransactionEntity::getDate))
                .map(transactionEntity -> {
                    TransactionDTO transactionDTO = (TransactionDTO) MappingDTO.convertToDto(
                        transactionEntity, new TransactionDTO());
                    transactionDTO.setCustomer(customer);
                    return transactionDTO;
                }).toList();
    }

    private TransactionEntity findTransactionEntityById(String id) {
        return this.iTransactionRepository.findById(id).orElse(null);
    }

    private void updateAccountBalance(AccountDTO account, Double balance) {
        account.setInitialBalance(balance);
        this.iAccountService.update(account);
    }

    private void validateAccountBalance(AccountDTO account,  Double balance) {
        if (balance + account.getInitialBalance() < BigInteger.ZERO.intValue()) {
            throw new ValidationException(Errors.INVALID_TRANSACTION.toString());
        }
    }

}