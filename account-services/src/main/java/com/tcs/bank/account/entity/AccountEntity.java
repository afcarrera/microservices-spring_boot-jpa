package com.tcs.bank.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @Column(name = "status", nullable = false)
    private boolean status;

    @OneToMany(mappedBy = "account")
    private Collection<TransactionEntity> transactions;
}