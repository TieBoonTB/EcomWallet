package com.apps.Ecomwallet.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apps.Ecomwallet.model.Balance;

@Repository
public interface BalanceRepository extends CrudRepository<Balance, Long> {

	@Modifying
	@Query("UPDATE balance b SET b.balanceAmt = ?2 WHERE b.id = ?1")
	int updateBalance(int id, double balanceAmt);

}