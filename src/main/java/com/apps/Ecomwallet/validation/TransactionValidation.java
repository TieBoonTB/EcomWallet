package com.apps.Ecomwallet.validation;

import org.springframework.stereotype.Component;

import com.apps.Ecomwallet.model.UserAccount;

@Component
public class TransactionValidation {
	
	public boolean chkEnoughBalance(UserAccount user, double value) {
		return user.getBalance().getBalanceAmt() <= value;
	}
	
	public boolean chkValidUser(UserAccount user) {
		if(user == null) return false;
			return true;
	}
	
}
