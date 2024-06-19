package com.tcs.bank.customer.repository;

import com.tcs.bank.customer.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public interface IPersonRepository extends JpaRepository<PersonEntity, String> {
}
