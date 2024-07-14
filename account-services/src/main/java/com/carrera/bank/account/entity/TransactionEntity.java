package com.carrera.bank.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction entity.
 * Represents a transaction in the banking system.
 *
 * @author acarrera
 * @version 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")
public class TransactionEntity {

    @Id
    @UuidGenerator
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "account_id")
    private String accountId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id",
            insertable = false, updatable = false)
    private AccountEntity account;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "transaction_type_code", nullable = false)
    private String transactionTypeCode;

    @Column(name = "transaction_value_code", nullable = false)
    private String transactionValueCode;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;
}