package com.acmebank.accountmanager.repository;

import com.acmebank.accountmanager.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional(Transactional.TxType.MANDATORY)
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNo(String accountNo);

}
