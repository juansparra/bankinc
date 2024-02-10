package com.prueba.bankinc.persistency.repository;

import com.prueba.bankinc.persistency.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
