package com.carrera.bank.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;

/**
 * Customer entity.
 *
 * @author acarrera
 * @version 1.0
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class CustomerEntity extends PersonEntity{

    @UuidGenerator
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private boolean status;
}