package com.apps.Ecomwallet.service;

import java.util.List;

import com.apps.Ecomwallet.model.Transaction;
import com.apps.Ecomwallet.model.UserAccount;

public interface AccountService {
	
	public UserAccount createAccount(UserAccount userAccount);
	public UserAccount getAccount(String email);
	public List<Transaction> getTransHistory(String email);
}
