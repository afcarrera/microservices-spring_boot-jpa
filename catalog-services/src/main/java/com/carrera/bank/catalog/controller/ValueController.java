package com.carrera.bank.catalog.controller;

import com.carrera.bank.catalog.entities.ValueEntity;
import com.carrera.bank.catalog.services.IValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("v1/values")
public class ValueController {

    @Autowired
    private IValueService valueService;

    @GetMapping
    public Flux<ValueEntity> getAllValues() {
        return valueService.getAllValues();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ValueEntity>> getValueById(@PathVariable Long id) {
        return valueService.getValueById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ValueEntity> createValue(@RequestBody ValueEntity value) {
        return valueService.createValue(value);
    }

    @PostMapping("/filtered")
    public Flux<ValueEntity> filter(@RequestBody ValueEntity value) {
        return valueService.filter(value);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<ValueEntity>> updateValue(@PathVariable Long id, @RequestBody ValueEntity value) {
        return valueService.updateValue(id, value)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteValue(@PathVariable Long id) {
        return valueService.getValueById(id)
                .flatMap(existingValue ->
                        valueService.deleteValue(id)
                                .then(Mono.just(ResponseEntity.noContent().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}