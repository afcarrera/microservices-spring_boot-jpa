package com.tcs.bank.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


/**
 * Person entity.
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
@Table(name = "persons")
public class PersonEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "person_id")
    private String personId;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private int age;

    @Column(name = "identification")
    private String identification;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @OneToOne(mappedBy = "person")
    private CustomerEntity customer;
}