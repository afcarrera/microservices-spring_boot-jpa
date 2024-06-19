package com.tcs.bank.account.repository;

import com.tcs.bank.account.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;


@Transactional
@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, String> {
    @Query("SELECT t FROM transactions t " +
            "WHERE t.date BETWEEN :startDate AND :endDate " +
            "AND t.account.customerId = :customerId" )
    Collection<TransactionEntity> getByQueryDate(LocalDateTime startDate, LocalDateTime endDate, String customerId);
}
