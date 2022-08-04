package com.acmebank.accountmanager.repository;

import com.acmebank.accountmanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional(Transactional.TxType.MANDATORY)
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
