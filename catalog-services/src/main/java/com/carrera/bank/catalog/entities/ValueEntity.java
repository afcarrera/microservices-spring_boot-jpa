package com.carrera.bank.catalog.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
@Table("catalog_values")
public class ValueEntity {
    @Id
    @Column("value_id")
    private Long valueId;

    @Column("type_code")
    private String typeCode;

    private String code;

    private String value;

    private boolean status;
}
