package com.carrera.bank.catalog.services;

import com.carrera.bank.catalog.entities.ValueEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IValueService {
    Flux<ValueEntity> getAllValues();

    Flux<ValueEntity> filter(ValueEntity value);

    Mono<ValueEntity> getValueById(Long id);

    Mono<ValueEntity> createValue(ValueEntity value);

    Mono<ValueEntity> updateValue(Long id, ValueEntity value);

    Mono<Void> deleteValue(Long id);
}
