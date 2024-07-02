package com.tcs.bank.customer.repository;

import com.tcs.bank.customer.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional
@Repository
public interface IPersonRepository extends JpaRepository<PersonEntity, String> {

    /**
     * Retrieves a person by their identifier.
     *
     * @param identification the identifier of the person to retrieve
     * @return the person data transfer object if found
     */
    Optional<PersonEntity> findByIdentification(String identification);
}
