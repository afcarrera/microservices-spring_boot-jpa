package com.carrera.bank.catalog.repository;

import com.carrera.bank.catalog.entities.ValueEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IValueRepository extends ReactiveCrudRepository<ValueEntity, Long> {
    @Query("SELECT * FROM catalog_values v WHERE " +
            "(:code IS NULL OR v.code = :code) AND " +
            "(:typeCode IS NULL OR v.type_code = :typeCode)")
    Flux<ValueEntity> filter(@Param("code") String code, @Param("typeCode") String typeCode);
}
