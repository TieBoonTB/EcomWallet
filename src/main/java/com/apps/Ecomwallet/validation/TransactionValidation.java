package com.apps.Ecomwallet.validation;

import com.apps.Ecomwallet.model.UserAccount;

public class TransactionValidation {
	
	public boolean chkEnoughBalance(UserAccount user, double value) {
		return user.getBalance().getBalanceAmt() <= value;
	}
	
	public boolean chkValidUser(UserAccount user) {
		if(user == null) return false;
			return true;
	}
	
}
