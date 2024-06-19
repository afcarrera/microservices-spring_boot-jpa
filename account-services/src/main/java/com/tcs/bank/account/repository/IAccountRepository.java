package com.tcs.bank.account.repository;

import com.tcs.bank.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface IAccountRepository extends JpaRepository<AccountEntity, String> {
}
