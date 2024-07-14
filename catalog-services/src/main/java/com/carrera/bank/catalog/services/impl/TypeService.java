package com.carrera.bank.catalog.services.impl;

import com.carrera.bank.catalog.entities.TypeEntity;
import com.carrera.bank.catalog.repository.ITypeRepository;
import com.carrera.bank.catalog.services.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
public class TypeService implements ITypeService {
    @Autowired
    private ITypeRepository typeRepository;

    public Flux<TypeEntity> getAllTypes() {
        return typeRepository.findAll();
    }

    public Mono<TypeEntity> getTypeById(Long id) {
        return typeRepository.findById(id);
    }

    public Mono<TypeEntity> createType(TypeEntity type) {
        type.setStatus(Boolean.TRUE);
        return typeRepository.save(type);
    }

    public Mono<TypeEntity> updateType(Long id, TypeEntity type) {
        return typeRepository.findById(id)
                .flatMap(existingType -> {
                    existingType.setValue(type.getValue());
                    existingType.setStatus(type.isStatus());
                    return typeRepository.save(existingType);
                });
    }

    public Mono<Void> deleteType(Long id) {
        return typeRepository.deleteById(id);
    }
}
