package com.carrera.bank.catalog.services.impl;

import com.carrera.bank.catalog.entities.TypeEntity;
import com.carrera.bank.catalog.entities.ValueEntity;
import com.carrera.bank.catalog.repository.IValueRepository;
import com.carrera.bank.catalog.services.ITypeService;
import com.carrera.bank.catalog.services.IValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ValueService implements IValueService {
    @Autowired
    private IValueRepository valueRepository;
    @Autowired
    private ITypeService typeService;

    public Flux<ValueEntity> getAllValues() {
        return valueRepository.findAll();
    }

    public Flux<ValueEntity> filter(ValueEntity value) {
        return valueRepository.filter(value.getCode(), value.getTypeCode());
    }
    public Mono<ValueEntity> getValueById(Long id) {
        return valueRepository.findById(id);
    }

    public Mono<ValueEntity> createValue(ValueEntity value) {
        value.setStatus(Boolean.TRUE);
        return valueRepository.save(value);
    }

    public Mono<ValueEntity> updateValue(Long id, ValueEntity value) {
        return valueRepository.findById(id)
                .flatMap(existingValue -> {
                    existingValue.setValue(value.getValue());
                    existingValue.setStatus(value.isStatus());
                    return valueRepository.save(existingValue);
                });
    }

    public Mono<Void> deleteValue(Long id) {
        return valueRepository.deleteById(id);
    }
}
