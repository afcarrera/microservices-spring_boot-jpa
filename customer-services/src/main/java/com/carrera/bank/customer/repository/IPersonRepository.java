package com.carrera.bank.customer.repository;

import com.carrera.bank.customer.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonRepository extends JpaRepository<PersonEntity, String> {

    /**
     * Retrieves a person by their identifier.
     *
     * @param identification the identifier of the person to retrieve
     * @param identificationUpdate the identifier of the person to retrieve
     * @return the person data transfer object if found
     */
    @Query("SELECT p FROM PersonEntity p " +
            "WHERE (:identificationUpdate IS NULL OR p.identification <> :identificationUpdate) " +
            "AND p.identification = :identification")
    Optional<PersonEntity> findByIdentification(String identification, String identificationUpdate);
}
