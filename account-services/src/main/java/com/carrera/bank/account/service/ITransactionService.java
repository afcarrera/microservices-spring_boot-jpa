package com.carrera.bank.account.service;

import com.carrera.bank.account.dto.impl.TransactionDTO;
import com.carrera.bank.account.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Interface for Transaction Service.
 * Defines the operations for managing transactions.
 *
 * @author acarrera
 * @version 1.0
 */
public interface ITransactionService {

    /**
     * Creates a new transaction in the system.
     *
     * @param transactionDTO the transaction data transfer object containing the details of the transaction to create
     * @return the created transaction data transfer object
     */
    TransactionDTO create(TransactionDTO transactionDTO);

    /**
     * Updates an existing transaction in the system.
     *
     * @param transactionDTO the transaction data transfer object containing the updated details of the transaction
     * @return the updated transaction data transfer object
     */
    TransactionDTO update(TransactionDTO transactionDTO);

    /**
     * Retrieves all transactions from the system.
     *
     * @param pageable Pageable filters
     * @return a collection of transaction data transfer objects
     */
    Page<TransactionDTO> findAll(Pageable pageable);

    /**
     * Retrieves a transaction by its unique identifier.
     *
     * @param id the unique identifier of the transaction to retrieve
     * @return the transaction data transfer object if found
     */
    TransactionDTO findById(String id);

    /**
     * Deletes a transaction from the system by its unique identifier.
     *
     * @param id the unique identifier of the transaction to delete
     */
    void delete(String id);

    /**
     * Retrieves a collection of transactions within the specified date range.
     *
     * @param startDate the start date of the date range
     * @param endDate   the end date of the date range
     * @param customerId   customer id
     * @param pageable Pageable filters
     * @return a collection of transactions that fall within the specified date range
     */
    Page<TransactionDTO> findByDateBetween(
            LocalDateTime startDate, LocalDateTime endDate, String customerId, Pageable pageable);
}