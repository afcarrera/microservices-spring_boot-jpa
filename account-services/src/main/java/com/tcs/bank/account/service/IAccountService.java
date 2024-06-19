package com.tcs.bank.account.service;

import com.tcs.bank.account.dto.impl.AccountDTO;

import java.util.Collection;

/**
 * Interface for Account Service.
 * Defines the operations for managing accounts.
 *
 * @author acarrera
 * @version 1.0
 */
public interface IAccountService {

    /**
     * Creates a new account in the system.
     *
     * @param accountDTO the account data transfer object containing the details of the account to create
     * @return the created account data transfer object
     */
    AccountDTO create(AccountDTO accountDTO);

    /**
     * Updates an existing account in the system.
     *
     * @param accountDTO the account data transfer object containing the updated details of the account
     * @return the updated account data transfer object
     */
    AccountDTO update(AccountDTO accountDTO);

    /**
     * Retrieves all accounts from the system.
     *
     * @return a collection of account data transfer objects
     */
    Collection<AccountDTO> findAll();

    /**
     * Retrieves an account by its unique identifier.
     *
     * @param id the unique identifier of the account to retrieve
     * @return the account data transfer object if found
     */
    AccountDTO findById(String id);

    /**
     * Deletes an account from the system by its unique identifier.
     *
     * @param id the unique identifier of the account to delete
     */
    void delete(String id);
}