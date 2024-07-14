package com.carrera.bank.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Account entity.
 * Represents an account in the banking system.
 *
 * @author acarrera
 * @version 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class AccountEntity {

    @Id
    @UuidGenerator
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_type_code", nullable = false)
    private String accountTypeCode;

    @Column(name = "account_value_code", nullable = false)
    private String accountValueCode;

    @Column(name = "initial_balance", nullable = false)
    private BigDecimal initialBalance;

    @Column(name = "status", nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "account")
    private Collection<TransactionEntity> transactions;
}