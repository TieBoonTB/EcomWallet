package com.apps.Ecomwallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apps.Ecomwallet.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	@Query("SELECT t FROM Transaction t WHERE t.fromEmail = ?1 OR t.toEmail = ?1")
	List<Transaction> getTransHistory(String email);

}