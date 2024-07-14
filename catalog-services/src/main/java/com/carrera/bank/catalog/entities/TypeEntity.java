package com.carrera.bank.catalog.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.OneToMany;
import java.util.Collection;

/**
 * Type entity.
 *
 * @author acarrera
 * @version 1.0
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("catalog_types")
public class TypeEntity {
    @Id
    @Column("type_id")
    private Long typeId;

    private String code;

    private String value;

    private boolean status;
}