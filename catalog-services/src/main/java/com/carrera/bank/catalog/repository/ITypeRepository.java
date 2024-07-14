package com.carrera.bank.catalog.repository;

import com.carrera.bank.catalog.entities.TypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITypeRepository extends ReactiveCrudRepository<TypeEntity, Long> {
}
