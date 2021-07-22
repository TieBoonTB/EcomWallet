package com.apps.Ecomwallet.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apps.Ecomwallet.model.UserAccount;

@Repository
public interface AccountRepository extends CrudRepository<UserAccount, Long> {

	UserAccount findByEmail(String email);

}