package com.carrera.bank.customer.entity;

import com.carrera.bank.customer.common.StringCryptoConverter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;


/**
 * Person entity.
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
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class PersonEntity {

    @Id
    @UuidGenerator
    @Column(name = "person_id")
    private String personId;

    @Column(name = "name")
    @Convert(converter = StringCryptoConverter.class)
    private String name;

    @Column(name = "gender_type_code")
    private String genderTypeCode;

    @Column(name = "gender_value_code")
    private String genderValueCode;

    @Column(name = "age")
    private short age;

    @Column(name = "identification", unique = true)
    private String identification;

    @Column(name = "address")
    @Convert(converter = StringCryptoConverter.class)
    private String address;

    @Column(name = "phone")
    @Convert(converter = StringCryptoConverter.class)
    private String phone;
}