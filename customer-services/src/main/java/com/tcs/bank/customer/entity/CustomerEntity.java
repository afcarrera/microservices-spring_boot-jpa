package com.tcs.bank.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

/**
 * Customer entity.
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
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "person_id")
    private String personId;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id",
            insertable = false, updatable = false)
    private PersonEntity person;
}