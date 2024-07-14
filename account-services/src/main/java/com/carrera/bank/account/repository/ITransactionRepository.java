package com.carrera.bank.account.repository;

import com.carrera.bank.account.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ITransactionRepository extends JpaRepository<TransactionEntity, String> {
    @Query("SELECT t FROM transactions t " +
            "WHERE t.date BETWEEN :startDate AND :endDate " +
            "AND t.account.customerId = :customerId" )
    Page<TransactionEntity> getByQueryDate(
            LocalDateTime startDate, LocalDateTime endDate, String customerId, Pageable pageable);
}
