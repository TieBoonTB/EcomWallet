package com.apps.Ecomwallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apps.Ecomwallet.Constants;
import com.apps.Ecomwallet.model.Transaction;
import com.apps.Ecomwallet.model.UserAccount;
import com.apps.Ecomwallet.repository.AccountRepository;
import com.apps.Ecomwallet.repository.BalanceRepository;
import com.apps.Ecomwallet.repository.TransactionRepository;
import com.apps.Ecomwallet.validation.TransactionException;
import com.apps.Ecomwallet.validation.TransactionValidation;

@Service
public class AccountServiceImp implements AccountService {

	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	BalanceRepository balanceRepo;
	
	@Autowired
	TransactionRepository transactionRepo;
	
	@Autowired
	TransactionValidation transactionValidation;
	
	public UserAccount createAccount(UserAccount userAccount) {
		userAccount.newAccount();
		return accountRepo.save(userAccount);
	}

	public UserAccount getAccount(String email) {
		UserAccount userAccount = accountRepo.findByEmail(email);
		return userAccount;
	}
	
	@Transactional(rollbackFor = {TransactionException.class, Exception.class} )
	public String transferCredit(String fromEmail, String toEmail, double credit) throws TransactionException{
		
		UserAccount fromAccount = accountRepo.findByEmail(fromEmail);
		UserAccount toAccount = accountRepo.findByEmail(toEmail);
		
		if(!transactionValidation.chkEnoughBalance(fromAccount, credit)){
			return Constants.INSUFFICIENT_BALANCE;
		}
		
		if(!transactionValidation.chkValidUser(toAccount)){
			return Constants.ERROR_INVALID_TRANSFEREE;
		}
		
		try {
			Transaction trans = new Transaction();
			trans.setFromEmail(fromEmail);
			trans.setToEmail(toEmail);
			trans.setAmount(credit);
			trans.setType(Constants.TYPE_TRANSFER);
			
			transactionRepo.save(trans);			
			balanceRepo.updateBalance(fromAccount.getBalance().getId(), fromAccount.getBalance().getBalanceAmt() - credit);				
			balanceRepo.updateBalance(toAccount.getBalance().getId(), toAccount.getBalance().getBalanceAmt() + credit);
		
		}catch (Exception e) {
			return Constants.FAIL;
		}
		
		return Constants.JSON_SUCCESS;
	}
	
	public List<Transaction> getTransHistory(String email) {
		return transactionRepo.getTransHistory(email);
	}

}


