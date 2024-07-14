package com.carrera.bank.catalog.services;

import com.carrera.bank.catalog.entities.TypeEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITypeService {
    Flux<TypeEntity> getAllTypes();

    Mono<TypeEntity> getTypeById(Long id);

    Mono<TypeEntity> createType(TypeEntity type);

    Mono<TypeEntity> updateType(Long id, TypeEntity type);

    Mono<Void> deleteType(Long id);
}