package com.carrera.bank.catalog.controller;

import com.carrera.bank.catalog.entities.TypeEntity;
import com.carrera.bank.catalog.services.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/types")
public class TypeController {

    @Autowired
    private ITypeService typeService;

    @GetMapping
    public Flux<TypeEntity> getAllTypes() {
        return typeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TypeEntity>> getTypeById(@PathVariable Long id) {
        return typeService.getTypeById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TypeEntity> createType(@RequestBody TypeEntity type) {
        return typeService.createType(type);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<TypeEntity>> updateType(@PathVariable Long id, @RequestBody TypeEntity type) {
        return typeService.updateType(id, type)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteType(@PathVariable Long id) {
        return typeService.getTypeById(id)
                .flatMap(existingValue ->
                        typeService.deleteType(id)
                                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}