package com.carrera.bank.account.repository;

import com.carrera.bank.account.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<AccountEntity, String> {

    /**
     * Retrieves an account by their number.
     *
     * @param accountNumber the number of the account to retrieve
     * @return the account data transfer object if found
     */
    @Query("SELECT a FROM AccountEntity a " +
            "WHERE (:accountNumberUpdate IS NULL OR a.accountNumber <> :accountNumberUpdate) " +
            "AND a.accountNumber = :accountNumber")
    Optional<AccountEntity> findByAccountNumber(String accountNumber, String accountNumberUpdate);

    /**
     * Retrieves all accounts with true status.
     *
     * @param pageable Pageable filters
     * @return the accounts if found
     */
    @Query("SELECT a FROM AccountEntity a WHERE a.status = true")
    Page<AccountEntity> findAllActiveItems(Pageable pageable);
}
